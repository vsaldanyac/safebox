package com.securitish.safebox.com.controller.beta;

import com.securitish.safebox.com.dto.SafeboxDTO;
import com.securitish.safebox.com.service.SafeboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author VSaldanya
 */
@RestController
@RequestMapping("/beta/safebox")
public class SafeboxController {

  @Autowired
  SafeboxService safeboxService;

  /**
   * Endpoint only to test the controller
   *
   * @return Service is up!
   */
  @GetMapping("/status")
  public ResponseEntity<?> testEndpoint() {

    return ResponseEntity.ok("Service is up!");
  }

  /**
   * Endpoint to create a new safebox
   *
   * @param safebox Safebox info (username and password)
   * @return the id of the created safebox
   */
  @PostMapping
  public ResponseEntity<?> postNewSafeBox(@RequestBody final SafeboxDTO safebox) {
    SafeboxDTO safeboxRet = safeboxService.createSafebox(safebox);
    return ResponseEntity.ok(safeboxRet);
  }

  /**
   * Endpoint to add items to a safebox
   *
   * @param id       safebox id
   * @param safeboxItems Items to be added to the safebox
   * @param authName name of the safebox owner not required
   * @param authPwd  password of the safebox owner
   * @return 200
   */
  @PutMapping("/{id}/items")
  public ResponseEntity<?> putItemsToSafebox(@PathVariable String id,
                                               @RequestBody final List<String> safeboxItems,
                                               @RequestHeader(name = "X-Auth-Name") String authName,
                                               @RequestHeader(name = "X-Auth-Pwd") String authPwd) {
    safeboxService.validateUserForGiveSafebox(id, authName, authPwd);
    safeboxService.putItemsOnSafebox(id, safeboxItems);
    return ResponseEntity.ok().build();
  }

  /**
   * Endpoint to get items from a given sandbox
   *
   * @param id       safebox id
   * @param authName name of the safebox owner
   * @param authPwd  password of the safebox owner
   * @return items inside the safebox
   */
  @GetMapping("/{id}/items")
  public ResponseEntity<?> getItemsFromSafebox(@PathVariable String id,
                                               @RequestHeader(name = "X-Auth-Name") String authName,
                                               @RequestHeader(name = "X-Auth-Pwd") String authPwd) {
    safeboxService.validateUserForGiveSafebox(id, authName, authPwd);
    return ResponseEntity.ok(safeboxService.getItemsFromSafebox(id));
  }
}
