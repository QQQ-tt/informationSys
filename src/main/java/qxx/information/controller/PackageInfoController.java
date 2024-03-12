package qxx.information.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qxx.information.config.Result;
import qxx.information.entity.PackageInfo;
import qxx.information.pojo.dto.PackageDTO;
import qxx.information.pojo.vo.PackageVO;
import qxx.information.service.PackageInfoService;

/**
 * <p>
 * 检查套餐表 前端控制器
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@RestController
@RequestMapping("/information/packageInfo")
public class PackageInfoController {

    private final PackageInfoService packageInfoService;

    public PackageInfoController(PackageInfoService packageInfoService) {
        this.packageInfoService = packageInfoService;
    }

    @PostMapping("/listPackageInfoPage")
    public Result<Page<PackageVO>> listPackageInfoPage(@RequestBody PackageDTO dto) {
        return Result.success(packageInfoService.listPackagePage(dto));
    }

    @PostMapping("/saveOrUpdatePackageInfo")
    public Result<Boolean> saveOrUpdatePackageInfo(@RequestBody PackageInfo packageInfo) {
        return Result.success(packageInfoService.saveOrUpdatePackage(packageInfo));
    }

    @GetMapping("/updatePackageStatusById")
    public Result<Boolean> updatePackageStatusById(@RequestParam Long id) {
        return Result.success(packageInfoService.updateStatusById(id));
    }

    @DeleteMapping("/deletePackageById")
    public Result<Boolean> deletePackageById(@RequestParam Long id) {
        return Result.success(packageInfoService.deletePackageById(id));
    }

    @GetMapping("/getPackageById")
    public Result<PackageInfo> getPackageById(@RequestParam Long id) {
        return Result.success(packageInfoService.getPackageById(id));
    }
}
