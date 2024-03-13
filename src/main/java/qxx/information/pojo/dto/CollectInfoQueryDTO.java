package qxx.information.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

/**
 * @Author: 何现成
 * @Date: 2024/3/13 20:19
 */
@Data
public class CollectInfoQueryDTO {


    private String district;

    private String hospitalName;

    private String collectName;

    private String collectCard;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate BeginCreateON;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate endCreateON;

}
