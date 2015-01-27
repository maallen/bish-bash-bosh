//package com.rpm.caash;
//
//import static org.mockito.Matchers.isA;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.verify;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.mongodb.BasicDBObject;
//import com.mongodb.DBCollection;
//import com.mongodb.DBObject;
//import com.rpm.caash.mongodb.MongoDBApiOperator;
//import com.rpm.model.Job;
//
//public class CaashServerTest {
//
//	@Mock
//	private MongoDBApiOperator mongoDBApiOperator;
//	@Mock
//	private DBCollection collection;
//	@InjectMocks
//	private CaashServer caashServer;
//
//	@Before
//	public void setUp() throws Exception {
//		MockitoAnnotations.initMocks(this);
//		doReturn(collection).when(mongoDBApiOperator).getCollection(isA(String.class));
//	}
//
//	@After
//	public void tearDown() throws Exception {
//		mongoDBApiOperator = null;
//		collection = null;
//		caashServer = null;
//	}
//
//	@Test
//	public void testCreateJob() {
//		final Job job = new Job();
//		job.setTitle("Test Job");
//		job.setDescription("Test description");
//		job.setLocation("Athlone");
//		job.setPrice(1000);
//		final DBObject dbObject = new BasicDBObject("description", job.getDescription())
//		.append("location", job.getLocation())
//		.append("title", job.getTitle())
//		.append("price", job.getPrice());
//		caashServer.createJobs(job);
//		verify(collection).insert(dbObject);
//	}
//
//}
