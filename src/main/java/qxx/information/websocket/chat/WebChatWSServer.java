package qxx.information.websocket.chat;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import qxx.information.websocket.WebSocketConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket
 *
 * @author qtx
 * @since 2024/6/3 9:11
 */

@Slf4j
@Component
@ServerEndpoint(value = "/websocket/chat/{username}")
public class WebChatWSServer {
    /**
     * 连接集合
     */
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        try {
            log.info("用户:{}上线", username);
            sessionMap.put(username, session);
            WebSocketConfig.logInfo(sessionMap);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("username") String username) {
        try (Session remove = sessionMap.remove(username)) {
            log.info("用户:{}下线", username);
            WebSocketConfig.logInfo(sessionMap);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        WebSocketConfig.logError("onError:" + error.getMessage());
    }

    /**
     * 服务器接收到客户端消息时调用的方法
     */
    @OnMessage
    public void onMessage(@PathParam("username") String username, String message) {
        log.info("收到消息:{},来自:{}", message, username);
    }
}
