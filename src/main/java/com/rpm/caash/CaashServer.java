package com.rpm.caash;

import java.net.UnknownHostException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.rpm.model.Job;

@Path("/")
public class CaashServer {
	
	private static final String CAASH = "caash";
	
	private static final String MONGOLAB_DB_URI = "mongodb://root:shroot@ds033419.mongolab.com:33419/caash";
	
	private MongoClient mongoClient;
	
	@GET
	@Path("/testHello")
	public String helloWorld(){
		return "Get in!!! It worked!!!";
	}
	
	@GET
	@Path("/getJobs")
	@Produces(MediaType.APPLICATION_JSON)
	public String getJobs(){
		DB db = getMongoDb();
		DBCollection collection = db.getCollection("Jobs");
		DBObject doc = collection.findOne();
		return doc.toString();
	}

	@POST
	@Path("/createJob")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createJobs(Job job){
		DBObject dbJob = new BasicDBObject("id", job.getId())
				.append("description", job.getDescription())
				.append("placedBy", job.getPlacedBy())
				.append("location", job.getLocation());	
		DB db = getMongoDb();
		DBCollection collection = db.getCollection("Jobs");
		collection.insert(dbJob);
	}
	
	private DB getMongoDb(){
		
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
