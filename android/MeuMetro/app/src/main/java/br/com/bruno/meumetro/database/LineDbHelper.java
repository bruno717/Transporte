package br.com.bruno.meumetro.database;

import java.util.ArrayList;
import java.util.List;

import br.com.bruno.meumetro.enums.StatusType;
import br.com.bruno.meumetro.models.Line;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Bruno on 24/06/2017.
 */

public class LineDbHelper {

    public static List<Line> getLinesByTypeStatus(StatusType statusType) {
        RealmQuery<Line> realmQuery = Realm.getDefaultInstance().where(Line.class).contains("statusType", statusType.getType());
        List<Line> lines = new ArrayList<>();
        if (realmQuery.findAll().size() > 0) {
            for (Line line : realmQuery.findAll().sort("id")) {
                lines.add(line);
            }
        }
        return lines;
    }
}
