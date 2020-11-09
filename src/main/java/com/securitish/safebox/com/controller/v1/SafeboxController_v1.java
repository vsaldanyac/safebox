package com.securitish.safebox.com.controller.v1;

import com.securitish.safebox.com.controller.beta.SafeboxController;
import com.securitish.safebox.com.service.SafeboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.securitish.safebox.com.util.SecurityUtils.getJWTToken;

@RestController
@RequestMapping("v1/safebox")
public class SafeboxController_v1 extends SafeboxController {

  @Autowired
  SafeboxService safeboxService;


  @GetMapping(value = "{id}/auth", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getSafebox(@PathVariable String id,
                                      @RequestHeader(name = "X-Auth-Name") String authName,
                                      @RequestHeader(name = "X-Auth-Pwd") String authPwd) {
    safeboxService.validateUserForGiveSafebox(id, authName, authPwd);
    String token = getJWTToken(authName);
    return ResponseEntity.ok(new StringResponse(token));
  }

  @Override
  @PutMapping("/{id}/items")
  public ResponseEntity<?> putItemsFromSafebox(@PathVariable String id,
                                               @RequestBody final List<String> safeboxItems,
                                               @RequestHeader(name = "X-Auth-Name", required = false) String authName,
                                               @RequestHeader(name = "X-Auth-Pwd", required = false) String authPwd) {
    safeboxService.putItemsOnSafebox(id, safeboxItems);
    return ResponseEntity.ok().build();
  }

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
