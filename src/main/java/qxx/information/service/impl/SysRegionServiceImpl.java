package qxx.information.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qxx.information.entity.SysRegion;
import qxx.information.mapper.SysRegionMapper;
import qxx.information.service.SysRegionService;
import qxx.information.service.SysRoleService;

import java.util.List;

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
        return sysRegionMapper.queryByIdRegion(id);
    }
}
