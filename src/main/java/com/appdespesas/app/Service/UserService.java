package com.appdespesas.app.Service;



import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appdespesas.app.DTO.UserDTO;
import com.appdespesas.app.Entity.User;
import com.appdespesas.app.Form.CreateUserForm;
import com.appdespesas.app.repository.UserRepository;
import com.appdespesas.app.util.CopyUtils;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CopyUtils copyUtils;
	
	public List<UserDTO> retrieveUsers(){
		List<UserDTO> list  = copyUtils.toListOfUsersDTO(this.userRepository.findAll());
		
		return list;
	}
	
	public UserDTO getUserById(UUID id){
		User user = this.userRepository.findById(id).get();
		UserDTO dto = copyUtils.toUserDTO(user);
		
		return dto;
	}
	
	public UserDTO getUserByEmail(String email){
		
		User entity = this.userRepository.findByEmail(email);
		
		if(entity != null) {
			UserDTO dto = copyUtils.toUserDTO(entity);
			return dto;
		}else {
			return null;
		}
	}
	
	public UserDTO saveNewUser(CreateUserForm userForm) {
		User createUser = new User(userForm);
		userRepository.save(createUser);
		
		return this.copyUtils.toUserDTO(createUser);
	}
	
	public String retrievePasswordByEmail(String email){
		User user = this.userRepository.findByEmail(email);
		
		return user.getPassword();
	}
}
