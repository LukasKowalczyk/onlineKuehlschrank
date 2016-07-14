package de.online.kuehlschrank.onlineKuehlschrank.controle;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.mongelp.Mongelper;
import de.mongelp.annotation.MongoDatabaseInformation;
import de.mongelp.exception.MongelpCollectionConnectionException;
import de.mongelp.exception.MongelpDatabaseConnectionException;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.DatenbankException;

@MongoDatabaseInformation(databaseName = "onlinekuehlschrank", username = "admin", password = "admin", host = "ds053972.mlab.com", port = "53972")
public class DatenbankControle {
	private static DatenbankControle datenbankControle = null;

	private MongoDatabase db;

	private DatenbankControle() {
		try {
			Mongelper.generateDatabaseConnection(this.getClass());
		} catch (MongelpDatabaseConnectionException e) {
			e.printStackTrace();
		}
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
		try {
			return Mongelper.findInCollection(clazz, new BasicDBObject());
		} catch (MongelpDatabaseConnectionException
				| MongelpCollectionConnectionException e) {
			return Collections.emptyList();
		}
	}

	public <T> T getCollectionElement(Class<T> clazz, String key, String value)
			throws DatenbankException {
		List<T> erg;
		try {
			erg = Mongelper.findInCollection(clazz,
					Mongelper.quereyBuilder(key, value));
		} catch (MongelpDatabaseConnectionException
				| MongelpCollectionConnectionException e) {
			throw new DatenbankException(e);
		}
		if (erg.isEmpty()) {
			throw new DatenbankException("Es wurde nichts gefunden!");
		} else {
			return erg.get(0);
		}
	}

	public void insertToCollection(Object object) throws DatenbankException {
		if (object != null) {
			try {
				Mongelper.insertIntoCollection(object.getClass(), object);
			} catch (MongelpDatabaseConnectionException
					| MongelpCollectionConnectionException e) {
				e.printStackTrace();
				throw new DatenbankException(e);
			}
		}
	}

	public <T> boolean findInCollection(Class<T> objectClass, String key,
			String value) throws DatenbankException {
		if (StringUtils.isBlank(key) && StringUtils.isBlank(value)) {
			return false;
		}

		try {
			List<T> erg = Mongelper.findInCollection(objectClass,
					Mongelper.quereyBuilder(key, value));
			if (erg.isEmpty()) {
				throw new DatenbankException("Es wurde nichts gefunden!");
			}
			return true;
		} catch (MongelpDatabaseConnectionException
				| MongelpCollectionConnectionException e) {
			throw new DatenbankException(e);
		}
	

	}
}
