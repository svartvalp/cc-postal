
package com.example.gateway;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
@Order(1)
public class TokenFilter implements Filter {
    private final String SECRET = "secret";
    private final Set<String> tokenStorage;

    public TokenFilter(Set<String> tokenStorage) {
        this.tokenStorage = tokenStorage;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String header = httpRequest.getHeader("Authorization");
        String requestURI = httpRequest.getRequestURI();
        if (header == null || !header.startsWith("Bearer ")) {
            if (requestURI.equals("/login")) {
                chain.doFilter(request, response);
            } else {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else if (verifyToken(header)) {
            if (requestURI.equals("/login")) {
                httpResponse.setStatus(HttpServletResponse.SC_OK);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }

    private boolean verifyToken(String token) {
        if (token != null) {
            try {
                JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                        .build()
                        .verify(token.replace("Bearer ", ""));
            } catch (JWTVerificationException exception) {
                return false;
            }
        }
        return tokenStorage.contains(token);
    }
}