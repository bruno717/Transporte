package br.com.bruno.meumetro.models.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;

/**
 * Created by Bruno on 30/04/2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Setting extends RealmObject {

    @PrimaryKey
    private int id;
    private Boolean isNotificationOfficial;
    private Boolean isNotificationByUser;
    private RealmList<LineSetting> lines;
    private RealmList<DaySetting> days;
    private RealmList<HourSetting> hours;

    public List<String> getDaysString() {
        List<String> days = new ArrayList<>();
        for (DaySetting daySetting : this.days) {
            days.add(daySetting.getDay());
        }
        return days;
    }

    public List<String> getHoursString() {
        List<String> hours = new ArrayList<>();
        for (HourSetting hourSetting : this.hours) {
            hours.add(hourSetting.getHour());
        }
        return hours;
    }

    public List<String> getLinesString() {
        List<String> lines = new ArrayList<>();
        for (LineSetting lineSetting : this.lines) {
            lines.add(lineSetting.getLine());
        }
        return lines;
    }

}
