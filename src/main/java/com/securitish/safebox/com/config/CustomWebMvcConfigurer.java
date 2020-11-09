package com.securitish.safebox.com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CustomWebMvcConfigurer extends WebMvcConfigurerAdapter {

  @Autowired
  private ConditionalRejectionInterceptor conditionalRejectionInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(conditionalRejectionInterceptor);
  }
}