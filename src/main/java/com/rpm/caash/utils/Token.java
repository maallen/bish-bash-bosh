package com.rpm.caash.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rpm.caash.model.User;

public class Token {
	String token;
	User user;

	public Token(@JsonProperty("token") final String token, @JsonProperty("user") final User user) {
		this.token = token;
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public User getUser(){
		return user;
	}
}
