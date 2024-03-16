package qxx.information.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import qxx.information.entity.CollectInfo;
import qxx.information.entity.HospitalInfo;
import qxx.information.entity.PackageInfo;
import qxx.information.mapper.CollectInfoMapper;
import qxx.information.pojo.dto.CollectInfoQueryDTO;
import qxx.information.pojo.dto.CollectInfoRecordQueryDTO;
import qxx.information.pojo.vo.CollectInfoRecordVO;
import qxx.information.pojo.vo.CollectInfoVO;
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


    @Override
    public Boolean insertCollectInfo(CollectInfo collectInfo) {
        return saveOrUpdate(collectInfo);
    }


    @Override
    public List<CollectInfoRecordVO> queryCollectInfoRecordList(CollectInfoRecordQueryDTO dto) {
        return collectInfoMapper.queryCollectInfoRecordList(dto);
    }

    @Override
    public CollectInfoVO getByIdQueryCollectInfo(Long id) {
        return collectInfoMapper.getByIdQueryCollectInfo(id);
    }

    @Override
    public IPage<CollectInfoVO> listByCollectInfoPage(CollectInfoQueryDTO dto) {
        Page<CollectInfoVO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        return collectInfoMapper.listByCollectInfoPage(page,dto);
    }
}
