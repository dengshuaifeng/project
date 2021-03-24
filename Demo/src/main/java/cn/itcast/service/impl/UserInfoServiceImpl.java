package cn.itcast.service.impl;


import cn.itcast.constant.ConstantKey;
import cn.itcast.dao.UserInfoDao;
import cn.itcast.dto.UserInfoDTO;
import cn.itcast.entity.UserInfo;
import cn.itcast.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;


    @Override
    public UserInfoDTO login(UserInfoDTO userInfo) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account",userInfo.getUserAccount())
                .eq("user_password",userInfo.getUserPassword());
        UserInfo user = userInfoDao.selectOne(queryWrapper);
        UserInfoDTO resultDTO=new UserInfoDTO();
        if(user!=null){
            BeanUtils.copyProperties(user,resultDTO);
        }
        return resultDTO;
    }

    @Override
    public int register(UserInfoDTO userInfo) {
        UserInfo user=new UserInfo();
        BeanUtils.copyProperties(userInfo,user);
        user.setStatus(ConstantKey.STATUS_VALID);
        user.setUserPostType(ConstantKey.USER);
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        int result = userInfoDao.insert(user);
        return result;
    }
}
