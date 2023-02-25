package com.appdespesas.app.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.appdespesas.app.DTO.TagDto;
import com.appdespesas.app.DTO.TagExpense;
import com.appdespesas.app.Entity.Expense;
import com.appdespesas.app.Entity.Tag;
import com.appdespesas.app.Service.TagService;
import com.appdespesas.app.repository.ExpenseRepository;
import com.appdespesas.app.repository.TagRepository;
import com.appdespesas.app.response.ResponseSuccess;

@RestController
@RequestMapping("/api/tag")
public class TagController {
	
	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private TagService tagService;
	
	@GetMapping
	public @ResponseBody ResponseEntity<ResponseSuccess<List<TagDto>>> listAllTags(){
		
		List<TagDto> tags = this.tagService.retrieveAllTags();
		ResponseSuccess<List<TagDto>> response = new ResponseSuccess<List<TagDto>>(tags);
		
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	@Transactional
	public @ResponseBody ResponseEntity<ResponseSuccess<TagDto>> addTagToList(@RequestBody TagDto tagDto){
		
		ResponseSuccess<TagDto> response = null;
		
		try {
			TagDto newTagDto = this.tagService.saveNewTag(tagDto);
			response = new ResponseSuccess<TagDto>(newTagDto, true, "gravado com sucesso");
		}catch(Exception e) {
			response = new ResponseSuccess<TagDto>(null, false, e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
	@DeleteMapping("/{id}")
	@Transactional
	public @ResponseBody<T> ResponseEntity<ResponseSuccess<T>> deleteTagToList(@PathVariable Integer id){
		
		ResponseSuccess<T> response = null;
		
		TagDto dto = this.tagService.deleteTag(id);
		
		if(dto != null) {
			response = new ResponseSuccess<T>(null, true, "deletado com sucesso");
		}else {
			response = new ResponseSuccess<T>(null, false, "id inválido ou inexistente");
		}
					
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
}
