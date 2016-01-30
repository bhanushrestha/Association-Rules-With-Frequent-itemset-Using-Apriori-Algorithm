package com.bhanucodes.associationrule.dto;

import java.util.List;
import java.util.Map;

import com.bhanucodes.associationrule.domain.Transaction;

public interface TransactionManager {

	public List<Transaction> populateTransaction();
	public Map<String,Integer> findInitialItemSet(List<Transaction> transactions);
	public Map<String, Integer> findInitialFrequestItemSet(
			List<Transaction> transactions, Map<String, Integer> l1, Integer minSupport);
	
	public Map<List<String>, Integer> findKthItemSet(
			List<Transaction> transactions, Map<List<String>, Integer> lk, int iteration);
	
	public Map<List<String>, Integer> findSecondItemSet(
			List<Transaction> transactions, Map<String, Integer> c1);
	
	public Map<List<String>, Integer> findFrequestItemSet(
			List<Transaction> transactions, Map<List<String>, Integer> cn, Integer minSupport);
}
