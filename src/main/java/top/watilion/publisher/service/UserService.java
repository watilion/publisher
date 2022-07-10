package top.watilion.publisher.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.watilion.publisher.po.UserPo;
import top.watilion.publisher.vo.UserAddVo;
import top.watilion.publisher.vo.UserUpdateVo;
import top.watilion.publisher.vo.UserVo;

/**
 * @author watilion
 * @date 2022/6/7 22:54
 */
public interface UserService extends IService<UserPo> {

    /**
     * 根据用户名检查用户是否存在
     * @param username 用户名
     * @return 检查结果
     */
    UserPo getByUsername(String username);

    /**
     * 根据用户ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    UserVo getById(Long id);

    /**
     * 用户新增保存
     * @param userAddVo 用户新增对象
     * @return 用户信息
     */
    UserVo add(UserAddVo userAddVo);

    /**
     * 用户修改保存
     * @param userUpdateVo 用户修改对象
     * @return 用户信息
     */
    UserVo update(UserUpdateVo userUpdateVo);

    /**
     * 根据用户ID删除用户
     * @param id 用户ID
     * @return 删除结果
     */
    boolean delete(Long id);
}
