package top.watilion.publisher.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author watilion
 * @date 2022/6/8 00:26
 */

@Data
@Schema(description = "用户交互对象")
public class UserVo {

    @Schema(name = "id", description = "用户ID")
    private Long id;

    @NotBlank(message = "姓名不能为空")
    @Schema(description = "姓名")
    private String name;

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "登录次数")
    private Integer loginTimes;

    @Schema(description = "最后登陆时间")
    private LocalDateTime lastLoginTime;

    @Schema(description = "是否锁定，0-未锁定，1-锁定")
    private Integer locked;

    @Schema(description = "用户状态，0-未删除，1-已删除")
    private Integer delFlag;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

}
