package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.example.demo.service.LoginServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginServiceImpl loginService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.authorizeRequests()
			// login画面,css,jsにはアクセス制限をかけない。
			.antMatchers("/login", "/css/**", "/js/**").permitAll()
			//.antMatchers("/h2-console/**").permitAll()	// H2DBデバッグ用
			// それ以外は制限をかける。（これを外すとどのアドレスにも制限がかからなくなる。）
			.anyRequest().authenticated()
			.and()
		.formLogin()
			// ログイン画面　（LoginController）
			.loginPage("/login")
			// ログインボタンを押した際に移動
			.loginProcessingUrl("/authenticate")
			// ログイン成功時に移動　（LoginController）
			.defaultSuccessUrl("/login/success")
			// ログイン失敗時に移動　（LoginController)
			.failureUrl("/login/error")
			// ID、パスワードの変数名
			.usernameParameter("code")
			.passwordParameter("password")
			.permitAll()
			.and()
		.logout()
			// ログアウト時　（LoginController）
			.logoutUrl("/login")
			.logoutSuccessUrl("/login/index.html")
			.invalidateHttpSession(true)
			.permitAll();
		
		//http.csrf().disable();	// H2DBデバッグ用
		//http.headers().frameOptions().disable(); // H2DBデバッグ用
	}
	
	// 認証情報
	@Autowired
	public void configureGlobal(
			AuthenticationManagerBuilder auth
			) throws Exception {
		auth
			// 認証用トークンの取得
			.authenticationProvider(loginService);
	}
}
