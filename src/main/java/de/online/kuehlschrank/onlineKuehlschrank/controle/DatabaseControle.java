package de.online.kuehlschrank.onlineKuehlschrank.controle;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mongodb.BasicDBObject;

import de.hongo.Hongo;
import de.hongo.annotation.MongoDatabaseInformation;
import de.hongo.exception.MongelpCollectionConnectionException;
import de.hongo.exception.MongelpDatabaseConnectionException;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.DatenbankException;

@MongoDatabaseInformation(databaseName = "onlinekuehlschrank", username = "admin", password = "admin", host = "ds053972.mlab.com", port = "53972")
public class DatabaseControle {
	private static DatabaseControle datenbankControle = null;

	private DatabaseControle() {
		try {
			Hongo.generateDatabaseConnection(this.getClass());
		} catch (MongelpDatabaseConnectionException e) {
			e.printStackTrace();
		}
	}

	public static DatabaseControle getInstance() {
		if (datenbankControle == null) {
			datenbankControle = new DatabaseControle();
		}
		return datenbankControle;
	}

	public <T> List<T> getAllCollectionElements(Class<T> clazz)
			throws DatenbankException {
		try {
			return Hongo.findInCollection(clazz, new BasicDBObject());
		} catch (MongelpDatabaseConnectionException
				| MongelpCollectionConnectionException e) {
			return Collections.emptyList();
		}
	}

	public <T> T getCollectionElement(Class<T> clazz, String key, String value)
			throws DatenbankException {
		List<T> erg;
		try {
			erg = Hongo
					.findInCollection(clazz, Hongo.quereyBuilder(key, value));
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
				Hongo.insertIntoCollection(object);
			} catch (MongelpDatabaseConnectionException
					| MongelpCollectionConnectionException e) {
				e.printStackTrace();
				throw new DatenbankException(e);
			}
		}
	}

	public void updateInCollection(Object object) throws DatenbankException {
		if (object != null) {
			try {
				Hongo.updateInCollection(object);
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
			List<T> erg = Hongo.findInCollection(objectClass,
					Hongo.quereyBuilder(key, value));
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
