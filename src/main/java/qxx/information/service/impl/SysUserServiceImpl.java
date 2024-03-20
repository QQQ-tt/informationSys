package qxx.information.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qxx.information.config.BaseEntity;
import qxx.information.config.CommonMethod;
import qxx.information.config.enums.DataEnums;
import qxx.information.config.exception.DataException;
import qxx.information.entity.SysUser;
import qxx.information.entity.SysUserHospital;
import qxx.information.entity.SysUserRole;
import qxx.information.mapper.SysUserMapper;
import qxx.information.pojo.dto.LoginDTO;
import qxx.information.pojo.dto.SysUserDTO;
import qxx.information.pojo.dto.SysUserPasswordDTO;
import qxx.information.pojo.vo.LoginVO;
import qxx.information.pojo.vo.SysUserVO;
import qxx.information.service.SysMenuService;
import qxx.information.service.SysRoleService;
import qxx.information.service.SysUserService;
import qxx.information.utils.JwtUtils;

import java.util.ArrayList;
import java.util.HashMap;
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

    private final CommonMethod commonMethod;

    private final SysRoleService sysRoleService;

    private final HospitalInfoServiceImpl hospitalInfoService;

    private final SysMenuService sysMenuService;

    public SysUserServiceImpl(SysUserRoleServiceImpl sysUserRoleService, SysUserHospitalServiceImpl sysUserHospitalService, PasswordEncoder passwordEncoder, CommonMethod commonMethod, SysRoleService sysRoleService, HospitalInfoServiceImpl hospitalInfoService, SysMenuService sysMenuService) {
        this.sysUserRoleService = sysUserRoleService;
        this.sysUserHospitalService = sysUserHospitalService;
        this.passwordEncoder = passwordEncoder;
        this.commonMethod = commonMethod;
        this.sysRoleService = sysRoleService;
        this.hospitalInfoService = hospitalInfoService;
        this.sysMenuService = sysMenuService;
    }

    @Override
    public Page<SysUserVO> listSysUserPage(SysUserDTO dto) {
        return baseMapper.selectPageNew(dto.getPage(), dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateSysUser(SysUser dto) {
        Long id = dto.getId();
        boolean flag = id != null;
        SysUser user = getOne(Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUserId, dto.getUserId())
                .ne(flag, BaseEntity::getId, id));
        if (Objects.isNull(user)) {
            if (!flag) {
                String password = dto.getPassword();
                String encode = passwordEncoder.encode(password);
                dto.setPassword(encode);
            }
            boolean savedOrUpdate = saveOrUpdate(dto);
            if (flag) {
                val list = sysUserRoleService.list(Wrappers.lambdaQuery(SysUserRole.class)
                        .eq(SysUserRole::getUserId,
                                dto.getId()));
                list.forEach(s -> sysRoleService.updateStatusById(s.getRoleId(), false));
                sysUserRoleService.remove(Wrappers.lambdaQuery(SysUserRole.class)
                        .eq(SysUserRole::getUserId, dto.getId()));
                val hospitalList = sysUserHospitalService.list(Wrappers.lambdaQuery(SysUserHospital.class)
                        .eq(SysUserHospital::getUserId
                                , dto.getId()));
                hospitalList.forEach(hospital -> hospitalInfoService.updateStatusById(hospital.getHospitalId(), false));
                sysUserHospitalService.remove(Wrappers.lambdaQuery(SysUserHospital.class)
                        .eq(SysUserHospital::getUserId, dto.getId()));
            }
            List<String> roles = Optional.ofNullable(dto.getRoles())
                    .orElse(new ArrayList<>());
            List<String> hospital = Optional.ofNullable(dto.getHospital())
                    .orElse(new ArrayList<>());
            ArrayList<SysUserHospital> sysUserHospitals = new ArrayList<>();
            ArrayList<SysUserRole> sysUserRoles = new ArrayList<>();
            id = dto.getId() == null ? id : dto.getId();
            Long finalId = id;
            roles.forEach(role -> {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(finalId);
                sysUserRole.setRoleId(Long.parseLong(role));
                sysUserRoles.add(sysUserRole);
                sysRoleService.updateStatusById(Long.parseLong(role), true);
            });
            Long finalId1 = id;
            hospital.forEach(hospitalId -> {
                SysUserHospital userHospital = new SysUserHospital();
                userHospital.setUserId(finalId1);
                userHospital.setHospitalId(Long.parseLong(hospitalId));
                sysUserHospitals.add(userHospital);
                hospitalInfoService.updateStatusById(Long.parseLong(hospitalId), true);
            });
            sysUserRoleService.saveBatch(sysUserRoles);
            sysUserHospitalService.saveBatch(sysUserHospitals);
            return savedOrUpdate;
        }
        throw new DataException(DataEnums.DATA_REPEAT);
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
                .set(SysUser::getStatus, flag));
    }

    @Override
    public boolean deleteSysUserById(Long id) {
        return removeById(id);
    }

    @Override
    public LoginVO login(LoginDTO dto) {
        SysUser user = getOne(Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getStatus, Boolean.TRUE)
                .eq(SysUser::getUserId, dto.getUserId()));
        if (Objects.nonNull(user)) {
            if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                val sysUserHospitals = sysUserHospitalService.listSysUserHospital(user.getId());
                val menus = sysMenuService.listByUserId(user.getId());
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", user.getId());
                map.put("userId", user.getUserId());
                map.put("name", user.getName());
                map.put("hospital", sysUserHospitals);
                map.put("phone", user.getPhone());
                return LoginVO.builder()
                        .userId(user.getUserId())
                        .name(user.getName())
                        .hospital(sysUserHospitals)
                        .menus(menus)
                        .token(JwtUtils.generateToken(user.getUserId(), map))
                        .build();
            }
        }
        throw new DataException(DataEnums.USER_IS_NULL);
    }

    @Override
    public String flushedToken() {
        val claims = JwtUtils.getClaimsFromToken(commonMethod.getToken());
        assert claims != null;
        val subject = claims.getSubject();
        SysUser user = getOne(Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getStatus, Boolean.TRUE)
                .eq(SysUser::getUserId, subject));
        if (Objects.nonNull(user)) {
            return JwtUtils.generateToken(subject, claims);
        }
        throw new DataException(DataEnums.USER_IS_NULL);
    }

    @Override
    public boolean loginUpdatePassword(SysUserPasswordDTO dto) {
        SysUser user = getOne(Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getStatus, Boolean.TRUE)
                .eq(SysUser::getUserId, dto.getUserId()));
        if (Objects.nonNull(user)) {
            if (passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
                String password = dto.getPassword();
                String encode = passwordEncoder.encode(password);
                return update(Wrappers.lambdaUpdate(SysUser.class)
                        .eq(BaseEntity::getId, dto.getUserId())
                        .set(SysUser::getPassword, encode));
            }
            throw new DataException(DataEnums.WRONG_PASSWORD);
        }
        throw new DataException(DataEnums.USER_IS_NULL);
    }

    @Override
    public boolean createSysUser(SysUser dto) {
        dto.setId(null);
        return saveOrUpdateSysUser(dto);
    }
}
