package qxx.information.controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import qxx.information.config.Result;
import qxx.information.config.enums.DataEnums;
import qxx.information.pojo.dto.HospitalInfoInsertDTO;
import qxx.information.pojo.dto.HospitalInfoQueryDTO;
import qxx.information.pojo.dto.PackageDTO;
import qxx.information.pojo.dto.RoleMenuDTO;
import qxx.information.pojo.vo.HospitalInfoVO;
import qxx.information.pojo.vo.PackageVO;
import qxx.information.service.impl.HospitalInfoServiceImpl;

import java.util.List;

/**
 * <p>
 * 医院信息管理表 前端控制器
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@RestController
@RequestMapping("/information/hospitalInfo")
public class HospitalInfoController {

    @Autowired
    private HospitalInfoServiceImpl hospitalInfoService;

    @PostMapping("/insertHospitalInfo")
    public Result insertHospitalInfo(@RequestBody HospitalInfoInsertDTO dto){
        int insert = hospitalInfoService.insertHospitalInfo(dto);
        return insert > 0 ? Result.success() : Result.failed(DataEnums.FAILED);
    }

    @PostMapping("/updateHospitalInfo")
    public Result updateHospitalInfo(@RequestBody HospitalInfoInsertDTO dto){
        int update = hospitalInfoService.updateHospitalInfo(dto);
        return update > 0 ? Result.success() : Result.failed(DataEnums.FAILED);
    }

    @PostMapping("/listByPage")
    public Result listByPage(@RequestBody HospitalInfoQueryDTO dto){
        return Result.success(hospitalInfoService.listByPage(dto));
    }

    @GetMapping("/deleteHospitalInfo")
    public Result deleteHospitalInfo(Long id){
        int delete = hospitalInfoService.deleteHospitalInfo(id);
        return delete > 0 ? Result.success() : Result.failed("数据被引用无法删除",316);
    }

    @GetMapping("/listAll")
    public Result<List<HospitalInfoVO>> listAll() {
        val dto = new HospitalInfoQueryDTO();
        dto.setPageNum(-1);
        dto.setPageSize(-1);
        return Result.success(hospitalInfoService.listByPage(dto).getRecords());
    }

}
