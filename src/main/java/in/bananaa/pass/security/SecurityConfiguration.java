package in.bananaa.pass.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationProvider jwtAuthenticationProvider;
	@Autowired
	private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("ADMIN");
		auth.authenticationProvider(jwtAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/user/**").permitAll();
		http.authorizeRequests().antMatchers("/scan/**").authenticated().and().addFilterBefore(
				customJwtAuthenticationFilter("/scan/**"), AbstractPreAuthenticatedProcessingFilter.class);
		http.authorizeRequests().antMatchers("/membership/**").authenticated().and().addFilterBefore(
				customJwtAuthenticationFilter("/membership/**"), AbstractPreAuthenticatedProcessingFilter.class);
		http.csrf().disable();
	}

	public JwtAuthenticationFilter customJwtAuthenticationFilter(String defaultProcessingUrl) throws Exception {
		JwtAuthenticationFilter customUsernamePasswordAuthenticationFilter = new JwtAuthenticationFilter(
				defaultProcessingUrl);
		customUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
		customUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler);
		return customUsernamePasswordAuthenticationFilter;
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET", "POST", "OPTIONS");
			}
		};
	}
}
