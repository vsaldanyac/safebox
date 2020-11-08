package com.securitish.safebox.com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Specified Basic Auth does not match")
public class AuthNotMatchException extends RuntimeException {
}
