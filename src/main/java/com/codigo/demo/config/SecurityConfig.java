//package com.codigo.demo.config;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.codigo.demo.domain.repository.UserMasterRepository;
//import com.codigo.demo.utils.JwtTokenFilter;
//
//@SuppressWarnings("deprecation")
//@EnableWebSecurity
//@ComponentScan(basePackages = { "com.codigo.demo.api" })
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	private UserMasterRepository userRepository;
//
//	@Autowired
//	private JwtTokenFilter jwtTokenFilter;
//
//	public SecurityConfig(UserMasterRepository userRepository) {
//		this.userRepository = userRepository;
//	}
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(username -> userRepository.findUserMasterByEmail(username)
//				.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found.")));
//	}
//
//	@Override
//	@Bean
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable();
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//		http.authorizeRequests().antMatchers("/api/v1/login").permitAll().anyRequest().authenticated();
//		
//		http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
//		});
//
//		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
//
//
//	}
//
//}
