package de.online.kuehlschrank.onlineKuehlschrank.container;

import java.util.Date;

public class StorageFood {

	private Food food;
	private int amount;
	private Units unit;
	private Date expireDate;

	public StorageFood(Food food, int amount, Units unit, Date expireDate) {
		super();
		this.food = food;
		this.amount = amount;
		this.unit = unit;
		this.expireDate = expireDate;
	}

	public StorageFood() {
		this.food = null;
		this.amount = 0;
		this.unit = null;
		this.expireDate = null;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
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

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

}
