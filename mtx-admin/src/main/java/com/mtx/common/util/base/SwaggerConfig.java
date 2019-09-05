package com.mtx.common.util.base;

import com.mtx.common.constant.SystemConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@EnableSwagger2
@Configuration
@Profile(SystemConstant.SYS_MODE_TEST)//只有测试环境才启用这个bean
public class SwaggerConfig {//swagger的配置类

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.mtx")) // 注意修改此处的包名
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("接口列表 v1.0") // 任意，请稍微规范点
				.description("接口列表") // 任意，请稍微规范点
				.termsOfServiceUrl("/swagger-ui.html") // 将“url”换成自己的ip:port
				.contact(new Contact("yudinggood","","")) // 无所谓（这里是作者的别称）
				.version("1.0")
				.build();
	}
	
}
