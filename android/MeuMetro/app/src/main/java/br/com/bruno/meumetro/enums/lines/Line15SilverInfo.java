package br.com.bruno.meumetro.enums.lines;

import br.com.bruno.meumetro.R;

/**
 * Created by Bruno on 03/09/2016.
 */
public enum Line15SilverInfo {

    vila_prudente(0, R.array.line_15_silver_vila_prudente_information, R.array.line_15_silver_vila_prudente_at_the_station),
    oratorio(1, R.array.line_15_silver_oratorio_information, R.array.line_15_silver_oratorio_at_the_station);

    private int index;
    private int resArrayInformation;
    private int resArrayAtTheStation;

    Line15SilverInfo(int index, int resArrayInformation, int resArrayAtTheStation) {
        this.index = index;
        this.resArrayInformation = resArrayInformation;
        this.resArrayAtTheStation = resArrayAtTheStation;
    }

    public static Line15SilverInfo getStationByPosition(int position) {
        Line15SilverInfo type = null;
        switch (position) {
            case 0:
                type = vila_prudente;
                break;
            case 1:
                type = oratorio;
                break;
        }
        return type;
    }

    public int getIndex() {
        return index;
    }

    public int getResArrayInformation() {
        return resArrayInformation;
    }

    public int getResArrayAtTheStation() {
        return resArrayAtTheStation;
    }
}
