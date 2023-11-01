package com.revy.kakaopaysec.infra.config;

import com.revy.kakaopaysec.common.constants.CommonConstant;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "카카오페이증권 과제",
                description = "카카오페이증권 과제 API 명세",
                version = "v1"))
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {
                CommonConstant.API_V1 + "/**"
                , CommonConstant.API_V2 + "/**"
        };

        return GroupedOpenApi.builder()
                .group("과제 API")
                .pathsToMatch(paths)
                .build();
    }

}
