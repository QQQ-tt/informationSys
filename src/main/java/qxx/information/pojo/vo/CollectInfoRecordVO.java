package qxx.information.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

/**
 * @Author: 何现成
 * @Date: 2024/3/13 13:50
 */
@Data
public class CollectInfoRecordVO {


    private Long id;

    @ExcelProperty(value = "采集人")
    private String collectName;

    @ExcelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate createON;

    @ExcelProperty(value = "是否作废")
    private Integer status;

}
