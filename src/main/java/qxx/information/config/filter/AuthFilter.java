package qxx.information.config.filter;

import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
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
@Order(1)
@WebFilter("/*")
public class AuthFilter extends OncePerRequestFilter {

    @Resource
    private CommonMethod commonMethod;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String userId = request.getHeader("id");
        String token = request.getHeader("token");

        if (AuthEnums.authPath(uri)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (userId == null || token == null) {
            commonMethod.failed(response, DataEnums.USER_NOT_LOGIN);
            return;
        }
        boolean equals = Objects.equals(JwtUtils.getBodyFromToken(token), userId);
        if (!equals) {
            commonMethod.failed(response, DataEnums.USER_CODE_FAIL);
            return;
        }
        boolean tokenExpired = JwtUtils.isTokenExpired(token);
        if (tokenExpired) {
            commonMethod.failed(response, DataEnums.TOKEN_LOGIN_EXPIRED);
            return;
        }
        commonMethod.setSysUserId(userId);
        filterChain.doFilter(request, response);
        commonMethod.clear();
    }
}
