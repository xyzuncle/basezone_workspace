package com.kq.common.ip;

import com.maxmind.geoip2.exception.GeoIp2Exception;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 集成根据IP获取地域的服务
 */
public class IPinfo {
    public static Map getIPtoName(String ip) throws IOException, GeoIp2Exception {
        List<String> fields = new ArrayList<String>();
        fields.add("city_code");
        fields.add("province_code");
        fields.add("city_name");
        fields.add("continent_name");
        fields.add("location");
        fields.add("region_name");

        String filePath = "ip/GeoLite2-City.mmdb";
        IPLookUp lookUp = new IPLookUp(fields, filePath, 1000);

        InetAddress ipAddress = InetAddress.getByName(ip);//地址存在返回:{continent_name=亚洲, city_name=武汉, city_code=420100, region_name=湖北省, location={lon=114.2734, lat=30.5801}, province_code=420000}

        //InetAddress ipAddress = InetAddress.getByName("172.16.0.5");//地址不存在返回:{city_code=000000, province_code=000000}
        Map<String,Object> map = null;

            map = lookUp.retrieveCityGeoData(ipAddress);


        return map;
    }
}
