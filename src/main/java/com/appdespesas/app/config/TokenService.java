package com.appdespesas.app.config;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.appdespesas.app.Entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${jwt.secret}")
	private String secret;
	
	public String gerarToken(Authentication authentication) {
		
		User logado = (User) authentication.getPrincipal();
		String id =  logado.getId().toString();
		System.out.println("ID" + id);
		Date hoje = new Date();
		Date exp = new Date(hoje.getTime() + 1000000000);
		
		return Jwts.builder()
				.setIssuer("app despesas")
				.setSubject(id)
				.setIssuedAt(hoje)
				.setExpiration(exp)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	public String gerarToken(UUID uuid) {
		
		String id =  uuid.toString();
		System.out.println("ID" + id);
		Date hoje = new Date();
		Date exp = new Date(hoje.getTime() + 1000000000);
		
		return Jwts.builder()
				.setIssuer("app despesas")
				.setSubject(id)
				.setIssuedAt(hoje)
				.setExpiration(exp)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	public boolean isValid(String token) {
		
		try {
			System.out.println("secret " + secret);
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		}catch (Exception e) {
			System.out.println("error " + e);
			return false;
		}
		
	}

	public UUID getIdUser(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		System.out.println("token " + token);
		UUID userId = UUID.fromString(claims.getSubject());
		//System.out.println("USERID " + userId);
		return userId;
	}
	
}
