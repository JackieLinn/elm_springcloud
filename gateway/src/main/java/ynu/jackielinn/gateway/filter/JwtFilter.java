package ynu.jackielinn.gateway.filter;

import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
import ynu.jackielinn.common.utils.JwtUtils;

import java.util.Arrays;

@Component
@Slf4j
public class JwtFilter implements WebFilter {
    private static final String[] WHITELISTED_PATHS = {"/auth", "/test"};

    @Resource
    private JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("auth--------------------------------------");
        // 跳过 /WHITELISTED_PATHS/** 路径
        if (Arrays.stream(WHITELISTED_PATHS).anyMatch(path -> exchange.getRequest().getURI().getPath().startsWith(path))) {
            return chain.filter(exchange);  // 跳过 JWT 认证，直接进行下一个过滤器
        }

        // 执行 JWT 认证逻辑
        // ...
        log.info("JWT Filter--------------------------------------");
        ServerHttpRequest request = exchange.getRequest();
        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.info("TOKEN: "+token);
        if (token != null && jwtUtils.resolveJwt(token) != null) {
            DecodedJWT jwt = jwtUtils.resolveJwt(token);
            if (jwt != null) {
                log.info(JSON.toJSONString(jwt)+"==========================");
                UserDetails user = jwtUtils.toUser(jwt);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                // 用 exchange 的 attributes 存储 ID
                exchange.getAttributes().put("id", jwtUtils.toId(jwt));
                return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
            }
        }
        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
