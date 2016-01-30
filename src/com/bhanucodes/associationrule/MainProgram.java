package com.bhanucodes.associationrule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bhanucodes.associationrule.domain.Transaction;
import com.bhanucodes.associationrule.dto.TransactionManager;
import com.bhanucodes.associationrule.dto.imp.TransactionManagerImpl;

public class MainProgram {

	public static void main(String[] args) {
		System.out.println("Hello ");
		
		TransactionManager transactionManager = new TransactionManagerImpl();
		
		List<Transaction> transactions = transactionManager.populateTransaction();
		
		for(Transaction transaction : transactions){
			System.out.println("For transaction with id :: " + transaction.getTransactionId());
			System.out.println("ITEMS ::");
			System.out.println(transaction.getItems());
		}
		
		Map<String,Integer> l1 = transactionManager.findInitialItemSet(transactions);
		
		System.out.println("INITIAL SET");
		for(String item : l1.keySet()){
			System.out.println(item + " "+ l1.get(item));		
		}
		
		Map<String,Integer> c1 = transactionManager.findInitialFrequestItemSet(transactions, l1, 2);
		System.out.println("PRUNED SET (C1)");
		for(String item : c1.keySet()){
			System.out.println(item + " "+ c1.get(item));		
		}
		
		
		Map<List<String>, Integer> c2 = new HashMap<List<String>, Integer>();
		c2 = transactionManager.findSecondItemSet(transactions, c1);
		System.out.println("SECOND ROUND SET (C2)");
		for(List<String> items : c2.keySet()){
			System.out.println(items +"  " + c2.get(items));
		}
		
		c2 = transactionManager.findFrequestItemSet(transactions, c2, 2);
		System.out.println("\n\n\n");
		System.out.println("PRUNED SET (C2)");
		for(List<String> item : c2.keySet()){
			System.out.println(item + " "+ c2.get(item));		
		}
		
		Map<List<String>, Integer> c3 = new HashMap<List<String>, Integer>();
		
		c3 = transactionManager.findKthItemSet(transactions, c2,3);
		
		System.out.println("\n\n\n");
		System.out.println("THIRD ROUND SET (C3)");
		for(List<String> items : c3.keySet()){
			System.out.println(items +"  " + c3.get(items));
		}
		
	}
}
