package com.rpm.caash;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rpm.caash.model.Coordinates;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.rpm.caash.model.Job;
import com.rpm.caash.mongodb.MongoDBApiOperator;
import com.rpm.caash.mongodb.MongoDbCollection;
import com.rpm.caash.mongodb.exceptions.MongoDbException;

public class CaashServerTest {

	@Mock
	private MongoDBApiOperator mockMongoDBApiOperator;

	@Mock
	private DBCursor mockDbCursor;

	@InjectMocks
	private CaashServer caashServer;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
		mockMongoDBApiOperator = null;
		caashServer = null;
		mockDbCursor = null;
	}

	@Test
	public void testCreateJob() throws MongoDbException {
		final Job job = new Job();
		job.setTitle("Test Job");
		job.setDescription("Test description");
		job.setLocation("Athlone");
		job.setPrice(1000);
		job.setCoordinates(new Coordinates(0.0563, 6.4444));
		job.setEmailId("dummyemail@gmail.com");
		final DBObject dbObject = new BasicDBObject("description", job.getDescription())
		    .append("location", job.getLocation())
		    .append("title", job.getTitle())
		    .append("price", job.getPrice())
            .append("latitude", job.getCoordinates().getLatitude())
            .append("longitude", job.getCoordinates().getLongitude())
            .append("email_id", job.getEmail_id());
		caashServer.createJobs(job);
		verify(mockMongoDBApiOperator, times(1)).addDbObjectToDbCollection(dbObject, MongoDbCollection.JOBS);
	}

	@Test
	public void testGetJobs() throws MongoDbException{
		when(mockMongoDBApiOperator.findAllInCollection(MongoDbCollection.JOBS)).thenReturn(mockDbCursor);
		caashServer.getJobs();
		verify(mockMongoDBApiOperator, times(1)).findAllInCollection(MongoDbCollection.JOBS);
		verify(mockDbCursor, times(1)).toArray();
	}

}
