package qxx.information.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import qxx.information.config.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色管理
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Getter
@Setter
@TableName("sys_role")
public class SysRole extends BaseEntity {

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 角色编号
     */
    @TableField("role_code")
    private String roleCode;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
