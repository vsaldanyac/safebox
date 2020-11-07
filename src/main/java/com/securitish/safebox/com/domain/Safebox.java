package com.securitish.safebox.com.domain;

import lombok.Data;

import java.util.List;

@Data
public class Safebox {

  String id;
  String name;
  String password;
  List<String> items;

}
