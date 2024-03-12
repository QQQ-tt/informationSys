package qxx.information.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import qxx.information.config.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 菜单信息表
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Getter
@Setter
@TableName("sys_menu")
public class SysMenu extends BaseEntity {

    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 菜单编号
     */
    @TableField("menu_code")
    private String menuCode;

    /**
     * 菜单地址
     */
    @TableField("path")
    private String path;

    /**
     * 父级id
     */
    @TableField("parent_id")
    private Integer parentId;
}
