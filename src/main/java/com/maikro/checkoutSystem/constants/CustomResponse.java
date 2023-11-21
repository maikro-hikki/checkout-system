package com.maikro.checkoutSystem.constants;

public class CustomResponse<T> {

	private T object;

	private String warningMessage;

	public CustomResponse() {
	}

	public CustomResponse(T object, String warningMessage) {
		this.object = object;
		this.warningMessage = warningMessage;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public String getWarningMessage() {
		return warningMessage;
	}

	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}

}
