package com.kq.perimission.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger配置项
 *
 * @description
 * @author yerui
 * @date 2018年4月12日14:55:58
 * @since 1.7
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration extends WebMvcConfigurerAdapter implements EnvironmentAware {
	private String basePackage;
	private String creatName;
	private String serviceName;
	private RelaxedPropertyResolver propertyResolver;
	private String description;
	private String url; //访问的端口
	/**
	 * 这个地方要重新注入一下资源文件，不然不会注入资源的，也没有注入requestHandlerMappping,相当于xml配置的
	 *  <!--swagger资源配置-->
	 *  <mvc:resources location="classpath:/META-INF/resources/" mapping="swagger-ui.html"/>
	 *  <mvc:resources location="classpath:/META-INF/resources/webjars/" mapping="/webjars/**"/>
	 *  不知道为什么，这也是spring boot的一个缺点（菜鸟觉得的）
	 *
	 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[]{"swagger-ui.html"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/"});
        registry.addResourceHandler(new String[]{"/webjars*"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"});
    }


	
	@Bean
	public Docket createRestApi() {

		ParameterBuilder tokenPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<Parameter>();
		tokenPar.name("authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
		pars.add(tokenPar.build());


		return new Docket(DocumentationType.SWAGGER_2)
				.globalOperationParameters(pars)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage(this.basePackage))
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))

				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(this.serviceName + " Restful APIs")
				.description(this.description)
                .termsOfServiceUrl("http://localhost:8900")
				.contact(this.creatName).version("1.0").build();
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment, null);
		this.basePackage = propertyResolver.getProperty("swagger.basepackage");
		this.creatName = propertyResolver.getProperty("swagger.service.developer");
		this.serviceName = propertyResolver.getProperty("swagger.service.name");
		this.url = propertyResolver.getProperty("swagger.service.url");
		this.description = "空基-权限相关服务";
	}

	@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration(null, "list", "alpha", "schema",
				UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
	}
}
