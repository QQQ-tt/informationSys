package qxx.information.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import qxx.information.config.CommonMethod;
import qxx.information.config.excel.ExcelTransfer;
import qxx.information.entity.CollectInfo;
import qxx.information.entity.HospitalInfo;
import qxx.information.entity.PackageInfo;
import qxx.information.mapper.CollectInfoMapper;
import qxx.information.mapper.SysUserHospitalMapper;
import qxx.information.pojo.dto.CollectInfoQueryDTO;
import qxx.information.pojo.dto.CollectInfoRecordQueryDTO;
import qxx.information.pojo.vo.CollectInfoRecordVO;
import qxx.information.pojo.vo.CollectInfoVO;
import qxx.information.pojo.vo.CollectStatusInfoVO;
import qxx.information.service.CollectInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import qxx.information.service.HospitalInfoService;

import java.util.List;

/**
 * <p>
 * 采集信息管理表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Service
public class CollectInfoServiceImpl extends ServiceImpl<CollectInfoMapper, CollectInfo> implements CollectInfoService {


    @Autowired
    private CollectInfoMapper collectInfoMapper;

    @Autowired
    private ExcelTransfer<CollectInfoVO> excelTransferByClass;

    @Autowired
    private SysUserHospitalMapper sysUserHospitalMapper;

    @Resource
    private CommonMethod commonMethod;

    @Override
    public Boolean insertCollectInfo(CollectInfo collectInfo) {
        return saveOrUpdate(collectInfo);
    }


    @Override
    public IPage<CollectInfoRecordVO> queryCollectInfoRecordList(CollectInfoRecordQueryDTO dto) {
        Page<CollectInfoRecordVO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        dto.setUserId(commonMethod.getSysUserId());
        return collectInfoMapper.queryCollectInfoRecordList(page,dto);
    }

    @Override
    public CollectInfoVO getByIdQueryCollectInfo(Long id) {
        return collectInfoMapper.getByIdQueryCollectInfo(id);
    }

    @Override
    public IPage<CollectInfoVO> listByCollectInfoPage(CollectInfoQueryDTO dto, HttpServletRequest request) {
        Page<CollectInfoVO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        String useId = request.getHeader("user");
        List<Long> longs = sysUserHospitalMapper.listByUserHospitalId(useId);
        IPage<CollectInfoVO> page1 = null;
        if (CollectionUtils.isNotEmpty(longs) ) {
            dto.setUserHospitalIdList(longs);
            return collectInfoMapper.listByCollectInfoPage(page, dto);
        }
        return page1;
    }

    @Override
    public void exportCollectInfo(HttpServletResponse response,CollectInfoQueryDTO dto) throws ClassNotFoundException {
        List<CollectInfoVO> collectInfoVOS = collectInfoMapper.exportCollectInfo(dto);
        excelTransferByClass.exportExcel(response,collectInfoVOS,"采集信息管理","sheet",CollectInfoVO.class);
    }

    @Override
    public CollectStatusInfoVO queryCollectStatus(CollectInfoRecordQueryDTO dto) {
        return collectInfoMapper.queryCollectStatus(dto);
    }

}
