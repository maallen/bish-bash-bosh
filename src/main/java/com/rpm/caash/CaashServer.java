package com.rpm.caash;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.rpm.caash.mongodb.MongoDBApiOperator;
import com.rpm.caash.mongodb.MongoDbCollection;
import com.rpm.caash.mongodb.exceptions.MongoDbException;
import com.rpm.model.Job;

@Path("/")
public class CaashServer {

	@Inject
	private MongoDBApiOperator mongoDBApiOperator;

	@GET
	@Path("/getJobs")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DBObject> getJobs(){
		DBCursor jobs;
		try {
			jobs = mongoDBApiOperator.findAllInCollection(MongoDbCollection.JOBS);
			return jobs.toArray();
		} catch (final MongoDbException e) {
			e.printStackTrace();
			return null;
		}
	}

	@POST
	@Path("/createJob")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createJobs(final Job job){
		final DBObject dbJob = new BasicDBObject("description", job.getDescription())
		.append("location", job.getLocation())
		.append("title", job.getTitle())
		.append("price", job.getPrice());
		try {
			mongoDBApiOperator.addDbObjectToDbCollection(dbJob, MongoDbCollection.JOBS);
			return Response.status(Status.OK).build();
		} catch (final MongoDbException e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
