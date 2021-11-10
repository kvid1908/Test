package com.org.intrestcalculator.IntrestCalculationApplication.repository;

import java.util.Date;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.org.intrestcalculator.IntrestCalculationApplication.dao.model.AccountBalance;

import reactor.core.publisher.Flux;

public interface DailyBalanceRepository extends ReactiveCassandraRepository<AccountBalance, Integer> {
	@Query("SELECT * FROM AccountBalance WHERE balanceDate >= ?0 and balanceDate  >= ?1")
	    Flux<AccountBalance> findAccountBalanceByDate(Date startDate, Date endDate);
}
