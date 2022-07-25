package top.watilion.publisher.params;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author watilion
 * @date 2022/6/15 23:19
 */
@Data
@Schema(description = "用户分页查询对象")
public class UserPageParams extends BasePageParams{

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "删除标识，0-未删除，1-已删除")
    private Integer delFlag;
}
