package qxx.information.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDate;

/**
 * @Author: 何现成
 * @Date: 2024/3/13 14:16
 */
@Data
public class CollectInfoVO {

    @ExcelProperty(value = "地区")
    private String district;

    @ExcelProperty(value = "民族")
    private String collectNation;

    @ExcelProperty(value = "医院名字")
    private String hospitalName;

    @ExcelProperty(value = "采集人名字")
    private String collectName;

    @ExcelProperty(value = "采集人性别")
    private Boolean collectSex;

    @ExcelProperty(value = "采集人出生日期")
    private LocalDate collectBirthData;

    @ExcelProperty(value = "采集人省份证号")
    private String collectCard;

    @ExcelProperty(value = "检查套餐")
    private String packageName;

    @ExcelProperty(value = "试管条码号")
    private String tubeCard;

    @ExcelProperty(value = "采样点")
    private String samplingPoint;

    @ExcelProperty(value = "是否作废 0是否 1是是")
    private Integer status;

    @ExcelProperty(value = "录入人")
    private String createName;

    @ExcelProperty(value = "录入时间")
    private LocalDate createOn;

}
