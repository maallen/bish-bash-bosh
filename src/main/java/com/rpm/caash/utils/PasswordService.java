package com.rpm.caash.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Service to hash a password on sign up and allow safe retrieval of same password on login
 * @author Robert
 *
 */
public class PasswordService {
	public static String hashPassword(String plaintext) {
		return BCrypt.hashpw(plaintext, BCrypt.gensalt());
	}
	
	public static boolean checkPassword(String plaintext , String hashed) {
		return BCrypt.checkpw(plaintext, hashed);
	}
}
