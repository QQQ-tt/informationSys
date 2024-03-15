package qxx.information.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import qxx.information.entity.SysRole;
import qxx.information.entity.SysRoleMenu;
import qxx.information.mapper.SysRoleMapper;
import qxx.information.mapper.SysRoleMenuMapper;
import qxx.information.pojo.dto.RoleMenuDTO;
import qxx.information.pojo.dto.SysRoleQueryDTO;
import qxx.information.pojo.vo.SysRoleVO;
import qxx.information.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色管理 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysRoleMenuServiceImpl service;

    @Override
    public Boolean insertOrUpdateRole(SysRole entity) {
        entity.setStatus(0);
        return saveOrUpdate(entity);
    }


    @Override
    public IPage<SysRoleVO> listByRolePage(SysRoleQueryDTO dto) {
        Page<SysRoleVO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        return sysRoleMapper.listByRolePage(page,dto);
    }

    @Override
    public int deleteRole(Long id) {

        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getId, id).gt(SysRole::getStatus, 1);
        Long aLong = sysRoleMapper.selectCount(queryWrapper);
        if (aLong>0){
            return 0;
        }else {

            LambdaUpdateWrapper<SysRole> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(SysRole::getId, id).set(SysRole::getDeleteFlag, 1);
            int update = sysRoleMapper.update(updateWrapper);
            return update;
        }
    }


    @Override
    public Boolean updateRoleMenu(RoleMenuDTO roleMenuDTO) {
        //拿到角色id
        Integer roleId = roleMenuDTO.getRoleId();
        //删除角色具有的权限  使用MP的删除
        LambdaUpdateWrapper<SysRoleMenu> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId, roleId).set(SysRoleMenu::getDeleteFlag,1);
        sysRoleMenuMapper.update(queryWrapper);

        List<SysRoleMenu> sysRoleMenuList = new ArrayList<>();

        //拿到菜单权限集合   页面传入的菜单
        List<Integer> menuIdList = roleMenuDTO.getMenuIdList();
        //传入的菜单权限结集合为空，抛异常
        if (menuIdList.isEmpty()){
            return false;
        }
        for (Integer menuId : menuIdList){
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenuList.add(sysRoleMenu);
        }
        boolean b = service.saveBatch(sysRoleMenuList);
        return b;
    }

}
