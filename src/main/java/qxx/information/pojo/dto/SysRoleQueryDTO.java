package qxx.information.pojo.dto;

import lombok.Data;
import qxx.information.config.PageDTO;
import qxx.information.entity.SysRole;

/**
 * @Author: 何现成
 * @Date: 2024/3/16 0:46
 */
@Data
public class SysRoleQueryDTO extends PageDTO<SysRole> {

    private String roleName;

}
