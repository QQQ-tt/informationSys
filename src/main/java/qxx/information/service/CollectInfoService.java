package qxx.information.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.annotations.Param;
import qxx.information.entity.CollectInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import qxx.information.pojo.dto.CollectInfoQueryDTO;
import qxx.information.pojo.dto.CollectInfoRecordQueryDTO;
import qxx.information.pojo.vo.CollectInfoRecordVO;
import qxx.information.pojo.vo.CollectInfoVO;
import qxx.information.pojo.vo.CollectStatusInfoVO;

import java.util.List;

/**
 * <p>
 * 采集信息管理表 服务类
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
public interface CollectInfoService extends IService<CollectInfo> {

    /**
     * 采添加集信息
     * @param collectInfo 采集信息
     * @return 是否添加成功
     * @date 2024/3/13 11:02
     * @version 1.0
     */
    Boolean insertCollectInfo(CollectInfo collectInfo);


    /**
     * 查询小程序记录信息
     * @param dto 查询条件
     * @return 记录查询信息
     * @date 2024/3/13 14:20
     * @version 1.0
     */
    List<CollectInfoRecordVO> queryCollectInfoRecordList(CollectInfoRecordQueryDTO dto);

    /**
     * 根据主键id查询详情
     * @param id 采集信息的主键id
     * @return 查看详情信息
     * @date 2024/3/13 19:58
     * @version 1.0
     */
    CollectInfoVO getByIdQueryCollectInfo(Long id);

    /**
     * 分页查询采集信息
     * @param dto 查询条件
     * @return 分页信息
     * @date 2024/3/13 20:29
     * @version 1.0
     */
    IPage<CollectInfoVO> listByCollectInfoPage(CollectInfoQueryDTO dto, HttpServletRequest request);

    void exportCollectInfo(HttpServletResponse response,CollectInfoQueryDTO dto) throws ClassNotFoundException;

    /**
     * 查询采集信息作废状态
     * @param dto 查询条件
     * @return 状态信息
     * @date 2024/4/3 20:24
     * @version 1.0
     */
    CollectStatusInfoVO queryCollectStatus(CollectInfoRecordQueryDTO dto);

}
