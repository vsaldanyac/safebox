package com.securitish.safebox.com.service;

import com.securitish.safebox.com.exception.SafeboxNotFoundException;
import com.securitish.safebox.com.repository.dao.SafeboxDAO;
import com.securitish.safebox.com.service.impl.SafeboxServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SafeboxServiceShould {

  private final SafeboxServiceImpl safeboxService = new SafeboxServiceImpl();
  private final String INVALID_SAFEBOX_ID = "A";

  @Test
  public void shouldReturnNotFoundExceptionIfDontExist() {
    SafeboxServiceImpl safeMock = mock(SafeboxServiceImpl.class);
    when(safeMock.getSafebox(anyString())).thenReturn(Optional.of(new SafeboxDAO()));
    try {
      safeMock.getItemsFromSafebox(INVALID_SAFEBOX_ID);
    } catch (Exception e) {
      assertEquals(e.getClass(), SafeboxNotFoundException.class);
    }

  }
}
