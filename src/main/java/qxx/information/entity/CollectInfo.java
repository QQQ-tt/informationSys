package qxx.information.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDate;

import qxx.information.config.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 采集信息管理表
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Getter
@Setter
@TableName("collect_info")
public class CollectInfo extends BaseEntity {

    /**
     * 地区名字
     */
    @TableField("district")
    private String district;

    /**
     * 医院名字
     */
    @TableField("hospital_name")
    private String hospitalName;

    /**
     * 采集人姓名
     */
    @TableField("collect_name")
    private String collectName;

    /**
     * 采集人性别：0是女，1是男
     */
    @TableField("collect_sex")
    private Boolean collectSex;

    /**
     * 采集人民族
     */
    @TableField("collect_nation")
    private String collectNation;

    /**
     * 采集人出生日期
     */
    @TableField("collect_birth_data")
    private LocalDate collectBirthData;

    /**
     * 采集人身份证号
     */
    @TableField("collect_card")
    private String collectCard;

    /**
     * 套餐ID
     */
    @TableField("package_id")
    private Integer packageId;

    /**
     * 试管条码号
     */
    @TableField("tube_card")
    private String tubeCard;

    /**
     * 采样点
     */
    @TableField("sampling_point")
    private String samplingPoint;

    /**
     * 作废状态：false是未作废，true是已作废
     */
    @TableField("status")
    private Boolean status;
}
