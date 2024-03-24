package qxx.information.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;
import qxx.information.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import qxx.information.pojo.dto.LoginDTO;
import qxx.information.pojo.dto.SysUserDTO;
import qxx.information.pojo.dto.SysUserPasswordDTO;
import qxx.information.pojo.vo.LoginVO;
import qxx.information.pojo.vo.OcrVO;
import qxx.information.pojo.vo.SysUserVO;

import java.io.IOException;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author qtx
 * @since 2024-03-12
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 分页查询用户信息
     * @param dto 查询条件
     * @return 分页结果
     */
    Page<SysUserVO> listSysUserPage(SysUserDTO dto);

    /**
     * 保存或更新用户信息
     * @param dto 用户信息
     * @return 是否保存成功
     */
    boolean saveOrUpdateSysUser(SysUser dto);

    /**
     * 通过id修改密码
     * @param dto 用户参数
     * @return 是否修改成功
     */
    boolean updatePasswordById(SysUserPasswordDTO dto);

    /**
     * 通过id修改状态
     * @param id 用户id
     * @param flag 状态
     * @return 是否修改成功
     */
    boolean updateStatusById(Long id, Boolean flag);

    /**
     * 通过id删除用户信息
     * @param id 用户id
     * @return 是否删除成功
     */
    boolean deleteSysUserById(Long id);

    /**
     * 用户登录
     * @param dto 登录信息
     * @return 登录结果
     */
    LoginVO login(LoginDTO dto);

    /**
     * 刷新token
     * @return 新密钥
     */
    String flushedToken();

    /**
     * 修改用户密码
     * @param dto 用户信息
     * @return 是否修改成功
     */
    boolean loginUpdatePassword(SysUserPasswordDTO dto);

    /**
     * 创建用户
     * @param dto 用户信息
     * @return 是否保存成功
     */
    boolean createSysUser(SysUser dto);

    /**
     * 身份证识别
     * @param file 文件
     * @param accessToken 密钥
     * @return 识别结果
     */
    OcrVO ocr(MultipartFile file, String accessToken) throws IOException;
}
