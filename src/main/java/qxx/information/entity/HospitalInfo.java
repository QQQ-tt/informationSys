package qxx.information.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import qxx.information.config.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 医院信息管理表
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Getter
@Setter
@TableName("hospital_info")
public class HospitalInfo extends BaseEntity {

    /**
     * 地区id
     */
    private String regionId;

    /**
     * 地区
     */
    @TableField("district_name")
    private String districtName;

    /**
     * 医院名称
     */
    @TableField("hospital_name")
    private String hospitalName;

    /**
     * 引用次数
     */
    @TableField("status")
    private Integer status;
}
