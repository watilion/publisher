package top.watilion.publisher.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author watilion
 * @date 2022/6/7 22:45
 */
@Data
@Schema(description = "分页信息")
public class Pagination {
    @Schema(description = "当前页数")
    private int current;

    @Schema(description = "数据总数")
    private long total;

    @Schema(description = "每页展示数")
    private int pageSize;

    @Schema(description = "总页数")
    private int pages;

    public Pagination() {
    }

    public Pagination(int current, long total,int pageSize,int pages) {
        this.current = current;
        this.total = total;
        this.pageSize = pageSize;
        this.pages = pages;
    }
}
