package com.service.user.user_reactive_service.infrastructure.filter;

import com.service.user.user_reactive_service.service.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Optional;

public class JwtAuthenticationFilter implements WebFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String jwtAccessToken = this.extractJwtTokenFromRequestHeader(exchange);
        if (jwtAccessToken == null) {
            return chain.filter(exchange); // if the token is null, then there is no reason to validate it
        }
        return this.isValidJwtToken(jwtAccessToken)
                .flatMap(isValid -> isValid ? this.authenticateAndContinue(jwtAccessToken, exchange, chain) : this.handleInvalidToken(exchange))
                .then();
    }

    private String extractJwtTokenFromRequestHeader(ServerWebExchange exchange) {
        Optional<String> jwtAccessTokenOptional = Optional.ofNullable(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
        if (jwtAccessTokenOptional.isPresent()) {
            String jwtAccessToken = jwtAccessTokenOptional.get();
            if (StringUtils.hasText(jwtAccessToken) && jwtAccessToken.startsWith("Bearer ")) {
                return jwtAccessToken.substring(7).trim(); // trimming "Bearer " from the access token
            }
        }
        return null;
    }

    private Mono<Boolean> isValidJwtToken(String jwtToken) {
        return jwtService.validateJwtToken(jwtToken);
    }

    /**
     * Here the subject is the userId (UUID), which will be also used for method level security as principal name.
     * */
    private Mono<Void> authenticateAndContinue(String jwtAccessToken, ServerWebExchange exchange, WebFilterChain chain) {
        return Mono.just(jwtService.extractTokenSubject(jwtAccessToken))
                .flatMap(subject -> {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(subject, null, Collections.emptyList());
                    return chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                });
    }

    private Mono<Void> handleInvalidToken(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
