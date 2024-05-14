package qxx.information.config.filter;

import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import qxx.information.config.CommonMethod;
import qxx.information.config.enums.AuthEnums;
import qxx.information.config.enums.DataEnums;
import qxx.information.utils.JwtUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * @author qtx
 * @since 2024/3/14
 */

@Slf4j
@WebFilter("/*")
public class AuthFilter extends OncePerRequestFilter {

    @Resource
    private CommonMethod commonMethod;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("请求路径：{}", request.getRequestURI());
        log.info("请求ip:{}", request.getRemoteAddr());
        String uri = request.getRequestURI();
        String userId = request.getHeader("user");
        String token = request.getHeader("Authorization");
        log.info("userId:{}",userId);
        log.info("token:{}",token);
        if (AuthEnums.authPath(uri)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (userId == null || token == null) {
            commonMethod.failed(request, response, DataEnums.USER_NOT_LOGIN);
            return;
        }
        boolean tokenExpired = JwtUtils.isTokenExpired(token);
        if (tokenExpired) {
            commonMethod.failed(request, response, DataEnums.TOKEN_LOGIN_EXPIRED);
            return;
        }
        boolean equals = Objects.equals(JwtUtils.getBodyFromToken(token), userId);
        if (!equals) {
            commonMethod.failed(request, response, DataEnums.USER_NOT_LOGIN);
            return;
        }
        commonMethod.setSysUserId(userId);
        commonMethod.setToken(token);
        filterChain.doFilter(request, response);
        commonMethod.clear();
    }
}
