package qxx.information.pojo.dto;

import lombok.Data;
import qxx.information.config.PageDTO;
import qxx.information.entity.HospitalInfo;

import java.util.List;

/**
 * @Author: 何现成
 * @Date: 2024/3/15 23:30
 */
@Data
public class HospitalInfoQueryDTO extends PageDTO<HospitalInfo> {


    private String districtName;


    private String hospitalName;

    private List<String> districtNames;

}
