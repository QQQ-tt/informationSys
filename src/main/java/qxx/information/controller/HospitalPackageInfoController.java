package qxx.information.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qxx.information.config.Result;
import qxx.information.pojo.vo.HospitalInfoPackageVO;
import qxx.information.service.HospitalPackageInfoService;

import java.util.List;

/**
 * <p>
 * 医院信息与套餐信息中间表 前端控制器
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@RestController
@RequestMapping("/information/hospitalPackageInfo")
public class HospitalPackageInfoController {

    @Autowired
    private HospitalPackageInfoService hospitalPackageInfoService;

    @GetMapping("/queryByHospitalIdPackageInfo")
    public Result<List<HospitalInfoPackageVO>> queryByHospitalIdPackageInfo(Long hospitalId){
        return Result.success(hospitalPackageInfoService.queryByHospitalIdPackageInfo(hospitalId));
    }

}
