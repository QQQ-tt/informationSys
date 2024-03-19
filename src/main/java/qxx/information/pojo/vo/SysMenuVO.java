package qxx.information.pojo.vo;

import lombok.Data;

/**
 * @Author: 何现成
 * @Date: 2024/3/16 12:05
 */
@Data
public class SysMenuVO {

    private Integer id;

    private String menuName;

    private String menuCode;

    private String path;

    private String icon;

    private Integer parentId;

}
