package br.com.bruno.meumetro.models.settings;

import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by Bruno on 30/04/2017.
 */
@Data
public class LineSetting extends RealmObject {
    private String line;
    private Integer positionInList;
}
