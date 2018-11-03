package br.com.bruno.meumetro.models.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by Bruno on 30/04/2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineSetting extends RealmObject {
    private String line;
    private Integer positionInList;
}
