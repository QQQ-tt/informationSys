package qxx.information.config.enums;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author qtx
 * @since 2023/2/8 13:32
 */
public enum AuthEnums {

    /**
     * 网关限制，放行地址:注册
     */
    REGISTER("/information/sysUser/createSysUser"),
    /**
     * 网关限制，放行地址:登录
     */
    LOGIN("/information/sysUser/login"),
    /**
     * 网关限制，放行地址:刷新token
     */
    REFRESH_TOKEN("/information/sysUser/flushedToken");


    private final String context;

    AuthEnums(String context) {
        this.context = context;
    }


    public static boolean authPath(String path) {
        return AUTH.contains(path);
    }

    private final static Set<String> AUTH = new HashSet<>();

    static {
        Stream.of(AuthEnums.values()).forEach(e -> AuthEnums.AUTH.add(e.context));
    }

}
