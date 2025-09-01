package com.omni.cadastro_cnpj.config;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
        		.info(new Info()
        				.title("Cadastro de CNPJs API")
        				.version("1.0")
        				.description("API para gerenciamento de CNPJs e sócios"));
    }
}