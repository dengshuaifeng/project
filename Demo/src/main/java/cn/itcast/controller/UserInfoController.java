package cn.itcast.controller;


import cn.itcast.config.CommonResult;
import cn.itcast.constant.ExceptionEnum;
import cn.itcast.dto.UserInfoDTO;
import cn.itcast.entity.UserInfo;
import cn.itcast.service.UserInfoService;
import cn.itcast.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("userinfo")
public class UserInfoController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;


    /**
     * @Author: Weny
     * @Des:用户登录
     */
    @PostMapping("login")
    public CommonResult login(@RequestBody UserInfoDTO userInfo) {
        String token = null;
        Map<String, Object> map = new HashMap<>();
        try {
            userInfo = userInfoService.login(userInfo);
            if (userInfo == null) {
                return Result.error(ExceptionEnum.WRONG_PWD);
            }
            token = JwtUtil.getToken(userInfo);
            map.put("user", userInfo);
            map.put("token", token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(map);
    }

    /**
     * @Author: Weny
     * @Des:用户注册
     */
    @PostMapping("register")
    public CommonResult register(@RequestBody UserInfoDTO userInfo) {
        if (StringUtils.isEmpty(userInfo.getUserAccount())
                || StringUtils.isEmpty(userInfo.getUserAccount())
                || userInfo.getUserPhone() == 0) {
            return Result.error(ExceptionEnum.PARAM_IS_EMPTY);
        }
        try {
            int result = userInfoService.register(userInfo);
            if (result <= 0) {
                return Result.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success();
    }

    @GetMapping("/valid/{id}")
    public CommonResult userIsValid(@PathVariable("id") Long id) {
        try {
            UserInfo user = userInfoService.getById(id);
            if (null != user.getUserValid() && user.getUserValid() == 1) {
                return Result.success();
            }else{
                return Result.error(ExceptionEnum.USER_NOT_VALID);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }


}
