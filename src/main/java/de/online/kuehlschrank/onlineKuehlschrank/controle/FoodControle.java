package de.online.kuehlschrank.onlineKuehlschrank.controle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.lapi.Lapi;
import de.online.kuehlschrank.onlineKuehlschrank.container.Food;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.DatenbankException;
import de.online.kuehlschrank.onlineKuehlschrank.utils.LapiKeynames;

public class FoodControle {

	private static final String NAME = "name";

	private static final String CODE = "code";

	private static final long WEEK = 604800000l;

	private static FoodControle foodControle;

	private SimpleDateFormat sdf;

	private DatabaseControle databaseControle;

	private Lapi lapi;

	private FoodControle() {
		lapi = Lapi.getInstance();
		databaseControle = DatabaseControle.getInstance();
		sdf = new SimpleDateFormat(lapi.getText(LapiKeynames.DATE_FORMAT));
	}

	public static FoodControle getInstance() {
		if (foodControle == null) {
			foodControle = new FoodControle();
		}

		return foodControle;
	}

	public boolean isExpiredDateReached(Food food) {
		if (food.getExpireDate() == null) {
			return false;
		}
		String tableDate = sdf.format(food.getExpireDate());
		try {
			return sdf.parse(tableDate).before(new Date());
		} catch (ParseException e) {
			return false;
		}
	}

	public boolean willBeExpiredDateReachedInAWeek(Food food) {
		SimpleDateFormat sdf = new SimpleDateFormat(
				lapi.getText(LapiKeynames.DATE_FORMAT));
		try {
			Date exipreDate = food.getExpireDate();
			if (exipreDate != null) {
				Date aktDate = sdf.parse(sdf.format(new Date()));
				Date expireDate = sdf.parse(sdf.format(exipreDate));

				Date startLastWeek = new Date(aktDate.getTime() + WEEK);
				if (expireDate.equals(aktDate)) {
					return false;
				}
				return expireDate.before(startLastWeek);
			}
			return false;
		} catch (ParseException e) {
			return false;
		}

	}

	public List<Food> findFoodsViaName(String name) {
		List<Food> out = new ArrayList<Food>();
		try {
			out = databaseControle.getElementsStartWithValueInCollection(
					Food.class, NAME, name);
		} catch (DatenbankException e) {
			return null;
		}
		return out;
	}

	public Food findFoodViaName(String name) {
		Food out = null;
		try {
			out = databaseControle.getCollectionElement(Food.class, NAME, name);
		} catch (DatenbankException e) {
			return null;
		}
		return out;
	}

	public List<Food> findFoodsViaCode(String code) {
		List<Food> out = new ArrayList<Food>();
		try {
			out = databaseControle.getElementsStartWithValueInCollection(
					Food.class, CODE, code);
		} catch (DatenbankException e) {
			return null;
		}
		return out;
	}

	public Food findFoodViaCode(String code) {
		Food out = null;
		try {
			out = databaseControle.getCollectionElement(Food.class, CODE, code);
		} catch (DatenbankException e) {
			return null;
		}
		return out;
	}

	public boolean isKnownFood(Food food) {
		try {
			return databaseControle.findInCollection(Food.class, CODE,
					food.getCode());
		} catch (DatenbankException e) {
			return false;
		}
	}

	public void saveFood(Food food) throws Exception {
		Food toSave = food;
		toSave.setAmount(0);
		toSave.setUnit(null);
		toSave.setExipreDate(null);
		databaseControle.updateInCollection(toSave);
	}

	public void addNewFood(Food food) throws Exception {
		Food toSave = new Food();
		toSave.setCode(food.getCode());
		toSave.setName(food.getName());
		toSave.setAmount(0);
		toSave.setUnit(null);
		toSave.setExipreDate(null);
		databaseControle.insertToCollection(toSave);
	}
}
