package com.appdespesas.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.appdespesas.app.DTO.UserDTO;
import com.appdespesas.app.Form.CreateUserForm;
import com.appdespesas.app.Form.LoginForm;
import com.appdespesas.app.Service.UserService;
import com.appdespesas.app.config.AutenticacaoService;
import com.appdespesas.app.config.AuthTokenFilter;
import com.appdespesas.app.config.TokenService;
import com.appdespesas.app.repository.TesteRepository;

@RestController
@RequestMapping("/api/")
public class UserController {
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
		
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	AuthTokenFilter authTokenFilter;
	
	public UserController(UserService userService, TesteRepository testeRepository) {
		this.userService = userService;
	}
	

	//LOGIN
	@Transactional
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, Object>> login(@RequestBody LoginForm loginForm, Authentication authentication){
		
		//String formPasswordEncrypted = bcrypt.encode(loginForm.getPassword());
		Map<String, Object> response = new HashMap<>();
		try {
			UserDTO userFinded =  this.userService.getUserByEmail(loginForm.getEmail());//userRepository.findByEmail(user.getEmail());
			String userPassword = this.userService.retrievePasswordByEmail(loginForm.getEmail());
			
			System.out.println("userpassword " + userPassword);
			System.out.println("loginForm.password " + loginForm.getPassword());
			System.out.println("LOGIN " + bcrypt.matches(loginForm.getPassword(), userPassword));
			
			if(userFinded != null && bcrypt.matches(loginForm.getPassword(), userPassword)) {
				System.out.println(userPassword);
				
				response.put("sucess", true);
				response.put("message", "user finded");
				response.put("user", new UserDTO(userFinded));
				
				String token = tokenService.gerarToken(userFinded.getId());
				//UserDetails userD = this.autenticacaoService.loadUserByUsername(loginForm.getEmail());
				//response.put("token", userPassword)
				authTokenFilter.autenticarCliente(token);
				response.put("token", token);
			}else {
				System.out.println("else ");
				response.put("message", "wrong email or password");
				response.put("success", false);
				
				response.put("user", null);
			}
		}catch(Exception e) {
			response.put("success", false);
			response.put("message", e.getMessage());
			response.put("body", null);
		}
				
		return ResponseEntity.ok().body(response);
	}
	
	//find all
	@RequestMapping(value = "/user", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<UserDTO>> list(){
		
		List<UserDTO> list = this.userService.retrieveUsers();
		
		return ResponseEntity.ok().body(list);
	}
	
	//findById
	@RequestMapping(value = "user/findone/{id}" )
	public @ResponseBody ResponseEntity<Model> findOne(@PathVariable UUID id, Model model) {

		try {
			UserDTO dto = userService.getUserById(id);
			model.addAttribute("success", true);
			model.addAttribute("message", "user found");
			model.addAttribute("user", dto);
			
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("success", false);
			model.addAttribute("error", e.getMessage());
		}
		
		return new ResponseEntity<Model>(model, HttpStatus.OK);
	}
	
	//create user
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, Object>>  create(@RequestBody CreateUserForm userToCreate){
		
		Map<String, Object> response = new HashMap<>();
		System.out.println(userToCreate);
		userToCreate.setPassword(bcrypt.encode(userToCreate.getPassword()));
		
		
		try {
			
			UserDTO user = userService.getUserByEmail(userToCreate.getEmail());
			
			if(user != null) {
				response.put("success", false);
				response.put("message", "user already exists");
			}else {
				UserDTO userDto = this.userService.saveNewUser(userToCreate);
				response.put("success", true);
				response.put("success", "user created");
				response.put("user", userDto);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			response.put("sucess", false);
			response.put("message", e.getMessage());
		}
		
		return ResponseEntity.ok().body(response);
		
	}
	
	//delete user
//	@RequestMapping(value = "user/delete/{id}", method = RequestMethod.DELETE)
//	public @ResponseBody ResponseEntity<Model> deleteUser(@PathVariable UUID id, Model response) {
//
//		try {
//			Optional<User> user = userRepository.findById(id);
//			//System.out.println(user.get());
//			
//			
//			if(user.get() != null) {
//				userRepository.delete(user.get());
//				response.addAttribute("success", true);
//				response.addAttribute("message", "user deleted");
//			}else {
//				response.addAttribute("success2", false);
//			}
//			
//		}catch(Exception e) {
//			response.addAttribute("success", false);
//			response.addAttribute("message",  e.getMessage());
//		}
//		
//		return ResponseEntity.ok().body(response);
//	}
	
	//update user
//	@RequestMapping(value = "user/update", method = RequestMethod.PUT)
//	public @ResponseBody ResponseEntity<Model> updateUser(@RequestBody User userToUpdate, Model response) {
//		
//		
//		try {
//			User user = userRepository.findByEmail(userToUpdate.getEmail());
//			
//			if(user != null && userToUpdate.getPassword() != null) {
//				user.setPassword(userToUpdate.getPassword());
//				userRepository.save(user);
//				
//				response.addAttribute("success", true);
//				response.addAttribute("message", "user password updated");
//				response.addAttribute("user", new UserDTO(user));
//				
//			}else {
//				response.addAttribute("success", false);
//				response.addAttribute("message", "user not founded");
//				response.addAttribute("user", user);
//			}
//		}catch(Exception e) {
//			response.addAttribute("message", e.getMessage());
//			response.addAttribute("user", null);
//		}
//		
//		return ResponseEntity.ok().body(response);
//	}
	
//	@RequestMapping(value = "/teste",  method = RequestMethod.GET)
//	public @ResponseBody ResponseEntity<Model> show(HttpServletRequest request, Model responseObject, Principal p) {
//		
//		
//		//User user = userRepository.findByEmail( p.getName());
//		UserDTO userDTO = new UserDTO(p,userRepository);
//		responseObject.addAttribute("user", userDTO);
//		
//		return ResponseEntity.ok().body(responseObject);
//	}
	
}
