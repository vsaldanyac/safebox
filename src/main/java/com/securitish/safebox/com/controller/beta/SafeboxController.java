package com.securitish.safebox.com.controller.beta;

import com.securitish.safebox.com.dto.SafeboxDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
  @RequestMapping("/safebox")
public class SafeboxController {

  @GetMapping("/status")
  public ResponseEntity<?> testEndpoint() {

    return ResponseEntity.ok("Service is up!");
  }

  @PostMapping
  public ResponseEntity<?> postNewSafeBox(@RequestBody final SafeboxDTO safebox) {

    return ResponseEntity.ok(safebox);
  }
}
