package com.appdespesas.app.DTO;

import java.util.Optional;

import com.appdespesas.app.Entity.Teste;

import lombok.Data;

@Data
public class TesteDTO {
	
	String nome;
	boolean sucesso = true;
	
	public TesteDTO(Teste teste) {
		this.nome = teste.getNome();
	}
	

		
}
