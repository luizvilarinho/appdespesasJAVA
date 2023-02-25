package com.appdespesas.app.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appdespesas.app.DTO.TypeTransationDTO;
import com.appdespesas.app.repository.TypeTransationRepository;
import com.appdespesas.app.util.CopyUtils;

@Service
public class TypeTransationService {
	
	@Autowired
	TypeTransationRepository typeTransationRepository;
	
	@Autowired
	CopyUtils copyUtils;
	
	public TypeTransationDTO findTypeTransactionById(Integer id){
		
		TypeTransationDTO dto = copyUtils.toTypeTransationDTO(this.typeTransationRepository.findById(id).get());
		
		return dto;
	}
}
