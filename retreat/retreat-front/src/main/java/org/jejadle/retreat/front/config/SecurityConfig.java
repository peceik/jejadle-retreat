package org.jejadle.retreat.front.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	private Environment env;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/images/**", "/resources/**", "/webjars/**", "/public/**", "/").permitAll()
				.antMatchers("/admin/**").hasRole("USER")		
				.and()
			.formLogin()
				.loginPage("/login")
				.failureUrl("/login-error")
				.loginProcessingUrl("/sign-in-process.html")
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/admin/list", true)
				.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/");
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		/*auth
			.inMemoryAuthentication()
				.withUser("admin").password("hello_jejadle12#").roles("USER");*/
		/*auth
		.inMemoryAuthentication()
			.withUser("admin").password("1").roles("USER");*/
		//default password admin:1
		auth
		.inMemoryAuthentication()
			.withUser(env.getProperty("security.user.name", "admin")).password(env.getProperty("security.user.password", "1")).roles(env.getProperty("security.user.role", "USER"));
	}
}
	


