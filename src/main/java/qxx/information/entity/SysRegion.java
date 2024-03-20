package qxx.information.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

/**
 * @Author: 何现成
 * @Date: 2024/3/16 22:24
 */
@Data
public class SysRegion {

    @TableId("id")
    private Long id;

    @TableField("name")
    private String name;

    @TableField("parent_id")
    private Integer parentId;

    private List<SysRegion> children;

}
