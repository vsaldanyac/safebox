package com.securitish.safebox.com.service;

import java.util.List;

public interface SafeboxService {

  String createSafebox(String username, String password);

  String putItemOnSafebox(String safeboxId, String item);

  List<String> getItemsFromSafebox(String safeboxId);
}
