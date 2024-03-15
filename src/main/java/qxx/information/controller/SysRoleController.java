package qxx.information.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import qxx.information.config.Result;
import qxx.information.config.enums.DataEnums;
import qxx.information.entity.SysRole;
import qxx.information.pojo.dto.RoleMenuDTO;
import qxx.information.pojo.dto.SysRoleQueryDTO;
import qxx.information.pojo.vo.SysRoleVO;
import qxx.information.service.SysRoleService;

/**
 * <p>
 * 角色管理 前端控制器
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@RestController
@RequestMapping("/information/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("/insertOrUpdateRole")
    public Result insertOrUpdateRole(@RequestBody SysRole entity){
        Boolean aBoolean = sysRoleService.insertOrUpdateRole(entity);
        return aBoolean ? Result.success() : Result.failed(DataEnums.FAILED);
    }

    @PostMapping("/listByRolePage")
    public Result listByRolePage(@RequestBody SysRoleQueryDTO dto){
        IPage<SysRoleVO> sysRoleVOIPage = sysRoleService.listByRolePage(dto);
        return Result.success(sysRoleVOIPage);
    }

    @GetMapping("/deleteRole")
    public Result deleteRole(Long id){
        int deleteRole = sysRoleService.deleteRole(id);
        return deleteRole >0 ? Result.success() : Result.failed("数据被引用无法删除",316);
    }

    @PostMapping("/updateRoleMenu")
    public Result updateRoleMenu(@RequestBody RoleMenuDTO roleMenuDTO){
        Boolean aBoolean = sysRoleService.updateRoleMenu(roleMenuDTO);
        return aBoolean ? Result.success() : Result.failed(DataEnums.FAILED);
    }

}
