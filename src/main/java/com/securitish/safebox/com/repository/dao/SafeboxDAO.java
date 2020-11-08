package com.securitish.safebox.com.repository.dao;

import com.securitish.safebox.com.util.AttributeEncryptor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
  @Convert(converter = AttributeEncryptor.class)
  Set<String> items;

}
