package qxx.information.pojo.dto;


import lombok.Data;
import qxx.information.config.PageDTO;
import qxx.information.entity.HospitalInfo;

import java.util.List;

/**
 * @Author: 何现成
 * @Date: 2024/3/15 22:32
 */
@Data
public class HospitalInfoInsertDTO  {

    private Long hospitalId;


    //医院id
    private Long id;

    /**
     * 地区
     */
    private String districtName;

    /**
     * 医院名字
     */
    private String HospitalName;

    /**
     * 套餐主键id
     */
    private List<HospitalPackageInsertDTO> packageIdList;

}
