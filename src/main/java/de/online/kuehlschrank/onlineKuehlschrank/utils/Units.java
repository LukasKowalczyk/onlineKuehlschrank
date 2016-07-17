package de.online.kuehlschrank.onlineKuehlschrank.utils;

public enum Units {
	GRAMM("g"), KILOGRAMM("kg"), MILLILITER("ml"), LITER("l"), PIECE("Stk");
	private String unitName;

	private Units(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitName() {
		return unitName;
	}

	public String toString() {
		return unitName;
	}

	public static Units getUnit(String unitName) {
		for (Units u : Units.values()) {
			if (u.getUnitName().equals(unitName)) {
				return u;
			}
		}
		return null;
	}
}
