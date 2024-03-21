package qxx.information.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: 何现成
 * @Date: 2024/3/15 23:25
 */
@Data
public class HospitalInfoVO {

    @ExcelProperty(value = "医院信息id")
    private Long id;

    /**
     * 地区id
     */
    private String regionId;

    @ExcelProperty(value = "地区")
    private String districtName;

    @ExcelProperty(value = "医院名称")
    private String hospitalName;

    @ExcelProperty(value = "套餐名字")
    private String packageName;

    @ExcelProperty(value = "创建人")
    private String createBy;

    @ExcelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private LocalDateTime createOn;

}
