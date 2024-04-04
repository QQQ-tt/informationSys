package qxx.information.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;

/**
 * @Author: 何现成
 * @Date: 2024/3/13 10:17
 */
@Data
public class CollectInfoRecordQueryDTO {


    private String collectName;



    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate createON;


    private Integer status;

}
