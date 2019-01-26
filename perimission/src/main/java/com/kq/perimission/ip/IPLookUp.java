
package com.kq.perimission.ip;

import java.io.*;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.maxmind.db.CHMCache;
import com.maxmind.db.InvalidDatabaseException;
import com.maxmind.db.Reader.FileMode;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Continent;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Location;
import com.maxmind.geoip2.record.Postal;
import com.maxmind.geoip2.record.Subdivision;

public class IPLookUp {
	
  private static final String CITY_LITE_DB_TYPE = "GeoLite2-City";
  private static final String COUNTRY_LITE_DB_TYPE = "GeoLite2-Country";
  private static final String ASN_LITE_DB_TYPE = "GeoLite2-ASN";

  private final Set<Fields> desiredFields;
  private final DatabaseReader databaseReader;

  private final Map<Integer, City2> id2city;
  private static final String CSV_NAME = "city.csv";

  public IPLookUp(List<String> fields, String databasePath, int cacheSize) throws IOException {

      InputStream database  =  IPLookUp.class.getClassLoader().getResourceAsStream(databasePath);
      File baseFile = new File(databasePath);
     ;
      BufferedReader br = null;
      try {
            br=new BufferedReader(new InputStreamReader(database));
      }catch (Exception e){

          e.printStackTrace();
      }
    try {
      //this.databaseReader = new DatabaseReader.Builder(database).withCache(new CHMCache(cacheSize)).build();
      this.databaseReader = new DatabaseReader.Builder(database)
      .withCache(new CHMCache(cacheSize))
      .fileMode(FileMode.MEMORY)
      .locales(Collections.singletonList("zh-CN"))
      .build();
    } catch (InvalidDatabaseException e) {
      throw new IllegalArgumentException("The database provided is invalid or corrupted.", e);
    } catch (IOException e) {
      throw new IllegalArgumentException("The database provided was not found in the path", e);
    }


    this.desiredFields = createDesiredFields(fields);

    // 读取城市编码信息配置
    this.id2city = new ConcurrentHashMap<Integer,City2>(1000);


    File csv = new File(baseFile.getParentFile(), CSV_NAME);
    InputStream csvinput = IPLookUp.class.getClassLoader().getResourceAsStream("/ip/city.csv");
    CsvReader csvReader = null;
	try {
		csvReader = new CsvReader(csvinput, ',', Charset.forName("UTF-8"));
		while (csvReader.readRecord()) {
			String[] ss = csvReader.getValues();
			if (ss != null && ss.length == 4) {
				this.id2city.put(Integer.valueOf(ss[3]), new City2(ss[1], ss[2]));
			}
		}
	} catch (FileNotFoundException e) {
		throw new IllegalArgumentException("The city.csv provided was not found in the path", e);
	} catch (IOException e) {
		throw new IllegalArgumentException("The city.csv provided is invalid or corrupted", e);
	} finally {
		if (csvReader != null) {
			csvReader.close();
            csvinput.close();
		}
	}

  }

  private Set<Fields> createDesiredFields(List<String> fields) {
    Set<Fields> desiredFields = EnumSet.noneOf(Fields.class);
    if (fields == null || fields.isEmpty()) {
      switch (databaseReader.getMetadata().getDatabaseType()) {
        case CITY_LITE_DB_TYPE:
          desiredFields = Fields.DEFAULT_CITY_FIELDS;
          break;
        case COUNTRY_LITE_DB_TYPE:
          desiredFields = Fields.DEFAULT_COUNTRY_FIELDS;
          break;
        case ASN_LITE_DB_TYPE:
          desiredFields = Fields.DEFAULT_ASN_LITE_FIELDS;
          break;
      }
    } else {
      for (String fieldName : fields) {
        desiredFields.add(Fields.parseField(fieldName));
      }
    }
    return desiredFields;
  }

  public Map<String,Object> retrieveCityGeoData(InetAddress ipAddress) throws GeoIp2Exception, IOException {
	CityResponse response = null;
	try {
		response = databaseReader.city(ipAddress);
	} catch (AddressNotFoundException ee) {
			ee.printStackTrace();
	}

	Country country = null;
	City city = null;
	Location location = null;
	Continent continent = null;
	Postal postal = null;
	Subdivision subdivision = null;
	City2 cc = null;
    Map<String, Object> geoData = new HashMap<>();
	if (response != null) {
		country = response.getCountry();
		city = response.getCity();
		location = response.getLocation();
		continent = response.getContinent();
		postal = response.getPostal();
		subdivision = response.getMostSpecificSubdivision();
		cc = id2city.get(city.getGeoNameId());
		
	    // if location is empty, there is no point populating geo data
	    // and most likely all other fields are empty as well
	    if (location.getLatitude() == null && location.getLongitude() == null) {
	      return geoData;
	    }
	}
    
    for (Fields desiredField : this.desiredFields) {
      switch (desiredField) {
        case CITY_CODE:
        	if (cc != null && cc.ccode != null){
        		geoData.put(Fields.CITY_CODE.fieldName(), cc.ccode);
        	} else {
        		geoData.put(Fields.CITY_CODE.fieldName(), City2.NO_CHINA_CODE);
        	}
        	break;
        case PROVINCE_CODE:
        	if (cc != null && cc.pcode != null){
        		geoData.put(Fields.PROVINCE_CODE.fieldName(), cc.pcode);
        	} else {
        		geoData.put(Fields.PROVINCE_CODE.fieldName(), City2.NO_CHINA_CODE);
        	}
        	break;
        case CITY_NAME:
        	if (city != null) {
	          String cityName = city.getName();
	          if (cityName != null) {
	            geoData.put(Fields.CITY_NAME.fieldName(), cityName);
	          }
        	}
          break;
        case CONTINENT_CODE:
        	if (continent != null) {
	          String continentCode = continent.getCode();
	          if (continentCode != null) {
	            geoData.put(Fields.CONTINENT_CODE.fieldName(), continentCode);
	          }
        	}
          break;
        case CONTINENT_NAME:
        	if (continent != null) {
	          String continentName = continent.getName();
	          if (continentName != null) {
	            geoData.put(Fields.CONTINENT_NAME.fieldName(), continentName);
	          }
        	}
          break;
        case COUNTRY_NAME:
        	if (country != null) {
	          String countryName = country.getName();
	          if (countryName != null) {
	            geoData.put(Fields.COUNTRY_NAME.fieldName(), countryName);
	          }
        	}
          break;
        case COUNTRY_CODE2:
        	if (country != null) {
	          String countryCode2 = country.getIsoCode();
	          if (countryCode2 != null) {
	            geoData.put(Fields.COUNTRY_CODE2.fieldName(), countryCode2);
	          }
        	}
          break;
        case COUNTRY_CODE3:
        	if (country != null) {
	          String countryCode3 = country.getIsoCode();
	          if (countryCode3 != null) {
	            geoData.put(Fields.COUNTRY_CODE3.fieldName(), countryCode3);
	          }
        	}
          break;
        case IP:
          geoData.put(Fields.IP.fieldName(), ipAddress.getHostAddress());
          break;
        case POSTAL_CODE:
        	if (postal != null) {
	          String postalCode = postal.getCode();
	          if (postalCode != null) {
	            geoData.put(Fields.POSTAL_CODE.fieldName(), postalCode);
	          }
        	}
          break;
        case DMA_CODE:
        	if (location != null) {
	          Integer dmaCode = location.getMetroCode();
	          if (dmaCode != null) {
	            geoData.put(Fields.DMA_CODE.fieldName(), dmaCode);
	          }
        	}
          break;
        case REGION_NAME:
        	if (subdivision != null) {
	          String subdivisionName = subdivision.getName();
	          if (subdivisionName != null) {
	            geoData.put(Fields.REGION_NAME.fieldName(), subdivisionName);
	          }
        	}
          break;
        case REGION_CODE:
        	if (subdivision != null) {
	          String subdivisionCode = subdivision.getIsoCode();
	          if (subdivisionCode != null) {
	            geoData.put(Fields.REGION_CODE.fieldName(), subdivisionCode);
	          }
        	}
          break;
        case TIMEZONE:
        	if (location != null) {
	          String locationTimeZone = location.getTimeZone();
	          if (locationTimeZone != null) {
	            geoData.put(Fields.TIMEZONE.fieldName(), locationTimeZone);
	          }
        	}
          break;
        case LOCATION:
        	if (location != null) {
	          Double latitude = location.getLatitude();
	          Double longitude = location.getLongitude();
	          if (latitude != null && longitude != null) {
	            Map<String, Object> locationObject = new HashMap<>();
	            locationObject.put("lat", latitude);
	            locationObject.put("lon", longitude);
	            geoData.put(Fields.LOCATION.fieldName(), locationObject);
	          }
        	}
          break;
        case LATITUDE:
        	if (location != null) {
	          Double lat = location.getLatitude();
	          if (lat != null) {
	            geoData.put(Fields.LATITUDE.fieldName(), lat);
	          }
        	}
          break;
        case LONGITUDE:
        	if (location != null) {
	          Double lon = location.getLongitude();
	          if (lon != null) {
	            geoData.put(Fields.LONGITUDE.fieldName(), lon);
	          }
        	}
          break;
      }
    }

    return geoData;
  }
  
  public static void main(String[] args) throws Exception {
	  List<String> fields = new ArrayList<String>();
	  fields.add("city_code");
	  fields.add("province_code");
	  fields.add("city_name");
	  fields.add("continent_name");
	  fields.add("location");
	  fields.add("region_name");
	  IPLookUp lookUp = new IPLookUp(fields, "D:/workspace/geoip/src/GeoLite2-City.mmdb", 1000);
	  
	  InetAddress ipAddress = InetAddress.getByName("27.16.192.1");//地址存在返回:{continent_name=亚洲, city_name=武汉, city_code=420100, region_name=湖北省, location={lon=114.2734, lat=30.5801}, province_code=420000}
	  //InetAddress ipAddress = InetAddress.getByName("172.16.0.5");//地址不存在返回:{city_code=000000, province_code=000000}
	  Map<String,Object> map = lookUp.retrieveCityGeoData(ipAddress); 
	  System.out.println(map);
  }
  
}
