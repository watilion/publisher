package top.watilion.publisher.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import top.watilion.publisher.constant.SystemConstant;
import top.watilion.publisher.dao.UserDao;
import top.watilion.publisher.exception.BaseException;
import top.watilion.publisher.exception.DuplicateException;
import top.watilion.publisher.params.UserPageParams;
import top.watilion.publisher.po.UserPo;
import top.watilion.publisher.vo.UserAddVo;
import top.watilion.publisher.vo.UserUpdateVo;
import top.watilion.publisher.vo.UserVo;

import java.util.Collections;
import java.util.List;

/**
 * @author watilion
 * @date 2022/7/11 00:54
 */
@Transactional
@SpringBootTest
class UserServiceImplTest {

    @Spy
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(UserServiceImplTest.class);
    }

    @Test
    void page() {
        UserPageParams userPageParams = new UserPageParams();
        String username = "admin";
        String name = "系统管理员";
        userPageParams.setUsername(username);
        Page<UserPo> userPoPage = userService.page(userPageParams);
        Assertions.assertNotNull(userPoPage);
        List<UserPo> userPoList = userPoPage.getRecords();
        boolean status = userPoList.stream().allMatch(userPo -> userPo.getUsername().contains(username));
        Assertions.assertTrue(status);
    }

    @Test
    void getByUsername() {
        String username = "admin";
        Mockito.when(userDao.selectList(Mockito.any())).thenReturn(Collections.emptyList());
        UserPo userPo = userService.getByUsername(username);
        Assertions.assertNull(userPo);
        Mockito.when(userDao.selectList(Mockito.any())).thenReturn(List.of(UserPo.builder().username(username).build()));
        userPo = userService.getByUsername(username);
        Assertions.assertNotNull(userPo);
        Assertions.assertEquals(username,userPo.getUsername());
        Mockito.when(userDao.selectList(Mockito.any())).thenReturn(Collections.emptyList());
        userPo = userService.getByUsername("");
        Assertions.assertNull(userPo);
    }

    @Test
    void getById() {
        Long id = 1L;
        Mockito.when(userDao.selectById(id)).thenReturn(UserPo.builder().id(id).build());
        UserVo userVo = userService.getById(id);
        Assertions.assertNotNull(userVo);
        Assertions.assertEquals(id, userVo.getId());
        Mockito.when(userDao.selectById(id)).thenReturn(null);
//        Mockito.when(userDao.selectById(id)).thenCallRealMethod();
        userVo = userService.getById(id);
        Assertions.assertNull(userVo);

    }

    @Test
    @Rollback
    void add() {
        String username = "admin";
        String name = "系统管理员";
        String phone = "15555555555";
        UserAddVo userAddVo = new UserAddVo();
        userAddVo.setUsername(username);
        userAddVo.setName(name);
        userAddVo.setPhone(phone);
        userAddVo.setLocked(0);
        //正常流程
        Mockito.when(userDao.selectList(Mockito.any())).thenReturn(null);
        Mockito.when(userDao.insert(Mockito.isA(UserPo.class))).thenReturn(1);
        Mockito.when(userDao.selectById(Mockito.any())).thenReturn(UserPo.builder().id(1L).username(username).name(name).build());
        UserVo userVo = userService.add(userAddVo);
        Assertions.assertEquals(username,userVo.getUsername());

        //测试存在未删除用户
        Mockito.when(userDao.selectList(Mockito.any())).thenReturn(List.of(UserPo.builder()
                .delFlag(SystemConstant.SYSTEM_NOT_DELETED_VALUE).build()));
        try {
            userService.add(userAddVo);
        } catch (Exception e) {
            Assertions.assertInstanceOf(DuplicateException.class, e);
        }
        //测试存在已删除用户
        Mockito.when(userDao.selectList(Mockito.any())).thenReturn(List.of(UserPo.builder()
                .delFlag(SystemConstant.SYSTEM_DELETED_VALUE).build()));
        try {
            userService.add(userAddVo);
        } catch (Exception e) {
            Assertions.assertInstanceOf(DuplicateException.class, e);
        }

        //测试新增失败
        Mockito.when(userDao.selectList(Mockito.any())).thenReturn(null);
        Mockito.when(userDao.insert(Mockito.isA(UserPo.class))).thenReturn(0);
        userVo = userService.add(userAddVo);
        Assertions.assertNull(userVo);
    }

    @Test
    void update() {
        long id = 1;
        String username = "admin";
        String name = "系统管理员";
        String phone = "15555555555";
        UserUpdateVo userUpdateVo = new UserUpdateVo();
        userUpdateVo.setUsername(username);
        userUpdateVo.setName(name);
        userUpdateVo.setPhone(phone);
        userUpdateVo.setLocked(0);
        userUpdateVo.setId(id);

        //测试用户ID不存在
        Mockito.when(userDao.selectById(id)).thenReturn(null);
        try {
            userService.update(userUpdateVo);
        } catch (Exception e) {
            Assertions.assertInstanceOf(BaseException.class, e);
        }
        //测试用户名修改情况
        UserPo oldUserPo = UserPo.builder().username("test").build();
        Mockito.when(userDao.selectById(id)).thenReturn(oldUserPo);
        //测试存在未删除用户
        Mockito.when(userDao.selectList(Mockito.any())).thenReturn(List.of(UserPo.builder()
                .delFlag(SystemConstant.SYSTEM_NOT_DELETED_VALUE).build()));
        try {
            userService.update(userUpdateVo);
        } catch (Exception e) {
            Assertions.assertInstanceOf(DuplicateException.class, e);
        }
        //测试存在已删除用户
        Mockito.when(userDao.selectList(Mockito.any())).thenReturn(List.of(UserPo.builder()
                .delFlag(SystemConstant.SYSTEM_DELETED_VALUE).build()));
        try {
            userService.update(userUpdateVo);
        } catch (Exception e) {
            Assertions.assertInstanceOf(DuplicateException.class, e);
        }
        //测试用户名未重复
        Mockito.when(userDao.selectList(Mockito.any())).thenReturn(Collections.emptyList());
        Mockito.when(userDao.updateById(Mockito.isA(UserPo.class))).thenReturn(1);
        Mockito.when(userDao.selectById(id)).thenReturn(UserPo.builder().id(id).name(name).username(username).phone(phone).build());
        UserVo userVo = userService.update(userUpdateVo);
        Assertions.assertEquals(id, userVo.getId());
    }

    @Test
    void delete() {
        long id = 1;
        //测试用户ID为查询到用户信息
        Mockito.when(userDao.selectById(id)).thenReturn(null);
        try {
            userService.delete(id);
        } catch (Exception e) {
            Assertions.assertInstanceOf(BaseException.class, e);
        }
        //测试删除用户为已删除
        Mockito.when(userDao.selectById(id)).thenReturn(UserPo.builder().delFlag(SystemConstant.SYSTEM_DELETED_VALUE).build());
        try {
            userService.delete(id);
        } catch (Exception e) {
            Assertions.assertInstanceOf(BaseException.class, e);
        }
        //测试删除用户为未删除
        Mockito.when(userDao.selectById(id)).thenReturn(UserPo.builder().delFlag(SystemConstant.SYSTEM_NOT_DELETED_VALUE).build());
        Mockito.when(userDao.updateById(Mockito.isA(UserPo.class))).thenReturn(1);
        Assertions.assertTrue(userService.delete(id));
        Mockito.when(userDao.selectById(id)).thenReturn(UserPo.builder().delFlag(SystemConstant.SYSTEM_NOT_DELETED_VALUE).build());
        Mockito.when(userDao.updateById(Mockito.isA(UserPo.class))).thenReturn(0);
        Assertions.assertFalse(userService.delete(id));
    }
}