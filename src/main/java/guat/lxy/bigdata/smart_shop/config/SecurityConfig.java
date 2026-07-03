package guat.lxy.bigdata.smart_shop.config;

import guat.lxy.bigdata.smart_shop.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. 权限拦截规则必须放在最前面
        http.authorizeRequests()
                // 静态资源、登录页所有人放行
                .antMatchers("/css/**", "/img/**", "/login").permitAll()
                // 商品增删改 + 分类管理 仅admin角色访问
                .antMatchers("/product/add", "/product/edit/**", "/product/delete/**", "/category/**").hasAuthority("ROLE_admin")
                // 剩下所有接口需要登录
                .anyRequest().authenticated();

        http.headers(headers -> headers.frameOptions().disable());
        // 2. 登录配置
        http.formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .defaultSuccessUrl("/index", true)
                .permitAll()
        );

        // 3. 退出登录配置
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
        );

        // 关闭csrf
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
