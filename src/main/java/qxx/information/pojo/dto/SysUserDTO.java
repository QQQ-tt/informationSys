package qxx.information.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import qxx.information.config.PageDTO;
import qxx.information.entity.SysUser;

/**
 * @author qtx
 * @since 2024/3/13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserDTO extends PageDTO<SysUser> {

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String userId;

    /**
     * 医院
     */
    private String hospital;
}
