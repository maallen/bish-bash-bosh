package com.rpm.caash;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.rpm.caash.model.Job;
import com.rpm.caash.model.User;
import com.rpm.caash.mongodb.DBObjectToPojoConverter;
import com.rpm.caash.mongodb.MongoDBApiOperator;
import com.rpm.caash.mongodb.MongoDbCollection;
import com.rpm.caash.mongodb.exceptions.MongoDbException;
import com.rpm.caash.utils.AuthUtils;
import com.rpm.caash.utils.PasswordService;
import com.rpm.caash.utils.Token;

@Path("/")
public class CaashServer {

	@EJB
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
                .append("price", job.getPrice())
				.append("latitude", job.getCoordinates().getLatitude())
                .append("longitude", job.getCoordinates().getLongitude())
                .append("email_id", job.getEmail_id());
		try {
			mongoDBApiOperator.addDbObjectToDbCollection(dbJob, MongoDbCollection.JOBS);
			return Response.status(Status.OK).build();
		} catch (final MongoDbException e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("{email_id}")
	public Response getUsersJobsById(@PathParam("email_id") String email_id) throws MongoDbException {
		
		final BasicDBObject jobsQuery = new BasicDBObject().append("email_id", email_id);
		final DBCursor usersJobs = mongoDBApiOperator.findDocumentsInCollection(jobsQuery, MongoDbCollection.JOBS);	
		
		List<Job> usersJobsAsList = new ArrayList<Job>();
		
			while (usersJobs.hasNext()) {
				DBObject jobObject = usersJobs.next();
				Job userJob = DBObjectToPojoConverter.convertToJobPOJO(jobObject);
				usersJobsAsList.add(userJob);
			}
			if(usersJobsAsList.size() > 0){
				return Response.ok().entity(usersJobsAsList).build();
			}
	   return Response.status(200).entity(usersJobs).build();

	}

}
