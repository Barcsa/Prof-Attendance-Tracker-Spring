package com.licenta.security.auth.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.licenta.OperationResponse;
import com.licenta.security.exception.JwtExpiredTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class AjaxAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper mapper;

    @Autowired
    public AjaxAwareAuthenticationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        if (exception instanceof BadCredentialsException) {
            mapper.writeValue(response.getWriter(), OperationResponse.of("Invalid email or password"));
        } else if (exception instanceof JwtExpiredTokenException) {
            mapper.writeValue(response.getWriter(), OperationResponse.of("Token has expired"));
        } else if (exception instanceof AuthenticationServiceException) {
            mapper.writeValue(response.getWriter(), OperationResponse.of(exception.getMessage()));
        }

        mapper.writeValue(response.getWriter(), OperationResponse.of("Authentication failed"));
    }
}
