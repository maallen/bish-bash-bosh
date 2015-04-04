package com.rpm.caash.utils;

import java.text.ParseException;

import org.joda.time.DateTime;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.rpm.caash.model.User;

/**
 * Utility class for OAuth Handling
 */
public class AuthUtils {

	public static final String AUTH_HEADER_KEY = "Authorization";
	private static final String TOKEN_SECRET = "aliceinwonderlandisareallypoormoviedontwatchit";
	private static final JWSHeader JWT_HEADER = new JWSHeader(JWSAlgorithm.HS256);

	public static Token createToken(final String host, final User user) throws JOSEException {
		final JWTClaimsSet claim = new JWTClaimsSet();
		claim.setSubject(user.getId());
		claim.setIssuer(host);
		claim.setIssueTime(DateTime.now().toDate());
		claim.setExpirationTime(DateTime.now().plusDays(14).toDate());

		final JWSSigner signer = new MACSigner(TOKEN_SECRET);
		final SignedJWT jwt = new SignedJWT(JWT_HEADER, claim);
		jwt.sign(signer);

		return new Token(jwt.serialize(), user);
	}

	public static String getSubject(final String authHeader) throws ParseException, JOSEException {
		return decodeToken(authHeader).getSubject();
	}

	public static ReadOnlyJWTClaimsSet decodeToken(final String authHeader) throws ParseException, JOSEException {
		final SignedJWT signedJWT = SignedJWT.parse(getSerializedToken(authHeader));
		if (signedJWT.verify(new MACVerifier(TOKEN_SECRET))) {
			return signedJWT.getJWTClaimsSet();
		} else {
			throw new JOSEException("Signature verification failed");
		}
	}
	public static String getSerializedToken(final String authHeader) {
		return authHeader.split(" ")[1];
	}

}
