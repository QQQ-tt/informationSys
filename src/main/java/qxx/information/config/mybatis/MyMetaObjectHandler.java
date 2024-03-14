package qxx.information.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import qxx.information.config.CommonMethod;

import java.time.LocalDateTime;

/**
 * @author qtx
 * @since 2022/8/30
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Resource
    private CommonMethod commonMethod;

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createOn", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createBy", String.class, commonMethod.getSysUserId());
        log.info("end insert fill ...");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateOn", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "updateBy", String.class, commonMethod.getSysUserId());
        log.info("end update fill ...");
    }
}

