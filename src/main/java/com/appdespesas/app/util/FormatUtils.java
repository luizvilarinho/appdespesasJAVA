package com.appdespesas.app.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class FormatUtils {
	
	public String formatDateToLocal(LocalDate localDate) {
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		
		return formatador.format(date);
	}
	
	public LocalDate formatDateToUS(String localDate) {
		  // Parse the input string to a LocalDate object
	    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    LocalDate date = LocalDate.parse(localDate, inputFormatter);

	    // Format the LocalDate object to the desired output format
	    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    String formattedDate = date.format(outputFormatter);

	    // Parse the formatted date to a LocalDate object in the desired output format
	    return LocalDate.parse(formattedDate);
	}
}
