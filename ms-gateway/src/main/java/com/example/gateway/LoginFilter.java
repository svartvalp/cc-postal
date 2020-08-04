
package com.example.gateway;

import com.auth0.jwt.JWT;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Set;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

public class LoginFilter extends ZuulFilter {
    private final Set<String> tokenStorage;

    public LoginFilter(Set<String> tokenStorage) {
        this.tokenStorage = tokenStorage;
    }

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        HttpServletResponse response = RequestContext.getCurrentContext().getResponse();
        return request.getRequestURI().equals("/login") && request.getMethod().equals("POST")
                && response.getStatus() == 200;
    }


    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletResponse response = context.getResponse();
        String token = "Bearer " + JWT.create()
                .withSubject("user")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .sign(HMAC512("secret".getBytes()));
        response.setHeader("Authorization", token);
        response.setStatus(HttpServletResponse.SC_OK);
        tokenStorage.add(token);
        return null;
    }
}