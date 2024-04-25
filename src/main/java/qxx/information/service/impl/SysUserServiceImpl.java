package qxx.information.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import qxx.information.config.BaseEntity;
import qxx.information.config.CommonMethod;
import qxx.information.config.enums.DataEnums;
import qxx.information.config.exception.DataException;
import qxx.information.entity.HospitalInfo;
import qxx.information.entity.SysUser;
import qxx.information.entity.SysUserHospital;
import qxx.information.entity.SysUserRole;
import qxx.information.mapper.SysUserMapper;
import qxx.information.pojo.dto.LoginDTO;
import qxx.information.pojo.dto.SysUserDTO;
import qxx.information.pojo.dto.SysUserPasswordDTO;
import qxx.information.pojo.vo.LoginVO;
import qxx.information.pojo.vo.OcrVO;
import qxx.information.pojo.vo.SysUserVO;
import qxx.information.service.SysMenuService;
import qxx.information.service.SysRoleService;
import qxx.information.service.SysUserService;
import qxx.information.utils.JwtUtils;

import java.io.IOException;
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

    private final RestClient restClient;

    public SysUserServiceImpl(SysUserRoleServiceImpl sysUserRoleService, SysUserHospitalServiceImpl sysUserHospitalService, PasswordEncoder passwordEncoder, CommonMethod commonMethod, SysRoleService sysRoleService, HospitalInfoServiceImpl hospitalInfoService, SysMenuService sysMenuService, RestClient restClient) {
        this.sysUserRoleService = sysUserRoleService;
        this.sysUserHospitalService = sysUserHospitalService;
        this.passwordEncoder = passwordEncoder;
        this.commonMethod = commonMethod;
        this.sysRoleService = sysRoleService;
        this.hospitalInfoService = hospitalInfoService;
        this.sysMenuService = sysMenuService;
        this.restClient = restClient;
    }

    @Override
    public Page<SysUserVO> listSysUserPage(SysUserDTO dto) {
        val voPage = baseMapper.selectPageNew(dto.getPage(), dto);
        voPage.getRecords()
                .forEach(e -> {
                    if (!Objects.equals(e.getUserId(), "admin")) {
                        val s = e.getRegion();
                        if (StringUtils.isNotBlank(s)) {
                            val objectMapper = new ObjectMapper();
                            List<List<Integer>> stringListList;
                            try {
                                stringListList = objectMapper.readValue(s,
                                        new TypeReference<>() {
                                        });
                            } catch (JsonProcessingException ex) {
                                throw new RuntimeException(ex);
                            }
                            e.setRegions(stringListList);
                        }
                    } else {
                        e.setListHospital(null);
                    }
                });
        return voPage;
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
                dto.setPassword(passwordEncoder.encode(dto.getPassword()));
            } else {
                dto.setPassword(null);
            }
            List<String> roles = Optional.ofNullable(dto.getRoles())
                    .orElse(new ArrayList<>())
                    .stream()
                    .filter(StringUtils::isNotBlank)
                    .toList();
            List<String> hospital = new ArrayList<>(Optional.ofNullable(dto.getHospital())
                    .orElse(new ArrayList<>())
                    .stream()
                    .filter(StringUtils::isNotBlank)
                    .toList());
            if (dto.getRegions() != null && !dto.getRegions()
                    .isEmpty()) {
                if (!Objects.equals(dto.getUserId(), "admin") || dto.getAdmin()) {
                    val jsonString = JSONObject.toJSONString(dto.getRegions());
                    dto.setRegion(jsonString);
                } else {
                    dto.setRegion(null);
                    dto.setHospitalStatus(Boolean.TRUE);
                }
                if (hospital.isEmpty() && dto.getId() == null) {
                    val strings = new ArrayList<String>();
                    dto.getRegions()
                            .forEach(k -> {
                                val builder = new StringBuilder();
                                k.forEach(j -> builder.append(j)
                                        .append("-"));
                                strings.add(builder.deleteCharAt(builder.length() - 1)
                                        .toString());
                            });
                    List<String> list = Optional.ofNullable(
                                    hospitalInfoService.list(Wrappers.lambdaQuery(HospitalInfo.class)
                                            .in(HospitalInfo::getRegionId,
                                                    strings)))
                            .orElse(new ArrayList<>())
                            .stream()
                            .map(BaseEntity::getId)
                            .map(String::valueOf)
                            .toList();
                    hospital.addAll(list);
                }
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
                hospitalList.forEach(h -> hospitalInfoService.updateStatusById(h.getHospitalId(), false));
                sysUserHospitalService.remove(Wrappers.lambdaQuery(SysUserHospital.class)
                        .eq(SysUserHospital::getUserId, dto.getId()));
            }

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
            hospital.forEach(hospitalId -> {
                SysUserHospital userHospital = new SysUserHospital();
                userHospital.setUserId(finalId);
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
                /*map.put("id", user.getId());
                map.put("userId", user.getUserId());
                map.put("name", user.getName());
                map.put("hospital", sysUserHospitals);
                map.put("phone", user.getPhone());*/
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
                        .eq(SysUser::getUserId, dto.getUserId())
                        .set(SysUser::getPassword, encode));
            }
            throw new DataException(DataEnums.WRONG_PASSWORD);
        }
        throw new DataException(DataEnums.USER_IS_NULL);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createSysUser(SysUser dto) {
        dto.setId(null);
        return saveOrUpdateSysUser(dto);
    }

    @Override
    public OcrVO ocr(MultipartFile img, String accessToken) throws IOException {
        val map = new HashMap<String, String>();
        map.put("access_token", accessToken);
        val builder = MultipartEntityBuilder.create()
                .addBinaryBody("img", img.getInputStream(),
                        ContentType.parse(Objects.requireNonNull(img.getContentType())), img.getOriginalFilename())
                .build();
        return restClient.post()
                .uri("https://api.weixin.qq.com/cv/ocr/idcard?access_token={access_token}", map)
                .contentType(MediaType.parseMediaType(builder.getContentType()
                        .getValue()))
                .body(builder::writeTo)
                .retrieve()
                .toEntity(OcrVO.class)
                .getBody();
    }
}
