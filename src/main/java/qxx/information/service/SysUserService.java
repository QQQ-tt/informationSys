package qxx.information.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import qxx.information.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import qxx.information.pojo.dto.LoginDTO;
import qxx.information.pojo.dto.SysUserDTO;
import qxx.information.pojo.dto.SysUserPasswordDTO;
import qxx.information.pojo.vo.LoginVO;
import qxx.information.pojo.vo.SysUserVO;

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
     * 修改用户密码
     * @param dto 用户信息
     * @return 是否修改成功
     */
    boolean loginUpdatePassword(SysUserPasswordDTO dto);
}
