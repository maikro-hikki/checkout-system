package com.maikro.checkoutSystem;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.maikro.checkoutSystem.constants.CustomResponse;

public class Utility {

	public static double roundToTwoDecimals(double number) {

		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

		String roundedNumberString = decimalFormat.format(number);
		double roundedNumber = Double.parseDouble(roundedNumberString);

		return roundedNumber;
	}

	public static double convertStringToDouble(String input) {

		double result;

		try {
			result = Double.parseDouble(input);
		} catch (NumberFormatException e) {
			result = Double.MIN_VALUE;
		}

		return result;
	}

	public static int convertStringToInt(String input) {

		int result;

		try {
			result = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			result = Integer.MIN_VALUE;
		}

		return result;
	}

	public static long convertStringToLong(String input) {
		long result;

		try {
			result = Long.parseLong(input);
		} catch (NumberFormatException e) {
			result = Long.MIN_VALUE;
		}

		return result;
	}

	public static <T> ResponseEntity<CustomResponse<T>> initialObjectValidator(T object, BindingResult bindingResult) {
		
		CustomResponse<T> customResponse = new CustomResponse<>();

		if (bindingResult.hasErrors()) {

			List<String> errorMessages = new ArrayList<>();

			for (ObjectError error : bindingResult.getAllErrors()) {

				if (error instanceof FieldError) {

					FieldError fieldError = (FieldError) error;
					errorMessages.add("Field '" + fieldError.getField() + "': " + fieldError.getDefaultMessage());

				} else {

					errorMessages.add(error.getDefaultMessage());
				}
			}
			
			customResponse.setData(object);
			customResponse.setMessage(errorMessages.toString());

			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.noContent().build();

	}

}
