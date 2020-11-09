package com.securitish.safebox.com.service.impl;

import com.securitish.safebox.com.service.ErrorService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.securitish.safebox.com.util.SecurityUtils.MAX_ERROR_NUMBER;


@Service
public class ErrorServiceImpl implements ErrorService {

  private final String ERROR = "ERROR";
  private Map<String, Integer> errorNumber = new HashMap<>();

  public synchronized int get() {
    return errorNumber.get(ERROR);
  }

  public synchronized void reset() {
    int errorCounter = 0;
    if (errorNumber.containsKey(ERROR)) {
      errorCounter = errorNumber.get(ERROR);
      if (errorCounter <= MAX_ERROR_NUMBER) {
        errorNumber.clear();
      }
    }
  }

  public synchronized void add() {
    int errorCounter = 0;

    if (errorNumber.containsKey(ERROR)) {
      errorCounter = errorNumber.get(ERROR);
    }

    errorCounter++;

    errorNumber.put(ERROR, errorCounter);
  }
}
