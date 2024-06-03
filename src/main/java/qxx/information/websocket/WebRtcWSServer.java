package qxx.information.websocket;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebRTC + WebSocket
 *
 * @author qtx
 * @since 2024/6/3 9:11
 */

@Slf4j
@Component
@ServerEndpoint(value = "/websocket/webrtc/{username}")
public class WebRtcWSServer {
    /**
     * 连接集合
     */
    private static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

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
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // JSON字符串转 HashMap
            Map<String, Object> hashMap = mapper.readValue(message, new TypeReference<>() {
            });

            // 消息类型
            String type = (String) hashMap.get("type");

            // to user
            String toUser = (String) hashMap.get("toUser");
            Session toUserSession = sessionMap.get(toUser);

            // msg
            String msg = (String) hashMap.get("msg");

            // sdp
            String sdp = (String) hashMap.get("sdp");

            // ice
            Map<String, Object> iceCandidate = (Map<String, Object>) hashMap.get("iceCandidate");

            Map<String, Object> map = new HashMap<>();
            map.put("type", type);

            // 呼叫的用户不在线
            if (toUserSession == null) {
                map.put("type", "call_back");
                map.put("fromUser", "系统消息");
                map.put("msg", "Sorry，呼叫的用户不在线！");
                WebSocketConfig.sendOneMassage(sessionMap, username, mapper.writeValueAsString(map));
                return;
            }

            switch (type) {
                case "hangup":
                    map.put("fromUser", username);
                    map.put("msg", "对方挂断！");
                    break;
                case "call_start":
                    map.put("fromUser", username);
                    map.put("msg", "1");
                    break;
                case "call_back":
                    map.put("fromUser", toUser);
                    map.put("msg", msg);
                    break;
                case "offer", "answer":
                    map.put("fromUser", toUser);
                    map.put("sdp", sdp);
                    break;
                case "_ice":
                    map.put("fromUser", toUser);
                    map.put("iceCandidate", iceCandidate);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown message type: " + type);
            }
            WebSocketConfig.sendOneMassage(sessionMap, toUser, mapper.writeValueAsString(map));
        } catch (Exception e) {
            WebSocketConfig.logError("onMessage" + e.getMessage());
        }
    }
}
