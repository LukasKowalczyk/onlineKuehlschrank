package de.online.kuehlschrank.onlineKuehlschrank.container;

import java.io.Serializable;

import de.hongo.annotation.MongoCollectionInformation;
import de.hongo.annotation.MongoUpdateKey;

@MongoCollectionInformation(collectionName = "foods", databaseName = "onlinekuehlschrank")
public class Food implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	@MongoUpdateKey
	private String code;

	public Food() {
		this.name = "";
		this.code = "";
	}

	public Food(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
