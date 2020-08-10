package com.example.gateway.filter;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.gateway.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class CurruentUserFilter implements Filter {
    private final Set<String> tokenStorage;
    @Value("${zuul.routes.user.url}")
    private String usersServiceUrl;

    public CurruentUserFilter(Set<String> tokens) {
        this.tokenStorage = tokens;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponseRequest = (HttpServletResponse) response;
        String uri = httpServletRequest.getRequestURI();
        String token = httpServletRequest.getHeader("Authorization");
        String method = httpServletRequest.getMethod();
        if ("/user".equals(uri) && !token.isEmpty() && token.startsWith("Bearer ") && "GET".equals(method)) {
            if (tokenStorage.contains(token.replace("Bearer ", ""))) {
                DecodedJWT jwt = JWT.decode(token.replace("Bearer ", ""));
                String login = jwt.getSubject();
                ResponseEntity<UserDto> responseEntity = new RestTemplate().getForEntity(usersServiceUrl + "/user?login=" + login, UserDto.class);
                ObjectMapper mapper = new ObjectMapper();
                String userJson = mapper.writeValueAsString(responseEntity.getBody());
                httpResponseRequest.getWriter().println(userJson);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
