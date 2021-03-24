package cn.itcast.util;

import cn.itcast.constant.ConstantKey;
import cn.itcast.dto.UserInfoDTO;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @Author: Weny
 * @Des:
 */

public class JwtUtil {

    /**
     * 生成token
     * @return token
     */
    public static String getToken(UserInfoDTO userInfo) {
        String token = Jwts.builder()
                .setSubject(JSONObject.toJSONString(userInfo))
                .setExpiration(new Date(System.currentTimeMillis() + 1 * 24 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, ConstantKey.SIGNING_KEY)
                .compact();
        return token;
    }
}