package com.appdespesas.app.DTO;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ExpenseDTO {
	
	private Integer id;
	private String description;
	private BigDecimal value;
	private String date;
	private boolean repeat;
	private TypeTransationDTO type;
	private UserDTO user;
	
    private List<TagDto> tags;
    
    public ExpenseDTO(Integer id, String description, BigDecimal value, String date, boolean repeat, TypeTransationDTO type, UserDTO user) {
    	this.id = id;
    	this.description = description;
    	this.value = value;
    	this.date = date;
    	this.repeat = repeat;
    	this.type = type;
    	this.user = user;
    	
    }
    
}
