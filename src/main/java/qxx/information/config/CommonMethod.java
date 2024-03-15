package qxx.information.config;

import com.alibaba.fastjson.JSONArray;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import qxx.information.config.enums.DataEnums;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author qtx
 * @since 2022/11/2
 */
@Component
public class CommonMethod {

    private final ThreadLocal<String> sysUserId = new ThreadLocal<>();

    private final ThreadLocal<String> ip = new ThreadLocal<>();
    private final ThreadLocal<String> token = new ThreadLocal<>();



    /**
     * 过滤器返回信息
     *
     * @param response  response
     * @param dataEnums 错误信息
     * @throws IOException io失败
     */
    public void failed(HttpServletResponse response, DataEnums dataEnums) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //设置响应状态码
        response.setStatus(HttpServletResponse.SC_OK);
        //输入响应内容
        PrintWriter writer = response.getWriter();
        String s = JSONArray.toJSON(Result.failed(dataEnums)).toString();
        writer.write(s);
        writer.flush();
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

    public void clear(){
        sysUserId.remove();
        ip.remove();
        token.remove();
    }
}
