package qxx.information.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import qxx.information.config.PageDTO;
import qxx.information.entity.PackageInfo;

/**
 * @author qtx
 * @since 2024/3/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PackageDTO extends PageDTO<PackageInfo> {

    private String packageName;
}
