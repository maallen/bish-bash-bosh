package com.rpm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
	
	private String id;
	private String email;
	private String password;
	private String displayName;
	private String facebook;
	private String google;
	private String twitter;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getFacebook() {
		return facebook;
	}

	public String getGoogle() {
		return google;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDisplayName(String name) {
		this.displayName = name;
	}

	public void setProviderId(OAuthProvider oauthProvider, String value) {
		switch (oauthProvider) {
		case FACEBOOK:
			this.facebook = value;
			break;
		case GOOGLE:
			this.google = value;
			break;
		case TWITTER:
			this.twitter = value;
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	@JsonIgnore
	public int getSignInMethodCount() throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		int count = 0;

		if (this.getPassword() != null) {
			count++;
		}

		for (OAuthProvider p : OAuthProvider.values()) {
			if (this.getClass().getDeclaredField(p.name).get(this) != null) {
				count++;
			}
		}

		return count;
	}

	
}
