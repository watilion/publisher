package top.watilion.publisher.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author watilion
 * @date 2022/6/7 22:47
 */
@Data
@Builder
@AllArgsConstructor
@Schema(description = "用户持久化对象")
@TableName("user")
public class UserPo {

    @TableId(type = IdType.AUTO)
    @Schema(name = "id", description = "用户ID")
    private Long id;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "登录次数")
    private Integer loginTimes;

    @Schema(description = "最后登陆时间")
    private LocalDateTime lastLoginTime;

    @Schema(description = "是否锁定，0-未锁定，1-锁定")
    private Integer locked;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "用户状态，0-未删除，1-已删除")
    private Integer status;
}
