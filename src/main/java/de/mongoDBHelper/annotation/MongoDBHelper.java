package de.mongoDBHelper.annotation;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBHelper {
	private final static String URISTART = "mongodb://";

	@SuppressWarnings("resource")
	public static MongoDatabase getDatabaseInstance(Class<?> c) {
		if (c.isAnnotationPresent(MongoDatabaseInforamtion.class)) {
			MongoDatabaseInforamtion mdbInfo = c
					.getAnnotation(MongoDatabaseInforamtion.class);
			String uri = URISTART + mdbInfo.username() + ":"
					+ mdbInfo.password() + "@" + mdbInfo.host() + ":"
					+ mdbInfo.port() + "/" + mdbInfo.databaseName();
			return new MongoClient(new MongoClientURI(uri)).getDatabase(mdbInfo
					.databaseName());
		}
		return null;
	}

	public static MongoCollection<Document> getCollection(
			MongoDatabase database, Class<?> c) {
		if (c.isAnnotationPresent(MongoCollectionInforamtion.class)) {
			MongoCollectionInforamtion mCInfo = c
					.getAnnotation(MongoCollectionInforamtion.class);
			return database.getCollection(mCInfo.collectionName());
		}
		return null;
	}
}
