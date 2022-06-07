package top.watilion.publisher.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.watilion.publisher.po.UserPo;

/**
 * @author watilion
 * @date 2022/6/7 22:52
 */
@Mapper
@Repository
public interface UserDao extends BaseMapper<UserPo> {
}
