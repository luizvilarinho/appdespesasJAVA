package com.appdespesas.app.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.appdespesas.app.DTO.ExpenseDTO;
import com.appdespesas.app.DTO.TagExpense;
import com.appdespesas.app.DTO.UserDTO;
import com.appdespesas.app.Entity.Expense;
import com.appdespesas.app.Entity.Tag;
import com.appdespesas.app.Entity.TypeTransation;
import com.appdespesas.app.Entity.User;
import com.appdespesas.app.Form.ExpenseForm;
import com.appdespesas.app.repository.ExpenseRepository;
import com.appdespesas.app.repository.TagRepository;
import com.appdespesas.app.repository.TypeTransationRepository;
import com.appdespesas.app.repository.UserRepository;
import com.appdespesas.app.util.CopyUtils;
import com.appdespesas.app.util.FormatUtils;

@Service
public class ExpenseService {
	
	@Autowired
	ExpenseRepository expenseRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TagRepository tagRepository;
	
	@Autowired
	TypeTransationRepository typeTransationRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TypeTransationService typeTransationService;
	
	@Autowired
	CopyUtils copyUtils;
	
	@Autowired
	FormatUtils formatUtils;
	
	@Transactional
	public ExpenseDTO saveExpense(ExpenseForm form, Authentication authentication){
		
		UserDTO userDTO =  (UserDTO) authentication.getPrincipal();
		User user = userRepository.findById(userDTO.getId()).get();
		
		TypeTransation type = typeTransationRepository.findById(form.getTypeTransationId()).get();
		
		Expense expenseEntity = new Expense();
		expenseEntity.setDate(formatUtils.formatDateToUS(form.getDate()));
		expenseEntity.setDescription(form.getDescription());
		expenseEntity.setRepeat(form.isRepeat());
		expenseEntity.setValue(form.getValue());
		
		System.out.println("EXP" + expenseEntity);
		
		expenseEntity.setUser(user);
		expenseEntity.setType(type);
		
		expenseRepository.save(expenseEntity);
		
		return copyUtils.toExpenseDTO(expenseEntity, false);
	}
	
	@Transactional
	public List<ExpenseDTO> retrieveExpenses(UUID userId){
		User user = userRepository.findById(userId).get();
		List<Expense> expenses = user.getExpenses();
		
		List<ExpenseDTO> list = new ArrayList<>();
		
		for(Expense e : expenses) {
			list.add(this.copyUtils.toExpenseDTO(e, true));
		}
		
		return list;
		
	}
	
	@Transactional
	public void deleteExpenseById(Integer id) {
		Expense expense = expenseRepository.findById(id).get();
		expenseRepository.delete(expense);
	}
	
	public void updateExpense(ExpenseForm expenseForm) {
		Expense expense = expenseRepository.findById(expenseForm.getId()).get();
		BeanUtils.copyProperties(expenseForm, expense);
		
		expense.setDate(this.formatUtils.formatDateToUS(expenseForm.getDate()));
	
		
		TypeTransation type = typeTransationRepository.findById(expenseForm.getTypeTransationId()).get();
		expense.setType(type);
		
		expenseRepository.save(expense);
	}
	
	public String tagExpense(TagExpense tagExpense) {
		
		Expense expense = this.expenseRepository.findById(tagExpense.getExpense_id()).get();
		Tag tag = this.tagRepository.findById(tagExpense.getTag_id()).get();
		
		if(expense != null && tag != null) {
			tag.getExpenses().add(expense);
			this.tagRepository.save(tag);
			return "sucesso";
		}else {
			return null;
		}
		
	}
}
