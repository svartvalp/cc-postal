package com.example.gateway;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
@Order(2)
public class LogoutFilter implements Filter {
    private final Set<String> tokenStorage;

    public LogoutFilter(Set<String> tokenStorage) {
        this.tokenStorage = tokenStorage;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String token = httpRequest.getHeader("Authorization");
        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();
        if (requestURI.equals("/logout") && method.equals("POST")) {
            if (token != null && tokenStorage.remove(token)) {
                httpResponse.setStatus(HttpServletResponse.SC_OK);
            } else {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
            return;
        }
        chain.doFilter(request, response);
    }
}
