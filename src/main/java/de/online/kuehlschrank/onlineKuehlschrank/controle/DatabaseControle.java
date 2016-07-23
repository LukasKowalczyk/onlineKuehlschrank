package de.online.kuehlschrank.onlineKuehlschrank.controle;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.mongodb.BasicDBObject;

import de.hongo.Hongo;
import de.hongo.annotation.MongoDatabaseInformation;
import de.hongo.enums.MongoDBConstants;
import de.hongo.enums.RegExConstants;
import de.hongo.exception.LogicalMongoDBWordException;
import de.hongo.exception.MongelpCollectionConnectionException;
import de.hongo.exception.MongelpDatabaseConnectionException;
import de.lapi.Lapi;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.DatenbankException;
import de.online.kuehlschrank.onlineKuehlschrank.utils.LapiKeynames;

@MongoDatabaseInformation(databaseName = "onlinekuehlschrank", username = "admin", password = "admin", host = "ds053972.mlab.com", port = "53972")
public class DatabaseControle {

	private Lapi lapi;

	private static DatabaseControle datenbankControle = null;

	private DatabaseControle() {
		try {
			lapi = Lapi.getInstance();
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

	public <T> List<T> getAllCollectionElements(Class<T> c)
			throws DatenbankException {
		try {
			return Hongo.findInCollection(c, new BasicDBObject());
		} catch (MongelpDatabaseConnectionException
				| MongelpCollectionConnectionException e) {
			return Collections.emptyList();
		}
	}

	public <T> T getCollectionElement(Class<T> c, String key, String value)
			throws DatenbankException {
		List<T> erg;
		try {
			erg = Hongo.findInCollection(c, Hongo.quereyBuilder(key, value));
		} catch (MongelpDatabaseConnectionException
				| MongelpCollectionConnectionException e) {
			throw new DatenbankException(e);
		}
		if (erg.isEmpty()) {
			throw new DatenbankException(
					lapi.getText(LapiKeynames.DATABASE_NOT_FOUND));
		} else {
			return erg.get(0);
		}
	}

	public <T> T getCollectionElement(Class<T> c, Map<String, Object> keyValue)
			throws DatenbankException {
		List<T> erg;
		try {
			erg = Hongo.findInCollection(c,
					Hongo.quereyBuilder(keyValue, MongoDBConstants.AND));
		} catch (MongelpDatabaseConnectionException
				| MongelpCollectionConnectionException
				| LogicalMongoDBWordException e) {
			throw new DatenbankException(e);
		}
		if (erg.isEmpty()) {
			throw new DatenbankException(
					lapi.getText(LapiKeynames.DATABASE_NOT_FOUND));
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

	public <T> List<T> getElementsInCollection(Class<T> c, String key,
			String value) throws DatenbankException {
		if (StringUtils.isBlank(key) && StringUtils.isBlank(value)) {
			return null;
		}

		try {
			List<T> erg = Hongo.findInCollection(c,
					Hongo.quereyBuilder(key, value));
			if (erg.isEmpty()) {
				throw new DatenbankException(
						lapi.getText(LapiKeynames.DATABASE_NOT_FOUND));
			}
			return erg;
		} catch (MongelpDatabaseConnectionException
				| MongelpCollectionConnectionException e) {
			throw new DatenbankException(e);
		}
	}

	public <T> List<T> getElementsStartWithValueInCollection(Class<T> c,
			String key, String value) throws DatenbankException {
		if (StringUtils.isBlank(key) && StringUtils.isBlank(value)) {
			return null;
		}

		try {
			String regexCmd = RegExConstants.generateRegExCommand(value,
					RegExConstants.REGEX_START_WITH_IGNORECASE);
			List<T> erg = Hongo.findInCollection(c,
					Hongo.quereyBuilder(key, regexCmd, MongoDBConstants.REGEX));
			if (erg.isEmpty()) {
				throw new DatenbankException(LapiKeynames.DATABASE_NOT_FOUND);
			}
			return erg;
		} catch (MongelpDatabaseConnectionException
				| MongelpCollectionConnectionException
				| LogicalMongoDBWordException e) {
			throw new DatenbankException(e);
		}
	}

	public <T> List<T> getElementsContainsValueInCollection(Class<T> c,
			String key, String value) throws DatenbankException {
		if (StringUtils.isBlank(key) && StringUtils.isBlank(value)) {
			return null;
		}

		try {
			String regexCmd = RegExConstants.generateRegExCommand(value,
					RegExConstants.REGEX_CONTAINS_IGNORECASE);
			List<T> erg = Hongo.findInCollection(c,
					Hongo.quereyBuilder(key, regexCmd, MongoDBConstants.REGEX));
			if (erg.isEmpty()) {
				throw new DatenbankException(
						lapi.getText(LapiKeynames.DATABASE_NOT_FOUND));
			}
			return erg;
		} catch (MongelpDatabaseConnectionException
				| MongelpCollectionConnectionException
				| LogicalMongoDBWordException e) {
			throw new DatenbankException(e);
		}
	}

	public <T> boolean findInCollection(Class<T> c, String key, String value)
			throws DatenbankException {
		if (StringUtils.isBlank(key) && StringUtils.isBlank(value)) {
			return false;
		}

		try {
			List<T> erg = Hongo.findInCollection(c,
					Hongo.quereyBuilder(key, value));
			if (erg.isEmpty()) {
				throw new DatenbankException(
						lapi.getText(LapiKeynames.DATABASE_NOT_FOUND));
			}
			return true;
		} catch (MongelpDatabaseConnectionException
				| MongelpCollectionConnectionException e) {
			throw new DatenbankException(e);
		}
	}
}
