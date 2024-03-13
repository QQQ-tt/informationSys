package qxx.information.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import qxx.information.config.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 医院信息与套餐信息中间表
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Getter
@Setter
@TableName("hospital_package_info")
public class HospitalPackageInfo extends BaseEntity {

    /**
     * 医院信息id
     */
    @TableField("hospital_info_id")
    private Long hospitalInfoId;

    /**
     * 套餐信息id
     */
    @TableField("info_package_id")
    private Long infoPackageId;

    /**
     * 排序号
     */
    @TableField("order_num")
    private Long orderNum;
}
