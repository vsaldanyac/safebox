package com.securitish.safebox.com.service.impl;

import com.securitish.safebox.com.dto.SafeboxDTO;
import com.securitish.safebox.com.dto.mapper.SafeboxMapper;
import com.securitish.safebox.com.exception.AuthNotMatchException;
import com.securitish.safebox.com.exception.SafeboxNotFoundException;
import com.securitish.safebox.com.repository.SafeboxRepository;
import com.securitish.safebox.com.repository.dao.SafeboxDAO;
import com.securitish.safebox.com.service.SafeboxService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author VSaldanya
 */
@Service
public class SafeboxServiceImpl implements SafeboxService {

  @Autowired
  SafeboxMapper safeboxMapper;

  @Autowired
  SafeboxRepository safeboxRepository;

  /**
   * Create a new Safebox on repository
   *
   * @param safeboxDTO Safebox info (username and password)
   * @return the id of the created safebox
   */
  @Override
  public SafeboxDTO createSafebox(SafeboxDTO safeboxDTO) {
    SafeboxDAO safeboxDAO = putSafebox(safeboxMapper.mapDAO(safeboxDTO));
    return safeboxMapper.mapDAO(safeboxDAO);
  }

  /**
   * Put items on a sandbox
   *
   * @param safeboxId given safebox id
   * @param items items to be added to the safebox
   */
  @Override
  public void putItemsOnSafebox(String safeboxId, List<String> items) {
    Optional<SafeboxDAO> safebox = getSafebox(safeboxId);
    if (safebox.isPresent()) {
      safebox.get().getItems().addAll(items);
      putSafebox(safebox.get());
    } else {
      throw new SafeboxNotFoundException();
    }
  }

  /**
   * Get the items from the safebox
   *
   * @param safeboxId given safebox id
   * @return the list of items on the given safebox id
   */
  @Override
  public List<String> getItemsFromSafebox(String safeboxId) {
    Optional<SafeboxDAO> safebox = getSafebox(safeboxId);
    if (safebox.isPresent()) {
      return new ArrayList<>(safebox.get().getItems());
    } else {
      throw new SafeboxNotFoundException();
    }
  }

  /**
   * Validate the user and password, stored in mdH5Hex.
   * I could use Basic Auth, but when I though I've implemented this auth manually
   *
   * @param safeboxId given safeboxId
   * @param name name of the owner of the given safeboxID
   * @param pwd password without encoding of the given safeboxId
   */
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

  /**
   * get Safebox from repository
   *
   * @param safeboxId given safeboxId
   * @return the stored Safebox
   */
  public Optional<SafeboxDAO> getSafebox(String safeboxId) {
    return safeboxRepository.findById(safeboxId);
  }

  /**
   * out Safebox from repository
   *
   * @param safeboxDAO given safebox
   * @return the stored Safebox
   */
  private SafeboxDAO putSafebox(SafeboxDAO safeboxDAO) {
    return safeboxRepository.save(safeboxDAO);
  }
}
