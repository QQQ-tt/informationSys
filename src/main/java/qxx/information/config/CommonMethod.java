package qxx.information.config;

import com.alibaba.fastjson.JSONArray;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import qxx.information.config.enums.DataEnums;
import qxx.information.config.exception.DataException;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author qtx
 * @since 2022/11/2
 */
@Slf4j
@Component
public class CommonMethod {

    private final ThreadLocal<String> sysUserId = new ThreadLocal<>();

    private final ThreadLocal<String> ip = new ThreadLocal<>();
    private final ThreadLocal<String> token = new ThreadLocal<>();

    @SneakyThrows
    public void failed(HttpServletRequest request, HttpServletResponse response, DataEnums dataEnums) {
        request.setAttribute("jwtException", new DataException(dataEnums));
        request.getRequestDispatcher("/exception/jwtException")
                .forward(request, response);
    }

    /**
     * 获取请求ip
     *
     * @return ip
     */
    public String getIp() {
        return ip.get();
    }

    public void setIp(String ip) {
        this.ip.set(ip);
    }

    public String getToken() {
        return token.get();
    }

    public void setToken(String token) {
        this.token.set(token);
    }

    public String getSysUserId() {
        return sysUserId.get();
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId.set(sysUserId);
    }

    public void clear() {
        log.info("清除线程变量");
        sysUserId.remove();
        ip.remove();
        token.remove();
    }
}
