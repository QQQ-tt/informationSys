package qxx.information.pojo.dto;

import lombok.Data;
import qxx.information.config.PageDTO;
import qxx.information.entity.PackageInfo;

/**
 * @author qtx
 * @since 2024/3/12
 */
@Data
public class PackageDTO extends PageDTO<PackageInfo> {

    private String packageName;
}
