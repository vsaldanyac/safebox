package com.securitish.safebox.com.service;

import com.securitish.safebox.com.service.impl.ErrorServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class ErrorServiceShould {

  private final ErrorServiceImpl errorService = new ErrorServiceImpl();

  @Test
  public void shouldCountErrorNumber() {
    errorService.add();
    assertEquals(errorService.get(), 1);
  }

  @Test
  public void shouldResponseAlways() {
    assertEquals(errorService.get(), 0);
  }

  @Test
  public void shouldResetCountErrorNumber() {
    errorService.add();
    errorService.reset();
    assertEquals(errorService.get(), 0);
  }

  @Test
  public void shouldNotResetCountErrorNumberIfNumberMoreThan3() {
    errorService.add();
    errorService.add();
    errorService.add();
    errorService.add();
    errorService.reset();
    assertEquals(errorService.get(), 4);
  }
}
