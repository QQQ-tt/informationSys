package qxx.information.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import qxx.information.config.Result;
import qxx.information.entity.SysUser;
import qxx.information.pojo.dto.LoginDTO;
import qxx.information.pojo.dto.SysUserDTO;
import qxx.information.pojo.dto.SysUserPasswordDTO;
import qxx.information.pojo.vo.LoginVO;
import qxx.information.pojo.vo.OcrVO;
import qxx.information.pojo.vo.SysUserVO;
import qxx.information.service.SysUserService;

import java.io.IOException;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@RestController
@RequestMapping("/information/sysUser")
public class SysUserController {

    private final SysUserService sysUserService;

    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @PostMapping("/listSysUserPage")
    public Result<Page<SysUserVO>> listSysUserPage(@RequestBody SysUserDTO dto) {
        return Result.success(sysUserService.listSysUserPage(dto));
    }

    @PostMapping("/saveOrUpdateSysUser")
    public Result<Boolean> saveOrUpdateSysUser(@RequestBody SysUser dto) {
        return Result.success(sysUserService.saveOrUpdateSysUser(dto));
    }

    @GetMapping("/updateStatusById")
    public Result<Boolean> updateStatusById(@RequestParam Long id, @RequestParam Boolean flag) {
        return Result.success(sysUserService.updateStatusById(id, flag));
    }

    @PostMapping("/updatePasswordById")
    public Result<Boolean> updatePasswordById(@RequestBody SysUserPasswordDTO dto) {
        return Result.success(sysUserService.updatePasswordById(dto));
    }


    @GetMapping("/deleteSysUserById")
    public Result<Boolean> deleteSysUserById(@RequestParam Long id) {
        return Result.success(sysUserService.deleteSysUserById(id));
    }

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO dto) {
        return Result.success(sysUserService.login(dto));
    }

    @GetMapping("/flushedToken")
    public Result<String> flushedToken() {
        return Result.success(sysUserService.flushedToken());
    }

    @PostMapping("/loginUpdatePassword")
    public Result<Boolean> loginUpdatePassword(@RequestBody SysUserPasswordDTO dto) {
        return Result.success(sysUserService.loginUpdatePassword(dto));
    }

    @PostMapping("/createSysUser")
    public Result<Boolean> createSysUser(@RequestBody SysUser dto) {
        return Result.success(sysUserService.createSysUser(dto));
    }

    @PostMapping("/ocr")
    public Result<OcrVO> ocr(MultipartFile file, @RequestParam String accessToken) throws IOException {
        return Result.success(sysUserService.ocr(file, accessToken));
    }
}
