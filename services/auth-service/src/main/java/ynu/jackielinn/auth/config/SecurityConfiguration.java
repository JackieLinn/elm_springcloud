package ynu.jackielinn.auth.config;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ynu.jackielinn.auth.dto.response.AuthorizeVO;
import ynu.jackielinn.auth.entity.Account;
import ynu.jackielinn.auth.entity.AccountRole;
import ynu.jackielinn.auth.entity.Role;
import ynu.jackielinn.auth.service.AccountRoleService;
import ynu.jackielinn.auth.service.AccountService;
import ynu.jackielinn.auth.service.RoleService;
import ynu.jackielinn.common.entity.RestBean;
import ynu.jackielinn.security.utils.JwtUtils;
import ynu.jackielinn.security.filter.JwtAuthorizeFilter;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 安全配置类，用于配置 Spring Security 的核心安全功能
 */
@Configuration
public class SecurityConfiguration {

    @Resource
    JwtUtils jwtUtils;

    @Resource
    JwtAuthorizeFilter jwtAuthorizeFilter;

    @Resource
    AccountService accountService;

    @Resource
    RoleService roleService;

    @Resource
    AccountRoleService accountRoleService;

    /**
     * 配置安全过滤链，定义了接口的权限控制规则、登录、登出逻辑以及会话管理策略
     *
     * @param http HttpSecurity 对象，提供配置 Spring Security 的能力
     * @return SecurityFilterChain 安全过滤链
     * @throws Exception 如果配置出现问题抛出异常
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 定义无需认证的公共路径
        var publicPaths = new String[]{"/doc/**", "/auth/**", "auth-service/api/account/remote/get-user-id","business-service/api/business/merchant/apply","/error"};
        return http
                // 配置接口访问权限
                .authorizeHttpRequests(conf -> conf
                        .requestMatchers(publicPaths).permitAll() // 公共路径无需认证
                        .anyRequest().authenticated() // 其他路径需要认证
                )
                // 配置表单登录
                .formLogin(conf -> conf
                        .loginProcessingUrl("/auth/login") // 登录接口路径
                        .failureHandler(this::onAuthenticationFailure) // 登录失败处理
                        .successHandler(this::onAuthenticationSuccess) // 登录成功处理
                )
                // 配置登出逻辑
                .logout(conf -> conf
                        .logoutUrl("/auth/logout") // 登出接口路径
                        .logoutSuccessHandler(this::onLogoutSuccess) // 登出成功处理
                )
                // 配置异常处理
                .exceptionHandling(conf -> conf
                        .authenticationEntryPoint(this::onUnauthorized)
                        .accessDeniedHandler(this::onAccessDeny)
                )
                // 禁用 CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // 配置无状态会话管理策略
                .sessionManagement(conf -> conf
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 添加 JWT 授权过滤器
                .addFilterBefore(jwtAuthorizeFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * 登录成功后的处理方法
     *
     * @param request        HttpServletRequest 对象
     * @param response       HttpServletResponse 对象
     * @param authentication Authentication 对象，包含用户的认证信息
     * @throws IOException   如果 IO 出现异常
     * @throws ServletException 如果 Servlet 出现异常
     */
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        User user = (User) authentication.getPrincipal();
        Account account = accountService.findAccountByUsernameOrEmail(user.getUsername());
        AccountRole accountRole = accountRoleService.findRIDByUID(account.getUserId());
        Role role = roleService.findRoleByRID(accountRole.getRoleId());
        String token = jwtUtils.createJwt(user, account.getUserId(), account.getUserName());
        AuthorizeVO authorizeVO = account.asViewObject(AuthorizeVO.class, vo -> {
            vo.setId(account.getUserId());
            vo.setExpire(jwtUtils.expireTime());
            vo.setToken(token);
            vo.setRole(role.getRoleName());
            vo.setUsername(account.getUserName());
        });
        response.getWriter().write(RestBean.success(authorizeVO).asJsonString());
    }

    /**
     * 登出成功后的处理方法
     *
     * @param request        HttpServletRequest 对象
     * @param response       HttpServletResponse 对象
     * @param authentication Authentication 对象，包含用户的认证信息
     * @throws IOException   如果 IO 出现异常
     * @throws ServletException 如果 Servlet 出现异常
     */
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        String authorization = request.getHeader("Authorization");
        if (jwtUtils.invalidateJwt(authorization)) {
            writer.write(RestBean.success().asJsonString());
        } else {
            writer.write(RestBean.failure(400, "退出登录失败").asJsonString());
        }
    }

    /**
     * 登录失败后的处理方法
     *
     * @param request   HttpServletRequest 对象
     * @param response  HttpServletResponse 对象
     * @param exception AuthenticationException 异常，包含失败的具体原因
     * @throws IOException 如果 IO 出现异常
     * @throws ServletException 如果 Servlet 出现异常
     */
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(RestBean.unauthorized(exception.getMessage()).asJsonString());
    }

    /**
     * 权限不足时的处理方法
     *
     * @param request   HttpServletRequest 对象
     * @param response  HttpServletResponse 对象
     * @param exception AccessDeniedException 异常，包含权限不足的具体原因
     * @throws IOException 如果 IO 出现异常
     * @throws ServletException 如果 Servlet 出现异常
     */
    public void onAccessDeny(HttpServletRequest request,
                             HttpServletResponse response,
                             AccessDeniedException exception) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(RestBean.forbidden(exception.getMessage()).asJsonString());
    }

    /**
     * 未认证时的处理方法
     *
     * @param request   HttpServletRequest 对象
     * @param response  HttpServletResponse 对象
     * @param exception AuthenticationException 异常，包含未认证的具体原因
     * @throws IOException 如果 IO 出现异常
     */
    public void onUnauthorized(HttpServletRequest request,
                               HttpServletResponse response,
                               AuthenticationException exception) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(RestBean.unauthorized(exception.getMessage()).asJsonString());
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/api/account/remote/get-user-id","/api/business/merchant/apply");  // 忽略这些路径
    }
}
