package qxx.information.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import qxx.information.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import qxx.information.pojo.dto.RoleMenuDTO;
import qxx.information.pojo.dto.SysRoleQueryDTO;
import qxx.information.pojo.vo.SysRoleVO;

/**
 * <p>
 * 角色管理 服务类
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 添加角色信息
     * @param entity 添加信息
     * @return 行数
     * @date 2024/3/16 0:26
     * @version 3.0
     */
    Boolean insertOrUpdateRole(SysRole entity);

    /**
     * 查询角色信息
     * @param dto 角色名称
     * @return 角色信息
     * @date 2024/3/16 0:41
     * @version 3.0
     */
    IPage<SysRoleVO> listByRolePage(SysRoleQueryDTO dto);

    /**
     * 根据主键删除角色
     * @param id 主键id
     * @return 行数
     * @date 2024/3/16 0:50
     * @version 3.0
     */
    int deleteRole(Long id);

    /**
     * 编辑菜单信息
     * @param roleMenuDTO 菜单信息id
     * @return 是否成功
     * @date 2024/3/16 10:08
     * @version 3.0
     */
    Boolean updateRoleMenu(RoleMenuDTO roleMenuDTO);

    /**
     * 更新引用状态
     *
     * @param id   套餐id
     * @param flag true 引用数据； false 取消引用
     * @return 是否成功
     */
    boolean updateStatusById(Long id,boolean flag);

}
