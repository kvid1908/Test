package com.org.intrestcalculator.IntrestCalculationApplication.model;

import java.util.Date;

import lombok.Data;

@Data
public class AccountOpening {
private int bsb;
private long identification;
private Date openingDate;
}
