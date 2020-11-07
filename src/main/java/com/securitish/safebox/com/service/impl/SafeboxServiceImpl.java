package com.securitish.safebox.com.service.impl;

import com.securitish.safebox.com.dto.mapper.SafeboxMapper;
import com.securitish.safebox.com.dto.SafeboxDTO;
import com.securitish.safebox.com.service.SafeboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SafeboxServiceImpl implements SafeboxService {

  @Autowired
  SafeboxMapper safeboxMapper;

  @Override
  public SafeboxDTO createSafebox(SafeboxDTO safeboxDTO) {
    SafeboxDTO safe = new SafeboxDTO();
    safe.setId("TESTING OK");
    return safeboxMapper.map(safeboxMapper.map(safe));
  }

  @Override
  public String putItemOnSafebox(String safeboxId, String item) {
    return null;
  }

  @Override
  public List<String> getItemsFromSafebox(String safeboxId) {
    return null;
  }
}
