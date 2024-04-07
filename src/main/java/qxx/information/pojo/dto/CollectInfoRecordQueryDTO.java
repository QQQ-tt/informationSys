package qxx.information.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import qxx.information.config.PageDTO;
import qxx.information.pojo.vo.CollectInfoRecordVO;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: 何现成
 * @Date: 2024/3/13 10:17
 */
@Data
public class CollectInfoRecordQueryDTO extends PageDTO<CollectInfoRecordVO> {


    private String collectName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime beginCreateON;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime endCreateON;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate createON;


    private Integer status;

}
