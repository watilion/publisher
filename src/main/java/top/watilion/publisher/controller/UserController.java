package top.watilion.publisher.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.watilion.publisher.po.UserPo;
import top.watilion.publisher.service.UserService;
import top.watilion.publisher.vo.Response;

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
    public Response<UserPo> get(@PathVariable Long id){
        Response<UserPo> response = new Response<>();
        UserPo userPo = userService.getById(id);
        return response.success(userPo);
    }
}
