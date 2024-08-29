package qxx.information.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qxx.information.entity.SysRole;
import qxx.information.pojo.dto.TestDTO;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qtx
 * @since 2024/3/19
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/pcLogin")
    public SysRole test(@RequestBody TestDTO dto) {
        System.out.println(dto);
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("test");
        return sysRole;
    }

    @PostMapping("/test1")
    public Map<String, Object> test1(@RequestBody Map<String, Object> map) {
        HashMap<String, Object> hashMap = new HashMap<>();
        map.forEach((k, v) -> hashMap.put(k + "_name", "哈哈哈" + v));
        map.putAll(hashMap);
        map.put("date", LocalDate.now());
        map.put("method", "post");
        map.put("date_name", "时间");
        return map;
    }

    @GetMapping("/test1")
    public Map<String, Object> test2(@RequestBody Map<String, Object> map) {
        HashMap<String, Object> hashMap = new HashMap<>();
        map.forEach((k, v) -> hashMap.put(k + "_name", "哈哈哈" + v));
        map.putAll(hashMap);
        map.put("date", LocalDate.now());
        map.put("method", "get");
        map.put("date_name", "时间");
        return map;
    }
}
