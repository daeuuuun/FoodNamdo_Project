package org.zerock.foodnamdo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "FoodNamdo-BackEnd API 명세서",
        description = "리뷰종합 및 식당 추천 시스템",
                version = "v3")
)

@Configuration
public class SwaggerConfig {

//    @Bean
//    public GroupedOpenApi adminApi() {
//        return GroupedOpenApi.builder()
//                .group("springshop-admin")
//                .packagesToScan("org.zerock.foodnamdodemo.controller")
//                .build();
//    }

    @Bean
    public GroupedOpenApi restApi() {

        return GroupedOpenApi.builder()
                .pathsToMatch("/api/**")
                .group("REST API")
                .packagesToScan("org.zerock.foodnamdodemo.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi commonApi() {

        return GroupedOpenApi.builder()
                .pathsToMatch("/**/*")
                .pathsToExclude("/api/**/*")
                .group("COMMON API")
                .build();
    }

}
