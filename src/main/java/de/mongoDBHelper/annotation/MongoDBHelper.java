package de.mongoDBHelper.annotation;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.mongoDBHelper.annotation.MongoCollectionInformation;
import de.mongoDBHelper.annotation.MongoDatabaseInformation;

public class MongoDBHelper {
	

	@SuppressWarnings("resource")
	public static MongoDatabase getDatabaseInstance(Class<?> c) {
		if (c.isAnnotationPresent(MongoDatabaseInformation.class)) {
			MongoDatabaseInformation mdbInfo = c
					.getAnnotation(MongoDatabaseInformation.class);
			String uri = MongoDatabaseInformation.URISTART + mdbInfo.username() + ":"
					+ mdbInfo.password() + "@" + mdbInfo.host() + ":"
					+ mdbInfo.port() + "/" + mdbInfo.databaseName();
			return new MongoClient(new MongoClientURI(uri)).getDatabase(mdbInfo
					.databaseName());
		}
		return null;
	}

	public static MongoCollection<Document> getCollection(
			MongoDatabase database, Class<?> c) {
		if (c.isAnnotationPresent(MongoCollectionInformation.class)) {
			MongoCollectionInformation mCInfo = c
					.getAnnotation(MongoCollectionInformation.class);
			return database.getCollection(mCInfo.collectionName());
		}
		return null;
	}
}
