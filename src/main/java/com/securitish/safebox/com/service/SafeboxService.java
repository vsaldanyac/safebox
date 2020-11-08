package com.securitish.safebox.com.service;

import com.securitish.safebox.com.dto.SafeboxDTO;

import java.util.List;

public interface SafeboxService {

  SafeboxDTO createSafebox(SafeboxDTO safeboxDTO);

  void putItemsOnSafebox(String safeboxId, List<String> item);

  List<String> getItemsFromSafebox(String safeboxId);

  void validateUserForGiveSafebox(String safeboxId, String name, String pwd);
}
