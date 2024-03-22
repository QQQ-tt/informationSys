package qxx.information.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import qxx.information.entity.HospitalInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qxx.information.pojo.dto.HospitalInfoQueryDTO;
import qxx.information.pojo.vo.CollectInfoVO;
import qxx.information.pojo.vo.HospitalInfoVO;

/**
 * <p>
 * 医院信息管理表 Mapper 接口
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Mapper
public interface HospitalInfoMapper extends BaseMapper<HospitalInfo> {

    IPage<HospitalInfoVO> listByPage(@Param("page") Page<HospitalInfoVO> page, @Param("dto") HospitalInfoQueryDTO dto);

    HospitalInfoVO queryByIdHospitalInfo(Long id);

}
