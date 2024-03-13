package qxx.information.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import qxx.information.config.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Getter
@Setter
@TableName("sys_user")
public class SysUser extends BaseEntity {

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 联系方式
     */
    @TableField("phone")
    private String phone;

    /**
     * 用户名
     */
    @TableField("user_id")
    private String userId;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 状态
     */
    @TableField("stats")
    private Boolean stats;

    @TableField(exist = false)
    private List<String> roles;

    @TableField(exist = false)
    private List<String> hospital;
}
