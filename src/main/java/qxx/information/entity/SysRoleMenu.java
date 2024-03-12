package qxx.information.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import qxx.information.config.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色菜单关系表
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Getter
@Setter
@TableName("sys_role_menu")
public class SysRoleMenu extends BaseEntity {

    /**
     * 角色id
     */
    @TableField("role_id")
    private Integer roleId;

    /**
     * 菜单id
     */
    @TableField("menu_id")
    private Integer menuId;
}
