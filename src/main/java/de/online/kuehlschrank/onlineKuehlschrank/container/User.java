package de.online.kuehlschrank.onlineKuehlschrank.container;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import de.hongo.annotation.MongoCollectionInformation;
import de.hongo.annotation.MongoUpdateKey;

@MongoCollectionInformation(collectionName = "users", databaseName = "onlinekuehlschrank")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	@MongoUpdateKey
	private String email;
	private String password;
	private Map<String, StorageFood> userStorage;

	public User(String email, String password, String name) {
		super();
		this.name = name;
		this.password = password;
		this.email = email;
		userStorage = Collections.emptyMap();
	}

	public User(String name, String email, String password,
			Map<String, StorageFood> userStorage) {
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

	public Map<String, StorageFood> getUserStorage() {
		return userStorage;
	}

	public void setUserStorage(Map<String, StorageFood> userStorage) {
		this.userStorage = userStorage;
	}

	public void addFood(StorageFood storageFood) {
		userStorage.put(generateStorageId(storageFood), storageFood);
	}

	public static String generateStorageId(StorageFood food) {
		String datum = new SimpleDateFormat("dd.MM.yyyy").format(food
				.getExpireDate());
		datum = StringUtils.remove(datum, ".");
		String id = food.getFood().getCode() + "_" + datum;
		return id;
	}

}
