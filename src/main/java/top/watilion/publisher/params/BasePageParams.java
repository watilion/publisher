package top.watilion.publisher.params;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author watilion
 * @date 2022/6/15 23:15
 */
@Data
@Schema(description = "基础分页查询参数")
public class BasePageParams {

    @Schema(description = "每页展示数",defaultValue = "10")
    private long pageSize;

    @Schema(description = "当前页数",defaultValue = "1")
    private long current;
}
