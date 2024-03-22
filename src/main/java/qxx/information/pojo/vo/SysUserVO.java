package qxx.information.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import qxx.information.entity.SysUserHospital;
import qxx.information.entity.SysUserRole;

import java.util.List;

/**
 * @author qtx
 * @since 2024/3/13
 */
@Data
public class SysUserVO {

    private Long id;

    private String name;

    private String phone;

    private String userId;

    private Boolean status;

    @JsonIgnore
    private String region;

    private List<String> regions;

    private List<SysUserHospital> listHospital;

    private List<SysUserRole> listRole;


}
