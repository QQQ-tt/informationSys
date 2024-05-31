package qxx.information.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: 何现成
 * @Date: 2024/3/16 0:30
 */
@Data
public class SysRoleVO {

    @ExcelProperty(value = "id")
    private Long id;

    @ExcelProperty(value = "角色名称")
    private String roleName;

    @ExcelProperty(value = "角色编码")
    private String roleCode;

    @ExcelProperty(value = "使用状态")
    private String status;

    @ExcelProperty(value = "备注")
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private LocalDateTime createOn;

    List<SysMenuVO> sysMenuVOS;

}
