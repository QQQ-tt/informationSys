package qxx.information.pojo.dto;

import lombok.Data;

/**
 * @author qtx
 * @since 2024/3/13
 */
@Data
public class SysUserPasswordDTO {
    private Long id;

    private String oldPassword;

    private String password;
}
