package com.appdespesas.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.appdespesas.app.Entity.User;
import com.appdespesas.app.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class AutenticacaoService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		
		if(user != null) {
			System.out.println("username " + user);
			return  user;
		}
		
		throw new UsernameNotFoundException("dados inválidos");
	}
	
}
