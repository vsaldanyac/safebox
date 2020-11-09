package com.securitish.safebox.com.config;

import com.securitish.safebox.com.service.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.securitish.safebox.com.util.SecurityUtils.MAX_ERROR_NUMBER;

@Component
public class ConditionalRejectionInterceptor extends HandlerInterceptorAdapter {

  @Autowired
  ErrorService errorService;

  @Override
  public boolean preHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler) throws Exception {
    String requestUri = request.getRequestURI();
    if (shouldReject(requestUri)) {
      response.setStatus(HttpStatus.NOT_FOUND.value());
      return false;
    }
    return super.preHandle(request, response, handler);
  }

  private boolean shouldReject(String requestUri) {
    if(requestUri.contains("beta")){
      return false;
    }
    int errorNumber = errorService.get();

    return errorNumber > MAX_ERROR_NUMBER;
  }
}