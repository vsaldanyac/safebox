package com.securitish.safebox.com.service;

import com.securitish.safebox.com.dto.SafeboxDTO;

import java.util.List;

public interface SafeboxService {

  SafeboxDTO createSafebox(SafeboxDTO safeboxDTO);

  String putItemOnSafebox(String safeboxId, String item);

  List<String> getItemsFromSafebox(String safeboxId);
}
