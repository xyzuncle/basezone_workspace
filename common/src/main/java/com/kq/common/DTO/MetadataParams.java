package com.kq.common.DTO;

import java.util.*;

/**
 * @author pg
 *
 */
public class MetadataParams {

	private String area; // 省市县
	private String[] metaId; // 卫星-传感器-精细级别-产品号
	private String[] satellite; // 卫星
	private String[] satelliteSensor; // 卫星_传感器
	private String imagerslx; // 分辨率
	private Integer[] sceneid; // 景ID
	private String prodlevel; // 产品级别
	private String sublevel; // 产品精细级别
	private String[] scenetime; // 采集时间
	private String cloudpsd; // 云盖
	private Integer[] prodid; // 产品号
	private Integer quality; // 数据质量
	private Integer[] orbit; // 轨道号
	private String[] prodtime; // 生产时间
	private String[] geom; // 坐标,圆 表示为["x","y","r"], 点线面表示为 ["polygon((.....))"]
	private String[] station; // 接收站
	private String[] path; // path
	private String[] row; // row
	private Boolean isRelease; // 客观发布状态
	private String ddsFlag; // 主观发布状态
	private String isproduced; // 是否发布
	private String areaname; // 行政区划名称

	private String imagingmode; // 成像模式(GF3)
	private String direction; // 轨道模式(GF3)

	private String instrumentmode; // 工作模式(GF5)

	private Integer page; // 页数
	private Integer size; // 页长

	private String[] asc; // 升序
	private String[] desc; // 降序

	private String userId;
	private String userType;

	// getter
	public String getArea() {
		return area;
	}

	public String[] getSatellite() {
		return satellite;
	}

	public String getImagerslx() {
		return imagerslx;
	}

	public Integer[] getSceneid() {
		return sceneid;
	}

	public String getProdlevel() {
		return prodlevel;
	}

	public String[] getScenetime() {
		return scenetime;
	}

	public String getCloudpsd() {
		return cloudpsd;
	}

	public Integer[] getProdid() {
		return prodid;
	}

	public Integer getQuality() {
		return quality;
	}

	public Integer[] getOrbit() {
		return orbit;
	}

	public String[] getProdtime() {
		return prodtime;
	}

	public String[] getGeom() {
		return geom;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setSatellite(String[] satellite) {
		this.satellite = satellite;
	}

	public void setImagerslx(String imagerslx) {
		this.imagerslx = imagerslx;
	}

	public void setSceneid(Integer[] sceneid) {
		this.sceneid = sceneid;
	}

	public void setProdlevel(String prodlevel) {
		this.prodlevel = prodlevel;
	}

	public void setScenetime(String[] scenetime) {
		this.scenetime = scenetime;
	}

	public void setCloudpsd(String cloudpsd) {
		this.cloudpsd = cloudpsd;
	}

	public void setProdid(Integer[] prodid) {
		this.prodid = prodid;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public void setOrbit(Integer[] orbit) {
		this.orbit = orbit;
	}

	public void setProdtime(String[] prodtime) {
		this.prodtime = prodtime;
	}

	public void setGeom(String[] geom) {
		this.geom = geom;
	}

	public void setStation(String[] station) {
		this.station = station;
	}

	public void setPath(String[] path) {
		this.path = path;
	}

	public void setRow(String[] row) {
		this.row = row;
	}

	public String[] getStation() {
		return station;
	}

	public String[] getPath() {
		return path;
	}

	public String[] getRow() {
		return row;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String[] getAsc() {
		return format(asc);
	}

	public void setAsc(String[] asc) {
		this.asc = asc;
	}

	public String[] getDesc() {
		return format(desc);
	}

	public void setDesc(String[] desc) {
		this.desc = desc;
	}

	public Boolean getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(Boolean isRelease) {
		this.isRelease = isRelease;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String[] getMetaId() {
		return metaId;
	}

	public void setMetaId(String[] metaId) {
		this.metaId = metaId;
	}

	public String getSublevel() {
		return sublevel;
	}

	public void setSublevel(String sublevel) {
		this.sublevel = sublevel;
	}

	public String[] getSatelliteSensor() {
		return satelliteSensor;
	}

	public void setSatelliteSensor(String[] satelliteSensor) {
		this.satelliteSensor = satelliteSensor;
	}

	public String getImagingmode() {
		return imagingmode;
	}

	public void setImagingmode(String imagingmode) {
		this.imagingmode = imagingmode;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getInstrumentmode() {
		return instrumentmode;
	}

	public void setInstrumentmode(String instrumentmode) {
		this.instrumentmode = instrumentmode;
	}

	public String getDdsFlag() {
		return ddsFlag;
	}

	public void setDdsFlag(String ddsFlag) {
		this.ddsFlag = ddsFlag;
	}

	public String getIsproduced() {
		return isproduced;
	}

	public void setIsproduced(String isproduced) {
		this.isproduced = isproduced;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	// 格式化排序参数
	private String[] format(String[] orderStr) {
		if (orderStr != null) {
			List<String> list = new LinkedList<String>();
			int len = orderStr.length;
			for (int i = 0; i < len; i++) {
				String str = orderStr[i];

				if (str.contains("imagegsd")) {
					list.add("image_gsd");
				}
				if (str.contains("orbit")) {
					list.add("orbit_id");
				}
				if (str.contains("productid")) {
					list.add("product_id");
				}
				if (str.contains("productlevel")) {
					list.add("product_level");
				}
				if (str.contains("satelliteid")) {
					list.add("satellite_id");
				}
				if (str.contains("sceneid")) {
					list.add("scene_id");
				}
				if (str.contains("scenetime")) {
					list.add("scene_date");
				}
				if (str.contains("sensorid")) {
					list.add("sensor_id");
				}
			}

			// list 转 String[]
			String[] newOrderStr = list.toArray(new String[list.size()]);
			orderStr = newOrderStr;
		}
		return orderStr;
	}

	@Override
	public String toString() {
		return "MetadataParams{" +
				"area='" + area + '\'' +
				", metaId=" + Arrays.toString(metaId) +
				", satellite=" + Arrays.toString(satellite) +
				", satelliteSensor=" + Arrays.toString(satelliteSensor) +
				", imagerslx='" + imagerslx + '\'' +
				", sceneid=" + Arrays.toString(sceneid) +
				", prodlevel='" + prodlevel + '\'' +
				", sublevel='" + sublevel + '\'' +
				", scenetime=" + Arrays.toString(scenetime) +
				", cloudpsd='" + cloudpsd + '\'' +
				", prodid=" + Arrays.toString(prodid) +
				", quality=" + quality +
				", orbit=" + Arrays.toString(orbit) +
				", prodtime=" + Arrays.toString(prodtime) +
				", geom=" + Arrays.toString(geom) +
				", station=" + Arrays.toString(station) +
				", path=" + Arrays.toString(path) +
				", row=" + Arrays.toString(row) +
				", isRelease=" + isRelease +
				", ddsFlag='" + ddsFlag + '\'' +
				", isproduced='" + isproduced + '\'' +
				", areaname='" + areaname + '\'' +
				", imagingmode='" + imagingmode + '\'' +
				", direction='" + direction + '\'' +
				", instrumentmode='" + instrumentmode + '\'' +
				", page=" + page +
				", size=" + size +
				", asc=" + Arrays.toString(asc) +
				", desc=" + Arrays.toString(desc) +
				", userId='" + userId + '\'' +
				", userType='" + userType + '\'' +
				'}';
	}

	public Map toMap(){
		Map map = new HashMap();
		map.put("q",area);
		map.put("w",metaId);
		map.put("e",satellite);
		map.put("r",satelliteSensor);
		map.put("t",imagerslx);
		map.put("y",sceneid);
		map.put("u",prodlevel);
		map.put("i",sublevel);
		map.put("o",scenetime);
		map.put("p",cloudpsd);
		map.put("a",prodid);
		map.put("s",quality);
		map.put("d",orbit);
		map.put("f",prodtime);
		map.put("g",geom);
		map.put("h",station);
		map.put("j",path);
		map.put("k",row);
		map.put("l",isRelease);
		map.put("z",ddsFlag);
		map.put("x",isproduced);
		map.put("c",areaname);
		map.put("v",imagingmode);
		map.put("b",direction);
		map.put("n",instrumentmode);
		map.put("m",page);
		map.put("si",size);
		map.put("as",format(asc));
		map.put("de",format(desc));
		map.put("us",userId);
		map.put("er",userType);
		return map;
	}
}
