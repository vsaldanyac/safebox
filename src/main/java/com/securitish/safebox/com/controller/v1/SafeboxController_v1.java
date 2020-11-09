package com.securitish.safebox.com.controller.v1;

import com.securitish.safebox.com.controller.beta.SafeboxController;
import com.securitish.safebox.com.service.SafeboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.securitish.safebox.com.util.SecurityUtils.getJWTToken;

/**
 * @author VSaldanya
 */
@RestController
@RequestMapping("v1/safebox")
public class SafeboxController_v1 extends SafeboxController {

  @Autowired
  SafeboxService safeboxService;

  /**
   * Enpoint to get token auth
   *
   * @param id       safebox id
   * @param authName name of the safebox owner
   * @param authPwd  password of the safebox owner
   * @return Auth token
   */
  @GetMapping(value = "{id}/auth", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getSafebox(@PathVariable String id,
                                      @RequestHeader(name = "X-Auth-Name") String authName,
                                      @RequestHeader(name = "X-Auth-Pwd") String authPwd) {
    safeboxService.validateUserForGiveSafebox(id, authName, authPwd);
    String token = getJWTToken(authName);
    return ResponseEntity.ok(new StringResponse(token));
  }

  /**
   * Endpoint to add items to a safebox
   *
   * @param id       safebox id
   * @param safeboxItems Items to be added to the safebox
   * @param authName name of the safebox owner not required (only for extends beta version)
   * @param authPwd  password of the safebox owner (only for extends beta version)
   * @return 200
   */
  @Override
  @PutMapping("/{id}/items")
  public ResponseEntity<?> putItemsToSafebox(@PathVariable String id,
                                               @RequestBody final List<String> safeboxItems,
                                               @RequestHeader(name = "X-Auth-Name", required = false) String authName,
                                               @RequestHeader(name = "X-Auth-Pwd", required = false) String authPwd) {
    safeboxService.putItemsOnSafebox(id, safeboxItems);
    return ResponseEntity.ok().build();
  }

  /**
   * Endpoint to get items from a given sandbox
   *
   * @param id       safebox id
   * @param authName name of the safebox owner (only for extends beta version)
   * @param authPwd  password of the safebox owner (only for extends beta version)
   * @return items inside the safebox
   */
  @Override
  @GetMapping("/{id}/items")
  public ResponseEntity<?> getItemsFromSafebox(@PathVariable String id,
                                               @RequestHeader(name = "X-Auth-Name", required = false) String authName,
                                               @RequestHeader(name = "X-Auth-Pwd", required = false) String authPwd) {
    return ResponseEntity.ok(safeboxService.getItemsFromSafebox(id));
  }

  class StringResponse {

    private String token;

    public StringResponse(String s) {
      this.token = s;
    }

    public String getToken() {
      return token;
    }
  }
}
