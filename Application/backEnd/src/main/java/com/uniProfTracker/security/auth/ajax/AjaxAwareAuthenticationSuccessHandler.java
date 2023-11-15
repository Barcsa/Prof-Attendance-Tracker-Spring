package com.licenta.security.auth.ajax;

import com.licenta.model.Role;
import com.licenta.model.User;
import com.licenta.security.model.UserContext;
import com.licenta.security.model.token.JwtToken;
import com.licenta.security.model.token.JwtTokenFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.licenta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/*
 * AjaxAwareAuthenticationSuccessHandler
 */
@Component
public class AjaxAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper mapper;
    private final JwtTokenFactory tokenFactory;

    @Autowired
    private UserService userService;

    @Autowired
    public AjaxAwareAuthenticationSuccessHandler(final ObjectMapper mapper, final JwtTokenFactory tokenFactory) {
        this.mapper = mapper;
        this.tokenFactory = tokenFactory;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserContext userContext = (UserContext) authentication.getPrincipal();


        JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);

        User user = userService.getByEmail(userContext.getEmail()).get();

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", accessToken.getToken());
        tokenMap.put("name", user.getName());
        tokenMap.put("surname", user.getSurname());
        tokenMap.put("email", user.getEmail());
        tokenMap.put("userId", Long.toString(user.getId()));
        tokenMap.put("profesorId", user.getProfesor() != null ? Long.toString(user.getProfesor().getId()) : "");
        tokenMap.put("mobile", user.getMobile());
        tokenMap.put("registeredAt", user.getCreatedDate() != null ? new SimpleDateFormat("hh:mm dd/MM/yyyy").format(user.getCreatedDate()) : "");
        tokenMap.put("role", getHighestRole(userContext.getAuthorities()));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getWriter(), tokenMap);
    }

    private String serializeRoles(List<GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining("#"));
    }

    private String getHighestRole(List<GrantedAuthority> authorities) {
        if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals(Role.ADMIN.authority()))) {
            return Role.ADMIN.authority();
        } else {
            return Role.PROFESOR.authority();
        }
    }
    }


