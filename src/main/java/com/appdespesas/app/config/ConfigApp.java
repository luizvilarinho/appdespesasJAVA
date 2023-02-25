package com.appdespesas.app.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.appdespesas.app.Service.UserService;
import com.appdespesas.app.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
@EnableWebSecurity
public class ConfigApp extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
//	@Autowired
//	@Bean
//	protected Authentication authentication() throws Exception{
//		return super.authenticationManagerBean();
//	}
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	 @Override
	    public void configure(WebSecurity web) throws Exception {
	        web.ignoring()
	        .antMatchers(HttpMethod.POST, "/api/user")
	        .antMatchers(HttpMethod.POST, "/api/login")
	        .antMatchers(HttpMethod.POST, "/api/auth");
	    }
	 
	@Override
	 protected void configure(HttpSecurity http) throws Exception {
		http
		.cors()
		.and()
		.authorizeHttpRequests()
			.antMatchers(HttpMethod.POST, "/api/login").permitAll()
	        .antMatchers(HttpMethod.POST, "/api/auth").permitAll()
	        .antMatchers(HttpMethod.POST, "/api/user").permitAll()
	        .antMatchers(HttpMethod.GET, "/**").permitAll()
	        .anyRequest().authenticated()
		.and()
		.csrf()
		.disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().addFilterAt(new AuthTokenFilter(tokenService, userRepository, userService), UsernamePasswordAuthenticationFilter.class);
		
	}
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	 public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	        converters.add(new MappingJackson2HttpMessageConverter(mapper));
	    }
}
