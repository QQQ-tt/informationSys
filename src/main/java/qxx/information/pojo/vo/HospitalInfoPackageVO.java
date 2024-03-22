package qxx.information.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: 何现成
 * @Date: 2024/3/22 21:45
 */
@Data
public class HospitalInfoPackageVO {

    private Long id;

    private String packageName;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createOn;

    private Integer orderNum;


}
