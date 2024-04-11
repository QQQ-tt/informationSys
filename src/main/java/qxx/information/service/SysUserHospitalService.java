package qxx.information.service;

import qxx.information.entity.SysUserHospital;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户医院关系表 服务类
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
public interface SysUserHospitalService extends IService<SysUserHospital> {

    List<SysUserHospital> listSysUserHospital(Long userId);

    List<SysUserHospital> listByUserIdHospital(String userId);

    List<SysUserHospital> listHospitalByRegionId(String regionId,List<Long> userId);

}
