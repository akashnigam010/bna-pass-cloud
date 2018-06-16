package in.bananaa.pass.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtAuthenticationProvider jwtAuthenticationProvider;
	@Autowired
	JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("ADMIN");
		auth.authenticationProvider(jwtAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/user/**").permitAll();
		//http.authorizeRequests().antMatchers("/search/**").authenticated();
		http.authorizeRequests().antMatchers("/search/**").authenticated().and()
		.addFilterBefore(customJwtAuthenticationFilter("/search/**"),
				AbstractPreAuthenticatedProcessingFilter.class);
		http.csrf().disable();
	}

	/**
	 * Allow access without authentication - public data <br>
	 */
	public JwtAuthenticationFilter customJwtAuthenticationFilter(String defaultProcessingUrl) throws Exception {
		JwtAuthenticationFilter customUsernamePasswordAuthenticationFilter = new JwtAuthenticationFilter(
				defaultProcessingUrl);
		customUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
		customUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler);
		return customUsernamePasswordAuthenticationFilter;
	}
}