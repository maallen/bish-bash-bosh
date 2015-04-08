package com.rpm.caash.rest;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.nimbusds.jose.JOSEException;
import com.rpm.caash.model.User;
import com.rpm.caash.mongodb.DBObjectToPojoConverter;
import com.rpm.caash.mongodb.MongoDBApiOperator;
import com.rpm.caash.mongodb.MongoDbCollection;
import com.rpm.caash.mongodb.exceptions.MongoDbException;
import com.rpm.caash.utils.AuthUtils;
import com.rpm.caash.utils.OAuthProvider;
import com.rpm.caash.utils.PasswordService;
import com.rpm.caash.utils.Token;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.core.util.MultivaluedMapImpl;
/**
 * 
 * @author robfitz85 (Robert Fitzgerald)
 * 
 * Rest Resource for creation and authorization of Users
 */
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

	@Inject
	private MongoDBApiOperator mongoDBOperator;

	@Valid
	@NotNull
	@JsonProperty
	private final ClientSecretsConfiguration clientSecrets = new ClientSecretsConfiguration();

	private static String FACEBOOK_PROFILE_PIC_URL = "https://graph.facebook.com/%s/picture";

	private final Client client = new Client();

	public static final ObjectMapper MAPPER = new ObjectMapper();
	public static final String CLIENT_ID_KEY = "client_id";
	public static final String REDIRECT_URI_KEY = "redirect_uri";
	public static final String CLIENT_SECRET = "client_secret";
	public static final String CODE_KEY = "code";
	public static final String GRANT_TYPE_KEY = "grant_type";
	public static final String AUTH_CODE = "authorization_code";
	public static final String CONFLICT_MSG = "There is already a%s account that belongs to you",
			NOT_FOUND_MSG = "User not found",
			LOGING_ERROR_MSG = "Wrong email and/or password",
			UNLINK_ERROR_MSG = "Could not unlink %s account because it is your only sign-in method",
			EMAIL_ALREADY_REGISTERED = "There is already an account registered with this email address";
	

	/**
	 * Login using Google credentials
	 * @param payload
	 * @param request
	 * @return
	 * @throws JOSEException
	 * @throws ParseException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws ClientHandlerException
	 * @throws UniformInterfaceException
	 * @throws IOException
	 * @throws MongoDbException
	 */
	@POST
	@Path("/google")
	public Response loginWithGoogle(@Valid final Payload payload, @Context final HttpServletRequest request)
			throws JOSEException, ParseException, JsonParseException, JsonMappingException,
			ClientHandlerException, UniformInterfaceException, IOException, MongoDbException {

		final String accessTokenUrl = "https://accounts.google.com/o/oauth2/token";
		final String peopleApiUrl = "https://www.googleapis.com/plus/v1/people/me/openIdConnect";
		ClientResponse response;

		// Step 1. Exchange authorization code for access token.
		final MultivaluedMap<String, String> accessData = new MultivaluedMapImpl();
		accessData.add(CLIENT_ID_KEY, payload.getClientId());
		accessData.add(REDIRECT_URI_KEY, payload.getRedirectUri());
		accessData.add(CLIENT_SECRET, clientSecrets.getGoogle());
		accessData.add(CODE_KEY, payload.getCode());
		accessData.add(GRANT_TYPE_KEY, AUTH_CODE);
		response = client.resource(accessTokenUrl).entity(accessData).post(ClientResponse.class);
		accessData.clear();

		// Step 2. Retrieve profile information about the current user.
		final String accessToken = (String) getResponseEntity(response).get("access_token");
		response = client.resource(peopleApiUrl)
				.header(AuthUtils.AUTH_HEADER_KEY, String.format("Bearer %s", accessToken))
				.get(ClientResponse.class);
		final Map<String, Object> userInfo = getResponseEntity(response);

		// Step 3. Process the authenticated the user.
		return processUser(request, OAuthProvider.GOOGLE, userInfo.get("sub").toString(),
				userInfo.get("name").toString(), userInfo.get("picture").toString());
	}

	/**
	 * Login using Facebook credentials
	 * @param payload
	 * @param request
	 * @return response
	 * @throws MongoDbException
	 * */
	@POST
	@Path("/facebook")

	public Response loginWithFacebook(@Valid Payload payload, @Context HttpServletRequest request)
			throws JsonParseException, JsonMappingException, ClientHandlerException,
			UniformInterfaceException, IOException, ParseException, JOSEException, MongoDbException {
		final String accessTokenUrl = "https://graph.facebook.com/oauth/access_token";
		final String graphApiUrl = "https://graph.facebook.com/me";
		ClientResponse response;

		// Step 1. Exchange authorization code for access token.
		final MultivaluedMap<String, String> accessData = new MultivaluedMapImpl();
		accessData.add(CLIENT_ID_KEY, payload.getClientId());
		accessData.add(REDIRECT_URI_KEY, payload.getRedirectUri());
		accessData.add(CLIENT_SECRET, clientSecrets.getFacebook());
		accessData.add(CODE_KEY, payload.getCode());
		response = client.resource(accessTokenUrl).queryParams(accessData)
				.get(ClientResponse.class);

		final String paramStr = Preconditions.checkNotNull(response.getEntity(String.class));
		// first param is token, second is expire
		final String[] params = paramStr.split("&");
		final String[] tokenPair = params[0].split("=");
		final String[] expirePair = params[1].split("=");
		final MultivaluedMap<String, String> accessParams = new MultivaluedMapImpl();
		accessParams.add(tokenPair[0], tokenPair[1]);
		accessParams.add(expirePair[0], expirePair[1]);

		// Step 2. Retrieve profile information about the current user.
		response = client.resource(graphApiUrl).queryParams(accessParams).get(ClientResponse.class);
		final Map<String, Object> userInfo = getResponseEntity(response);
		final String id = userInfo.get("id").toString();

		// Step 3. Process the authenticated the user.
		return processUser(request, OAuthProvider.FACEBOOK, id,
				userInfo.get("name").toString(), String.format(FACEBOOK_PROFILE_PIC_URL, id));
	}
	
	@POST
	@Path("/login")
	public Response login(@Valid User user, @Context HttpServletRequest request)
			throws JOSEException, MongoDbException {
		
		final BasicDBObject userQuery = new BasicDBObject().append("email", user.getEmail());
		DBCursor usersFoundInDB = mongoDBOperator.findDocumentsInCollection(userQuery, MongoDbCollection.USERS);
		
		int numberOfUsersFound = usersFoundInDB.count();
//		User user = new User();
		
		switch(numberOfUsersFound){
		case 0:
			System.out.println("No records found for this user ==> Attempting to create user now and log in");			
			break;
	}
		return Response.status(Status.UNAUTHORIZED).entity(String.format(LOGING_ERROR_MSG))
				.build();
	}
	
	@POST
	@Path("/signup")
	public Response signup(@Valid User user, @Context HttpServletRequest request) throws JOSEException, MongoDbException {
		user.setPassword(PasswordService.hashPassword(user.getPassword()));
		
		final BasicDBObject userQuery = new BasicDBObject().append("email", user.getEmail());
		DBCursor usersFoundInDB = mongoDBOperator.findDocumentsInCollection(userQuery, MongoDbCollection.USERS);
		
		int numberOfUsersFound = usersFoundInDB.count();
		switch(numberOfUsersFound){
			case 0: addNewNonOAuthUserToDB(user);
			break;
			case 1:
				User existingUser = new User();
				existingUser.setEmail(user.getEmail());
				Response response = Response.status(Status.CONFLICT).entity(existingUser).build();
				return response;
			default:
				return Response.status(Status.CONFLICT).entity(String.format(CONFLICT_MSG, " user account")).build();
		}
		// set the password to null before return user object to client
		user.setPassword(null);
		Token token = AuthUtils.createToken(request.getRemoteHost(), user);
		return Response.status(Status.CREATED).entity(token).build();
	}
	
	
	private Response processUser(final HttpServletRequest request, final OAuthProvider provider, final String id,
			final String displayName, final String profilePicUrl) throws ParseException, JOSEException, MongoDbException {
		final BasicDBObject userQuery = new BasicDBObject().append("id", id)
				.append("provider", provider.toString());
		final DBCursor usersFoundInDB = mongoDBOperator.findDocumentsInCollection(userQuery, MongoDbCollection.USERS);

		final int numberOfUsersFound = usersFoundInDB.count();
		User user = new User();

		switch(numberOfUsersFound){
		case 0:
			System.out.println("No records found for this user ==> Attempting to create user now and log in");
			addNewUserToDB(provider, id, displayName, profilePicUrl, user);
			break;

		case 1:
			System.out.println("One record found for this user ==> Attempting to log in");
			user = DBObjectToPojoConverter.convertToUserPOJO(usersFoundInDB.next());
			break;

		default:
			System.out.println("User records for this user are duplicated");
			return Response
					.status(Status.CONFLICT)
					.entity(String.format(CONFLICT_MSG, provider.capitalize()))
					.build();
		}

		final Token token = AuthUtils.createToken(request.getRemoteHost(), user);
		return Response.ok().entity(token).build();
	}

	private void addNewUserToDB(final OAuthProvider provider, final String id,
			final String displayName, final String pictureUrl, final User user) throws MongoDbException {
		user.setProviderId(provider, id);
		user.setDisplayName(displayName);
		user.setPictureUrl(pictureUrl);

		final DBObject userDBObject = new BasicDBObject("id", id)
		.append("provider", provider.toString()).append("displayName",user.getDisplayName()).append("pictureUrl", pictureUrl);

		mongoDBOperator.addDbObjectToDbCollection(userDBObject, MongoDbCollection.USERS);
	}
	
	private void addNewNonOAuthUserToDB(User user) throws MongoDbException {
		
		final DBObject userDBObject = new BasicDBObject("email", user.getEmail())
		.append("password", user.getPassword()).append("displayName",user.getDisplayName());
		
		mongoDBOperator.addDbObjectToDbCollection(userDBObject, MongoDbCollection.USERS);
	}


	public static class ClientSecretsConfiguration {

		@JsonProperty
		String facebook;

		@JsonProperty
		//Need to figure out how to get this dynamically
		String google = "ZY8Q-UYg3gcYz5S2dbiF86BT";

		@JsonProperty
		String twitter;

		public String getFacebook() {
			//Need to figure out how to get this dynamically
			return facebook = "07173eab9b302e2c6a003168b3c4da71";
		}

		public String getGoogle() {
			return google;
		}

		public String getTwitter() {
			return twitter;
		}
	}

	public static class Payload {

		String clientId;
		String redirectUri;
		String code;

		public String getClientId() {
			return clientId;
		}

		public String getRedirectUri() {
			return redirectUri;
		}

		public String getCode() {
			return code;
		}
	}

	private Map<String, Object> getResponseEntity(final ClientResponse response)
			throws JsonParseException, JsonMappingException, ClientHandlerException,
			UniformInterfaceException, IOException {
		return MAPPER.readValue(response.getEntity(String.class),
				new TypeReference<Map<String, Object>>() {});
	}
}
