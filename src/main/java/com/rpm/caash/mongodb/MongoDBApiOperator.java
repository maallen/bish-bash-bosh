package com.rpm.caash.mongodb;

import java.net.UnknownHostException;

import javax.ejb.Singleton;
import javax.inject.Named;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * Operator Class to access and use Mongo database
 */

@Named
@Singleton
public class MongoDBApiOperator {

	private static final String CAASH = "caash";

	private static final String MONGOLAB_DB_URI = "mongodb://root:shroot@ds033419.mongolab.com:33419/caash";

	private MongoClient mongoClient;

	public MongoDBApiOperator(){

	}

	/**
	 * @return The MongoDB Database
	 */
	public DB getMongoDb(){

		if (mongoClient != null){
			return mongoClient.getDB(CAASH);
		}

		final MongoClientURI mongoClientURI = new MongoClientURI(MONGOLAB_DB_URI);
		try {
			mongoClient = new MongoClient(mongoClientURI);
		} catch (final UnknownHostException e) {
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
	public DBCollection getCollection(final String collectionName){
		return getMongoDb().getCollection(collectionName);
	}

}
