package br.com.bruno.meumetro.enums.lines;

import br.com.bruno.meumetro.R;

/**
 * Created by Bruno on 03/09/2016.
 */
public enum Line4YellowInfo {

    luz(0, R.array.line_4_yellow_luz_information, R.array.line_4_yellow_luz_at_the_station),
    republica(1, R.array.line_4_yellow_republica_information, R.array.line_4_yellow_republica_at_the_station),
    higienopolis_mackenzie(2, R.array.line_4_yellow_higienopolis_mackenzie_information, R.array.line_4_yellow_higienopolis_mackenzie_at_the_station),
    paulista(3, R.array.line_4_yellow_paulista_information, R.array.line_4_yellow_paulista_at_the_station),
    oscar_freire(4, R.array.line_4_yellow_oscar_freire_information, R.array.line_4_yellow_oscar_freire_at_the_station),
    fradique_coutinho(5, R.array.line_4_yellow_fradique_coutinho_information, R.array.line_4_yellow_fradique_coutinho_at_the_station),
    faria_lima(6, R.array.line_4_yellow_faria_lima_information, R.array.line_4_yellow_faria_lima_at_the_station),
    pinheiros(7, R.array.line_4_yellow_pinheiros_information, R.array.line_4_yellow_pinheiros_at_the_station),
    butanta(8, R.array.line_4_yellow_butanta_information, R.array.line_4_yellow_butanta_at_the_station),
    sao_paulo_morumbi(9, R.array.line_4_yellow_sao_paulo_morumbi_information, R.array.line_4_yellow_sao_paulo_morumbi_at_the_station);

    private int index;
    private int resArrayInformation;
    private int resArrayAtTheStation;

    Line4YellowInfo(int index, int resArrayInformation, int resArrayAtTheStation) {
        this.index = index;
        this.resArrayInformation = resArrayInformation;
        this.resArrayAtTheStation = resArrayAtTheStation;
    }

    public static Line4YellowInfo getStationByPosition(int position) {
        Line4YellowInfo type = null;

        for (int i = 0; i < Line4YellowInfo.values().length; i++) {
            Line4YellowInfo info = Line4YellowInfo.values()[i];
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
