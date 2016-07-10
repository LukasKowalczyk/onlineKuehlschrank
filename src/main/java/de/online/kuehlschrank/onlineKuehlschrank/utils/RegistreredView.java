package de.online.kuehlschrank.onlineKuehlschrank.utils;

public enum RegistreredView {
	LOGIN("login"), MAIN("main");
	private String name;

	private RegistreredView(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
