package com.rpm.caash.mongodb;

import java.net.UnknownHostException;

import javax.ejb.Singleton;
import javax.inject.Named;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * Operator Class to access and use Mongo database
 */

@Named
@Singleton
public class MongoDBApiOperator {

	private static final String CAASH = "caash";

	private static final String MONGOLAB_DB_USER = System.getenv("MONGOLAB_DB_USER");

	private static final String MONGOLAB_DB_PASS = System.getenv("MONGOLAB_DB_PASS");

	private static final String MONGOLAB_DB_URI = String.format("mongodb://%1$s:%2$s@ds033419.mongolab.com:33419/%3$s", MONGOLAB_DB_USER, MONGOLAB_DB_PASS, CAASH);

	private MongoClient mongoClient;

	/**
	 * Method to add a DBObject to a specified Mongo DB Collection
	 * @param dbObject
	 * @param collection
	 */
	public void addDbObjectToDbCollection(final DBObject dbObject, final MongoDbCollection collection){
		final DBCollection dbCollection = getCollection(collection);
		dbCollection.insert(dbObject);
	}

	/**
	 * Retrieve all documents in a MongoDB Collection
	 * @param collection
	 * @return DBCursor object
	 */
	public DBCursor findAllInCollection(final MongoDbCollection collection){
		return getMongoDb().getCollection(collection.toString()).find();
	}

	/**
	 * 
	 * @param collectionName
	 * @return DBCollection object for the required MongoDB Collection
	 */
	public DBCollection getCollection(final MongoDbCollection collection){
		return getMongoDb().getCollection(collection.toString());
	}

	private DB getMongoDb(){

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

}
