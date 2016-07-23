package de.online.kuehlschrank.onlineKuehlschrank.utils;

public enum SessionKeys {
	SIGNED_IN_USER("signedInUser");
	
	private String name;

	private SessionKeys(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
