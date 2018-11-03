package br.com.bruno.meumetro.enums.lines;

import br.com.bruno.meumetro.R;

/**
 * Created by Bruno on 03/09/2016.
 */
public enum Line12SapphireInfo {

    bras(0, R.array.line_12_sapphire_bras_information, R.array.line_12_sapphire_bras_at_the_station),
    tatuape(1, R.array.line_12_sapphire_tatuape_information, R.array.line_12_sapphire_tatuape_at_the_station),
    eng_goulart(2, R.array.line_12_sapphire_eng_goulart_information, R.array.line_12_sapphire_eng_goulart_at_the_station),
    usp_leste(3, R.array.line_12_sapphire_usp_leste_information, R.array.line_12_sapphire_usp_leste_at_the_station),
    comendador_ermelino(4, R.array.line_12_sapphire_comendador_ermelino_information, R.array.line_12_sapphire_comendador_ermelino_at_the_station),
    sao_miguel_paulista(5, R.array.line_12_sapphire_sao_miguel_paulista_information, R.array.line_12_sapphire_sao_miguel_paulista_at_the_station),
    jardim_helena_vila_mara(6, R.array.line_12_sapphire_jardim_helena_vila_mara_information, R.array.line_12_sapphire_jardim_helena_vila_mara_at_the_station),
    itaim_paulista(7, R.array.line_12_sapphire_itaim_paulista_information, R.array.line_12_sapphire_itaim_paulista_at_the_station),
    jardim_romano(8, R.array.line_12_sapphire_jardim_romano_information, R.array.line_12_sapphire_jardim_romano_at_the_station),
    eng_manoel_feio(9, R.array.line_12_sapphire_eng_manoel_feio_information, R.array.line_12_sapphire_eng_manoel_feio_at_the_station),
    itaquaquecetuba(10, R.array.line_12_sapphire_itaquaquecetuba_information, R.array.line_12_sapphire_itaquaquecetuba_at_the_station),
    aracare(11, R.array.line_12_sapphire_aracare_information, R.array.line_12_sapphire_aracare_at_the_station),
    calmon_viana(12, R.array.line_12_sapphire_calmon_viana_information, R.array.line_12_sapphire_calmon_viana_at_the_station);

    private int index;
    private int resArrayInformation;
    private int resArrayAtTheStation;

    Line12SapphireInfo(int index, int resArrayInformation, int resArrayAtTheStation) {
        this.index = index;
        this.resArrayInformation = resArrayInformation;
        this.resArrayAtTheStation = resArrayAtTheStation;
    }

    public static Line12SapphireInfo getStationByPosition(int position) {
        Line12SapphireInfo type = null;

        for (int i = 0; i < Line12SapphireInfo.values().length; i++) {
            Line12SapphireInfo info = Line12SapphireInfo.values()[i];
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