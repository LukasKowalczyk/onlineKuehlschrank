package de.online.kuehlschrank.onlineKuehlschrank.controle;

import org.bson.Document;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatenbankControle {
	private static DatenbankControle datenbankControle = null;

	private MongoClient mongoClient;

	private MongoDatabase db;

	private DatenbankControle() {
		mongoClient = new MongoClient();
		db = mongoClient.getDatabase("test");
	}

	public static DatenbankControle getInstance() {
		if (datenbankControle == null) {
			datenbankControle = new DatenbankControle();
		}
		return datenbankControle;
	}

	public void test() {
		MongoCollection<Document> dbCollection = db.getCollection("User");
	}
}
