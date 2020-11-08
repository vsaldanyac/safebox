package com.securitish.safebox.com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SafeboxDTO {

  String id;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  String name;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  String password;

}
