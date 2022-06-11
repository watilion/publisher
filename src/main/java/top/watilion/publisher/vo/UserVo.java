package top.watilion.publisher.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author watilion
 * @date 2022/6/8 00:26
 */

@Data
@Schema(description = "用户持久化对象")
@TableName("user")
public class UserVo {

    @Schema(name = "id", description = "用户ID")
    private Long id;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "用户名")
    private String username;

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

}
