package com.appdespesas.app.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ResponseSuccess<T> {
	
	private T response;
	private boolean success = true;
	private String message = "realizado com sucesso";
	
	public ResponseSuccess(T response, boolean success, String message) {
		this.response = response;
		this.success = success;
		this.message = message;
	}
	
	public ResponseSuccess(T response) {
		this.response = response;
		
	}
}
