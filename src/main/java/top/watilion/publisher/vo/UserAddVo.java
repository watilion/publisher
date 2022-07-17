package top.watilion.publisher.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author watilion
 * @date 2022/6/8 00:26
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户修改对象")
public class UserAddVo {

    @NotBlank(message = "姓名不能为空")
    @Schema(description = "姓名")
    private String name;

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "是否锁定，0-未锁定，1-锁定")
    private Integer locked;

    @Schema(description = "手机号")
    private String phone;

}
