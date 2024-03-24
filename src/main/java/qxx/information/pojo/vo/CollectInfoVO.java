package qxx.information.pojo.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import qxx.information.config.excel.CollectInfoStatusConverter;
import qxx.information.config.excel.LocalDateConverter;
import qxx.information.config.excel.LocalDateTimeConverter;
import qxx.information.config.excel.SexConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: 何现成
 * @Date: 2024/3/13 14:16
 */
@Data
public class CollectInfoVO {

    @ExcelIgnore
    private Long id;

    @ExcelProperty(value = "地区")
    private String district;

    @ExcelProperty(value = "医院名称")
    private String hospitalName;

    @ExcelProperty(value = "姓名")
    private String collectName;

    @ExcelProperty(value = "性别" ,converter = SexConverter.class)
    private Integer collectSex;

    @ExcelProperty(value = "民族")
    private String collectNation;

    @ExcelProperty(value = "出生日期")
    private LocalDate collectBirthData;

    @ExcelProperty(value = "身份证号码")
    private String collectCard;

    @ExcelProperty(value = "检查套餐")
    private String packageName;

    @ExcelIgnore
    private Long packageId;

    @ExcelProperty(value = "试管条码号")
    private String tubeCard;

    @ExcelProperty(value = "采样点")
    private String samplingPoint;

    @ExcelProperty(value = "作废状态" ,converter = CollectInfoStatusConverter.class)
    private Integer status;

    @ExcelProperty(value = "录入人")
    private String createName;

    @ExcelProperty(value = "录入时间" ,converter = LocalDateTimeConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private LocalDateTime createOn;

}
