package com.org.intrestcalculator.IntrestCalculationApplication.dao.model;

import java.math.BigDecimal;

import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyIntrestHistory {
	private int bsb;
	private int identification;
	private int month;
	private int year;
	private BigDecimal totalInterrest;
}