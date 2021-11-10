package com.org.intrestcalculator.IntrestCalculationApplication.repository;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.org.intrestcalculator.IntrestCalculationApplication.dao.model.Account;

import reactor.core.publisher.Flux;

public interface AccountOpeningRepository extends ReactiveCassandraRepository<Account, Integer> {
	 @AllowFiltering
	    Flux<Account> findAccount(int bsp, int identification);

}
