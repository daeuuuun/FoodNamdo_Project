package org.zerock.foodnamdo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
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
    // 모든 api 요청에 대한 jwt 적용 코드
    @Bean
    public OpenAPI openAPI() {



        // SecuritySecheme명
        String jwtSchemeName = "jwtAuth";
        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional)

        return new OpenAPI()
//                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }


    @Bean
    public GroupedOpenApi restApi() {

        return GroupedOpenApi.builder()
                .pathsToMatch("/api/**")
                .group("REST API")
                .packagesToScan("org.zerock.foodnamdo.controller")
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
