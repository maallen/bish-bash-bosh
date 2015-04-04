package com.rpm.caash.mongodb;

import javax.ejb.Singleton;
import javax.inject.Named;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.rpm.caash.mongodb.exceptions.MongoDbException;

/**
 * @author maallen87 (Mark Allen)
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
	 * @throws MongoDbException
	 */
	public void addDbObjectToDbCollection(final DBObject dbObject, final MongoDbCollection collection) throws MongoDbException{
		final DBCollection dbCollection = getCollection(collection);
		dbCollection.insert(dbObject);
	}

	/**
	 * Method to update an existing document in a collection
	 * @param searchKey
	 * @param searchValue
	 * @param updatedAttribute
	 * @param updatedAttributeValue
	 * @param collection
	 * @throws MongoDbException
	 */
	public void updateDocumentInCollection(final String searchKey, final String searchValue,final String updatedAttribute, final String updatedAttributeValue,final MongoDbCollection collection) throws MongoDbException{
		final DBCollection dbCollection = getCollection(collection);
		final BasicDBObject newDocument = new BasicDBObject();
		newDocument.append("$set", new BasicDBObject().append(updatedAttribute, updatedAttributeValue));

		final BasicDBObject searchQuery = new BasicDBObject().append(searchKey, searchValue);

		dbCollection.update(searchQuery, newDocument);
	}

	/**
	 * Retrieve all documents in a MongoDB Collection
	 * @param collection
	 * @return DBCursor object
	 * @throws MongoDbException
	 */
	public DBCursor findAllInCollection(final MongoDbCollection collection) throws MongoDbException{
		return getMongoDb().getCollection(collection.toString()).find();
	}

	/**
	 * Returns the DBCollection object for the specified collection
	 * @param collection
	 * @return DBCollection
	 * @throws MongoDbException
	 */
	public DBCollection getCollection(final MongoDbCollection collection) throws MongoDbException{
		return getMongoDb().getCollection(collection.toString());
	}

	/**
	 * Returns DBCursor object containing documents that match query
	 * 
	 * @param query
	 * @param collection
	 * @return DBCursor
	 * @throws MongoDbException
	 */
	public DBCursor findDocumentsInCollection(final BasicDBObject query, final MongoDbCollection collection) throws MongoDbException{
		final DBCollection dbCollection = getMongoDb().getCollection(collection.toString());
		return dbCollection.find(query);
	}

	private DB getMongoDb() throws MongoDbException{

		if (mongoClient != null){
			return mongoClient.getDB(CAASH);
		}

		final MongoClientURI mongoClientURI = new MongoClientURI(MONGOLAB_DB_URI);
		try {
			mongoClient = new MongoClient(mongoClientURI);
		} catch (final Exception e) {
			System.out.print("Error retrieving MongoDB Client from MongoLab");
			e.printStackTrace();
			throw new MongoDbException(e.getMessage());
		}

		return mongoClient.getDB(CAASH);
	}

}
