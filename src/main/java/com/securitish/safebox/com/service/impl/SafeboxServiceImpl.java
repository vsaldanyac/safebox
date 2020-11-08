package com.securitish.safebox.com.service.impl;

import com.securitish.safebox.com.dto.SafeboxDTO;
import com.securitish.safebox.com.dto.mapper.SafeboxMapper;
import com.securitish.safebox.com.exception.AuthNotMatchException;
import com.securitish.safebox.com.exception.SafeboxNotFoundException;
import com.securitish.safebox.com.repository.dao.SafeboxDAO;
import com.securitish.safebox.com.repository.dao.SafeboxRepository;
import com.securitish.safebox.com.service.SafeboxService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SafeboxServiceImpl implements SafeboxService {

  @Autowired
  SafeboxMapper safeboxMapper;

  @Autowired
  SafeboxRepository safeboxRepository;

  @Override
  public SafeboxDTO createSafebox(SafeboxDTO safeboxDTO) {
    SafeboxDAO safeboxDAO = putSafebox(safeboxMapper.mapDAO(safeboxDTO));
    return safeboxMapper.mapDAO(safeboxDAO);
  }

  @Override
  public void putItemsOnSafebox(String safeboxId, List<String> items) {
    Optional<SafeboxDAO> safebox = getSafebox(safeboxId);
    if (safebox.isPresent()) {
      safebox.get().getItems().addAll(items);
      putSafebox(safebox.get());
    }
  }

  @Override
  public List<String> getItemsFromSafebox(String safeboxId) {
    Optional<SafeboxDAO> safebox = getSafebox(safeboxId);
    if (safebox.isPresent()) {
      return new ArrayList<>(safebox.get().getItems());
    } else {
      throw new SafeboxNotFoundException();
    }
  }

  @Override
  public void validateUserForGiveSafebox(String safeboxId, String name, String pwd) {
    Optional<SafeboxDAO> safebox = getSafebox(safeboxId);
    if (safebox.isPresent()) {
      if (!safebox.get().getName().equals(name) || !safebox.get().getPassword().equals(DigestUtils.md5Hex(pwd))) {
        throw new AuthNotMatchException();
      }
    } else {
      throw new SafeboxNotFoundException();
    }
  }


  private Optional<SafeboxDAO> getSafebox(String safeboxId) {
    return safeboxRepository.findById(safeboxId);
  }

  private SafeboxDAO putSafebox(SafeboxDAO safeboxDAO) {
    return safeboxRepository.save(safeboxDAO);
  }
}
