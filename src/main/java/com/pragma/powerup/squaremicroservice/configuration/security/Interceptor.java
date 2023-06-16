package com.pragma.powerup.squaremicroservice.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.*;

@Component
public class Interceptor implements HandlerInterceptor {

    private static final String TOKEN_PREFIX = "Bearer ";

    @Setter
    private static String token;

    @Setter
    private static Long idUser;

    @Value("${my.variables.admin}")
    String admin;

    @Value("${my.variables.owner}")
    String owner;

    @Value("${my.variables.employee}")
    String employee;

    @Value("${my.variables.client}")
    String client;

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

        if (employee.equals(roleUser) && isAllowedEmployeeEndpoint(request.getRequestURI())) {
            return true;
        }

        if (client.equals(roleUser) && isAllowedClientEndpoint(request.getRequestURI())) {
            return true;
        }

        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
        return false;
    }

     private boolean isAllowedOwnerEndpoint(String requestURI) {

        if (requestURI.startsWith("/category/")) {
            return true;
        }

        if (requestURI.startsWith("/dish/createDish")) {
            return true;
        }

        if (requestURI.startsWith("/dish/updateDish/")) {
             return true;
        }

         if (requestURI.startsWith("/dish/enableDisableDish/")) {
             return true;
         }

        if (requestURI.startsWith("/restaurant/employee")) {
             return true;
        }
        return false;
    }

    private boolean isAllowedAdminEndpoint(String requestURI) {
        return requestURI.startsWith("/restaurant/createRestaurant");
    }

    private boolean isAllowedEmployeeEndpoint(String requestURI) {
        if(requestURI.startsWith("/order/orders")) {
            return true;
        }
        if(requestURI.startsWith("/order/assignOrder/")) {
            return true;
        }

        if(requestURI.startsWith("/order/orderReady/")) {
            return true;
        }
        return false;
    }

    private boolean isAllowedClientEndpoint(String requestURI) {

        if(requestURI.startsWith("/restaurant/allRestaurants")){
            return true;
        }

        if(requestURI.startsWith("/dish/dishes")){
            return true;
        }

        if(requestURI.startsWith("/order/createOrder")){
            return true;
        }

        if(requestURI.startsWith("/order/orderDish")){
            return true;
        }
        return false;


    }


    public static String getToken(){
        return token;
    }

    public static Long getIdUser(){
        return idUser;
    }
}
