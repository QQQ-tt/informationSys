package qxx.information.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import qxx.information.config.BaseEntity;
import qxx.information.config.enums.DataEnums;
import qxx.information.config.exception.DataException;
import qxx.information.entity.PackageInfo;
import qxx.information.mapper.PackageInfoMapper;
import qxx.information.pojo.dto.PackageDTO;
import qxx.information.pojo.vo.PackageVO;
import qxx.information.service.PackageInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

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
        if (Objects.isNull(one)) {
            return saveOrUpdate(entity);
        } else {
            throw new DataException(DataEnums.PROJECT_REPEAT);
        }
    }

    @Override
    public boolean updateStatusById(Long id, boolean flag) {
        return update(Wrappers.lambdaUpdate(PackageInfo.class)
                .eq(PackageInfo::getId, id)
                .setSql(flag, "status = status + 1")
                .setSql(!flag, "status = status - 1"));
    }

    @Override
    public boolean deletePackageById(Long id) {
        Integer status = Optional.ofNullable(getById(id))
                .orElse(new PackageInfo())
                .getStatus();
        if (status != null && status == 0) {
            return removeById(id);
        } else {
            throw new DataException(DataEnums.PROJECT_DELETE);
        }
    }

    @Override
    public PackageInfo getPackageById(Long id) {
        return getById(id);
    }
}
