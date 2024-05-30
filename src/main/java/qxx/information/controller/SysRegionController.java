package qxx.information.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import qxx.information.config.Result;
import qxx.information.entity.SysRegion;
import qxx.information.entity.SysUser;
import qxx.information.pojo.dto.LoginDTO;
import qxx.information.pojo.dto.SysUserDTO;
import qxx.information.pojo.dto.SysUserPasswordDTO;
import qxx.information.pojo.vo.LoginVO;
import qxx.information.pojo.vo.SysUserVO;
import qxx.information.service.SysRegionService;
import qxx.information.service.SysUserService;

import java.util.List;

/**
 * <p>
 * 地区 前端控制器
 * </p>
 *
 * @author hxc
 * @since 2024-03-16
 */
@RestController
@RequestMapping("/information/sysRegion")
public class SysRegionController {

    @Autowired
    private SysRegionService sysRegionService;

    @GetMapping("/queryByIdRegion")
    public Result<List<SysRegion>> queryByIdRegion(Long id){
        List<SysRegion> sysRegions = sysRegionService.queryByIdRegion(id);
        return Result.success(sysRegions);
    }

}
