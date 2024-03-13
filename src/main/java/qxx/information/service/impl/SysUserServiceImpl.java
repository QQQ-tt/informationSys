package qxx.information.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import qxx.information.config.BaseEntity;
import qxx.information.entity.SysUser;
import qxx.information.entity.SysUserHospital;
import qxx.information.entity.SysUserRole;
import qxx.information.mapper.SysUserMapper;
import qxx.information.pojo.dto.LoginDTO;
import qxx.information.pojo.dto.SysUserDTO;
import qxx.information.pojo.dto.SysUserPasswordDTO;
import qxx.information.pojo.vo.LoginVO;
import qxx.information.pojo.vo.SysUserVO;
import qxx.information.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserRoleServiceImpl sysUserRoleService;

    private final SysUserHospitalServiceImpl sysUserHospitalService;

    private final PasswordEncoder passwordEncoder;

    public SysUserServiceImpl(SysUserRoleServiceImpl sysUserRoleService, SysUserHospitalServiceImpl sysUserHospitalService, PasswordEncoder passwordEncoder) {
        this.sysUserRoleService = sysUserRoleService;
        this.sysUserHospitalService = sysUserHospitalService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<SysUserVO> listSysUserPage(SysUserDTO dto) {
        return null;
    }

    @Override
    public boolean saveOrUpdateSysUser(SysUser dto) {
        Long id = dto.getId();
        boolean flag = id != null;
        SysUser user = getOne(Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUserId, dto.getUserId())
                .ne(flag,
                        BaseEntity::getId, id));
        if (Objects.isNull(user)) {
            if (!flag) {
                String password = dto.getPassword();
                String encode = passwordEncoder.encode(password);
                dto.setPassword(encode);
            }
            boolean savedOrUpdate = saveOrUpdate(dto);
            if (flag) {
                sysUserRoleService.remove(Wrappers.lambdaQuery(SysUserRole.class)
                        .eq(SysUserRole::getUserId, dto.getUserId()));
                sysUserHospitalService.remove(Wrappers.lambdaQuery(SysUserHospital.class)
                        .eq(SysUserHospital::getUserId, dto.getUserId()));
            }
            List<String> roles = Optional.ofNullable(dto.getRoles())
                    .orElse(new ArrayList<>());
            List<String> hospital = Optional.ofNullable(dto.getHospital())
                    .orElse(new ArrayList<>());
            ArrayList<SysUserHospital> sysUserHospitals = new ArrayList<>();
            ArrayList<SysUserRole> sysUserRoles = new ArrayList<>();
            roles.forEach(role -> {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(id);
                sysUserRole.setRoleId(Long.parseLong(role));
                sysUserRoles.add(sysUserRole);
            });
            hospital.forEach(hospitalId -> {
                SysUserHospital userHospital = new SysUserHospital();
                userHospital.setUserId(id);
                userHospital.setHospitalId(Long.parseLong(hospitalId));
                sysUserHospitals.add(userHospital);
            });
            sysUserRoleService.saveBatch(sysUserRoles);
            sysUserHospitalService.saveBatch(sysUserHospitals);
            return savedOrUpdate;
        }
        return false;
    }

    @Override
    public boolean updatePasswordById(SysUserPasswordDTO dto) {
        return update(Wrappers.lambdaUpdate(SysUser.class)
                .eq(BaseEntity::getId, dto.getId())
                .set(SysUser::getPassword,
                        passwordEncoder.encode(dto.getPassword())));
    }

    @Override
    public boolean updateStatusById(Long id, Boolean flag) {
        return update(Wrappers.lambdaUpdate(SysUser.class)
                .eq(BaseEntity::getId, id)
                .set(SysUser::getStats, flag));
    }

    @Override
    public boolean deleteSysUserById(Long id) {
        return removeById(id);
    }

    @Override
    public LoginVO login(LoginDTO dto) {
        return null;
    }

    @Override
    public boolean loginUpdatePassword(SysUserPasswordDTO dto) {
        return false;
    }
}
