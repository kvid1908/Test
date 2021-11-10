package com.org.intrestcalculator.IntrestCalculationApplication.repository;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.org.intrestcalculator.IntrestCalculationApplication.dao.model.MonthlyIntrestHistory;

public interface MonthlyIntrestHistoryRepository extends ReactiveCassandraRepository<MonthlyIntrestHistory, Integer> {

}
