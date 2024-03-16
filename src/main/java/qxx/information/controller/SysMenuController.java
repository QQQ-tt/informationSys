package qxx.information.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qxx.information.config.Result;
import qxx.information.pojo.vo.SysMenuVO;
import qxx.information.service.SysMenuService;

import java.util.List;

/**
 * <p>
 * 菜单信息表 前端控制器
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@RestController
@RequestMapping("/information/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/queryMenuInfo")
    public Result queryMenuInfo(){
        List<SysMenuVO> sysMenuVOS = sysMenuService.queryMenuInfo();
        return Result.success(sysMenuVOS);
    }

}
