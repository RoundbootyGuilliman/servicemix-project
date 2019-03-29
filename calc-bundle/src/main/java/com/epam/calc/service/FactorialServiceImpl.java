package com.epam.calc.service;

import com.epam.calc.api.FactorialService;

public class FactorialServiceImpl implements FactorialService {
	
	public long calculateFactorial(int input) {
		long result = 1L;
		for (int i = 1; i < input; i++) {
			result += result * i;
		}
		return result;
	}
}
