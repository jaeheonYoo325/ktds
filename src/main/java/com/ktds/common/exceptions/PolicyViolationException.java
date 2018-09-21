package com.ktds.common.exceptions;

public class PolicyViolationException extends RuntimeException {

	private String message;
	private String redirectUri;

	public PolicyViolationException(String message, String redirectUri) {
		this.message = message;
		this.redirectUri = redirectUri;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

}
