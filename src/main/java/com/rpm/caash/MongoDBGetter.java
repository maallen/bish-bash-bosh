package com.rpm.caash;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * Utility Class to get the MongoDB Database
 */
public class MongoDBGetter {
	
	private static final String CAASH = "caash";
	
	private static final String MONGOLAB_DB_URI = "mongodb://root:shroot@ds033419.mongolab.com:33419/caash";
	
	private static MongoClient mongoClient;

	/**
	 * @return The MongoDB Database
	 */
	public static DB getMongoDb(){
			
		if (mongoClient != null){
			return mongoClient.getDB(CAASH);
		}
		
		MongoClientURI mongoClientURI = new MongoClientURI(MONGOLAB_DB_URI);
		try {
			mongoClient = new MongoClient(mongoClientURI);
		} catch (UnknownHostException e) {
			System.out.print("Error retrieving MongoDB Client from MongoLab");
			e.printStackTrace();
		}
		
		return mongoClient.getDB(CAASH);
	}
	
}
