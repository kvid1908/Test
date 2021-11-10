package com.org.intrestcalculator.IntrestCalculationApplication.dao.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalance {
	private int bsb;
	private int identification;
	private BigDecimal balance;
	private Date balanceDate;
	private BigDecimal interest;
}