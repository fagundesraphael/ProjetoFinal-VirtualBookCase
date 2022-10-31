package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api(){

        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
            .build()
            .useDefaultResponseMessages(false)
            .apiInfo(info());
    }

    private ApiInfo info() {

        return new ApiInfoBuilder()
            .title("API de Livros")
            .description("Loja desapega livros")
            .version("1.0.0")
            .contact(new Contact("Raphael", "github.com/fagundesraphael", "fagundesraphael@dev.com"))
            .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
            .build();
    }
    
}
