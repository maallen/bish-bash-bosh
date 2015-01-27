package com.rpm.caash;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.rpm.caash.mongodb.MongoDBApiOperator;
import com.rpm.model.Job;

@Path("/")
public class CaashServer {

	@Inject
	private MongoDBApiOperator mongoDBApiOperator;

	@GET
	@Path("/getJobs")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DBObject> getJobs(){
		final DB db = mongoDBApiOperator.getMongoDb();
		final DBCollection collection = db.getCollection("Jobs");
		final DBCursor jobs = collection.find();
		return jobs.toArray();
	}

	@POST
	@Path("/createJob")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createJobs(final Job job){
		final DBObject dbJob = new BasicDBObject("description", job.getDescription())
		.append("location", job.getLocation())
		.append("title", job.getTitle())
		.append("price", job.getPrice());
		final DBCollection collection = mongoDBApiOperator.getCollection("Jobs");
		collection.insert(dbJob);
	}

}
