package top.watilion.publisher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.watilion.publisher.constant.SystemConstant;
import top.watilion.publisher.dao.UserDao;
import top.watilion.publisher.exception.BaseException;
import top.watilion.publisher.exception.DuplicateException;
import top.watilion.publisher.mapstruct.UserMapStruct;
import top.watilion.publisher.params.UserPageParams;
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
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Page<UserPo> page(UserPageParams userPageParams) {
        Page<UserPo> userPoPage = new Page<>(userPageParams.getCurrent(), userPageParams.getPageSize());
        LambdaQueryWrapper<UserPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(userPageParams.getName()), UserPo::getName, userPageParams.getName())
                .like(StringUtils.isNotBlank(userPageParams.getUsername()), UserPo::getUsername, userPageParams.getUsername())
                .eq(userPageParams.getDelFlag() != null, UserPo::getDelFlag, userPageParams.getDelFlag());
        userDao.selectPage(userPoPage,queryWrapper);
        return userPoPage;
    }

    @Override
    public UserPo getByUsername(String username) {
        LambdaQueryWrapper<UserPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserPo::getUsername, username);
        List<UserPo> userPoList = userDao.selectList(lambdaQueryWrapper);
        if (CollectionUtils.isEmpty(userPoList)) {
            return null;
        } else {
            return userPoList.get(0);
        }
    }

    @Override
    public UserVo getById(Long id) {
        UserPo userPo = userDao.selectById(id);
        return UserMapStruct.INSTANCE.poToVo(userPo);
    }

    @Override
    public UserVo add(UserAddVo userAddVo) {
        this.checkUsername(userAddVo.getUsername());
        UserPo userPo = UserMapStruct.INSTANCE.userAddVoToPo(userAddVo);
        int count = userDao.insert(userPo);
        if (count == 0) {
            return null;
        }
        return getById(userPo.getId());
    }

    private void checkUsername(String username) {
        UserPo oldUserPo = this.getByUsername(username);
        if (oldUserPo == null) {
            return;
        }
        DuplicateException duplicateException ;
        if (oldUserPo.getDelFlag() != null && Objects.equals(oldUserPo.getDelFlag(), SystemConstant.SYSTEM_NOT_DELETED_VALUE)) {
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
        UserPo oldUserPo = userDao.selectById(userUpdateVo.getId());
        if (oldUserPo == null) {
            log.warn("用户ID[{}]不存在", userUpdateVo.getId());
            throw new BaseException("用户ID不存在");
        }
        //如果修改用户名则判断修改后的用户名是否存在
        if (!userUpdateVo.getUsername().equals(oldUserPo.getUsername())) {
            this.checkUsername(userUpdateVo.getUsername());
        }
        UserPo userPo = UserMapStruct.INSTANCE.userUpdateVoToPo(userUpdateVo);
        userDao.updateById(userPo);
        return this.getById(userUpdateVo.getId());
    }

    @Override
    public boolean delete(Long id) {
        UserPo userPo = userDao.selectById(id);
        if (userPo == null || Objects.equals(userPo.getDelFlag(), SystemConstant.SYSTEM_DELETED_VALUE)){
            log.warn("用户ID[{}]不存在，或已删除", id);
            throw new BaseException("用户ID不存在，或已删除");
        }
        userPo.setDelFlag(SystemConstant.SYSTEM_DELETED_VALUE);
        return userDao.updateById(userPo) > 0;
    }
}
