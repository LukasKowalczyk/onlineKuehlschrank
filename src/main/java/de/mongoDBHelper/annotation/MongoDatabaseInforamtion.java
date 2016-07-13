package de.mongoDBHelper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MongoDatabaseInforamtion {
	String username();

	String password();

	String host();

	String port();

	String databaseName();

}
