package com.example.spring5mvcrest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@EnableSwagger2
@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport {

    public static final Set<String> DEFAULT_PRODCUE_CONSUME = new HashSet<String>(Arrays.asList("application/json", "application/xml"));

    @Bean
    public Docket api(){

            return new Docket(DocumentationType.SWAGGER_2)
                      .select().apis(RequestHandlerSelectors.any())
                      .paths(PathSelectors.any())
                      .build()
                      .pathMapping("/")
                      .produces(DEFAULT_PRODCUE_CONSUME)
                      .consumes(DEFAULT_PRODCUE_CONSUME)
                      .apiInfo(metaData());

    }

    private ApiInfo metaData() {

        Contact contact = new Contact("Test Name", "test.com", "test@email.com");

        return new ApiInfo(
                "Spring Restful Services",
                "Spring Restful Services - MVC",
                "1.0",
                "Terms of Service: ",
                contact,
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>()        );
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
