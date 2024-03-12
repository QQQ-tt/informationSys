package qxx.information.config.excel;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author qtx
 * @since 2022/9/8
 */
@Data
@Builder
public class ExcelVO {
    private String sheet;
    private List<List<String>> listsHead;
    private List<List<Object>> listsData;
}
