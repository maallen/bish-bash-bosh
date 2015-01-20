package com.rpm.rest;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.rpm.caash.MongoDBGetter;
import com.rpm.model.User;

@Path("/members")
public class UserResourceRESTService {
	
	
	/**
	 * @return a user from MongoDB database
	 */
	@GET
    @Path("/getUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public User lookupMemberById() {
	
		DBCollection collection = MongoDBGetter.getMongoDb().getCollection("Users");
		DBObject user = collection.findOne();
		
        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return (User) user;
    }
	
	/**
	 * @return a list of users users
	 */
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DBObject> listAllUserss() {
		
		DBCollection collection = MongoDBGetter.getMongoDb().getCollection("Users");
		DBCursor users = collection.find();
		
        return users.toArray();
    }

	
	/** Create a User
	 * @param user
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createMember(User user){
		DBObject dbJob = new BasicDBObject("email", user.getEmail())
				.append("password", user.getPassword());

		DBCollection collection = MongoDBGetter.getMongoDb().getCollection("Users");
		
		collection.insert(dbJob);

	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
    public Response logIn(User user) {
		String returnValue ="Failure ==> User does not exist in the DB";
		
		DBCollection collection = MongoDBGetter.getMongoDb().getCollection("Users");
		
		DBCursor cursor = collection.find();
		
			while(cursor.hasNext()){
				DBObject o = cursor.next();
				String email = (String) o.get("email");
				String password = (String) o.get("password");
				
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
