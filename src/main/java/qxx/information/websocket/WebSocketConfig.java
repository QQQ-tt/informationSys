package qxx.information.websocket;

import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.util.Map;

/**
 * @author qtx
 * @since 2024/6/3 9:36
 */
@Slf4j
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    public static void sendAllMassage(Map<String, Session> hashMap, String message) {
        hashMap.forEach((k, v) -> v.getAsyncRemote()
                .sendText(message));
    }

    public static void sendOneMassage(Map<String, Session> hashMap, String username, String message) {
        Session session = hashMap.get(username);
        if (session != null) {
            session.getAsyncRemote()
                    .sendText(message);
        }
    }

    public static void logError(String message) {
        log.error(message);
    }

    public static void logInfo(Map<String, Session> hashMap) {
        log.info("当前在线人数:{}", hashMap.size());
        WebSocketConfig.sendAllMassage(hashMap,"updateOnlineUser");
    }
}
