package shaoyu.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.bind.annotation.PutMapping;
import shaoyu.validate.SmsCodeFilter;
import shaoyu.validate.ValidateCodeFilter;

import javax.servlet.Filter;
import javax.sql.DataSource;

/**
 * @author leeshaoyu
 * @description SpringSecurityConfig
 * @className SpringSecurityConfig
 * @since 2019/10/31
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyAuthenticationFailureHandler failureHandler;

	@Autowired
	private MyAuthenticationSuccessHandler successHandler;

	@Autowired
	private MyUserDetailService userDetailService;

	@Autowired
	private MyLogoutSuccessHandler LogoutSuccessHandler;

	@Autowired
	private HikariDataSource hikariDataSource;

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		// tokenRepository.setCreateTableOnStartup(true);
		tokenRepository.setDataSource(hikariDataSource);
		return tokenRepository;
	}

	@Override
	public void configure(WebSecurity web) {
		// 一个请求地址不需要拦截的话
		web.ignoring().antMatchers("/index");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
		validateCodeFilter.setAuthenticationFailureHandler(failureHandler);

		SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
		smsCodeFilter.setAuthenticationFailureHandler(failureHandler);

		http
				.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
				// .addFilterAfter(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
				.formLogin()
				//  允许为与基于表单的登录相关联的所有URL授予对所有用户的访问权限。
				.permitAll()
				//  自定义登录页面
				.loginPage("/login")
				//  自定义表单提交页
				.loginProcessingUrl("/toLogin")
				//  登录成功处理
				.successHandler(successHandler)
				//  登录失败处理
				.failureHandler(failureHandler)
				.and()
				//  身份认证
				.authorizeRequests()
				//  添加多个子项来指定网址的自定义要求，表示访问 /admin/** 这个接口，需要具备 admin 这个角色
				.antMatchers("/login", "/code/image").permitAll()
				.antMatchers("/admin/**").hasRole("admin")
				.antMatchers("/access/**").access("hasRole('ADMIN') and hasRole('MANAGER')")
				.anyRequest()
				//  身份认证
				.authenticated()
				//  跨站请求
				.and()
				.csrf().disable();

		// 注销
		http.logout()
				//  注销后重定向到的URL
				.logoutUrl("/logout")
				.logoutSuccessUrl("/logoutSuccess")
				//  注销成功处理程序
				.logoutSuccessHandler(LogoutSuccessHandler)
				//  指定在注销成功时删除的cookie的名称
				.deleteCookies();
		//  记住密码
		http.rememberMe()
				.userDetailsService(userDetailService)
				.tokenRepository(persistentTokenRepository());
	}


}