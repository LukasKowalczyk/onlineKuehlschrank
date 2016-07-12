package de.online.kuehlschrank.onlineKuehlschrank.controle;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.online.kuehlschrank.onlineKuehlschrank.container.User;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.DatenbankException;

public class DatenbankControle {
	private static DatenbankControle datenbankControle = null;

	private MongoClient mongoClient;

	private MongoDatabase db;

	private DatenbankControle() {
		mongoClient = new MongoClient();
		db = mongoClient.getDatabase("onlineKuehlschrank");
	}

	public static DatenbankControle getInstance() {
		if (datenbankControle == null) {
			datenbankControle = new DatenbankControle();
		}
		return datenbankControle;
	}

	private MongoCollection<Document> getCollection(String collectionName)
			throws DatenbankException {
		if (StringUtils.isBlank(collectionName)) {
			throw new DatenbankException("CollectionsName ist leer!");
		}
		return db.getCollection(collectionName);
	}

	private void insertIntoCollection(MongoCollection<Document> collection,
			Document document) throws DatenbankException {
		if (document == null) {
			throw new DatenbankException("Document ist leer!");
		}
		collection.insertOne(document);

	}

	public <T> List<T> getCollectionElements(String collectionName,
			Class<T> clazz) throws DatenbankException {
		List<T> ausg = Collections.emptyList();
		FindIterable<Document> finds = getCollection(collectionName).find();
		for (Document document : finds) {
			ausg.add((T) new Gson().fromJson(document.toJson(), clazz));
		}
		return ausg;
	}

	public void insertToCollection(String collectionName, Object object)
			throws DatenbankException {
		if (object != null) {
			insertIntoCollection(getCollection(collectionName),
					Document.parse(new Gson().toJson(object)));
		}
	}

	public boolean findInCollection(String collectionName, String key,
			String value) throws DatenbankException {
		if (StringUtils.isBlank(key) && StringUtils.isBlank(value)) {
			return false;
		}

		FindIterable<Document> iterable = getCollection(collectionName).find(
				new Document(key.toString(), value.toString()));
		if (iterable.first() == null) {
			throw new DatenbankException("Es wurde nichts gefunden!");
		}
		return true;

	}
}
