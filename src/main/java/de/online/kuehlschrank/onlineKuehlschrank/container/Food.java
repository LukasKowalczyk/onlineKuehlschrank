package de.online.kuehlschrank.onlineKuehlschrank.container;

import java.io.Serializable;
import java.util.Date;

import de.hongo.annotation.MongoCollectionInformation;
import de.hongo.annotation.MongoUpdateKey;
import de.online.kuehlschrank.onlineKuehlschrank.utils.Units;

@MongoCollectionInformation(collectionName = "foods", databaseName="onlinekuehlschrank")
public class Food implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private int amount;
	private Units unit;
	@MongoUpdateKey
	private String code;
	private Date exipreDate;

	public Food(){
		this.name = "";
		this.amount = 0;
		this.unit = null;
		this.code = "";
		this.exipreDate = new Date();
	}

	public Food(String name, int amount, Units unit, String code,
			Date exipreDate) {
		super();
		this.name = name;
		this.amount = amount;
		this.unit = unit;
		this.code = code;
		this.exipreDate = exipreDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Units getUnit() {
		return unit;
	}

	public void setUnit(Units unit) {
		this.unit = unit;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getExpireDate() {
		return exipreDate;
	}

	public void setExipreDate(Date exipreDate) {
		this.exipreDate = exipreDate;
	}
}
