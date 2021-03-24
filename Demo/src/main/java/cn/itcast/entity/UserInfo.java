package cn.itcast.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("user_info")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    private String userAccount;

    private String userPassword;

    private String userPostType;

    private Long companyId;

    private Long userPhone;

    private String userName;

    private Short userValid;

    private String userNum;

    private String userNumFront;

    private String userNumBack;

    private String userNumPeople;

    private Integer status;

    private Long createUser;

    private Long updateUser;

    private Date createTime;

    private Date updateTime;
}