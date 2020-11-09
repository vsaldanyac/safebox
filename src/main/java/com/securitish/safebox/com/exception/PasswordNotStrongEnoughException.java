package com.securitish.safebox.com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Malformed expected data")
public class PasswordNotStrongEnoughException extends RuntimeException {
}
