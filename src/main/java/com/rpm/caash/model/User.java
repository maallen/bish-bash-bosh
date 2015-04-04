package com.rpm.caash.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rpm.caash.utils.OAuthProvider;

public class User {

	private String id;
	private String email;
	private String password;
	private String displayName;
	private String facebook;
	private String google;
	private String twitter;
	private String pictureUrl;

	public void setId(final String id) {
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

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setDisplayName(final String name) {
		this.displayName = name;
	}

	public void setProviderId(final OAuthProvider oauthProvider, final String value) {
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

		for (final OAuthProvider p : OAuthProvider.values()) {
			if (this.getClass().getDeclaredField(p.getName()).get(this) != null) {
				count++;
			}
		}

		return count;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}


}
