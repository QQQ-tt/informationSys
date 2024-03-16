package qxx.information.service;

import com.baomidou.mybatisplus.extension.service.IService;
import qxx.information.entity.SysRegion;

import java.util.List;

/**
 * <p>
 * 地区 服务类
 * </p>
 *
 * @author hxc
 * @since 2024-03-16
 */
public interface SysRegionService extends IService<SysRegion> {

    List<SysRegion> queryByIdRegion(Long id);
}
