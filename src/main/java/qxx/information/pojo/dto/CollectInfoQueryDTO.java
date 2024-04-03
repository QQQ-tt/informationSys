package qxx.information.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import qxx.information.config.PageDTO;
import qxx.information.entity.PackageInfo;
import qxx.information.pojo.vo.CollectInfoVO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: 何现成
 * @Date: 2024/3/13 20:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CollectInfoQueryDTO extends PageDTO<CollectInfoVO> {


    private String district;

    private Long hospitalId;

    private String collectName;

    private String collectCard;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime beginCreateON;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime endCreateON;

    private List<Long> userHospitalIdList;

}
