package qxx.information.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qxx.information.entity.SysRole;
import qxx.information.pojo.dto.TestDTO;

/**
 * @author qtx
 * @since 2024/3/19
 */
@RestController
@RequestMapping("/bdSysUser")
public class TestController {

    @PostMapping("/pcLogin")
    public SysRole test(@RequestBody TestDTO dto) {
        System.out.println(dto);
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("test");
        return sysRole;
    }
}
