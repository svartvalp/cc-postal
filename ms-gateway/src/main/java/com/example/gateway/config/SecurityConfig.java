package com.example.gateway.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.gateway.dto.LoginUserDto;
import com.example.gateway.dto.UserDto;
import com.example.gateway.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.gateway.filter.CurruentUserFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final MessageService messageService;
    @Value("${zuul.routes.login.url}")
    private String usersServiceUrl;
    @Value("${server.jwt.secret.key}")
    private String secret;

    public SecurityConfig(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").anonymous()
                .anyRequest().authenticated()
                .and()
                .addFilter(usernamePasswordAuthenticationFilter())
                .addFilterBefore(TokenAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(TokenAuthorizationFilter(), LogoutFilter.class)
                .addFilterAfter(currentUserFilter(), UsernamePasswordAuthenticationFilter.class)
                .logout().addLogoutHandler(((request, response, authentication) -> {
            tokenStorage().remove(authentication.getCredentials());
            response.setStatus(SC_OK);
        })).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authentication -> {
            LoginUserDto userDto = new LoginUserDto();
            userDto.setLogin((String) authentication.getPrincipal());
            userDto.setPassword((String) authentication.getCredentials());
            String userJson;
            try {
                userJson = new ObjectMapper().writeValueAsString(userDto);
            } catch (JsonProcessingException e) {
                throw new BadCredentialsException(messageService.getMessage("invalid.input.authentication"));
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(userJson, headers);
            try {
                ResponseEntity<String> response = new RestTemplate().postForEntity(usersServiceUrl + "/login", entity, String.class);
            } catch (HttpClientErrorException exception) {
                throw new BadCredentialsException(messageService.getMessage("invalid.input.authentication"));
            }
            return authentication;
        });

        filter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.setStatus(SC_OK);
            String token = JWT.create()
                    .withSubject((String) (authentication.getPrincipal()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                    .sign(HMAC512(secret.getBytes()));
            response.setHeader("Authorization", "Bearer " + token);
            tokenStorage().add(token);
        });

        return filter;
    }

    @Bean
    public BearerTokenAuthenticationFilter TokenAuthorizationFilter() {
        BearerTokenAuthenticationFilter filter = new BearerTokenAuthenticationFilter((Authentication authentication) -> {
            String token = (String) authentication.getCredentials();
            if (token != null) {
                try {
                    JWT.require(Algorithm.HMAC512(secret.getBytes()))
                            .build()
                            .verify(token.replace("Bearer ", ""));
                } catch (JWTVerificationException exception) {
                    throw new BadCredentialsException(messageService.getMessage("invalid.input.authentication"));
                }
            }
            if (tokenStorage().contains(token)) {
                authentication.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return authentication;
            } else {
                throw new BadCredentialsException(messageService.getMessage("invalid.input.authentication"));
            }
        });

        filter.setAuthenticationFailureHandler(((request, response, exception) -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)));
        return filter;
    }

    @Bean
    public Set<String> tokenStorage() {
        return new TreeSet<>();
    }

    @Bean
    public Filter currentUserFilter() {
        return new CurruentUserFilter(tokenStorage());
    }
}
