package qxx.information.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import qxx.information.config.BaseEntity;
import lombok.Getter;
import lombok.Setter;

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
    private Integer userId;

    /**
     * 医院id
     */
    @TableField("hospital_id")
    private Integer hospitalId;
}
