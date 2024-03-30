package qxx.information.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qxx.information.config.Result;
import qxx.information.entity.SysUserHospital;
import qxx.information.service.SysUserHospitalService;

import java.util.List;

/**
 * <p>
 * 用户医院关系表 前端控制器
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@RestController
@RequestMapping("/information/sysUserHospital")
public class SysUserHospitalController {

    @Autowired
    private SysUserHospitalService sysUserHospitalService;

    @GetMapping("/listSysUserHospital")
    public Result<List<SysUserHospital>> listSysUserHospital(String userId){
        return Result.success(sysUserHospitalService.listByUserIdHospital(userId));
    }

}
