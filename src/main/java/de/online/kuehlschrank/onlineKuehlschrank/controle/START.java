package de.online.kuehlschrank.onlineKuehlschrank.controle;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class START {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		MongoDatabase db = mongoClient.getDatabase("test");
		db.createCollection("User");
		
		MongoCollection<Document> dbCollection = db.getCollection("User");
		for (final Document index : dbCollection.listIndexes()) {
		    System.out.println(index.toJson());
		}
	}

}
