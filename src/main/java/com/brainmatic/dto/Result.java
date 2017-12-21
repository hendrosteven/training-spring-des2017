package com.brainmatic.dto;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {
	private int status; // 0=error 1=success
	private List<String> messages = new ArrayList<String>();
	private T payload;
	
	public Result(){}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Result(List<String> messages, T payload) {
		super();
		this.messages = messages;
		this.payload = payload;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}
}
