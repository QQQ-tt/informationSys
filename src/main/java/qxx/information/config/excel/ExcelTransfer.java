package qxx.information.config.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import qxx.information.config.enums.DataEnums;
import qxx.information.config.exception.DataException;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qtx
 * @since 2022/5/17
 */
@Slf4j
@Component
public class ExcelTransfer<T> {
    private final String packageName = "com.project.echo.entity.admin.";

    private final int size = 36;

    /**
     * 上传excel 对用实体类不允许使用链式调用注解
     *
     * <p>@Accessors(chain = true) 对应实体类实现equals和hashCode方法会自动过滤与数据库重复的数据
     *
     * @param file    文件
     * @param service 对应实体的service
     * @return 成功与否
     * @throws ClassNotFoundException
     */
    public boolean importExcel(MultipartFile file, IService<T> service) throws ClassNotFoundException {
        isEmpty(file);
        String name = service.getClass()
                .getName();
        String s = name.substring(size, name.length() - 11);
        Class<?> aClass = Class.forName(packageName + s);
        try {
            DataListener<T> listener = new DataListener<>(service);
            EasyExcel.read(file.getInputStream(), aClass, listener)
                    .sheet()
                    .doRead();
            if (StringUtils.isNotBlank(listener.errorString)) {
                return false;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * 上传excel 对用实体类不允许使用链式调用注解 自定义格式转换
     *
     * <p>@Accessors(chain = true) 对应实体类实现equals和hashCode方法会自动过滤与数据库重复的数据
     *
     * @param file    文件
     * @param service 对应实体的service
     * @param list    实现自定义转换方法
     * @return 成功与否
     * @throws ClassNotFoundException 为找到对应的类
     */
    public String importExcel(MultipartFile file, IService<T> service, ConvertList<T> list) throws ClassNotFoundException {
        isEmpty(file);
        String name = service.getClass()
                .getName();
        String s = name.substring(size, name.length() - 11);
        Class<?> aClass = Class.forName(packageName + s);
        try {
            DataListener<T> listener = new DataListener<>(service, list);
            EasyExcel.read(file.getInputStream(), aClass, listener)
                    .sheet()
                    .doRead();
            if (StringUtils.isNotBlank(listener.errorString)) {
                return listener.errorString;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return "false";
        }
        return "true";
    }

    /**
     * 下载excel
     *
     * @param response http
     * @param list     需要下载的数据
     * @param name     文件名称
     * @param sheet    表名
     */
    public void exportExcel(HttpServletResponse response, List<T> list, String name, String sheet,
                            IService<T> service) throws ClassNotFoundException {
        String className = service.getClass()
                .getName();
        String s = className.substring(size, className.length() - 11);
        Class<?> aClass = Class.forName(packageName + s);
        export(response, list, name, sheet, aClass);
    }

    /**
     * 下载excel
     *
     * @param response http
     * @param list     需要下载的数据
     * @param name     文件名称
     * @param sheet    表名
     * @param aClass   实体类
     */
    public void exportExcel(HttpServletResponse response, List<T> list, String name, String sheet, Class<?> aClass) throws ClassNotFoundException {
        export(response, list, name, sheet, aClass);
    }

    private void export(HttpServletResponse response, List<T> list, String name, String sheet, Class<?> aClass) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode(name, "UTF-8")
                    .replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), aClass)
                    .sheet(sheet)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .doWrite(list);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void isEmpty(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new DataException(DataEnums.FAILED);
        }
    }

    /**
     * 下载excel，多实体，多sheet
     *
     * @param response http
     * @param name     文件名称
     * @param list     实体:数据
     */
    public void exportExcel(HttpServletResponse response, String name, List<ExcelVO> list) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode(name, "UTF-8")
                .replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .build()) {
            AtomicInteger i = new AtomicInteger(0);
            list.forEach(e -> {
                WriteSheet writeSheet = EasyExcel.writerSheet(i.get(), e.getSheet())
                        .head(e.getListsHead())
                        .build();
                i.getAndIncrement();
                excelWriter.write(e.getListsData(), writeSheet);
            });
        }
    }
}
