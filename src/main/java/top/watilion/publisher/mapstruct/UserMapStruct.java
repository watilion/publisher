package top.watilion.publisher.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import top.watilion.publisher.po.UserPo;
import top.watilion.publisher.vo.UserAddVo;
import top.watilion.publisher.vo.UserUpdateVo;
import top.watilion.publisher.vo.UserVo;

import java.util.List;

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

    /**
     * 批量转换用户对象
     * @param userPoList po列表
     * @return 用户交互对象
     */
    List<UserVo> poListToVoList(List<UserPo> userPoList);

    /**
     * 用户新增保存交互对象转换数据持久化对象
     * @param userAddVo 用户新增保存交互对象
     * @return 用户持久话对象
     */
    @Mapping(target = "updateTime", expression = "java( java.time.LocalDateTime.now() )")
    @Mapping(target = "lastLoginTime", expression = "java( java.time.LocalDateTime.now() )")
    @Mapping(target = "createTime", expression = "java( java.time.LocalDateTime.now())")
    @Mapping(target = "loginTimes", constant = "0")
    @Mapping(target = "delFlag", constant = "0")
    @Mapping(target = "id", ignore = true)
    UserPo userAddVoToPo(UserAddVo userAddVo);

    /**
     * 用户修改保存交互对象转换数据持久化对象
     * @param userUpdateVo 用户修改保存交互对象
     * @return 用户持久话对象
     */
    @Mapping(target = "updateTime", expression = "java( java.time.LocalDateTime.now() )")
    @Mapping(target = "lastLoginTime", expression = "java( java.time.LocalDateTime.now() )")
    @Mapping(target = "createTime", expression = "java( java.time.LocalDateTime.now())")
    @Mapping(target = "loginTimes", constant = "0")
    @Mapping(target = "delFlag", constant = "0")
    @Mapping(target = "id", source = "id")
    UserPo userUpdateVoToPo(UserUpdateVo userUpdateVo);
}
