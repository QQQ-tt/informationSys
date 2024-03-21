package qxx.information.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qxx.information.entity.HospitalInfo;
import qxx.information.entity.HospitalPackageInfo;
import qxx.information.mapper.CollectInfoMapper;
import qxx.information.mapper.HospitalInfoMapper;
import qxx.information.mapper.HospitalPackageInfoMapper;
import qxx.information.pojo.dto.HospitalInfoInsertDTO;
import qxx.information.pojo.dto.HospitalInfoQueryDTO;
import qxx.information.pojo.dto.HospitalPackageInsertDTO;
import qxx.information.pojo.vo.HospitalInfoVO;
import qxx.information.pojo.vo.HospitalPackageInfoVO;
import qxx.information.service.HospitalInfoService;
import qxx.information.service.HospitalPackageInfoService;
import qxx.information.service.PackageInfoService;

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
@Service
public class HospitalInfoServiceImpl extends ServiceImpl<HospitalInfoMapper, HospitalInfo> implements HospitalInfoService {


    @Autowired
    private HospitalInfoMapper hospitalInfoMapper;

    @Autowired
    private HospitalPackageInfoService hospitalPackageInfoService;

    @Autowired
    private HospitalPackageInfoMapper hospitalPackageInfoMapper;

    @Autowired
    private PackageInfoService packageInfoService;

    @Autowired
    private CollectInfoMapper collectInfoMapper;



    @Override
    public int insertHospitalInfo(HospitalInfoInsertDTO dto) {

        HospitalInfo hospitalInfo = new HospitalInfo();
        hospitalInfo.setHospitalName(dto.getHospitalName());
        hospitalInfo.setDistrictName(dto.getDistrictName());
        hospitalInfo.setRegionId(dto.getRegionId());
        hospitalInfo.setStatus(0);
        int insert = hospitalInfoMapper.insert(hospitalInfo);
        if(CollectionUtils.isNotEmpty(dto.getPackageIdList())) {
            ArrayList<HospitalPackageInfo> hospitalPackageInfos = new ArrayList<>();
            //添加套餐中间表
            dto.getPackageIdList().forEach(item -> {
                HospitalPackageInfo hospitalPackageInfo = new HospitalPackageInfo();
                hospitalPackageInfo.setHospitalInfoId(hospitalInfo.getId());
                hospitalPackageInfo.setInfoPackageId(item.getId());
                hospitalPackageInfo.setOrderNum(item.getOrderNum());
                hospitalPackageInfos.add(hospitalPackageInfo);
            });
            hospitalPackageInfoService.saveBatch(hospitalPackageInfos);
        }
        return insert;
    }

    public List<HospitalPackageInfoVO> filtrationDelete(List<HospitalPackageInfoVO> all,List<HospitalPackageInsertDTO> updatePackageIdList){
        List<HospitalPackageInfoVO> collect = all.stream().filter(item -> updatePackageIdList.stream().noneMatch(item2 -> item2.getId() .equals(item.getInfoPackageId()) ))
                .collect(Collectors.toList());
        return collect;
    }

    public List<HospitalPackageInsertDTO> filtrationInsert(List<HospitalPackageInsertDTO> updatePackageIdList,List<HospitalPackageInfoVO> all){
        List<HospitalPackageInsertDTO> collect = updatePackageIdList.stream().filter(h1 -> all.stream().noneMatch(h2 -> h2.getInfoPackageId().equals(h1.getId())))
                .collect(Collectors.toList());
        return collect;
    }


    @Override
    public int updateHospitalInfo(HospitalInfoInsertDTO dto) {

        //查询本次原来的医院套餐信息
        List<HospitalPackageInfoVO> longs = hospitalPackageInfoMapper.listByHospitalInfoId(dto.getId());

        List<HospitalPackageInfoVO> hospitalPackageInfoVOS = filtrationDelete(longs, dto.getPackageIdList());
        //减少原来的菜单引用次数
        hospitalPackageInfoVOS.forEach(item->{
            packageInfoService.updateStatusById(item.getHospitalInfoId(), false);
        });

        List<HospitalPackageInsertDTO> hospitalPackageInsertDTOS = filtrationInsert(dto.getPackageIdList(), longs);
        hospitalPackageInsertDTOS.forEach(item2->{
            packageInfoService.updateStatusById(item2.getId(), true);
        });


        HospitalInfo hospitalInfo = new HospitalInfo();
        hospitalInfo.setHospitalName(dto.getHospitalName());
        hospitalInfo.setDistrictName(dto.getDistrictName());
        hospitalInfo.setRegionId(dto.getRegionId());
        int update = hospitalInfoMapper.updateById(hospitalInfo);
        //删除原来的套餐关联表，根据医院信息id删除
        LambdaUpdateWrapper<HospitalPackageInfo> hospitalPackageInfoUpdateWrapper = new LambdaUpdateWrapper<>();
        hospitalPackageInfoUpdateWrapper.eq(HospitalPackageInfo::getHospitalInfoId,dto.getId()).set(HospitalPackageInfo::getDeleteFlag,1);
        int delete = hospitalPackageInfoMapper.update(hospitalPackageInfoUpdateWrapper);

        //添加套餐中间表
        ArrayList<HospitalPackageInfo> hospitalPackageInfos = new ArrayList<>();
        dto.getPackageIdList().forEach(item->{
            HospitalPackageInfo hospitalPackageInfo = new HospitalPackageInfo();
            hospitalPackageInfo.setHospitalInfoId(dto.getId());
            hospitalPackageInfo.setInfoPackageId(item.getId());
            hospitalPackageInfo.setOrderNum(item.getOrderNum());
            hospitalPackageInfos.add(hospitalPackageInfo);
        });
        hospitalPackageInfoService.saveBatch(hospitalPackageInfos);
        return delete;
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

    @Override
    public List<HospitalInfo> queryDistrictGetHospitalInfo(HospitalInfoQueryDTO districtName) {
        LambdaQueryWrapper<HospitalInfo> hospitalInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        hospitalInfoLambdaQueryWrapper.in(districtName.getDistrictNames() != null && !districtName.getDistrictNames()
                                .isEmpty(), HospitalInfo::getDistrictName,
                        districtName.getDistrictNames())
                .eq(HospitalInfo::getDeleteFlag, 0);
        List<HospitalInfo> hospitalInfos = hospitalInfoMapper.selectList(hospitalInfoLambdaQueryWrapper);
        return hospitalInfos;
    }
}
