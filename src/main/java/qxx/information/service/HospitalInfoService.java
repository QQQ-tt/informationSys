package qxx.information.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import qxx.information.entity.HospitalInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import qxx.information.pojo.dto.HospitalInfoInsertDTO;
import qxx.information.pojo.dto.HospitalInfoQueryDTO;
import qxx.information.pojo.dto.RoleMenuDTO;
import qxx.information.pojo.vo.HospitalInfoVO;

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



}
