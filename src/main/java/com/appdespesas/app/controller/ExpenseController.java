package com.appdespesas.app.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.appdespesas.app.DTO.ExpenseDTO;
import com.appdespesas.app.DTO.TagExpense;
import com.appdespesas.app.DTO.UserDTO;
import com.appdespesas.app.Entity.Expense;
import com.appdespesas.app.Entity.Tag;
import com.appdespesas.app.Form.ExpenseForm;
import com.appdespesas.app.Service.ExpenseService;
import com.appdespesas.app.Service.TypeTransationService;
import com.appdespesas.app.Service.UserService;
import com.appdespesas.app.response.ResponseSuccess;

@RestController
@RequestMapping("/api/")
public class ExpenseController {
	
	@Autowired
	TypeTransationService typeTransationService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ExpenseService expenseService;
	
	@RequestMapping(value = "/expense")
	public @ResponseBody ResponseEntity<Model> listExpensesByUserId(Authentication authentication, Model response, Principal principal) {
		
		try {
			UserDTO logado = (UserDTO) authentication.getPrincipal();
			response.addAttribute("user", logado.getEmail());
			
			List<ExpenseDTO> list = this.expenseService.retrieveExpenses(logado.getId());
			response.addAttribute("expenses", list);
			
		}catch(Exception e) {
			response.addAttribute("Error", e.getMessage());
		}
		
		//response.addAttribute("expenses", expenses);
		
		return ResponseEntity.ok().body(response);
	}
	
	@Transactional
	@RequestMapping(value = "/expense", method = RequestMethod.POST)
	public ResponseEntity<?> createExpense(@RequestBody ExpenseForm expenseForm, Authentication authentication ) throws Exception {
		
		try {
			this.expenseService.saveExpense(expenseForm, authentication);
		} catch (Exception e) {
			throw new Error(e);
		}
		
		return ResponseEntity.ok().body("created");
	}
	
	@RequestMapping(value = "/expense", method=RequestMethod.PUT)
	public ResponseEntity<?> updateExpense(@RequestBody ExpenseForm expenseForm){
		
		try {
			
			this.expenseService.updateExpense(expenseForm);
			
		}catch(Exception e) {
			throw new Error(e);
		}
		return ResponseEntity.ok().body("updated");
	}
	
	//DELETE
	@RequestMapping(value="/expense/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteExpense(@PathVariable Integer id){
		
		try {
			this.expenseService.deleteExpenseById(id);
			//expenseRepository.deleteById(id);
			
		}catch(Exception e) {
			throw new Error(e);
		}
		
		return ResponseEntity.ok().body("DELETED");
	}
	
	@PostMapping("/tag/expense")
	@Transactional
	public @ResponseBody<T> ResponseEntity<ResponseSuccess<T>> addExpenseToTag(@RequestBody TagExpense tagExpense){
		
		ResponseSuccess<T> response = null;
		
		try {
			String success = this.expenseService.tagExpense(tagExpense);
			
			if(success == "sucesso") {
				response = new ResponseSuccess<T>(null, true, "gravado com sucesso");
				
			}else {
				response = new ResponseSuccess<T>(null, false, "algo deu errado");
			}
			
		}catch(Exception e) {
			response = new ResponseSuccess<T>(null, false, e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
}
