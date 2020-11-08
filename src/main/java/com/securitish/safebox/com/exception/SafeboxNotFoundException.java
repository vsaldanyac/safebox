package com.securitish.safebox.com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Safebox not found")
public class SafeboxNotFoundException extends RuntimeException {
}
