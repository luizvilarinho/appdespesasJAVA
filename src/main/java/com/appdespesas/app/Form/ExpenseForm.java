package com.appdespesas.app.Form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ExpenseForm implements Serializable{
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
		private Integer id;
		private String description;
		private BigDecimal value;
		private String date;
		private boolean repeat;
		
		@JsonProperty("type_transational_id")
		private Integer typeTransationId;

		public ExpenseForm() {}
		
	
}
