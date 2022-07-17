package top.watilion.publisher.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author watilion
 * @date 2022/6/8 00:26
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户修改对象")
public class UserUpdateVo extends UserAddVo{

    @NotNull(message = "用户ID不能为空")
    @Schema(name = "id", description = "用户ID")
    private Long id;

    @Schema(description = "是否锁定，0-未锁定，1-锁定")
    private Integer locked;

}
