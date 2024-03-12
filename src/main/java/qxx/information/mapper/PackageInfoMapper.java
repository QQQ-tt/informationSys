package qxx.information.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import qxx.information.entity.PackageInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qxx.information.pojo.vo.PackageVO;

/**
 * <p>
 * 检查套餐表 Mapper 接口
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Mapper
public interface PackageInfoMapper extends BaseMapper<PackageInfo> {

    Page<PackageVO> selectPageNew(IPage<PackageInfo> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<PackageInfo> eq);
}
