/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.ejercicio.challange.ejercicio;

import org.springframework.scheduling.annotation.EnableScheduling;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.Components;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication

@EnableAsync

/**
 *
 * @author Informatica
 */
public class ChallangeEjercicio {

    public static void main(String[] args) {
          SpringApplication.run(ChallangeEjercicio.class, args);
        System.out.println("Hello World!");
    }
    
      @Bean
    public OpenAPI customOpenAPI() {
        final String seguridadNombre = "TokenJWT";
        
        return new OpenAPI()
                // 1. Esto le dice a Swagger que agregue el Token a ABSOLUTAMENTE TODOS los endpoints del proyecto
                .addSecurityItem(new SecurityRequirement().addList(seguridadNombre))
                // 2. Esto define que el tipo de seguridad es un Bearer Token (JWT)
                .components(new Components()
                        .addSecuritySchemes(seguridadNombre,
                                new SecurityScheme()
                                        .name(seguridadNombre)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

}
