package qxx.information.service.impl;

import qxx.information.entity.HospitalPackageInfo;
import qxx.information.mapper.HospitalPackageInfoMapper;
import qxx.information.pojo.vo.HospitalInfoPackageVO;
import qxx.information.service.HospitalPackageInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 医院信息与套餐信息中间表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Service
public class HospitalPackageInfoServiceImpl extends ServiceImpl<HospitalPackageInfoMapper, HospitalPackageInfo> implements HospitalPackageInfoService {

    @Override
    public List<HospitalInfoPackageVO> queryByHospitalIdPackageInfo(Long hospitalId) {
        return baseMapper.queryByIdPackage(hospitalId);
    }

}
