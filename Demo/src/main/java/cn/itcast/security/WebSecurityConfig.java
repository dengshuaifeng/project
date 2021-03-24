package cn.itcast.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * SpringSecurity的配置
 * 通过SpringSecurity的配置，将JWTLoginFilter，JWTAuthenticationFilter组合在一起
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 白名单
     */
    private static final String[] AUTH_WHITELIST = {
            "/",
            "/userinfo/login",
            "/userinfo/test",
            "/userinfo/list",
            "/price/list",
            "/price/add",
            "/price/delete/*",
            "/price/edit"
    };

    // 设置 HTTP 验证规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        LogoutConfigurer<HttpSecurity> httpSecurityLogoutConfigurer =
                http.cors()
                        .and().csrf().disable()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                        .authorizeRequests()
                        .antMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated()  // 所有请求需要身份认证
                        .and()
                        .addFilter(new JwtLoginFilter(authenticationManager()))
                        .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                        .logout() // 默认注销行为为logout，可以通过下面的方式来修改
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")// 设置注销成功后跳转页面，默认是跳转到登录页面;
                        // .logoutSuccessHandler(customLogoutSuccessHandler)
                        .permitAll();
    }
}
