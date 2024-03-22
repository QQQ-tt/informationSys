package qxx.information.mapper;

import qxx.information.entity.CollectInfo;
import qxx.information.entity.HospitalPackageInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qxx.information.pojo.vo.HospitalInfoPackageVO;
import qxx.information.pojo.vo.HospitalInfoVO;
import qxx.information.pojo.vo.HospitalPackageInfoVO;

import java.util.List;

/**
 * <p>
 * 医院信息与套餐信息中间表 Mapper 接口
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Mapper
public interface HospitalPackageInfoMapper extends BaseMapper<HospitalPackageInfo> {


    List<HospitalPackageInfoVO> listByHospitalInfoId(Long id);

    List<HospitalInfoPackageVO> queryByIdPackage(Long id);

}
