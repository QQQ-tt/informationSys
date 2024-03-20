package qxx.information.service;

import qxx.information.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import qxx.information.pojo.vo.SysMenuVO;

import java.util.List;

/**
 * <p>
 * 菜单信息表 服务类
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 查询菜单信息
     * @param
     * @return 菜单信息
     * @date 2024/3/16 12:07
     * @version 3.0
     */
    List<SysMenuVO> queryMenuInfo();


    /**
     * 根据角色查询菜单id
     * @param id 角色id
     * @return 菜单信息
     * @date 2024/3/16 12:10
     * @version 3.0
     */
    List<SysMenuVO> getByRoleIdMenuInfo(Long id);

    /**
     * 根据用户id查询菜单
     * @param id
     * @return
     */
    List<SysMenuVO> listByUserId(String id);

}
