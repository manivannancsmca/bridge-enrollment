package com.bridge.enrollment;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bridgeEnrollmentOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bridge Enrollment API")
                        .description("RESTful API for managing student enrollments in the Bridge program. "
                                + "Provides full CRUD operations for student records.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Bridge Enrollment Team")
                                .email("support@bridge-enrollment.com")
                                .url("https://bridge-enrollment.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development Server")
                ));
    }
}
