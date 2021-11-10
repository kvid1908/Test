package com.org.intrestcalculator.IntrestCalculationApplication.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MonthData {
	private int bsb;
	private int identification;
	private BigDecimal totalIntrest;

	@Override
	public int hashCode() {
		return this.bsb + this.identification;
	}

	@Override
	public boolean equals(Object obj) {
		MonthData monthData = (MonthData) obj;
		if (monthData.bsb == this.bsb && monthData.identification == this.bsb) {
			return true;
		} else {
			return false;
		}
	}
}
