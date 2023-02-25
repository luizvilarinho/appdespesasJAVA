package com.appdespesas.app.Entity;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Teste implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	Integer id;
	String nome;
	
}
