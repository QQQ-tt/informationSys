package qxx.information.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import qxx.information.entity.SysMenu;
import qxx.information.mapper.SysMenuMapper;
import qxx.information.mapper.SysRoleMenuMapper;
import qxx.information.pojo.vo.SysMenuVO;
import qxx.information.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单信息表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenuVO> queryMenuInfo() {
        return sysMenuMapper.queryMenuInfo();
    }

    @Override
    public List<SysMenuVO> getByRoleIdMenuInfo(Long id) {
        return sysRoleMenuMapper.getByRoleIdMenuInfo(id);
    }

    @Override
    public List<SysMenuVO> listByUserId(String id) {
        return baseMapper.selectByUserId(id);
    }
}
