package qxx.information.service.impl;

import qxx.information.entity.SysUserHospital;
import qxx.information.mapper.SysUserHospitalMapper;
import qxx.information.service.SysUserHospitalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户医院关系表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Service
public class SysUserHospitalServiceImpl extends ServiceImpl<SysUserHospitalMapper, SysUserHospital> implements SysUserHospitalService {

    @Override
    public List<SysUserHospital> listSysUserHospital(Long userId) {
        return baseMapper.selectListNew(userId);
    }

    @Override
    public List<SysUserHospital> listByUserIdHospital(String userId) {
        Long id = baseMapper.listByUserIdHospital(userId);
        return baseMapper.selectListNew(id);
    }

    @Override
    public List<SysUserHospital> listHospitalByRegionId(String regionId, List<Long> userId) {
        return baseMapper.selectHospitalByRegionId(regionId, userId);
    }

}
