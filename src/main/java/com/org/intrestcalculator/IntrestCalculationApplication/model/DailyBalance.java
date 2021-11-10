package com.org.intrestcalculator.IntrestCalculationApplication.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class DailyBalance {
	private Date balanceDate;
	private List<AccountData> accountData;

@Data
public static class AccountData {
	public int bsb;
	public int identification;
	public BigDecimal balance;
}
}