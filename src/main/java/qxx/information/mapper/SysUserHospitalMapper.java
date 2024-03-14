package qxx.information.mapper;

import qxx.information.entity.SysUserHospital;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户医院关系表 Mapper 接口
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Mapper
public interface SysUserHospitalMapper extends BaseMapper<SysUserHospital> {

    List<SysUserHospital> selectListNew(Long userId);
}
