package com.appdespesas.app.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.appdespesas.app.DTO.UserDTO;
import com.appdespesas.app.Service.UserService;
import com.appdespesas.app.repository.UserRepository;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
	
	private TokenService tokenService;
	private UserRepository userRepository;
	private UserService userService;
	
	public AuthTokenFilter(TokenService tokenService, UserRepository userRepository,UserService userService) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperarToken(request);
		
		boolean valid = tokenService.isValid(token);
		
		System.out.println("valid " + valid);
		
		if(valid) {
			autenticarCliente(token);
		}
		
		filterChain.doFilter(request, response);
		
	}

	public void autenticarCliente(String token) {
		
		UUID userId = tokenService.getIdUser(token);
		System.out.println(userId);
		UserDTO userDTO = this.userService.getUserById(userId);
		
		
		UsernamePasswordAuthenticationToken autentication = new UsernamePasswordAuthenticationToken(userDTO, null,null);
		
		SecurityContextHolder.getContext().setAuthentication(autentication);
		
	}

	private String recuperarToken(HttpServletRequest request) {
		
		//String token = request.getHeader("Authorization");
		Cookie[] cookies = request.getCookies();
		Optional<String> cookieOp = null;
		String cookie = "";
//		
//		if(cookieOp != null) {
//			cookieOp = Arrays.stream(cookies).filter(c->"ad_access_tkn".equals(c.getName())).map(Cookie::getValue).findAny();
//			cookie = cookieOp.get();
//		}
		
		try {
			cookieOp = Arrays.stream(cookies).filter(c->"ad_access_tkn".equals(c.getName())).map(Cookie::getValue).findAny();
			cookie = cookieOp.get();
			
			return cookie;
		} catch (Exception e) {
			return null;
		}
		
		//String token = "Bearer " + cookie.get();
		
//		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
//			return null;
//		}
		
//		return token.substring(7, token.length());
		
	}

}
