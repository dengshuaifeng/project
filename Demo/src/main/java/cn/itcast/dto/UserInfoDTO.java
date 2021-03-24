package cn.itcast.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Weny
 * @Date:2021/3/23
 */
@Data
public class UserInfoDTO implements Serializable {

    private Integer userId;

    private String userAccount;

    private String userPassword;

    private String userPostType;

    private Long companyId;

    private Long userPhone;

    private String userName;

    private String userValid;

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
