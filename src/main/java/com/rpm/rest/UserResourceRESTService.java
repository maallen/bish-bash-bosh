package com.rpm.rest;
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
import com.rpm.model.User;

@Path("/members")
public class UserResourceRESTService {

	@Inject
	private MongoDBApiOperator mongoDBOperator;

	/**
	 * @return a list of users users
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DBObject> listAllUsers() {

		DBCursor users;
		try {
			users = mongoDBOperator.findAllInCollection(MongoDbCollection.USERS);
			return users.toArray();
		} catch (final MongoDbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}


	/** Create a User
	 * @param user
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createMember(final User user){
		final DBObject dbJob = new BasicDBObject("email", user.getEmail())
		.append("password", user.getPassword());
		try {
			mongoDBOperator.addDbObjectToDbCollection(dbJob, MongoDbCollection.USERS);
		} catch (final MongoDbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response logIn(final User user) {
		String returnValue ="Failure ==> User does not exist in the DB";

		DBCursor cursor;
		try {
			cursor = mongoDBOperator.findAllInCollection(MongoDbCollection.USERS);
		} catch (final MongoDbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		while(cursor.hasNext()){
			final DBObject o = cursor.next();
			final String email = (String) o.get("email");
			final String password = (String) o.get("password");

			if(user.getEmail().equals(email) && user.getPassword().equals(password)){

				returnValue = "Success ==> This user exists in the DB";
				System.out.println(returnValue);
				return Response.ok().build();

			}
		}

		System.out.println(returnValue);
		return Response.status(Status.BAD_REQUEST).build();
	}

}
