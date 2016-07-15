package de.online.kuehlschrank.onlineKuehlschrank.controle;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import de.hongo.Hongo;
import de.hongo.annotation.MongoDatabaseInformation;
import de.hongo.enums.LogicalMongoDBWord;
import de.hongo.exception.LogicalMongoDBWordException;
import de.hongo.exception.MongelpCollectionConnectionException;
import de.hongo.exception.MongelpDatabaseConnectionException;
import de.online.kuehlschrank.onlineKuehlschrank.container.Food;
import de.online.kuehlschrank.onlineKuehlschrank.container.User;

@MongoDatabaseInformation(databaseName = "onlinekuehlschrank", username = "admin", password = "admin", host = "ds053972.mlab.com", port = "53972")
public class ReinitDatenbank {

	public static void main(String[] args)
			throws MongelpDatabaseConnectionException,
			MongelpCollectionConnectionException, LogicalMongoDBWordException {
		// TODO Auto-generated method stub
		Hongo.generateDatabaseConnection(ReinitDatenbank.class);

		// new MongoClient(
		// new MongoClientURI(
		// "mongodb://admin:admin@ds053972.mlab.com:53972/onlinekuehlschrank"));
		// // MongoClient mongoClient = new MongoClient("localhost", 27017);
		// MongoDatabase db = mongoClient.getDatabase("onlinekuehlschrank");
		// db.drop();
		// db.createCollection("users",
		// new CreateCollectionOptions().autoIndex(true));
		// db.createCollection("foods");
		// ListCollectionsIterable<Document> dbCollection =
		// db.listCollections();
		// for (Document document : dbCollection) {
		// System.out.println(document);
		// }
		// db.createCollection("User");
		// MongoCollection<Document> colls = MongoDBHelper.getCollection(db,
		// User.class);
		// Document document = Document.parse(new Gson().toJson(new
		// User("Lukas",
		// "passwort", "lukas@gmail.com")));
		// colls.insertOne(document);
		// Document document2 = Document.parse(new Gson().toJson(new
		// User("anna",
		// "pasffs", "anna@gmail.com")));
		// colls.insertOne(document2);
		List<Object> arrayList = new ArrayList<>();
		arrayList.add("anna");
		arrayList.add("test");

		System.out.println(Hongo.quereyBuilder("name", arrayList,
				LogicalMongoDBWord.IN));
//		List<User> ausg = Mongelper.findInCollection(User.class, Mongelper
//				.quereyBuilder("name", arrayList, LogicalMongoDBWord.IN));
		List<User> ausg = Hongo.getCollection(User.class);
		for (User u : ausg) {
			System.out.println(new Gson().toJson(u));
		}
		List<Food> ausg2 = Hongo.getCollection(Food.class);
		for (Food f : ausg2) {
			System.out.println(new Gson().toJson(f));
		}

	}

}
