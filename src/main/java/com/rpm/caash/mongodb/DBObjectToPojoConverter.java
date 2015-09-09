package com.rpm.caash.mongodb;

import com.mongodb.DBObject;
import com.rpm.caash.model.Coordinates;
import com.rpm.caash.model.Job;
import com.rpm.caash.model.User;
import com.rpm.caash.utils.OAuthProvider;

/**
 * 
 * @author maallen87 (Mark Allen)
 * 
 * Utility class that converts DBObject objects to POJO Objects
 */
public final class DBObjectToPojoConverter {

	private DBObjectToPojoConverter(){

	}

	/**
	 * Converts DBObject to Job POJO
	 * @param dbObject
	 * @return Job
	 */
	public static Job convertToJobPOJO(final DBObject dbObject){
		final Job job = new Job();
		
		convertCoOrdinates(dbObject, job);
		
		job.setLocation((String) dbObject.get("location"));
		job.setDescription((String) dbObject.get("description"));
		job.setPrice((Integer) dbObject.get("price"));
		job.setTitle((String) dbObject.get("title"));
		return job;
	}

	private static void convertCoOrdinates(final DBObject dbObject,
			final Job job) {
		final Coordinates coOrdinates = new Coordinates();
		coOrdinates.setLatitude((double) dbObject.get("latitude"));
		coOrdinates.setLongitude((double) dbObject.get("longitude"));
		job.setCoordinates(coOrdinates);
	}

	/**
	 * Converts DBObject to User POJO
	 * @param dbObject
	 * @return User
	 */
	public static User convertToUserPOJO(final DBObject dbObject){
		final User user = new User();
		user.setEmail((String) dbObject.get("email"));
		user.setDisplayName((String) dbObject.get("displayName"));
		user.setPictureUrl((String) dbObject.get("pictureUrl"));
		user.setPassword((String) dbObject.get("password")); 
		if(dbObject.get("provider") != null){
			final String providerAsString = (String) dbObject.get("provider");
			final OAuthProvider provider = OAuthProvider.valueOf(providerAsString);
			user.setProviderId(provider, providerAsString);
		}
		return user;
	}

}
