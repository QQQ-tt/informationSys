package qxx.information.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import qxx.information.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qxx.information.pojo.dto.SysUserDTO;
import qxx.information.pojo.vo.SysUserVO;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    Page<SysUserVO> selectPageNew(IPage<SysUser> page,@Param("dto") SysUserDTO dto);
}
