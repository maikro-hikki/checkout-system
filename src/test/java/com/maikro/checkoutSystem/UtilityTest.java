package com.maikro.checkoutSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UtilityTest {

	@Test
	void testRoundToTwoDecimals_InputValueOf0point987463801_ShouldRoundUpTo0point99() {
		
		double roundedNumber = Utility.roundToTwoDecimals(0.987463801);
		
		assertEquals(0.99, roundedNumber);
	}
	
	@Test
	void testRoundToTwoDecimals_InputValueOf0point9857820_ShouldRoundUpTo0point99() {
		
		double roundedNumber = Utility.roundToTwoDecimals(0.9857820);
		
		assertEquals(0.99, roundedNumber);
	}
	
	@Test
	void testRoundToTwoDecimals_InputValueOf0point9837615_ShouldRoundDownTo0point98() {
		
		double roundedNumber = Utility.roundToTwoDecimals(0.9837615);
		
		assertEquals(0.98, roundedNumber);
	}
	
	@Test
	void testRoundToTwoDecimals_InputValueOf0point35_ShouldReturn0point35() {
		
		double roundedNumber = Utility.roundToTwoDecimals(0.35);
		
		assertEquals(0.35, roundedNumber);
	}

}
