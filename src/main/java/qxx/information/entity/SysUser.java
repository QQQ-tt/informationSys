package qxx.information.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import qxx.information.config.BaseEntity;

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
    @TableField("status")
    private Boolean status;

    /**
     * 地区
     */
    @TableField("region")
    private String region;

    /**
     * 医院自动添加状态
     */
    @TableField("hospital_status")
    private Boolean hospitalStatus;

    @TableField(exist = false)
    private List<String> roles;

    @TableField(exist = false)
    private List<String> hospital;

    @TableField(exist = false)
    private List<List<Integer>> regions;

    @TableField(exist = false)
    private Boolean admin = false;
}
