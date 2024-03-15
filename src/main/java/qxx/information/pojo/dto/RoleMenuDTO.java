package qxx.information.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleMenuDTO {

    //角色id
    private Integer roleId;

    //修改权限的菜单id
    private List<Integer> menuIdList;


}
