package qxx.information.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import qxx.information.config.BaseEntity;

/**
 * <p>
 * 用户医院关系表
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Getter
@Setter
@TableName("sys_user_hospital")
public class SysUserHospital extends BaseEntity {

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 医院id
     */
    @TableField("hospital_id")
    private Long hospitalId;

    /**
     * 医院名称
     */
    @TableField(exist = false)
    private String hospitalName;

    /**
     * 地区
     */
    @TableField(exist = false)
    private String districtName;

    @TableField(exist = false)
    private Integer status;
}
