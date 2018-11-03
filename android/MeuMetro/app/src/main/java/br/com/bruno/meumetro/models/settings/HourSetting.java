package br.com.bruno.meumetro.models.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by Bruno on 30/04/2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HourSetting extends RealmObject {
    private String hour;
    private Integer positionInList;
}
