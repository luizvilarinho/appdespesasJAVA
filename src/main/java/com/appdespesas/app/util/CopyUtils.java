package com.appdespesas.app.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appdespesas.app.DTO.ExpenseDTO;
import com.appdespesas.app.DTO.TagDto;
import com.appdespesas.app.DTO.TypeTransationDTO;
import com.appdespesas.app.DTO.UserDTO;
import com.appdespesas.app.Entity.Expense;
import com.appdespesas.app.Entity.Tag;
import com.appdespesas.app.Entity.TypeTransation;
import com.appdespesas.app.Entity.User;

@Component
public class CopyUtils {
	
	@Autowired
	FormatUtils formatUtils;
	
	//expense
	public ExpenseDTO toExpenseDTO(Expense entity, boolean copyTags) {
		ExpenseDTO dto = new ExpenseDTO();
		System.out.println(entity.getType());
		//BeanUtils.copyProperties(entity, dto);
		dto.setDate(formatUtils.formatDateToLocal(entity.getDate()));
		dto.setDescription(entity.getDescription());
		dto.setId(entity.getId());
		dto.setRepeat(entity.isRepeat());
		dto.setType(this.toTypeTransationDTO(entity.getType()));
		dto.setValue(entity.getValue());
		
		System.out.println(dto);
		if (copyTags) {
			dto.setTags(new ArrayList<>());
			
			for (Tag t : entity.getTags()) {
				dto.getTags().add(toTagDTO(t));
			}
		}
		
		
		return dto;
	}
	
	//tag
	public TagDto toTagDTO(Tag entity) {
		TagDto dto = new TagDto();
		dto.setDescription(entity.getDescription());
		dto.setId(entity.getId());
		return dto;
	}
	
	//user
	public UserDTO toUserDTO(User entity){
		UserDTO dto = new UserDTO();
		dto.setEmail(entity.getEmail());
		dto.setName(entity.getName());
		dto.setId(entity.getId());
		
		return dto;
	}
	
	//userlist
	public List<UserDTO> toListOfUsersDTO(Iterable<User> users) {
		
		List<UserDTO> listDTO = new ArrayList<>();
		
		for (User u : users) {
			listDTO.add(this.toUserDTO(u));
		}
		
		return listDTO;
	}
	
	public TypeTransationDTO toTypeTransationDTO(TypeTransation entity) {
		TypeTransationDTO dto = new TypeTransationDTO();
		dto.setId(entity.getId());
		dto.setType(entity.getType());
		
		return dto;
	}
}
