package br.com.bruno.meumetro.database;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by Bruno on 22/04/2017.
 */

public class MigrationMyDataBase implements RealmMigration {

    public static final Long VERSION = 1L;

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            schema.get("Line").addField("lineType", String.class);
            schema.get("Line").addField("statusType", String.class);
            oldVersion++;
        }
    }
}
