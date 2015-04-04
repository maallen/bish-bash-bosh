package com.rpm.caash.utils;
import org.apache.commons.lang3.StringUtils;

/**
 * Enum for OAuth Provider
 * 
 */
public enum OAuthProvider {
	FACEBOOK("facebook"), GOOGLE("google"), TWITTER("twitter");

	String name;

	OAuthProvider(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String capitalize() {
		return StringUtils.capitalize(this.name);
	}
}
