package com.kq.perimission.dto;

import java.util.List;

/**
 * 根据swagger json 封装对应的实体
 *
 */
public class SwaggerDTO {
    String host;
    String basePath;
    List<List<Object>> paths;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public List<List<Object>> getPaths() {
        return paths;
    }

    public void setPaths(List<List<Object>> paths) {
        this.paths = paths;
    }
}
