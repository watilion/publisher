package top.watilion.publisher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.watilion.publisher.constant.SystemConstant;
import top.watilion.publisher.dao.UserDao;
import top.watilion.publisher.exception.BaseException;
import top.watilion.publisher.exception.DuplicateException;
import top.watilion.publisher.mapstruct.UserMapStruct;
import top.watilion.publisher.po.UserPo;
import top.watilion.publisher.service.UserService;
import top.watilion.publisher.vo.UserAddVo;
import top.watilion.publisher.vo.UserUpdateVo;
import top.watilion.publisher.vo.UserVo;

import java.util.List;
import java.util.Objects;

/**
 * @author watilion
 * @date 2022/6/7 22:54
 */

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserPo> implements UserService {
    @Override
    public UserPo getByUsername(String username) {
        LambdaQueryWrapper<UserPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserPo::getUsername, username);
        List<UserPo> userPoList = getBaseMapper().selectList(lambdaQueryWrapper);
        if (CollectionUtils.isEmpty(userPoList)) {
            return null;
        } else {
            return userPoList.get(0);
        }
    }

    @Override
    public UserVo getById(Long id) {
        UserPo userPo = getBaseMapper().selectById(id);
        return UserMapStruct.INSTANCE.poToVo(userPo);
    }

    @Override
    public UserVo add(UserAddVo userAddVo) {
        this.checkUsername(userAddVo.getUsername());
        UserPo userPo = UserMapStruct.INSTANCE.userAddVoToPo(userAddVo);
        getBaseMapper().insert(userPo);
        return getById(userPo.getId());
    }

    void checkUsername(String username) {
        UserPo oldUserPo = this.getByUsername(username);
        if (oldUserPo == null) {
            return;
        }
        DuplicateException duplicateException ;
        if (oldUserPo.getStatus() != null && Objects.equals(oldUserPo.getStatus(), SystemConstant.SYSTEM_NOT_DELETED_VALUE)) {
            log.warn("[{}]有同名用户，请重新填写用户名", username);
            duplicateException = new DuplicateException("当前有同名用户，请重新填写用户名");
        } else {
            log.warn("[{}]有同名已删除用户，请从已删除列表恢复", username);
            duplicateException = new DuplicateException("当前有同名已删除用户，请从已删除列表恢复");
        }
        throw duplicateException;
    }

    @Override
    public UserVo update(UserUpdateVo userUpdateVo) {
        UserPo oldUserPo = getBaseMapper().selectById(userUpdateVo.getId());
        if (oldUserPo == null) {
            log.warn("用户ID[{}]不存在", userUpdateVo.getId());
            throw new BaseException("用户ID不存在");
        }
        //如果修改用户名则判断修改后的用户名是否存在
        if (!userUpdateVo.getUsername().equals(oldUserPo.getUsername())) {
            this.checkUsername(userUpdateVo.getUsername());
        }
        UserPo userPo = UserMapStruct.INSTANCE.userUpdateVoToPo(userUpdateVo);
        getBaseMapper().updateById(userPo);
        return this.getById(userUpdateVo.getId());
    }

    @Override
    public boolean delete(Long id) {
        UserPo userPo = getBaseMapper().selectById(id);
        if (userPo == null || Objects.equals(userPo.getStatus(), SystemConstant.SYSTEM_DELETED_VALUE)){
            log.warn("用户ID[{}]不存在，或已删除", id);
            throw new BaseException("用户ID不存在，或已删除");
        }
        userPo.setStatus(SystemConstant.SYSTEM_DELETED_VALUE);
        return getBaseMapper().updateById(userPo) > 0;
    }
}
