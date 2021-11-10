package com.org.intrestcalculator.IntrestCalculationApplication.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.org.intrestcalculator.IntrestCalculationApplication.dao.model.Account;
import com.org.intrestcalculator.IntrestCalculationApplication.dao.model.AccountBalance;
import com.org.intrestcalculator.IntrestCalculationApplication.dao.model.MonthlyIntrestHistory;
import com.org.intrestcalculator.IntrestCalculationApplication.model.AccountOpening;
import com.org.intrestcalculator.IntrestCalculationApplication.model.DailyBalance;
import com.org.intrestcalculator.IntrestCalculationApplication.model.MonthData;
import com.org.intrestcalculator.IntrestCalculationApplication.repository.AccountOpeningRepository;
import com.org.intrestcalculator.IntrestCalculationApplication.repository.DailyBalanceRepository;
import com.org.intrestcalculator.IntrestCalculationApplication.repository.MonthlyIntrestHistoryRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class IntrestCalculatorService {
	private AccountOpeningRepository accountOpeningRepository;
	private DailyBalanceRepository dailyBalnceRepository;
	private MonthlyIntrestHistoryRepository monthlyHistRepository;

	private static final BigDecimal DEFAULT_PERCENTAGE_PER_DAY = new BigDecimal(0.01);

	public void initiateAccount(AccountOpening accountData) {
		Account account = new Account();
		BeanUtils.copyProperties(accountData, account);
		Mono<Object> savedAccount = Mono.just(account).map(accountOpeningRepository::save);
		savedAccount.subscribe();
	}

	public void updateDailyBalance(DailyBalance dailyBalance) {
		if (!Objects.nonNull(dailyBalance)) {
			List<AccountBalance> accountBalList = new ArrayList<AccountBalance>();
			dailyBalance.getAccountData().forEach(accountData -> {
				if (accountOpeningRepository.findAccount(accountData.getBsb(),
						accountData.getIdentification()) != null) {
					AccountBalance accountBal = new AccountBalance();
					accountBal.setBalance(accountData.getBalance());
					accountBal.setBalanceDate(dailyBalance.getBalanceDate());
					accountBal.setBsb(accountData.getBsb());
					accountBal.setIdentification(accountData.getIdentification());
					accountBal.setInterest(calculateIntrest(accountData.getBalance()));
					accountBalList.add(accountBal);
				}
			});

			Mono<Object> savedAccountBalance = Mono.just(accountBalList).map(dailyBalnceRepository::saveAll);
			savedAccountBalance.subscribe();
		}
	}

	public Set<MonthData> MonthEndData(LocalDate first, LocalDate last) {
		Set<MonthData> monthData = new HashSet<>();
		Flux<AccountBalance> accountBal = dailyBalnceRepository.findAccountBalanceByDate(toDate(first), toDate(last));
		accountBal.collectList().block().forEach(accountBalance -> {
			if (monthData.contains(accountBalance)) {
				MonthData monthD = (MonthData) monthData.stream().filter(monthDa -> monthData.equals(accountBalance));
				monthD.setTotalIntrest(monthD.getTotalIntrest().add(accountBalance.getInterest()));
				monthData.add(monthD);
			} else {
				MonthData mnt = new MonthData();
				BeanUtils.copyProperties(accountBalance, mnt);
				monthData.add(mnt);
			}
		});
		monthData.stream().forEach(mntData ->{
			MonthlyIntrestHistory monthHist = new MonthlyIntrestHistory();
			monthHist.setBsb(mntData.getBsb());
			monthHist.setIdentification(mntData.getIdentification());
			monthHist.setTotalInterrest(mntData.getTotalIntrest());
			monthHist.setMonth(first.getMonth().getValue());
			monthHist.setYear(first.getYear());
			monthlyHistRepository.save(monthHist);
		});
		return monthData;
	}

	private BigDecimal calculateIntrest(BigDecimal balance) {
		return balance.multiply(DEFAULT_PERCENTAGE_PER_DAY);
	}

	private Date toDate(LocalDate localDate) {
		return Date.from(Instant.from(localDate.atStartOfDay(ZoneId.systemDefault())));
	}

}
