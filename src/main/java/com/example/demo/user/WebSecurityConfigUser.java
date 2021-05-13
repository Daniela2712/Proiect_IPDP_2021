
package com.example.demo.user;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfigUser extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	
	 @Autowired
	 CustomSuccessHandler customSuccessHandler;

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	@Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
            .dataSource(dataSource)
            .usersByUsernameQuery("select email, password, enabled from user where email=?")
            .authoritiesByUsernameQuery("select email, role from user where email=?")
        ;
    }
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {	
		http.authorizeRequests()
		.antMatchers("/home").permitAll()
//        .antMatchers("/").hasAnyAuthority("USER", "ADMIN")
//        .antMatchers("/new").hasAnyAuthority("ADMIN")
//        .antMatchers("/edit/**").hasAnyAuthority("ADMIN")
//        .antMatchers("/delete/**").hasAuthority("ADMIN")
        .antMatchers("/userPage").hasAnyAuthority("USER")
        .antMatchers("/adminPage").hasAnyAuthority("ADMIN")
       // .antMatchers("/users").authenticated()
        //.anyRequest().permitAll()
        .and()
        .formLogin().permitAll()
        .usernameParameter("email")
		.passwordParameter("password")
		.successHandler(customSuccessHandler)
        .and()
        .logout()
        		.permitAll()
        		//.logoutSuccessUrl("/home")
        		.logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK );})
        .and()
        .exceptionHandling().accessDeniedPage("/403")
		;
	}
	
}

	 

	 
