package br.com.bruno.meumetro.models;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.com.bruno.meumetro.R;
import br.com.bruno.meumetro.enums.StatusType;
import br.com.bruno.meumetro.enums.lines.Line10TurquoiseInfo;
import br.com.bruno.meumetro.enums.lines.Line11CoralInfo;
import br.com.bruno.meumetro.enums.lines.Line12SapphireInfo;
import br.com.bruno.meumetro.enums.lines.Line15SilverInfo;
import br.com.bruno.meumetro.enums.lines.Line1BlueInfo;
import br.com.bruno.meumetro.enums.lines.Line2GreenInfo;
import br.com.bruno.meumetro.enums.lines.Line3RedInfo;
import br.com.bruno.meumetro.enums.lines.Line4YellowInfo;
import br.com.bruno.meumetro.enums.lines.Line5LilacInfo;
import br.com.bruno.meumetro.enums.lines.Line7RubyInfo;
import br.com.bruno.meumetro.enums.lines.Line8DiamondInfo;
import br.com.bruno.meumetro.enums.lines.Line9EmeraldInfo;
import br.com.bruno.meumetro.enums.lines.LineType;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import lombok.Data;

/**
 * Created by Bruno on 28/08/2016.
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Line extends RealmObject {

    public static final String PATTERN_DATE = "yyyy-MM-dd'T'HH:mm:ss";

    public Line() {
        setName("");
        setSituation("");
        setDescription("");
        setType("");
    }

    private Integer id;
    private String lineType;
    private String name;
    private String situation;
    private String description;
    private String type;
    @Ignore
    private List<Station> stations = new ArrayList<>();
    private String modificationDate;
    @JsonIgnore
    private String statusType;

    public String getModificationDate() {
        if (modificationDate == null)
            modificationDate = new SimpleDateFormat(Line.PATTERN_DATE, Locale.US).format(Calendar.getInstance().getTime());

        return modificationDate;
    }

    @JsonIgnore
    public void putStations(Context c) {
        if (!stations.isEmpty())
            stations.clear();
        for (int i = 0; i < getStations(c).length; i++) {
            Station station = new Station();
            station.setName(getStations(c)[i]);
            String[] information = getStationInformation(i, c);
            if (information.length > 0) {
                station.setOperationSundayToFriday(information[0]);
                station.setOperationSaturday(information[1]);
                station.setLocation(information[2]);
            }
            station.setAtTheStation(getAtTheStation(i, c));
            stations.add(station);
        }
    }

    @JsonIgnore
    public int getColorBackground() {
        switch (getLineType()) {

            case LINE_1_BLUE:
                return R.color.line_1_blue;

            case LINE_2_GREEN:
                return R.color.line_2_green;

            case LINE_3_RED:
                return R.color.line_3_red;

            case LINE_4_YELLOW:
                return R.color.line_4_yellow;

            case LINE_5_LILAC:
                return R.color.line_5_lilac;

            case LINE_7_RUBY:
                return R.color.line_7_ruby;

            case LINE_8_DIAMOND:
                return R.color.line_8_diamond;

            case LINE_9_EMERALD:
                return R.color.line_9_emerald;

            case LINE_10_TURQUOISE:
                return R.color.line_10_turquoise;

            case LINE_11_CORAL:
                return R.color.line_11_coral;

            case LINE_12_SAPPHIRE:
                return R.color.line_12_sapphire;

            case LINE_15_SILVER:
                return R.color.line_15_silver;

            default:
                return R.color.line_1_blue;
        }
    }

    @JsonIgnore
    private String[] getStations(Context c) {
        switch (getLineType()) {
            case LINE_1_BLUE:
                return c.getResources().getStringArray(R.array.line_1_blue);

            case LINE_2_GREEN:
                return c.getResources().getStringArray(R.array.line_2_green);

            case LINE_3_RED:
                return c.getResources().getStringArray(R.array.line_3_red);

            case LINE_4_YELLOW:
                return c.getResources().getStringArray(R.array.line_4_yellow);

            case LINE_5_LILAC:
                return c.getResources().getStringArray(R.array.line_5_lilac);

            case LINE_7_RUBY:
                return c.getResources().getStringArray(R.array.line_7_ruby);

            case LINE_8_DIAMOND:
                return c.getResources().getStringArray(R.array.line_8_diamond);

            case LINE_9_EMERALD:
                return c.getResources().getStringArray(R.array.line_9_emerald);

            case LINE_10_TURQUOISE:
                return c.getResources().getStringArray(R.array.line_10_turquoise);

            case LINE_11_CORAL:
                return c.getResources().getStringArray(R.array.line_11_coral);

            case LINE_12_SAPPHIRE:
                return c.getResources().getStringArray(R.array.line_12_sapphire);

            case LINE_15_SILVER:
                return c.getResources().getStringArray(R.array.line_15_silver);

            default:
                return c.getResources().getStringArray(R.array.line_1_blue);
        }
    }


    @JsonIgnore
    private String[] getStationInformation(int position, Context c) {

        switch (getLineType()) {
            case LINE_1_BLUE:
                return c.getResources().getStringArray(Line1BlueInfo.getStationByPosition(position).getResArrayInformation());

            case LINE_2_GREEN:
                return c.getResources().getStringArray(Line2GreenInfo.getStationByPosition(position).getResArrayInformation());

            case LINE_3_RED:
                return c.getResources().getStringArray(Line3RedInfo.getStationByPosition(position).getResArrayInformation());

            case LINE_4_YELLOW:
                return c.getResources().getStringArray(Line4YellowInfo.getStationByPosition(position).getResArrayInformation());

            case LINE_5_LILAC:
                return c.getResources().getStringArray(Line5LilacInfo.getStationByPosition(position).getResArrayInformation());

            case LINE_7_RUBY:
                return c.getResources().getStringArray(Line7RubyInfo.getStationByPosition(position).getResArrayInformation());

            case LINE_8_DIAMOND:
                return c.getResources().getStringArray(Line8DiamondInfo.getStationByPosition(position).getResArrayInformation());

            case LINE_9_EMERALD:
                return c.getResources().getStringArray(Line9EmeraldInfo.getStationByPosition(position).getResArrayInformation());

            case LINE_10_TURQUOISE:
                return c.getResources().getStringArray(Line10TurquoiseInfo.getStationByPosition(position).getResArrayInformation());

            case LINE_11_CORAL:
                return c.getResources().getStringArray(Line11CoralInfo.getStationByPosition(position).getResArrayInformation());

            case LINE_12_SAPPHIRE:
                return c.getResources().getStringArray(Line12SapphireInfo.getStationByPosition(position).getResArrayInformation());

            case LINE_15_SILVER:
                return c.getResources().getStringArray(Line15SilverInfo.getStationByPosition(position).getResArrayInformation());
        }
        return c.getResources().getStringArray(Line1BlueInfo.getStationByPosition(position).getResArrayInformation());
    }

    @JsonIgnore
    private String[] getAtTheStation(int position, Context c) {
        switch (getLineType()) {
            case LINE_1_BLUE:
                return c.getResources().getStringArray(Line1BlueInfo.getStationByPosition(position).getResArrayAtTheStation());

            case LINE_2_GREEN:
                return c.getResources().getStringArray(Line2GreenInfo.getStationByPosition(position).getResArrayAtTheStation());

            case LINE_3_RED:
                return c.getResources().getStringArray(Line3RedInfo.getStationByPosition(position).getResArrayAtTheStation());

            case LINE_4_YELLOW:
                return c.getResources().getStringArray(Line4YellowInfo.getStationByPosition(position).getResArrayAtTheStation());

            case LINE_5_LILAC:
                return c.getResources().getStringArray(Line5LilacInfo.getStationByPosition(position).getResArrayAtTheStation());

            case LINE_7_RUBY:
                return c.getResources().getStringArray(Line7RubyInfo.getStationByPosition(position).getResArrayAtTheStation());

            case LINE_8_DIAMOND:
                return c.getResources().getStringArray(Line8DiamondInfo.getStationByPosition(position).getResArrayAtTheStation());

            case LINE_9_EMERALD:
                return c.getResources().getStringArray(Line9EmeraldInfo.getStationByPosition(position).getResArrayAtTheStation());

            case LINE_10_TURQUOISE:
                return c.getResources().getStringArray(Line10TurquoiseInfo.getStationByPosition(position).getResArrayAtTheStation());

            case LINE_11_CORAL:
                return c.getResources().getStringArray(Line11CoralInfo.getStationByPosition(position).getResArrayAtTheStation());

            case LINE_12_SAPPHIRE:
                return c.getResources().getStringArray(Line12SapphireInfo.getStationByPosition(position).getResArrayAtTheStation());

            case LINE_15_SILVER:
                return c.getResources().getStringArray(Line15SilverInfo.getStationByPosition(position).getResArrayAtTheStation());
        }
        return c.getResources().getStringArray(Line1BlueInfo.getStationByPosition(position).getResArrayAtTheStation());
    }

    public StatusType getStatusType() {
        return statusType != null ? StatusType.valueOf(statusType) : null;
    }

    public LineType getLineType() {
        return lineType != null ? LineType.valueOf(lineType) : null;
    }

    public void setLineType(LineType type) {
        lineType = type.name();
    }
}
