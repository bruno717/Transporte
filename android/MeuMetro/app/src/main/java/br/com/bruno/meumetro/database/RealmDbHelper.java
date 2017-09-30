package br.com.bruno.meumetro.database;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Bruno on 22/04/2017.
 */

public class RealmDbHelper {

    public static <T extends RealmObject> void save(final T element) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // This will create a new object in Realm or throw an exception if the
                // object already exists (same primary key)
                realm.copyToRealm(element);
            }
        });
    }

    public static <T extends RealmObject> void update(final T element) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // This will update an existing object with the same primary key
                // or create a new object if an object with no primary key = 42
                realm.copyToRealmOrUpdate(element);
            }
        });
    }

    public static <T extends RealmObject> void deleteAll(final Class<T> entityClass) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Realm.getDefaultInstance().delete(entityClass);
            }
        });
    }

    public static <T extends RealmObject> void delete(final T element) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
               element.deleteFromRealm();
            }
        });
    }

    public static <T extends RealmObject> int count(Class<T> typeParameterClass) {
        return Realm.getDefaultInstance().where(typeParameterClass).findAll().size();
    }

    public static <T extends RealmObject> List<T> findAll(Class<T> typeParameterClass) {
        List<T> results = new ArrayList<>();
        for (T result : Realm.getDefaultInstance().where(typeParameterClass).findAll()) {
            results.add(result);
        }
        Realm.getDefaultInstance().where(typeParameterClass).findAll();
        return results;
    }

    public static <T extends RealmObject> T findWithPrimaryKey(Class<T> typeParameterClass, Integer primaryKey) throws Exception {
        for (Field field : typeParameterClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                return Realm.getDefaultInstance().where(typeParameterClass).equalTo(field.getName(), primaryKey).findFirst();
            }
        }
        throw new Exception("Não foi encontrado nenhuma coluna anotada como PrimaryKey.");
    }

    public static <T extends RealmObject> T findWithPrimaryKey(Class<T> typeParameterClass, Long primaryKey) throws Exception {
        for (Field field : typeParameterClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                return Realm.getDefaultInstance().where(typeParameterClass).equalTo(field.getName(), primaryKey).findFirst();
            }
        }
        throw new Exception("Não foi encontrado nenhuma coluna anotada como PrimaryKey.");
    }

    public static <T extends RealmObject> T findLast(Class<T> typeParameterClass) {
        RealmResults<T> results = Realm.getDefaultInstance().where(typeParameterClass).findAll();
        if (results.size() > 0)
            return results.last();
        return null;
    }

    public static <T extends RealmObject> T findFirst(Class<T> typeParameterClass) {
        RealmResults<T> results = Realm.getDefaultInstance().where(typeParameterClass).findAll();
        if (results.size() > 0)
            return results.first();
        return null;
    }

}
