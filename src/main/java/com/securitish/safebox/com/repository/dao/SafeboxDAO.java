package com.securitish.safebox.com.repository.dao;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Set;

@Data
@Entity(name = "Safebox")
public class SafeboxDAO {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  String id;
  String name;
  String password;
  @ElementCollection
  Set<String> items;

}