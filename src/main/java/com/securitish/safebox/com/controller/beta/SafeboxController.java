package com.securitish.safebox.com.controller.beta;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
  @RequestMapping("/beta/safebox")
public class SafeboxController {

  @GetMapping("/status")
  public ResponseEntity<?> testEndpoint() {

    return ResponseEntity.ok("Service is up!");
  }
}
