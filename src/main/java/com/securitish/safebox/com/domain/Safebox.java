package com.securitish.safebox.com.domain;

import lombok.Data;

import java.util.List;

/**
 * @author VSaldanya
 * Domain class never used because no logic needed
 */
@Data
public class Safebox {

  String id;
  String name;
  String password;
  List<String> items;

}
