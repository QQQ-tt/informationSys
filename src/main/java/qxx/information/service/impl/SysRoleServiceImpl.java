package qxx.information.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import qxx.information.entity.SysRole;
import qxx.information.mapper.SysRoleMapper;
import qxx.information.pojo.dto.SysRoleQueryDTO;
import qxx.information.pojo.vo.SysRoleVO;
import qxx.information.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public Boolean insertRole(SysRole entity) {
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
}
