package top.watilion.publisher.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.watilion.publisher.service.UserService;
import top.watilion.publisher.po.UserPo;
import top.watilion.publisher.dao.UserDao;

/**
 * @author watilion
 * @date 2022/6/7 22:54
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserPo> implements UserService {
}
