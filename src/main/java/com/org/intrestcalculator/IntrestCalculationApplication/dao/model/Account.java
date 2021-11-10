package com.org.intrestcalculator.IntrestCalculationApplication.dao.model;

import java.util.Date;

import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import com.datastax.driver.core.LocalDate.rece;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
	private int bsb;
	private int identification;
	private Date openingDate;
}