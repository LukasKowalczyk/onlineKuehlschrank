package de.online.kuehlschrank.onlineKuehlschrank.controle;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.ListCollectionsIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;

import de.online.kuehlschrank.onlineKuehlschrank.container.User;

public class ReinitDatenbank {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase db = mongoClient.getDatabase("onlineKuehlschrank");
		//db.drop();
		// db.createCollection("users",
		// new CreateCollectionOptions().autoIndex(true));
		// db.createCollection("foods");
		// ListCollectionsIterable<Document> dbCollection =
		// db.listCollections();
		// for (Document document : dbCollection) {
		// System.out.println(document);
		// }
		// db.createCollection("User");

		MongoCollection<Document> colls = db.getCollection("users");
		// Document document = Document.parse(new Gson().toJson(new
		// User("Lukas",
		// "passwort", "lukas@gmail.com")));
		// colls.insertOne(document);
		// Document document2 = Document.parse(new Gson().toJson(new
		// User("anna",
		// "pasffs", "anna@gmail.com")));
		// colls.insertOne(document2);
		FindIterable<Document> ausg = colls.find();

		ausg.forEach(new Block<Document>() {

			@Override
			public void apply(Document arg0) {
				System.out.println(arg0);

			}

		});
	}

}
