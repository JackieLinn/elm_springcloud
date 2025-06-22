package ynu.jackielinn.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import ynu.jackielinn.security.utils.JwtUtils;

import java.util.Arrays;

@Component
public class JwtFilter implements WebFilter {

    private static final String[] WHITELISTED_PATHS = {"/doc", "/auth", "/error"};

    @Resource
    private JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (Arrays.stream(WHITELISTED_PATHS).anyMatch(path -> exchange.getRequest().getURI().getPath().startsWith(path))) {
            return chain.filter(exchange);
        }

        ServerHttpRequest request = exchange.getRequest();
        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (token != null && jwtUtils.resolveJwt(token) != null) {
            DecodedJWT jwt = jwtUtils.resolveJwt(token);
            if (jwt != null) {
                UserDetails user = jwtUtils.toUser(jwt);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                exchange.getAttributes().put("id", jwtUtils.toId(jwt));
                return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
            }
        }
        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
