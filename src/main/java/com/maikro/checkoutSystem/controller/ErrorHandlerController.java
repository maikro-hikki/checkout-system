//package com.maikro.checkoutSystem.controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class ErrorHandlerController {
//
//	@ExceptionHandler(HttpMessageNotReadableException.class)
//	public ResponseEntity<String> handleHttpMessageNotReadableException() {
//		String errorMessage = "HttpMessageNotReadableException: Please check if the inputs are valid";
//		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
//	}
//}
