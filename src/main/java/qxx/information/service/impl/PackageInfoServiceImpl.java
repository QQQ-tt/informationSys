package qxx.information.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import qxx.information.config.BaseEntity;
import qxx.information.entity.PackageInfo;
import qxx.information.mapper.PackageInfoMapper;
import qxx.information.pojo.dto.PackageDTO;
import qxx.information.pojo.vo.PackageVO;
import qxx.information.service.PackageInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 检查套餐表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Service
public class PackageInfoServiceImpl extends ServiceImpl<PackageInfoMapper, PackageInfo> implements PackageInfoService {

    @Override
    public Page<PackageVO> listPackagePage(PackageDTO dto) {
        return baseMapper.selectPageNew(dto.getPage(),
                Wrappers.lambdaQuery(PackageInfo.class)
                        .eq(StringUtils.isNotBlank(dto.getName()),
                                PackageInfo::getPackageName, dto.getName()));
    }

    @Override
    public boolean saveOrUpdatePackage(PackageInfo entity) {
        PackageInfo one = getOne(Wrappers.lambdaQuery(PackageInfo.class)
                .eq(PackageInfo::getPackageName, entity.getPackageName())
                .ne(entity.getId() != null,
                        BaseEntity::getId, entity.getId()));
        if (Objects.isNull(one)){
            return saveOrUpdate(entity);
        }
        return false;
    }

    @Override
    public boolean deletePackageById(Long id) {
        return false;
    }

    @Override
    public PackageInfo getPackageById(Long id) {
        return null;
    }
}
