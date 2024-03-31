package qxx.information.service;

import qxx.information.entity.HospitalPackageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import qxx.information.pojo.vo.HospitalInfoPackageVO;

import java.util.List;

/**
 * <p>
 * 医院信息与套餐信息中间表 服务类
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
public interface HospitalPackageInfoService extends IService<HospitalPackageInfo> {

    /**
     * 根据医院信息id，查询套餐信息
     * @param hospitalId 医院信息id
     * @return 套餐信息集合
     * @date 2024/3/31 16:39
     * @version 3.0
     */
    List<HospitalInfoPackageVO> queryByHospitalIdPackageInfo(Long hospitalId);

}
