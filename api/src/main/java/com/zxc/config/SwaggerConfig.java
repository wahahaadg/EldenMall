package com.zxc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author wahaha
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 用于读取配置文件 application.properties 中 swagger 属性是否开启
     */

    @Bean
    public Docket docket() {
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        apiInfoBuilder.title("商城说明文档")
                .description("说明啦")
                .version("2.0.1")
                .contact(new Contact("zxc","https://github.com/wahahaadg","2587595982@qq.com"));
        ApiInfo apiInfo = apiInfoBuilder.build();



        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo)
                // 是否开启swagger
                .select()
                // 过滤条件，扫描指定路径下的文件
                .apis(RequestHandlerSelectors.basePackage("com.zxc.controller"))
                // 指定路径处理，PathSelectors.any()代表不过滤任何路径
                .paths(PathSelectors.any())
                .build();
    }
}


