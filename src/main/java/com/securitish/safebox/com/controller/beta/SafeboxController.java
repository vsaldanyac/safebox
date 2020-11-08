package com.securitish.safebox.com.controller.beta;

import com.securitish.safebox.com.dto.SafeboxDTO;
import com.securitish.safebox.com.service.SafeboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/safebox")
public class SafeboxController {

  @Autowired
  SafeboxService safeboxService;

  @GetMapping("/status")
  public ResponseEntity<?> testEndpoint() {

    return ResponseEntity.ok("Service is up!");
  }

  @PostMapping
  public ResponseEntity<?> postNewSafeBox(@RequestBody final SafeboxDTO safebox) {
    SafeboxDTO safeboxRet = safeboxService.createSafebox(safebox);
    return ResponseEntity.ok(safeboxRet);
  }

  @PutMapping("/{id}/items")
  public ResponseEntity<?> putItemsFromSafebox(@PathVariable String id, @RequestBody final List<String> safeboxItems) {
    safeboxService.putItemsOnSafebox(id, safeboxItems);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{id}/items")
  public ResponseEntity<?> getItemsFromSafebox(@PathVariable String id) {

    return ResponseEntity.ok(safeboxService.getItemsFromSafebox(id));
  }
}
