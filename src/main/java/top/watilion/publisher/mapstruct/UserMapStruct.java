package top.watilion.publisher.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import top.watilion.publisher.po.UserPo;
import top.watilion.publisher.vo.UserVo;

/**
 * @author watilion
 * @date 2022/6/8 00:40
 */

@Mapper
public interface UserMapStruct {

    UserMapStruct INSTANCE = Mappers.getMapper(UserMapStruct.class);

    /**
     * 用户 po 转 vo
     * @param po 数据持久化对象
     * @return 前端交互对象
     */
    UserVo poToVo(UserPo po);
}
