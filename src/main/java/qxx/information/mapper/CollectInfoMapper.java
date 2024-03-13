package qxx.information.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import qxx.information.entity.CollectInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qxx.information.pojo.dto.CollectInfoQueryDTO;
import qxx.information.pojo.dto.CollectInfoRecordQueryDTO;
import qxx.information.pojo.vo.CollectInfoRecordVO;
import qxx.information.pojo.vo.CollectInfoVO;

import java.util.List;

/**
 * <p>
 * 采集信息管理表 Mapper 接口
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
@Mapper
public interface CollectInfoMapper extends BaseMapper<CollectInfo> {

    List<CollectInfoRecordVO> queryCollectInfoRecordList(@Param("dto")CollectInfoRecordQueryDTO dto);

    CollectInfoVO getByIdQueryCollectInfo(Long id);

    IPage<CollectInfoVO> listByCollectInfoPage(@Param("page") Page<CollectInfoVO> page, @Param("dto") CollectInfoQueryDTO dto);

}
