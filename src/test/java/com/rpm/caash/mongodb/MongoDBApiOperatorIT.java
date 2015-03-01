package com.rpm.caash.mongodb;

import java.io.File;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mongodb.DBCollection;
import com.rpm.caash.CaashServer;
import com.rpm.model.Job;


@RunWith(Arquillian.class)
public class MongoDBApiOperatorIT {

	@Deployment
	public static WebArchive createDeployment(){

		final File pom = new File("pom.xml");

		final File[] libs = Maven.resolver().loadPomFromFile(pom).importRuntimeDependencies().
				resolve().withoutTransitivity().asFile();

		final WebArchive war = ShrinkWrap.create(WebArchive.class, "arquillan-caash-test.war").
				addClasses(MongoDBApiOperator.class, CaashServer.class, Job.class, MongoDbCollection.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

		for(final File lib: libs){
			war.addAsLibrary(lib);
		}

		return war;
	}

	@Inject
	private MongoDBApiOperator mongoDBApiOperator;

	@After
	public void tearDown(){
		mongoDBApiOperator = null;
	}

	@Test
	public void testMongoDBApiOperatorIsNotNull(){
		Assert.assertNotNull(mongoDBApiOperator);
		Assert.assertTrue(mongoDBApiOperator instanceof MongoDBApiOperator);
	}

	@Test
	public void testCollectionsCanBeRetrievedFromMongoLab(){
		final DBCollection jobsCollection = mongoDBApiOperator.getCollection(MongoDbCollection.JOBS);
		final DBCollection usersCollection = mongoDBApiOperator.getCollection(MongoDbCollection.USERS);
		Assert.assertEquals("Jobs", jobsCollection.getName());
		Assert.assertEquals("Users", usersCollection.getName());
	}

}
