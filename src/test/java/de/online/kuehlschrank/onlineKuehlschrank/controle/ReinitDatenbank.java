package de.online.kuehlschrank.onlineKuehlschrank.controle;

import java.util.List;

import de.hongo.Hongo;
import de.hongo.annotation.MongoDatabaseInformation;
import de.hongo.exception.LogicalMongoDBWordException;
import de.hongo.exception.MongelpCollectionConnectionException;
import de.hongo.exception.MongelpDatabaseConnectionException;
import de.online.kuehlschrank.onlineKuehlschrank.container.User;

@MongoDatabaseInformation(databaseName = "onlinekuehlschrank", username = "admin", password = "admin", host = "ds053972.mlab.com", port = "53972")
public class ReinitDatenbank {

	public static void main(String[] args)
			throws MongelpDatabaseConnectionException,
			MongelpCollectionConnectionException, LogicalMongoDBWordException {
		Hongo.generateDatabaseConnection(DatabaseControle.class);
		List<User> erg = Hongo.getCollection(User.class);
		User user = erg.get(0);
		user.setName("Luki");
		Hongo.updateInCollection(user);
		for (User f : erg) {
			System.out.println(f.getEmail());
			System.out.println(f.getName());
		}
	}
}
