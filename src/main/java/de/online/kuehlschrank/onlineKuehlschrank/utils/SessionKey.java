package de.online.kuehlschrank.onlineKuehlschrank.utils;

public enum SessionKey {
	SIGNED_IN_USER("signedInUser")
	;
	private String name;

	private SessionKey(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
