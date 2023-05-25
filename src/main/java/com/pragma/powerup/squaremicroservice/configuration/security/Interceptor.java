package com.pragma.powerup.squaremicroservice.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.List;

@Component
public class Interceptor implements HandlerInterceptor {

    private static final String TOKEN_PREFIX = "Bearer ";

    private static String token;

    private static Long idUser;

    @Value("${my.variables.admin}")
    String admin;

    @Value("${my.variables.owner}")
    String owner;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        token = request.getHeader("Authorization");

        String jwtToken = token.substring(TOKEN_PREFIX.length());
        List<String> roles;

        DecodedJWT decodedJWT = JWT.decode(jwtToken);
        roles = decodedJWT.getClaim("roles").asList(String.class);
        idUser = decodedJWT.getClaim("user").asLong();
        String roleUser = roles.get(0);
        if (admin.equals(roleUser) && isAllowedAdminEndpoint(request.getRequestURI())) {
            return true;
        }

        if (owner.equals(roleUser) && isAllowedOwnerEndpoint(request.getRequestURI())) {
            return true;
        }

        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
        return false;
    }

    private boolean isAllowedOwnerEndpoint(String requestURI) {

        if (requestURI.startsWith("/category/")) {
            return true;
        }

        if (requestURI.startsWith("/dish/")) {
            return true;
        }
        return false;
    }

    private boolean isAllowedAdminEndpoint(String requestURI) {
        return requestURI.startsWith("/restaurant/");
    }

    public String getToken(){
        return token;
    }

    public Long getIdUser(){
        return idUser;
    }
}
