package com.appdespesas.app.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appdespesas.app.DTO.TokenDTO;
import com.appdespesas.app.Form.LoginForm;
import com.appdespesas.app.config.TokenService;

@RestController
@CrossOrigin(origins = "/**", allowCredentials = "true")
@RequestMapping("/api/auth")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDTO> autenticar(@RequestBody LoginForm form, HttpServletResponse response){
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		//System.out.println("DADOS LOGIN" + dadosLogin);
		try {
			
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication);
			
			Cookie accessCookie = new Cookie("ad_access_tkn", token);
			accessCookie.setHttpOnly(true);
			accessCookie.setSecure(false);
			response.addCookie(accessCookie);

			return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
			
		}catch (org.springframework.security.core.AuthenticationException e) {
			System.out.println(e);
			return ResponseEntity.badRequest().build();
		}
		
	}

}
