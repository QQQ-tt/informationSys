package qxx.information.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import qxx.information.entity.PackageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import qxx.information.pojo.dto.PackageDTO;
import qxx.information.pojo.vo.PackageVO;

/**
 * <p>
 * 检查套餐表 服务类
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
public interface PackageInfoService extends IService<PackageInfo> {

    /**
     * 分页查询
     * @param dto 查询条件
     * @return 分页集合
     */
    Page<PackageVO> listPackagePage(PackageDTO dto);

    /**
     * 保存或更新
     * @param entity 实体
     * @return 是否成功
     */
    boolean saveOrUpdatePackage(PackageInfo entity);

    /**
     * 更新引用状态
     *
     * @param id   套餐id
     * @param flag true 引用数据； false 取消引用
     * @return 是否成功
     */
    boolean updateStatusById(Long id,boolean flag);

    /**
     * 删除
     * @param id 主键
     * @return 是否成功
     */
    boolean deletePackageById(Long id);

    /**
     * 根据id查询
     * @param id 主键
     * @return 实体
     */
    PackageInfo getPackageById(Long id);
}
