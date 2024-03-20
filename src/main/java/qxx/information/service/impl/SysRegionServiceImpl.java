package qxx.information.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qxx.information.entity.SysMenu;
import qxx.information.entity.SysRegion;
import qxx.information.mapper.SysRegionMapper;
import qxx.information.service.SysRegionService;
import qxx.information.service.SysRoleService;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 地区 服务实现类
 * </p>
 *
 * @author hxc
 * @since 2024-03-16
 */
@Service
public class SysRegionServiceImpl extends ServiceImpl<SysRegionMapper, SysRegion> implements SysRegionService {

    @Autowired
    private SysRegionMapper sysRegionMapper;

    @Override
    public List<SysRegion> queryByIdRegion(Long id) {
        return buildTree(sysRegionMapper.queryByIdRegion());
    }


    public static List<SysRegion> buildTree(List<SysRegion> menus) {
        List<SysRegion> trees = new ArrayList<>();
        for (SysRegion menu : menus) {
            if (menu.getParentId() == 1) {
                menu.setChildren(getChildren(menu.getId(), menus));
                trees.add(menu);
            }
        }
        return trees;
    }


    public static List<SysRegion> getChildren(Long id, List<SysRegion> menus) {
        List<SysRegion> children = new ArrayList<>();
        for (SysRegion menu : menus) {
            if (menu.getParentId().equals(id.intValue())) {
                menu.setChildren(getChildren(menu.getId(), menus));
                children.add(menu);
            }
        }
        return children;
    }

}
