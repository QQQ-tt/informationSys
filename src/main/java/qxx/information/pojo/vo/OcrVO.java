package qxx.information.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qtx
 * @since 2024/3/24 11:12
 */
@Data
public class OcrVO implements Serializable {

    private String errcode;
    private String errmsg;
    private String type;
    private String name;
    private String id;
    private String addr;
    private String gender;
    private String nationality;
}
