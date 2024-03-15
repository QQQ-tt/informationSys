package qxx.information.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import qxx.information.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qxx.information.pojo.dto.SysRoleQueryDTO;
import qxx.information.pojo.vo.SysRoleVO;

/**
 * <p>
 * 角色管理 Mapper 接口
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    IPage<SysRoleVO> listByRolePage(@Param("page") Page<SysRoleVO> page, @Param("dto") SysRoleQueryDTO roleName);

}
