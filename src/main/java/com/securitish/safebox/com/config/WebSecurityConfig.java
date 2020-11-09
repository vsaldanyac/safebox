package com.securitish.safebox.com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author VSaldanya
 * Class to configure security with access token
 * Is allowed to post a new safebox
 * Is allowed to get a valid token
 * All beta endpoints are allowed
 * Swagger is allowed
 * h2-console is allowed
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .addFilterAfter(new JWTAuthorizationFilter(getApplicationContext()), UsernamePasswordAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/v1/safebox/").permitAll()
        .antMatchers(HttpMethod.GET, "/v1/safebox/**/auth").permitAll()
        .antMatchers("/beta/**").permitAll()
        .and().authorizeRequests()
        .antMatchers("/swagger-ui.html#").permitAll()
        .antMatchers("/h2-console/**").permitAll()
        .anyRequest().authenticated();

    http.headers().frameOptions().disable();
  }

}
