package qxx.information.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import qxx.information.entity.HospitalInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import qxx.information.pojo.dto.HospitalInfoInsertDTO;
import qxx.information.pojo.dto.HospitalInfoQueryDTO;
import qxx.information.pojo.vo.HospitalInfoVO;

import java.util.List;

/**
 * <p>
 * 医院信息管理表 服务类
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
public interface HospitalInfoService extends IService<HospitalInfo> {

    /**
     * 添加医院管理信息
     * @param dto 添加信息
     * @return 行数
     * @date 2024/3/15 22:36
     * @version 3.0
     */
    int insertHospitalInfo(HospitalInfoInsertDTO dto);


    /**
     * 编辑医院管理信息
     * @param dto 编辑信息
     * @return 行数
     * @date 2024/3/15 23:16
     * @version 3.0
     */
    int updateHospitalInfo(HospitalInfoInsertDTO dto);

    /**
     * 分页查询医院信息
     * @param dto 查询信息
     * @return 分页信息
     * @date 2024/3/15 23:31
     * @version 3.0
     */
    IPage<HospitalInfoVO> listByPage(HospitalInfoQueryDTO dto);

    /**
     * 删除医院信息id
     * @param id 医院信息主键id
     * @return 行数
     * @date 2024/3/16 0:09
     * @version 3.0
     */
    int deleteHospitalInfo(Long id);


    /**
     * 更新引用状态
     *
     * @param id   套餐id
     * @param flag true 引用数据； false 取消引用
     * @return 是否成功
     */
    boolean updateStatusById(Long id,boolean flag);

    /**
     * 根据地区名字获取医院信息
     * @param districtName 地区名字
     * @return 医院信息
     * @date 2024/3/17 21:37
     * @version 3.0
     */
    List<HospitalInfo> queryDistrictGetHospitalInfo(HospitalInfoQueryDTO districtName);


    /**
     * 根据医院主键id查询医院套餐信息
     * @param id 医院主键id
     * @return 医院套餐信息
     * @date 2024/3/22 21:49
     * @version 3.0
     */
    HospitalInfoVO queryByIdHospitalInfoPackage(Long id);

}
