package de.online.kuehlschrank.onlineKuehlschrank.utils;

public enum KnownView {
	LOGIN("login"), MAIN("main"), REGISTRATION("registration");
	private String name;

	private KnownView(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
