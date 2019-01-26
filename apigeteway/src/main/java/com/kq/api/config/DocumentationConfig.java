package com.kq.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 说明: 配置swagger文档资源<br>
 */
@Component
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {
    @Override
    public List<SwaggerResource> get() {
        List resources = new ArrayList<>();
        resources.add(swaggerResource("订单服务", "/order/v2/api-docs", "2.0"));
        resources.add(swaggerResource("权限服务", "/pmsn/v2/api-docs", "2.0"));
        resources.add(swaggerResource("认证服务", "/uaa/v2/api-docs", "2.0"));
        resources.add(swaggerResource("分析服务", "/big/kongji/v2/api-docs", "2.0"));
        resources.add(swaggerResource("元数据服务", "/meta/v2/api-docs", "2.0"));
        resources.add(swaggerResource("新闻服务和用户类别服务", "/news/v2/api-docs", "2.0"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
