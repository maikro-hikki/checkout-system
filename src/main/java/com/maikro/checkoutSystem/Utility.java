package com.maikro.checkoutSystem;

import java.math.RoundingMode;
import java.text.DecimalFormat;

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
			result = Double.MIN_VALUE; // A sentinel value indicating an error
		}

		return result;
	}

	public static int convertStringToInt(String input) {
		
		int result;

		try {
			result = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			result = Integer.MIN_VALUE; // A sentinel value indicating an error
		}

		return result;
	}
	
	public static long convertStringToLong(String input) {
        long result;
        
        try {
            result = Long.parseLong(input);
        } catch (NumberFormatException e) {
            result = Long.MIN_VALUE; // A sentinel value indicating an error
        }
        
        return result;
    }

}
