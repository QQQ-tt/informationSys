package qxx.information.websocket.chat;

import jakarta.websocket.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qxx.information.config.Result;
import qxx.information.websocket.WebSocketConfig;

import java.util.Map;
import java.util.Set;

/**
 * @author qtx
 * @since 2024/6/3 17:13
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @GetMapping("/getOnlineUser")
    public Result<Set<String>> getOnlineUser() {
        Map<String, Session> map = WebChatWSServer.sessionMap;
        return Result.success(map.keySet());
    }

    @GetMapping("/sendOneMassage")
    public void sendOneMassage(@RequestParam String username,@RequestParam String message) {
        WebSocketConfig.sendOneMassage(WebChatWSServer.sessionMap,username, message);
    }
}
