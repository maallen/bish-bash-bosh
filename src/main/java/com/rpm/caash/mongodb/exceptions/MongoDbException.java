package com.rpm.caash.mongodb.exceptions;

public class MongoDbException extends Exception{

	private static final long serialVersionUID = -911301128303403942L;

	private final String message;

	public MongoDbException(final String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
