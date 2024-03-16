package qxx.information.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import qxx.information.entity.SysRegion;
import qxx.information.entity.SysUser;
import qxx.information.pojo.dto.SysUserDTO;
import qxx.information.pojo.vo.SysUserVO;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Mapper
public interface SysRegionMapper extends BaseMapper<SysRegion> {


    List<SysRegion> queryByIdRegion(Long id);

}
