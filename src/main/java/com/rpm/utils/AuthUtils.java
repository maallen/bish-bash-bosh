package com.rpm.utils;

import org.joda.time.DateTime;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

/**
 * Utility class for OAuth Handling
 */
public class AuthUtils {
	
	public static final String AUTH_HEADER_KEY = "Authorization";
	private static final String TOKEN_SECRET = "aliceinwonderlandisareallypoormoviedontwatchit";
	private static final JWSHeader JWT_HEADER = new JWSHeader(JWSAlgorithm.HS256);
	
	public static Token createToken(String host, long sub) throws JOSEException {
		JWTClaimsSet claim = new JWTClaimsSet();
		claim.setSubject(Long.toString(sub));
		claim.setIssuer(host);
		claim.setIssueTime(DateTime.now().toDate());
		claim.setExpirationTime(DateTime.now().plusDays(14).toDate());
		
		JWSSigner signer = new MACSigner(TOKEN_SECRET);
		SignedJWT jwt = new SignedJWT(JWT_HEADER, claim);
		jwt.sign(signer);
		
		return new Token(jwt.serialize());
	}

}
