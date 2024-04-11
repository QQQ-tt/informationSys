package qxx.information.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qxx.information.config.BaseEntity;
import qxx.information.config.CommonMethod;
import qxx.information.entity.HospitalInfo;
import qxx.information.entity.HospitalPackageInfo;
import qxx.information.entity.SysUser;
import qxx.information.entity.SysUserHospital;
import qxx.information.mapper.HospitalInfoMapper;
import qxx.information.mapper.HospitalPackageInfoMapper;
import qxx.information.mapper.SysUserHospitalMapper;
import qxx.information.pojo.dto.HospitalInfoInsertDTO;
import qxx.information.pojo.dto.HospitalInfoQueryDTO;
import qxx.information.pojo.dto.HospitalPackageInsertDTO;
import qxx.information.pojo.vo.HospitalInfoPackageVO;
import qxx.information.pojo.vo.HospitalInfoVO;
import qxx.information.pojo.vo.HospitalPackageInfoVO;
import qxx.information.service.HospitalInfoService;
import qxx.information.service.HospitalPackageInfoService;
import qxx.information.service.PackageInfoService;
import qxx.information.service.SysUserHospitalService;
import qxx.information.service.SysUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 医院信息管理表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Slf4j
@Service
public class HospitalInfoServiceImpl extends ServiceImpl<HospitalInfoMapper, HospitalInfo> implements HospitalInfoService {

    @Lazy
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserHospitalService sysUserHospitalService;

    @Autowired
    private HospitalInfoMapper hospitalInfoMapper;

    @Autowired
    private HospitalPackageInfoService hospitalPackageInfoService;

    @Autowired
    private HospitalPackageInfoMapper hospitalPackageInfoMapper;

    @Autowired
    private PackageInfoService packageInfoService;

    @Autowired
    private SysUserHospitalMapper sysUserHospitalMapper;

    @Resource
    private CommonMethod commonMethod;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertHospitalInfo(HospitalInfoInsertDTO dto) {

        HospitalInfo hospitalInfo = new HospitalInfo();
        hospitalInfo.setHospitalName(dto.getHospitalName());
        hospitalInfo.setDistrictName(dto.getDistrictName());
        hospitalInfo.setRegionId(dto.getRegionId());
        hospitalInfo.setStatus(0);
        int insert = hospitalInfoMapper.insert(hospitalInfo);
        if (CollectionUtils.isNotEmpty(dto.getPackageIdList())) {
            ArrayList<HospitalPackageInfo> hospitalPackageInfos = new ArrayList<>();
            //添加套餐中间表
            dto.getPackageIdList()
                    .forEach(item -> {
                        HospitalPackageInfo hospitalPackageInfo = new HospitalPackageInfo();
                        hospitalPackageInfo.setHospitalInfoId(hospitalInfo.getId());
                        hospitalPackageInfo.setInfoPackageId(item.getId());
                        hospitalPackageInfo.setOrderNum(item.getOrderNum());
                        hospitalPackageInfos.add(hospitalPackageInfo);
                    });
            hospitalPackageInfoService.saveBatch(hospitalPackageInfos);
        }
        val sysUserHospitals = new ArrayList<SysUserHospital>();
        val split = dto.getRegionId()
                .replace("-", ",");
        val list = sysUserService.list(Wrappers.lambdaQuery(SysUser.class)
                .like(SysUser::getRegion, split)
                .eq(SysUser::getHospitalStatus, Boolean.TRUE));
        val hospitalInfoList = list(Wrappers.lambdaQuery(HospitalInfo.class)
                .eq(HospitalInfo::getRegionId, dto.getRegionId()));
        list.forEach(item -> hospitalInfoList.forEach(h -> {
            SysUserHospital sysUserHospital = new SysUserHospital();
            sysUserHospital.setUserId(item.getId());
            sysUserHospital.setHospitalId(h.getId());
            sysUserHospitals.add(sysUserHospital);
        }));
        val sysUserHospitals1 = sysUserHospitalService.listHospitalByRegionId(dto.getRegionId(),
                list.stream()
                        .map(BaseEntity::getId)
                        .toList());
        for (SysUserHospital hospital : sysUserHospitals1) {
            update(Wrappers.lambdaUpdate(HospitalInfo.class)
                    .setSql("status = status -" + hospital.getStatus())
                    .eq(BaseEntity::getId, hospital.getHospitalId()));
        }
        sysUserHospitalService.saveBatch(sysUserHospitals);
        val collect = sysUserHospitals.stream()
                .collect(Collectors.groupingBy(SysUserHospital::getHospitalId));
        collect.forEach((k, v) -> update(Wrappers.lambdaUpdate(HospitalInfo.class)
                .eq(BaseEntity::getId, k)
                .setSql("status = status +" + v.size())));
        return insert;
    }

    public List<HospitalPackageInfoVO> filtrationDelete(List<HospitalPackageInfoVO> all, List<HospitalPackageInsertDTO> updatePackageIdList) {
        List<HospitalPackageInfoVO> collect = all.stream()
                .filter(item -> updatePackageIdList.stream()
                        .noneMatch(item2 -> item2.getId()
                                .equals(item.getInfoPackageId())))
                .collect(Collectors.toList());
        return collect;
    }

    public List<HospitalPackageInsertDTO> filtrationInsert(List<HospitalPackageInsertDTO> updatePackageIdList, List<HospitalPackageInfoVO> all) {
        List<HospitalPackageInsertDTO> collect = updatePackageIdList.stream()
                .filter(h1 -> all.stream()
                        .noneMatch(h2 -> h2.getInfoPackageId()
                                .equals(h1.getId())))
                .collect(Collectors.toList());
        return collect;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateHospitalInfo(HospitalInfoInsertDTO dto) {

        val byId = getById(dto.getId());

        //查询本次原来的医院套餐信息
        List<HospitalPackageInfoVO> longs = hospitalPackageInfoMapper.listByHospitalInfoId(dto.getId());

        if (CollectionUtils.isNotEmpty(dto.getPackageIdList())) {
            List<HospitalPackageInfoVO> hospitalPackageInfoVOS = filtrationDelete(longs, dto.getPackageIdList());
            //减少原来的菜单引用次数
            hospitalPackageInfoVOS.forEach(item -> {
                packageInfoService.updateStatusById(item.getHospitalInfoId(), false);
            });
            List<HospitalPackageInsertDTO> hospitalPackageInsertDTOS = filtrationInsert(dto.getPackageIdList(), longs);
            hospitalPackageInsertDTOS.forEach(item2 -> {
                packageInfoService.updateStatusById(item2.getId(), true);
            });
            //删除原来的套餐关联表，根据医院信息id删除
            LambdaUpdateWrapper<HospitalPackageInfo> hospitalPackageInfoUpdateWrapper = new LambdaUpdateWrapper<>();
            hospitalPackageInfoUpdateWrapper.eq(HospitalPackageInfo::getHospitalInfoId, dto.getId())
                    .set(HospitalPackageInfo::getDeleteFlag, 1);
            int delete = hospitalPackageInfoMapper.update(hospitalPackageInfoUpdateWrapper);

            //添加套餐中间表
            ArrayList<HospitalPackageInfo> hospitalPackageInfos = new ArrayList<>();
            dto.getPackageIdList()
                    .forEach(item -> {
                        HospitalPackageInfo hospitalPackageInfo = new HospitalPackageInfo();
                        hospitalPackageInfo.setHospitalInfoId(dto.getId());
                        hospitalPackageInfo.setInfoPackageId(item.getId());
                        hospitalPackageInfo.setOrderNum(item.getOrderNum());
                        hospitalPackageInfos.add(hospitalPackageInfo);
                    });
            hospitalPackageInfoService.saveBatch(hospitalPackageInfos);
        }
        HospitalInfo hospitalInfo = new HospitalInfo();
        hospitalInfo.setHospitalName(dto.getHospitalName());
        hospitalInfo.setDistrictName(dto.getDistrictName());
        hospitalInfo.setRegionId(dto.getRegionId());
        hospitalInfo.setId(dto.getId());
        int update = hospitalInfoMapper.updateById(hospitalInfo);
        val split = byId.getRegionId()
                .replace("-", ",");
        val list = sysUserService.list(Wrappers.lambdaQuery(SysUser.class)
                .like(SysUser::getRegion, split)
                .eq(SysUser::getHospitalStatus, Boolean.TRUE));
        val collect = list.stream()
                .map(SysUser::getId)
                .collect(Collectors.toList());
        long count = 0L;
        if (!collect.isEmpty()) {
            count = sysUserHospitalService.count(Wrappers.lambdaQuery(SysUserHospital.class)
                    .eq(SysUserHospital::getHospitalId, dto.getId())
                    .in(SysUserHospital::getUserId,
                            collect));
            sysUserHospitalService.remove(Wrappers.lambdaUpdate(SysUserHospital.class)
                    .eq(SysUserHospital::getHospitalId, dto.getId())
                    .in(SysUserHospital::getUserId,
                            collect));
        }
        val sysUserHospitals = new ArrayList<SysUserHospital>();
        val splitDTO = dto.getRegionId()
                .replace("-", ",");
        val listDTO = sysUserService.list(Wrappers.lambdaQuery(SysUser.class)
                .like(SysUser::getRegion, splitDTO)
                .eq(SysUser::getHospitalStatus, Boolean.TRUE));
        listDTO.forEach(item -> {
            SysUserHospital sysUserHospital = new SysUserHospital();
            sysUserHospital.setUserId(item.getId());
            sysUserHospital.setHospitalId(hospitalInfo.getId());
            sysUserHospitals.add(sysUserHospital);
        });
        sysUserHospitalService.saveBatch(sysUserHospitals);
        val l = Long.parseLong(String.valueOf(sysUserHospitals.size())) - count;
        String sql;
        if (l > 0) {
            sql = "+ " + l;
        } else {
            sql = l + "";
        }
        log.info("sql:{}", sql);
        if (l != 0) {
            update(Wrappers.lambdaUpdate(HospitalInfo.class)
                    .eq(BaseEntity::getId, dto.getId())
                    .setSql("status = status " + sql));
        }
        return update;
    }

    @Override
    public IPage<HospitalInfoVO> listByPage(HospitalInfoQueryDTO dto) {
        Page<HospitalInfoVO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        String useId = commonMethod.getSysUserId();
        List<Long> longs = sysUserHospitalMapper.listByUserHospitalId(useId);
        dto.setUserHospitalIdList(longs);
        if (CollectionUtils.isNotEmpty(longs)) {
            return hospitalInfoMapper.listByPage(page, dto);
        }
        IPage<HospitalInfoVO> iPage = null;
        return iPage;
    }

    @Override
    public int deleteHospitalInfo(Long id) {
        LambdaQueryWrapper<HospitalInfo> hospitalInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        hospitalInfoLambdaQueryWrapper.eq(HospitalInfo::getId, id)
                .gt(HospitalInfo::getStatus, 0);
        Long aLong = hospitalInfoMapper.selectCount(hospitalInfoLambdaQueryWrapper);
        if (aLong > 0) {
            return 0;
        } else {
            LambdaUpdateWrapper<HospitalInfo> hospitalInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            hospitalInfoLambdaUpdateWrapper.eq(HospitalInfo::getId, id)
                    .set(HospitalInfo::getDeleteFlag, 1);
            int update = hospitalInfoMapper.update(hospitalInfoLambdaUpdateWrapper);
            LambdaUpdateWrapper<HospitalPackageInfo> hospitalPackageInfoUpdateWrapper = new LambdaUpdateWrapper<>();
            hospitalPackageInfoUpdateWrapper.eq(HospitalPackageInfo::getHospitalInfoId, id)
                    .set(HospitalPackageInfo::getDeleteFlag, 1);
            hospitalPackageInfoMapper.update(hospitalPackageInfoUpdateWrapper);
            return update;
        }
    }

    @Override
    public boolean updateStatusById(Long id, boolean flag) {
        return update(Wrappers.lambdaUpdate(HospitalInfo.class)
                .eq(HospitalInfo::getId, id)
                .setSql(flag, "status = status + 1")
                .setSql(!flag, "status = status - 1"));
    }

    @Override
    public List<HospitalInfo> queryDistrictGetHospitalInfo(HospitalInfoQueryDTO districtName) {
        LambdaQueryWrapper<HospitalInfo> hospitalInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        hospitalInfoLambdaQueryWrapper.eq(BaseEntity::getDeleteFlag, Boolean.FALSE)
                .in(districtName.getDistrictNames() != null && !districtName.getDistrictNames()
                                .isEmpty(), HospitalInfo::getDistrictName,
                        districtName.getDistrictNames())
                .eq(HospitalInfo::getDeleteFlag, 0);
        return hospitalInfoMapper.selectList(hospitalInfoLambdaQueryWrapper);
    }

    @Override
    public HospitalInfoVO queryByIdHospitalInfoPackage(Long id) {
        List<HospitalInfoPackageVO> hospitalInfoPackageVOS = hospitalPackageInfoMapper.queryByIdPackage(id);
        HospitalInfoVO hospitalInfoVO = hospitalInfoMapper.queryByIdHospitalInfo(id);
        hospitalInfoVO.setPackageInfoList(hospitalInfoPackageVOS);
        return hospitalInfoVO;
    }
}
