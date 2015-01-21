package com.rpm.caash.mongodb;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * Operator Class to access and use Mongo database
 */
public class MongoDBApiOperator {
	
	private static final String CAASH = "caash";
	
	private static final String MONGOLAB_DB_URI = "mongodb://root:shroot@ds033419.mongolab.com:33419/caash";
	
	private static MongoClient mongoClient;
	
	private static MongoDBApiOperator mongoDBApiOperator;
	
	private MongoDBApiOperator(){
		
	}
	
	public static MongoDBApiOperator getInstance(){
		if(mongoDBApiOperator != null){
			return mongoDBApiOperator;
		}
		
		return new MongoDBApiOperator();
	}

	/**
	 * @return The MongoDB Database
	 */
	public DB getMongoDb(){
			
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
	
	/**
	 *  
	 * @param collectionName
	 * @return DBCollection object for the required MongoDB Collection
	 */
	public DBCollection getCollection(String collectionName){
		return getMongoDb().getCollection(collectionName);
	}
	
}
