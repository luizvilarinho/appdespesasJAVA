package com.appdespesas.app.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.appdespesas.app.DTO.TypeTransationDTO;

import lombok.Data;

@Entity
@Data
@Table(name="tb_type_transation")
public class TypeTransation implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	
	@Column(nullable = false, length = 50)
	private String type;

	public TypeTransation(Integer id, String type) {
		this.id = id;
		this.type = type;
	}
	
	public TypeTransation() {}
	
}
