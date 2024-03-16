package qxx.information.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import qxx.information.entity.HospitalInfo;
import qxx.information.entity.HospitalPackageInfo;
import qxx.information.entity.PackageInfo;
import qxx.information.entity.SysRoleMenu;
import qxx.information.mapper.HospitalInfoMapper;
import qxx.information.mapper.HospitalPackageInfoMapper;
import qxx.information.mapper.SysRoleMenuMapper;
import qxx.information.pojo.dto.HospitalInfoInsertDTO;
import qxx.information.pojo.dto.HospitalInfoQueryDTO;
import qxx.information.pojo.dto.RoleMenuDTO;
import qxx.information.pojo.vo.CollectInfoVO;
import qxx.information.pojo.vo.HospitalInfoVO;
import qxx.information.service.HospitalInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import qxx.information.service.HospitalPackageInfoService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 医院信息管理表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Service
public class HospitalInfoServiceImpl extends ServiceImpl<HospitalInfoMapper, HospitalInfo> implements HospitalInfoService {


    @Autowired
    private HospitalInfoMapper hospitalInfoMapper;

    @Autowired
    private HospitalPackageInfoService hospitalPackageInfoService;

    @Autowired
    private HospitalPackageInfoMapper hospitalPackageInfoMapper;



    @Override
    public int insertHospitalInfo(HospitalInfoInsertDTO dto) {

        HospitalInfo hospitalInfo = new HospitalInfo();
        hospitalInfo.setHospitalName(dto.getHospitalName());
        hospitalInfo.setDistrictName(dto.getDistrictName());
        hospitalInfo.setStatus(0);
        int insert = hospitalInfoMapper.insert(hospitalInfo);
        ArrayList<HospitalPackageInfo> hospitalPackageInfos = new ArrayList<>();
        //添加套餐中间表
        dto.getPackageIdList().forEach(item->{
            HospitalPackageInfo hospitalPackageInfo = new HospitalPackageInfo();
            hospitalPackageInfo.setHospitalInfoId(hospitalInfo.getId());
            hospitalPackageInfo.setInfoPackageId(item.getId());
            hospitalPackageInfo.setOrderNum(item.getOrderNum());
            hospitalPackageInfos.add(hospitalPackageInfo);
        });
        hospitalPackageInfoService.saveBatch(hospitalPackageInfos);
        return insert;

    }

    @Override
    public int updateHospitalInfo(HospitalInfoInsertDTO dto) {
        HospitalInfo hospitalInfo = new HospitalInfo();
        hospitalInfo.setHospitalName(dto.getHospitalName());
        hospitalInfo.setDistrictName(dto.getDistrictName());
        int update = hospitalInfoMapper.updateById(hospitalInfo);
        //删除原来的套餐关联表，根据医院信息id删除
        LambdaUpdateWrapper<HospitalPackageInfo> hospitalPackageInfoUpdateWrapper = new LambdaUpdateWrapper<>();
        hospitalPackageInfoUpdateWrapper.eq(HospitalPackageInfo::getHospitalInfoId,dto.getId()).set(HospitalPackageInfo::getDeleteFlag,1);
        int delete = hospitalPackageInfoMapper.update(hospitalPackageInfoUpdateWrapper);

        //添加套餐中间表
        ArrayList<HospitalPackageInfo> hospitalPackageInfos = new ArrayList<>();
        dto.getPackageIdList().forEach(item->{
            HospitalPackageInfo hospitalPackageInfo = new HospitalPackageInfo();
            hospitalPackageInfo.setHospitalInfoId(hospitalInfo.getId());
            hospitalPackageInfo.setInfoPackageId(item.getId());
            hospitalPackageInfo.setOrderNum(item.getOrderNum());
            hospitalPackageInfos.add(hospitalPackageInfo);
        });
        hospitalPackageInfoService.saveBatch(hospitalPackageInfos);
        return update;
    }

    @Override
    public IPage<HospitalInfoVO> listByPage(HospitalInfoQueryDTO dto) {
        Page<HospitalInfoVO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        return hospitalInfoMapper.listByPage(page,dto);
    }

    @Override
    public int deleteHospitalInfo(Long id) {
        LambdaQueryWrapper<HospitalInfo> hospitalInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        hospitalInfoLambdaQueryWrapper.eq(HospitalInfo::getId,id).gt(HospitalInfo::getStatus,0);
        Long aLong = hospitalInfoMapper.selectCount(hospitalInfoLambdaQueryWrapper);
        if (aLong > 0){
            return 0;
        }else {
            LambdaUpdateWrapper<HospitalInfo> hospitalInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            hospitalInfoLambdaUpdateWrapper.eq(HospitalInfo::getId, id).set(HospitalInfo::getDeleteFlag, 1);
            int update = hospitalInfoMapper.update(hospitalInfoLambdaUpdateWrapper);
            LambdaUpdateWrapper<HospitalPackageInfo> hospitalPackageInfoUpdateWrapper = new LambdaUpdateWrapper<>();
            hospitalPackageInfoUpdateWrapper.eq(HospitalPackageInfo::getHospitalInfoId,id).set(HospitalPackageInfo::getDeleteFlag,1);
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
}
