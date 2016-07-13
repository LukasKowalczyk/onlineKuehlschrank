package de.online.kuehlschrank.onlineKuehlschrank.controle;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.mongoDBHelper.annotation.MongoDBHelper;
import de.mongoDBHelper.annotation.MongoDatabaseInforamtion;
import de.online.kuehlschrank.onlineKuehlschrank.container.User;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.DatenbankException;

@MongoDatabaseInforamtion(databaseName = "onlinekuehlschrank", username = "admin", password = "admin", host = "ds053972.mlab.com", port = "53972")
public class DatenbankControle {
	private static DatenbankControle datenbankControle = null;

	private MongoDatabase db;

	private DatenbankControle() {
		db = MongoDBHelper.getDatabaseInstance(this.getClass());
	}

	public static DatenbankControle getInstance() {
		if (datenbankControle == null) {
			datenbankControle = new DatenbankControle();
		}
		return datenbankControle;
	}

	private void insertIntoCollection(MongoCollection<Document> collection,
			Document document) throws DatenbankException {
		if (document == null) {
			throw new DatenbankException("Document ist leer!");
		}
		collection.insertOne(document);
	}

	public <T> List<T> getAllCollectionElements(Class<T> clazz)
			throws DatenbankException {
		List<T> ausg = Collections.emptyList();
		FindIterable<Document> finds = MongoDBHelper.getCollection(db, clazz)
				.find();
		for (Document document : finds) {
			ausg.add((T) new Gson().fromJson(document.toJson(), clazz));
		}
		return ausg;
	}

	public <T> T getCollectionElement(Class<T> clazz, String key, String value)
			throws DatenbankException {
		FindIterable<Document> iterable = MongoDBHelper
				.getCollection(db, clazz).find(
						new Document(key.toString(), value.toString()));
		Document document = iterable.first();
		if (document == null) {
			throw new DatenbankException("Es wurde nichts gefunden!");
		} else {
			return  new Gson().fromJson(document.toJson(), clazz);
		}
	}

	public void insertToCollection(Object object) throws DatenbankException {
		if (object != null) {
			insertIntoCollection(
					MongoDBHelper.getCollection(db, object.getClass()),
					Document.parse(new Gson().toJson(object)));
		}
	}

	public boolean findInCollection(Class<?> objectClass, String key,
			String value) throws DatenbankException {
		if (StringUtils.isBlank(key) && StringUtils.isBlank(value)) {
			return false;
		}

		FindIterable<Document> iterable = MongoDBHelper.getCollection(db,
				objectClass).find(
				new Document(key.toString(), value.toString()));
		if (iterable.first() == null) {
			throw new DatenbankException("Es wurde nichts gefunden!");
		}
		return true;

	}
}
