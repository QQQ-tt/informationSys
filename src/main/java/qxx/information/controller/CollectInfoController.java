package qxx.information.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import qxx.information.config.DataEnums;
import qxx.information.config.Result;
import qxx.information.entity.CollectInfo;
import qxx.information.pojo.dto.CollectInfoQueryDTO;
import qxx.information.pojo.dto.CollectInfoRecordQueryDTO;
import qxx.information.pojo.vo.CollectInfoRecordVO;
import qxx.information.pojo.vo.CollectInfoVO;
import qxx.information.service.CollectInfoService;

import java.util.List;

/**
 * <p>
 * 采集信息管理表 前端控制器
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@RestController
@RequestMapping("/information/collectInfo")
public class CollectInfoController {

    @Autowired
    private CollectInfoService collectInfoService;

    @PostMapping("/insertCollectInfo")
    public Result insertCollectInfo(@RequestBody CollectInfo entity){
        Boolean insert = collectInfoService.insertCollectInfo(entity);
        return insert ? Result.success() : Result.failed(DataEnums.FAILED);
    }

    @PostMapping("/queryCollectInfoRecordList")
    public Result queryCollectInfoRecordList(@RequestBody CollectInfoRecordQueryDTO dto){
        List<CollectInfoRecordVO> collectInfoRecordVOS = collectInfoService.queryCollectInfoRecordList(dto);
        return Result.success(collectInfoRecordVOS);
    }

    @GetMapping("/getByIdQueryCollectInfo")
    public Result getByIdQueryCollectInfo(Long id){
        CollectInfoVO byIdQueryCollectInfo = collectInfoService.getByIdQueryCollectInfo(id);
        return Result.success(byIdQueryCollectInfo);
    }


    @PostMapping("/listByCollectInfoPage")
    public Result listByCollectInfoPage(@RequestBody CollectInfoQueryDTO dto){
        IPage<CollectInfoVO> collectInfoVOIPage = collectInfoService.listByCollectInfoPage(dto);
        return Result.success(collectInfoVOIPage);
    }

}
