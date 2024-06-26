package qxx.information.config.excel;

import java.util.List;

/**
 * @author qtx
 * @since 2022/5/24
 */
public interface ConvertList<E> {

    /**
     * excel与数据库不一致，手动实现转换方式
     *
     * @param list
     * @return
     */
    List<E> convert(List<E> list);
}
