package com.rpm.caash.mongodb;

import com.mongodb.DBObject;
import com.rpm.model.Job;
import com.rpm.model.User;

/**
 * 
 * @author maallen87 (Mark Allen)
 * 
 * Utility class that converts DBObject objects to POJO Objects
 */
public class DBObjectToPojoConverter {
	
	/**
	 * Converts DBObject to Job POJO 
	 * @param dbObject
	 * @return Job
	 */
	public static Job convertToJobPOJO(DBObject dbObject){
		Job job = new Job();
		job.setLocation((String) dbObject.get("location"));
		job.setDescription((String) dbObject.get("description"));
		job.setJobPrice((Integer) dbObject.get("jobPrice"));
		job.setJobName((String) dbObject.get("jobName"));
		return job;
	}
	
	/**
	 * Converts DBObject to User POJO 
	 * @param dbObject
	 * @return User
	 */
	public static User convertToUserPOJO(DBObject dbObject){
		User user = new User();
		user.setEmail((String) dbObject.get("email"));
		user.setPassword((String) dbObject.get("password"));
		return user;
	}

}
