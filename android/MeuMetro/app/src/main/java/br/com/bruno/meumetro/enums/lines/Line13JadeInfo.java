package br.com.bruno.meumetro.enums.lines;

import br.com.bruno.meumetro.R;

/**
 * Created by Bruno on 03/09/2016.
 */
public enum Line13JadeInfo {

    eng_goulart(0, R.array.line_13_jade_eng_goulart_information, R.array.line_13_jade_eng_goulart_at_the_station),
    guarulhos_cecap(1, R.array.line_13_jade_guarulhos_cecap_information, R.array.line_13_jade_guarulhos_cecap_at_the_station),
    aeroporto_guarulhos(2, R.array.line_13_jade_aeroporto_guarulhos_information, R.array.line_13_jade_aeroporto_guarulhos_at_the_station);

    private int index;
    private int resArrayInformation;
    private int resArrayAtTheStation;

    Line13JadeInfo(int index, int resArrayInformation, int resArrayAtTheStation) {
        this.index = index;
        this.resArrayInformation = resArrayInformation;
        this.resArrayAtTheStation = resArrayAtTheStation;
    }

    public static Line13JadeInfo getStationByPosition(int position) {
        Line13JadeInfo type = null;

        for (int i = 0; i < Line13JadeInfo.values().length; i++) {
            Line13JadeInfo info = Line13JadeInfo.values()[i];
            if (info.index == position) {
                type = info;
                break;
            }
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
