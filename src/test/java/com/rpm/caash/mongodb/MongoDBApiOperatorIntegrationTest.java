package com.rpm.caash.mongodb;

import java.io.File;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mongodb.DB;
import com.mongodb.DBCollection;


@RunWith(Arquillian.class)
public class MongoDBApiOperatorIntegrationTest {

	@Deployment
	public static WebArchive createDeployment(){

		return ShrinkWrap.create(ZipImporter.class, "test.war")
				.importFrom(new File("target/caash-1.0.war"))
				.as(WebArchive.class);
	}

	@Inject
	private MongoDBApiOperator mongoDBApiOperator;

	@After
	public void tearDown(){
		mongoDBApiOperator = null;
	}

	@Test
	public void testMongoDBApiOperatorIsAvailableThroughCDI(){
		Assert.assertNotNull(mongoDBApiOperator);
		Assert.assertTrue(mongoDBApiOperator instanceof MongoDBApiOperator);
	}

	@Test
	public void testMongoDbIsRetrievedFromMongoLab(){
		final DB mongoDb = mongoDBApiOperator.getMongoDb();
		Assert.assertEquals("caash", mongoDb.getName());
	}

	@Test
	public void testCollectionsCanBeRetrievedFromMongoLab(){
		final DB mongoDb = mongoDBApiOperator.getMongoDb();
		final DBCollection jobsCollection = mongoDb.getCollection("Jobs");
		final DBCollection usersCollection = mongoDb.getCollection("Users");
		Assert.assertEquals("Jobs", jobsCollection.getName());
		Assert.assertEquals("Users", usersCollection.getName());
	}

	//	@Test
	//	public void testCaashServerMongoDbIntegration(){
	//		final CaashServer caashServer = new CaashServer();
	//		caashServer.getJobs();
	//	}

}
