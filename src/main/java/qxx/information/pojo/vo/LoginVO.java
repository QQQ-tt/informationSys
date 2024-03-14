package qxx.information.pojo.vo;

import lombok.Builder;
import lombok.Data;
import qxx.information.entity.SysUserHospital;

import java.util.List;

/**
 * @author qtx
 * @since 2024/3/13
 */
@Data
@Builder
public class LoginVO {

    private String userId;

    private String name;

    private String token;

    private List<SysUserHospital> hospital;
}
