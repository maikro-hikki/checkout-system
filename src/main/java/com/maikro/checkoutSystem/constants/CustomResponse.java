package com.maikro.checkoutSystem.constants;

public class CustomResponse<T> {

	private T data;

	private String message;

	public CustomResponse() {
	}

	public CustomResponse(T object, String message) {
		this.data = object;
		this.message = message;
	}

	public T getObject() {
		return data;
	}

	public void setObject(T object) {
		this.data = object;
	}

	public String getWarningMessage() {
		return message;
	}

	public void setWarningMessage(String message) {
		this.message = message;
	}

}
