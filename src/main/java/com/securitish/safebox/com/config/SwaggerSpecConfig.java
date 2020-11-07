package com.securitish.safebox.com.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerSpecConfig {
  @Primary
  @Bean
  public SwaggerResourcesProvider swaggerResourcesProvider(InMemorySwaggerResourcesProvider defaultResourcesProvider) {
    return () -> {
      SwaggerResource wsResource = new SwaggerResource();
      wsResource.setName("default");
      wsResource.setSwaggerVersion("2.0");
      wsResource.setLocation("/beta.api.swagger.yaml");

      List<SwaggerResource> resources = new ArrayList<>(defaultResourcesProvider.get());
      resources.add(wsResource);
      return resources;
    };
  }
}
