package qxx.information.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import qxx.information.config.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 检查套餐表
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Getter
@Setter
@TableName("package_info")
public class PackageInfo extends BaseEntity {

    /**
     * 套餐名称
     */
    @TableField("package_name")
    private String packageName;

    /**
     * 引用次数
     */
    @TableField("status")
    private Integer status;
}
