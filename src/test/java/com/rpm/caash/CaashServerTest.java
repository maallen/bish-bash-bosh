package com.rpm.caash;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.rpm.caash.mongodb.MongoDBApiOperator;
import com.rpm.caash.mongodb.MongoDbCollection;
import com.rpm.caash.mongodb.exceptions.MongoDbException;
import com.rpm.model.Job;

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
		final DBObject dbObject = new BasicDBObject("description", job.getDescription())
		.append("location", job.getLocation())
		.append("title", job.getTitle())
		.append("price", job.getPrice());
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
