package com.securitish.safebox.com;

import com.securitish.safebox.com.config.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
public class SafeboxApplication {

  public static void main(String[] args) {
    SpringApplication.run(SafeboxApplication.class, args);
  }

  @EnableWebSecurity
  @Configuration
  class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable()
          .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
          .authorizeRequests()
          .antMatchers(HttpMethod.POST, "/v1/safebox/").permitAll()
          .antMatchers(HttpMethod.GET, "/v1/safebox/**").permitAll()
          .and().authorizeRequests()
          .antMatchers("/h2-console/**").permitAll()
          .antMatchers("/console/**").permitAll()
          .anyRequest().authenticated();

      http.csrf().disable();
      http.headers().frameOptions().disable();

    }
  }
}
