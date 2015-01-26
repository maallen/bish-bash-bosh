package com.rpm.caash;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.rpm.caash.mongodb.MongoDBApiOperator;
import com.rpm.model.Job;

@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest(MongoDBApiOperator.class)
public class CaashServerTest {

	private CaashServer caashServer;
	private MongoDBApiOperator mongoOperator;

	@Mock
	private DB mongoDb;
	@Mock
	private DBCollection collection;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mongoOperator = spy(MongoDBApiOperator.getInstance());
		PowerMockito.mockStatic(MongoDBApiOperator.class);
		when(MongoDBApiOperator.getInstance()).thenReturn(mongoOperator);
		doReturn(collection).when(mongoOperator).getCollection(isA(String.class));
		caashServer = new CaashServer();
	}

	@After
	public void tearDown() throws Exception {
		mongoDb = null;
		mongoOperator = null;
		collection = null;
		caashServer = null;
	}

	@Test
	public void testCreateJob() {
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
		verify(collection).insert(dbObject);
	}

}
