package com.appdespesas.app.DTO;

import java.util.List;
import java.util.UUID;

import com.appdespesas.app.Form.CreateUserForm;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
	
	UUID id;
	String name;
	String email;
	List<ExpenseDTO> expenses;
	
	
	public UserDTO(UserDTO user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.expenses = user.getExpenses();
	}
	
	public UserDTO(CreateUserForm user) {
		this.name = user.getName();
		this.email = user.getEmail();
	}


	
}
