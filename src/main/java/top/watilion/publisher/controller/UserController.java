package top.watilion.publisher.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.watilion.publisher.mapstruct.UserMapStruct;
import top.watilion.publisher.params.UserPageParams;
import top.watilion.publisher.po.UserPo;
import top.watilion.publisher.service.UserService;
import top.watilion.publisher.vo.Response;
import top.watilion.publisher.vo.UserAddVo;
import top.watilion.publisher.vo.UserUpdateVo;
import top.watilion.publisher.vo.UserVo;

import java.util.List;

/**
 * @author watilion
 * @date 2022/6/7 22:55
 */

@Slf4j
@RestController
@Tag(name = "UserController", description = "用户信息")
@RequestMapping("/system/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @Operation(description="根据ID获取用信息")
    public Response<UserVo> get(@PathVariable Long id){
        Response<UserVo> response = new Response<>();
        UserVo userVo = userService.getById(id);
        if (userVo == null) {
            response.fail("用户ID不存在");
        } else {
            response.success(userVo);
        }
        return response;
    }

    @PostMapping("/page")
    @Operation(description = "分页查询用户信息")
    public Response<List<UserVo>> page(UserPageParams userPageParams) {
        Response<List<UserVo>> response = new Response<>();
        Page<UserPo> userPoPage = userService.page(userPageParams);
        List<UserVo> userVoList = UserMapStruct.INSTANCE.poListToVoList(userPoPage.getRecords());
        return response.success(userVoList,userPoPage);
    }

    @PutMapping("/add")
    @Operation(description = "用户新增")
    public Response<UserVo> add(@RequestBody @Validated UserAddVo userSaveVo) {
        Response<UserVo> response = new Response<>();
        UserVo userVo = userService.add(userSaveVo);
        return response.success(userVo);
    }

    @PostMapping("/update")
    @Operation(description = "用户更新")
    public Response<UserVo> update(UserUpdateVo userUpdateVo) {
        Response<UserVo> response = new Response<>();
        UserVo userVo = userService.update(userUpdateVo);
        return response.success(userVo);
    }

    @DeleteMapping("/{id}")
    @Operation(description="根据ID删除用")
    public Response<Void> delete(@PathVariable Long id){
        Response<Void> response = new Response<>();
        if (userService.delete(id)) {
            response.success();
        } else {
            response.fail("删除失败");
        }
        return response;
    }
}
