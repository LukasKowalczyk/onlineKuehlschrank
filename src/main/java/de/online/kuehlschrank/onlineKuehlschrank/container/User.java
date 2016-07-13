package de.online.kuehlschrank.onlineKuehlschrank.container;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import de.mongoDBHelper.annotation.MongoCollectionInforamtion;

@MongoCollectionInforamtion(collectionName = "users")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String email;
	private String password;
	private List<Food> userStorage;

	public User(String email, String password, String name) {
		super();
		this.name = name;
		this.password = password;
		this.email = email;
		userStorage = Collections.emptyList();
	}

	public User(String name, String email, String password,
			List<Food> userStorage) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.userStorage = userStorage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Food> getUserStorage() {
		return userStorage;
	}

	public void setUserStorage(List<Food> userStorage) {
		this.userStorage = userStorage;
	}
}
