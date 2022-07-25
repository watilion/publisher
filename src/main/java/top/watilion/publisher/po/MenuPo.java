package top.watilion.publisher.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author watilion
 * @date 2022/7/24 04:13
 */

@Data
@Builder
@AllArgsConstructor
public class MenuPo {

    @TableId(type = IdType.AUTO)
    @Schema(name = "id", description = "用户ID")
    private Long id;

    @Schema(name = "name", description = "菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称长度不能超过50个字符")
    private String name;

    @Schema(name = "parentId", description = "父菜单ID")
    private Long parentId;

    @Schema(name = "sort", description = "显示顺序")
    @NotBlank(message = "显示顺序不能为空")
    private String sort;

    @Schema(name = "href",description = "路由地址")
    @Size(max = 200, message = "路由地址不能超过200个字符")
    private String href;

    @Schema(name = "component", description = "组件路径")
    @Size(max = 200, message = "组件路径不能超过255个字符")
    private String component;

    @Schema(name = "frame", description = "是否为外链（0-是,1-否）")
    private int frame;

    @Schema(name = "menuType", description = "类型（M-目录,C-菜单 F-按钮）")
    private String menuType;

    @Schema(name = "show", description = "菜单状态:0显示,1隐藏")
    private int show;

    @Schema(name = "permission", description = "权限字符串")
    @Size(max = 100, message = "权限标识长度不能超过100个字符")
    private String permission;

    @Schema(name = "icon", description = "菜单图标")
    private String icon;

    @Schema(name = "createBy", description = "创建者")
    private Long createBy;

    @Schema(name = "createTime", description = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private LocalDateTime createTime;

    @Schema(name = "updateBy", description = "更新者")
    private Long updateBy;

    @Schema(name = "updateTime", description = "更新时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private LocalDateTime updateTime;

    @Schema(name = "remark", description = "备注")
    private String remark;
}
