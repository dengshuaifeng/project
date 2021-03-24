package cn.itcast.service;


import cn.itcast.dto.UserInfoDTO;
import cn.itcast.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserInfoService extends IService<UserInfo> {


    UserInfoDTO login(UserInfoDTO userInfo);

    int register(UserInfoDTO userInfo);
}
