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
        List<SysRegion> sysRegions = sysRegionMapper.queryByIdRegion(1);
        List<SysRegion> two = sysRegionMapper.queryByIdRegion(2);
        List<SysRegion> three = sysRegionMapper.queryByIdRegion(3);
        for (SysRegion t:two){
            ArrayList<SysRegion> sysRegions2 = new ArrayList<>();
            for (SysRegion sysRegion:three){
                if (t.getId().equals(sysRegion.getParentId().longValue())){
                    sysRegions2.add(sysRegion);
                }
            }
            t.setChildren(sysRegions2);
        }
        for (SysRegion one:sysRegions){
            ArrayList<SysRegion> sysRegions1 = new ArrayList<>();
            for (SysRegion t:two){
                if (one.getId().equals(t.getParentId().longValue())){
                    sysRegions1.add(t);
                }
            }
            one.setChildren(sysRegions1);
        }
        return sysRegions;
    }



}
