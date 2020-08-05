package com.example.gateway.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final String SECRET = "secret";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/login").permitAll()
                .anyRequest().authenticated().
                and().
                addFilter(usernamePasswordAuthFilter()).
                addFilterBefore(basicAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).
                logout().addLogoutHandler(((request, response, authentication) -> getTokenStorage().remove(authentication.getCredentials())));

    }


    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthFilter() {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setFilterProcessesUrl("/login");
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        return filter;
    }

    @Bean
    public BearerTokenAuthenticationFilter basicAuthenticationFilter() {
        BearerTokenAuthenticationFilter filter = new BearerTokenAuthenticationFilter(tokenAuthenticationManager());
        filter.setAuthenticationFailureHandler(((request, response, exception) -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)));
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return authentication -> {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>("{ \"login\": \"" + authentication.getPrincipal() + "\"," +
                    " \"password\": \"" + authentication.getCredentials() + "\" }", headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8081/login", entity, String.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new BadCredentialsException("Incorrect username or password");
            }
            return authentication;
        };
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            response.setStatus(HttpServletResponse.SC_OK);
            String token = JWT.create()
                    .withSubject((String) (authentication.getPrincipal()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                    .sign(HMAC512(SECRET.getBytes()));
            response.setHeader("Authorization", "Bearer " + token);
            getTokenStorage().add(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        };
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandlersHandler() {
        return (request, response, authentication) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            SecurityContextHolder.clearContext();
        };
    }

    @Bean
    public AuthenticationManager tokenAuthenticationManager() {
        return authentication -> {
            String token = (String) authentication.getCredentials();
            if (token != null) {
                try {
                    JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                            .build()
                            .verify(token.replace("Bearer ", ""));
                } catch (JWTVerificationException exception) {
                    throw new BadCredentialsException("Incorrect username or password");
                }
            }
            if (getTokenStorage().contains(token)) {
                authentication.setAuthenticated(true);
                return authentication;
            } else {
                throw new BadCredentialsException("Incorrect username or password");
            }
        };
    }

    @Bean
    public Set<String> getTokenStorage() {
        return new TreeSet<>();
    }
}
