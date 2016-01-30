package com.bhanucodes.associationrule.dto.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bhanucodes.associationrule.domain.Item;
import com.bhanucodes.associationrule.domain.Transaction;
import com.bhanucodes.associationrule.dto.TransactionManager;

public class TransactionManagerImpl implements TransactionManager {

	@Override
	public List<Transaction> populateTransaction() {
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		List<String> items = new ArrayList<String>();
		items.add("A");items.add("B");items.add("C");
		transactions.add(new Transaction("T1",items));
		
		items = new ArrayList<String>();
		items.add("A");items.add("B");items.add("D");
		transactions.add(new Transaction("T2",items));
		
		items = new ArrayList<String>();
		items.add("B");items.add("E");items.add("C");
		transactions.add(new Transaction("T3",items));
		
		items = new ArrayList<String>();
		items.add("C");items.add("E");
		transactions.add(new Transaction("T4",items));
		
		items = new ArrayList<String>();
		items.add("C");items.add("B");items.add("E");
		transactions.add(new Transaction("T5",items));
		
		return transactions;
	}
	
	@Override
	public Map<String,Integer> findInitialItemSet(List<Transaction> transactions){
		Map<String, Integer> l1 = new HashMap<String, Integer>();
		
		Set<String> individualItems = new HashSet<String>();
		for(Transaction transaction: transactions){
			List<String> items = transaction.getItems();
			
			for(String item : items){
				individualItems.add(item);
			}
			
			for(String item : individualItems){
				l1.put(item, 0);
			}
			
		}
		for(Transaction transaction: transactions){
			List<String> items = transaction.getItems();
			
			for(String item :items){
				 int count = l1.get(item);
				if(items.contains(item)){
					l1.put(item, ++count);
				}
			}
		}
		
		return l1;
	}
	
	@Override
	public Map<String, Integer> findInitialFrequestItemSet(
			List<Transaction> transactions, Map<String, Integer> l1, Integer minSupport) {

		Map<String, Integer> c1 = new HashMap<String, Integer>();
		
		for(String item : l1.keySet()){
			if(l1.get(item)>= minSupport){
				c1.put(item, l1.get(item));
			}
		}
		return c1;
	}

	@Override
	public Map<List<String>, Integer> findSecondItemSet(
			List<Transaction> transactions, Map<String, Integer> c1) {

		Map<List<String>, Integer> c2 = new HashMap<List<String>, Integer>();
		
		//Create initial combinations
		
		Set<List<String>> c2Items = new HashSet<List<String>>();
		
		for(String item1 : c1.keySet()){
			
			System.out.println("\n\n\n\n");
			
			System.out.println("PROCESSING FOR " + item1);
			for(String item2 : c1.keySet()){
				System.out.println("Comparing with " + item2);	
				List<String> items = new ArrayList<String>();
				if(!item1.equals(item2)){
					items.add(item1);
					items.add(item2);
					java.util.Collections.sort((items));
					System.out.println("OBTAINED LIST :: " + items);
					c2Items.add(items);
				}
				
			}
		}
		
		for(List<String> items : c2Items){
			c2.put(items, 0);
		}
		
		
		c2 = getSupportForListOfItems(c2, transactions, c2Items, 2);
		
		return c2;
	}
	
	
	public Map<List<String>, Integer> getSupportForListOfItems(Map<List<String>, Integer> c2, List<Transaction> transactions, Set<List<String>> c2Items, int iteration){
		
		for(Transaction transaction: transactions){
			List<String> items = transaction.getItems();
		
			
				for(List<String> candidateItems : c2Items){
					 int count = c2.get(candidateItems);
					 int flag = 0;
					 for(String item: candidateItems){
						 if(items.contains(item)){
							flag++;
						 }
					 }
					 
					 if(flag == iteration){
						 c2.put(candidateItems,++count);
					 }
				}
			
		}
		
		return c2;
	}

	@Override
	public Map<List<String>, Integer> findFrequestItemSet(
			List<Transaction> transactions, Map<List<String>, Integer> lk,
			Integer minSupport) {
	
		Map<List<String>, Integer> ck = new HashMap<List<String>, Integer>();
		
		for(List<String> itemset : lk.keySet()){
			if(lk.get(itemset)>= minSupport){
				ck.put(itemset, lk.get(itemset));
			}
		}
		
		return ck;
	}

	@Override
	public Map<List<String>, Integer> findKthItemSet(
			List<Transaction> transactions, Map<List<String>, Integer> lk, int iteration) {
		
		Map<List<String>, Integer> ck = new HashMap<List<String>, Integer>();
		
		//Create initial combinations
		
		Set<List<String>> ckItems = new HashSet<List<String>>();
		
		for(List<String> itemset1 : lk.keySet()){
			
			System.out.println("\n\n\n\n");
			
			System.out.println("PROCESSING FOR " + itemset1);
			
			for(List<String> itemset2 : lk.keySet()){
				
				System.out.println("\nCOMPARING WITH " + itemset2);
				if(itemset1 != itemset2){
					List<String> mergedItems =new ArrayList<String>(); 
						mergedItems =	mergeItemsets(itemset1,itemset2); 
					if(mergedItems.size() == iteration && checkSubItemSets(mergedItems, lk, iteration) ){
					 ckItems.add(mergedItems);
					 System.out.println("OBTAINED IS " + mergedItems);
					}
					
				}
			}
		}
		
		for(List<String> items : ckItems){
			ck.put(items, 0);
		}
		
		
		ck = getSupportForListOfItems(ck, transactions, ckItems, 3);
		
		return ck;
	}

	private List<String> mergeItemsets(List<String> itemset1,
			List<String> itemset2 ) {
		Set<String> items = new HashSet<String>();
		
		for(String item :itemset1){
			items.add(item);
		}
		
		for(String item :itemset2){
			items.add(item);
		}
		
		List<String> mergedList = new ArrayList<String>(items);
		java.util.Collections.sort((mergedList));
		
		return mergedList;
	}
	
	private boolean checkSubItemSets(List<String> mergedItems,Map<List<String>, Integer> lk, int iteration){
		
		Set<List<String>> subItemSets = new HashSet<List<String>>();
		
		for(int i = 0 ; i < (iteration); i++){
			List<String> subset = new ArrayList<String>();
			for(int j = 0 ; j< (iteration-1) ; j++){
				int index = (i+j)%(iteration);
				subset.add(mergedItems.get(index));
				System.out.println();
			}
			System.out.println(subset);
			java.util.Collections.sort((subset));
			subItemSets.add(subset);
			
		}
		
		System.out.println("CHECKING subseys");
		for(List<String> subset : subItemSets){
			System.out.println(subset);
			if(!lk.keySet().contains(subset)){
				return false;
			}
		}
		
		return true;
	}

}
