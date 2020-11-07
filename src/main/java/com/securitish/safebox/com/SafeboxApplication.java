package com.securitish.safebox.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class SafeboxApplication {

  public static void main(String[] args) {
    SpringApplication.run(SafeboxApplication.class, args);
  }

}
