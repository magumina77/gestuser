package it.cineca.GestUser.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;


@Configuration
public class OpenApiConfig
{
    //http://localhost:9091/swagger-ui.html

    @Bean
    public OpenAPI customOpenAPI()
    {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("GESTUSER WEB SERVICE API")
                        .description("Spring Boot REST API per la gestione degli utenti")
                        .termsOfService("terms")
                        .contact(new Contact().email("matteo.gumina@gmail.com").name("Matteo Gumina").url(""))
                        .license(new License().name("Apache License Version 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                        .version("1.0")
                );
    }
}
