package com.rpm.caash.mongodb;

public enum MongoDbCollection {
	JOBS("Jobs"), USERS("Users");

	private String collectionName;

	private MongoDbCollection(final String collectionName){
		this.collectionName = collectionName;
	}

	@Override
	public String toString(){
		return collectionName;
	}

}
