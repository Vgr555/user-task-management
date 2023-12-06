package com.projectvgr.taskmanagement.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;

import java.io.IOException;
import java.util.Base64;

public class HeaderFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        System.out.println("Filter");
        System.out.println("User id : "+httpRequest.getHeader("userId"));
        String authorizationHeader = httpRequest.getHeader("Authorization");

        // Check if the Authorization header is present and starts with "Basic "
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            // Extract the Base64-encoded credentials (excluding "Basic ")
            String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();

            // Decode the Base64 string
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            System.out.println("credentials : "+credentials);
            // Split the credentials into username and password
            String[] userPass = credentials.split(":");
            String username = userPass[0];
            String password = userPass[1];

            // Log the extracted username and password
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
        }
        filterChain.doFilter(servletRequest, servletResponse);
        return;
        // checking for valid X_AUTH_TOKEN
//        String userId = httpRequest.getHeader("userId");
//        if (userId == null) {
//            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }

    }
    //extract header from servletRequest, decode header, using userRepository get userEntity by sending userId extracted from header, compare userName and password
    //stored in db with that of in header

    //curl --location 'http://localhost:8082/task/2/newTask' \
    //--header 'Content-Type: application/json' \
    //--header 'Authorization: Basic Og==' \
    //--data '{
    //    "title":"Task 3",
    //    "description":"Task 2 of User 1",
    //    "deadline":"2023-12-18"
    //}'
}
