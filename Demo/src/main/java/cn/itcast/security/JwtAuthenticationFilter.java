package cn.itcast.security;


import cn.itcast.constant.ConstantKey;
import cn.itcast.constant.ExceptionEnum;
import cn.itcast.util.ExceptionUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *@Author: Weny
 * 自定义JWT认证过滤器
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 */
@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (StringUtils.isEmpty(header)) {
            chain.doFilter(request, response);
            return;
        }
        String url =   request.getRequestURI();
        log.error("http请求："+ url);
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request,response);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            throw new ExceptionUtil(ExceptionEnum.TOKEN_EMPTY);
        }
        String user = null;
        try {
            user = Jwts.parser()
                    .setSigningKey(ConstantKey.SIGNING_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, null);
            }

        } catch (ExpiredJwtException e) {
            logger.error("Token已过期: {} " + e);
            throw new ExceptionUtil(ExceptionEnum.TOKEN_OUT_OF_DATE);
        } catch (UnsupportedJwtException e) {
            logger.error("Token格式错误: {} " + e);
            throw new ExceptionUtil(ExceptionEnum.TOKEN_FORMAT_ERROR);
        } catch (MalformedJwtException e) {
            logger.error("Token没有被正确构造: {} " + e);
            throw new ExceptionUtil(ExceptionEnum.TOKEN_CREATE_ERROR);
        } catch (SignatureException e) {
            logger.error("签名失败: {} " + e);
            throw new ExceptionUtil(ExceptionEnum.TOKEN_SIGN_DEFAULT);
        } catch (IllegalArgumentException e) {
            logger.error("非法参数异常: {} " + e);
            throw new ExceptionUtil(ExceptionEnum.ILLEGAL_ERROR);
        }
        return null;
    }

}
