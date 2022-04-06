package com.ajblima.dscatalog.resource.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationErro extends StandartError{

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();

	public List<FieldMessage> getErrors() {
		return errors;
	}
	
	public void addErro(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
}
