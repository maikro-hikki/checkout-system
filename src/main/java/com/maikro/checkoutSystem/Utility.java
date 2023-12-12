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

/**
 * Utility class containing various helper methods.
 */
public class Utility {
	
	/**
	 * Rounds a given double number to two decimal places.
	 *
	 * @param number the number to be rounded
	 * @return the rounded number with two decimal places
	 */
	public static double roundToTwoDecimals(double number) {

		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

		String roundedNumberString = decimalFormat.format(number);
		double roundedNumber = Double.parseDouble(roundedNumberString);

		return roundedNumber;
	}

	/**
	 * Converts a string representation of a number to a double.
	 *
	 * @param input the string representation of the number
	 * @return the converted double value, or Double.MIN_VALUE if the conversion fails
	 */
	public static double convertStringToDouble(String input) {

		double result;

		try {
			result = Double.parseDouble(input);
		} catch (NumberFormatException e) {
			result = Double.MIN_VALUE;
		}

		return result;
	}

	/**
	 * Converts a string representation of a number to an integer.
	 *
	 * @param input the string representation of the number
	 * @return the converted integer value, or Integer.MIN_VALUE if the conversion fails
	 */
	public static int convertStringToInt(String input) {

		int result;

		try {
			result = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			result = Integer.MIN_VALUE;
		}

		return result;
	}

	/**
	 * Converts a string representation of a number to an integer.
	 *
	 * @param input the string representation of the number
	 * @return the converted integer value, or Integer.MIN_VALUE if the conversion fails
	 */
	public static long convertStringToLong(String input) {
		long result;

		try {
			result = Long.parseLong(input);
		} catch (NumberFormatException e) {
			result = Long.MIN_VALUE;
		}

		return result;
	}

	/**
	 * Validates an initial object and returns an appropriate ResponseEntity with a CustomResponse object.
	 *
	 * @param <T> the type of the object being validated
	 * @param object the object to be validated
	 * @param bindingResult the BindingResult object containing the validation errors
	 * @return a ResponseEntity containing a CustomResponse object with the validation result
	 */
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
