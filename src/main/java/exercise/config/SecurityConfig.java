package exercise.config;

import exercise.model.ExUserDetailService;
import exercise.model.ExeUserDetail;
import exercise.session.LoginSuccessHandler;
import exercise.session.LogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * Created by feixiaobo on 2016/11/11.
 */
@Configuration @EnableWebSecurity @EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Value("${application.mode.dev}") private boolean isDevMode;

//    @Autowired
//    private DoFilter filter;

    public SecurityConfig(){}

    @Autowired public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder()).addObjectPostProcessor(
            new ObjectPostProcessor<DaoAuthenticationProvider>() {
                @Override public DaoAuthenticationProvider postProcess(DaoAuthenticationProvider object) {
                    object.setHideUserNotFoundExceptions(false);
                    return object;
                }
            });
    }

    protected void configure(HttpSecurity http) throws Exception {
        if (isDevMode) {
            //开发模式,单机调试使用
            http.authorizeRequests().antMatchers("/**").permitAll();
        } else {
            http.authorizeRequests()
                .antMatchers("/logoutSuccess", "/loginFail", "/login").permitAll()
                .antMatchers("/admin-user/**").hasAuthority("user_manage")//用户管理
                .antMatchers("/role/**").hasAuthority("role_manage")//角色管理
                .anyRequest().authenticated();
        }
        http.formLogin()
            .withObjectPostProcessor(new ObjectPostProcessor<LoginUrlAuthenticationEntryPoint>() {
                @Override
                public <O extends LoginUrlAuthenticationEntryPoint> O postProcess(O laep) {
                    laep.setUseForward(false);
                    return laep;
                }
            })
            .successHandler(successHandler())
            .failureHandler(failureHandler())
            .loginPage("/login")
            .permitAll()
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler(logoutHandler())
            .permitAll()
            .and()
            .sessionManagement()
            .maximumSessions(1)//第二次登录会使第一次失效
            .and()
            .sessionAuthenticationErrorUrl("/login") //如果第二次验证使用了其 他非内置的机制，比如“remember-me”，一个“未认证”(402)错误就会发送给客户端 。如 果你希望使用一个错误页面替代，你可以在 session-management 中添加 session-authentication-error-url 属性。
            .and()
            //.addFilterAfter(filter,DoFilter.class)
            .csrf()
            .disable();
    }

    @Bean public UserDetailsService userDetailsService() {
        return new ExUserDetailService();
    }

    @Bean @Scope("prototype") ExeUserDetail userDetails() {
        return new ExeUserDetail();
    }

    @Bean PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean public SimpleUrlAuthenticationFailureHandler failureHandler() {
        SimpleUrlAuthenticationFailureHandler failHandler = new SimpleUrlAuthenticationFailureHandler();
        failHandler.setUseForward(false);
        failHandler.setDefaultFailureUrl("/loginFail");
        return failHandler;
    }

    @Bean public LoginSuccessHandler successHandler() {
        LoginSuccessHandler successHandler = new LoginSuccessHandler();
        successHandler.setDefaultTargetUrl("/loginSuccess");
        successHandler.setAlwaysUseDefaultTargetUrl(true);
        return successHandler;
    }

    public LogoutSuccessHandler logoutHandler() {
        LogoutSuccessHandler handler = new LogoutSuccessHandler();
        handler.setDefaultTargetUrl("/logoutSuccess");
        return handler;
    }
}
