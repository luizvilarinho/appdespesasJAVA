package com.appdespesas.app.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.appdespesas.app.DTO.ExpenseDTO;
import com.appdespesas.app.Form.ExpenseForm;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tb_expense")
public class Expense implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false, length = 150)
	private String description;
	
	@Column(nullable = false)
	private BigDecimal value;
	
	@Column(nullable = false)
	private LocalDate date;
	
	private boolean repeat;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="type_transation_id")
	private TypeTransation type;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToMany(mappedBy = "expenses")
    @JsonManagedReference // <- adiciona esta anotação aqui
    private List<Tag> tags;

	
	
}
