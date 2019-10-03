package org.sid.security;




import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
@Autowired
	private DataSource dataSource;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
	    
		auth.inMemoryAuthentication()
			.withUser("admin").password("1234").roles("ADMIN","USER");
		auth.inMemoryAuthentication()
		.withUser("user").password("1234").roles("USER");
		
		*/
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")
		.authoritiesByUsernameQuery("select username as principal, roles as role from users_roles where username=?") //connaitre les roles
		.rolePrefix("ROLE_") //ajout du prefix
		.passwordEncoder(  new Md5PasswordEncoder() ); //mdp codé en MD5

	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		   
		http.formLogin().loginPage("/login");
		http.authorizeRequests().antMatchers("/operations","/consulterCompte","/saveOperation").hasRole("USER");
		http.authorizeRequests().antMatchers("/operations","/consulterCompte","/clients").hasRole("ADMIN");
		
	http.exceptionHandling().accessDeniedPage("/403");
	}
}
