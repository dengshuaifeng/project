package cn.itcast.security;

import cn.itcast.constant.ConstantKey;
import cn.itcast.entity.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Author: Weny
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法
 * attemptAuthentication ：接收并解析用户凭证。
 * successfulAuthentication ：用户成功登录后，生成token
 * @Date:2021/3/22
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * 接收并解析用户凭证
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            UserInfo user = new ObjectMapper().readValue(req.getInputStream(), UserInfo.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUserAccount(),
                            user.getUserPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) {
        // builder the token
        String token = null;
        try {
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            // 定义存放角色集合的对象
            List roleList = new ArrayList<>();
            for (GrantedAuthority grantedAuthority : authorities) {
                roleList.add(grantedAuthority.getAuthority());
            }
            // 设置过期时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date time = calendar.getTime();

            token = Jwts.builder()
                    .setSubject(auth.getName() + "-" + roleList)
                    .setExpiration(time)
                    .signWith(SignatureAlgorithm.HS512, ConstantKey.SIGNING_KEY )
                    .compact();
            // 登录成功后，返回token到header里面
            response.addHeader("Authorization", token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
