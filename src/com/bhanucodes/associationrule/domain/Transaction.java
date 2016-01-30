package com.bhanucodes.associationrule.domain;

import java.util.List;

public class Transaction {

	private String transactionId;
	private List<String> items;

	public Transaction(String transactionId, List<String> items) {
		super();
		this.transactionId = transactionId;
		this.items = items;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

}
