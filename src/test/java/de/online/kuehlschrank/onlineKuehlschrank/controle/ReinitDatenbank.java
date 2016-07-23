package de.online.kuehlschrank.onlineKuehlschrank.controle;

import java.util.List;

import de.hongo.Hongo;
import de.hongo.annotation.MongoDatabaseInformation;
import de.hongo.enums.MongoDBConstants;
import de.hongo.exception.LogicalMongoDBWordException;
import de.hongo.exception.MongelpCollectionConnectionException;
import de.hongo.exception.MongelpDatabaseConnectionException;
import de.online.kuehlschrank.onlineKuehlschrank.container.Food;

@MongoDatabaseInformation(databaseName = "onlinekuehlschrank", username = "admin", password = "admin", host = "ds053972.mlab.com", port = "53972")
public class ReinitDatenbank {

	public static void main(String[] args)
			throws MongelpDatabaseConnectionException,
			MongelpCollectionConnectionException, LogicalMongoDBWordException {
		Hongo.generateDatabaseConnection(DatabaseControle.class);
		System.out.println(Hongo.quereyBuilder("name", "/b/i",
				MongoDBConstants.REGEX));
		List<Food> erg = Hongo.findInCollection(Food.class,
				Hongo.quereyBuilder("name", "/^b/i", MongoDBConstants.REGEX));
		for (Food food : erg) {
			System.out.println(food.getName());
		}
	}
}
