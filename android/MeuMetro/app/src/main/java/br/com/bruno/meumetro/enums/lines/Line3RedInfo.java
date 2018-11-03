package br.com.bruno.meumetro.enums.lines;

import br.com.bruno.meumetro.R;

/**
 * Created by Bruno on 03/09/2016.
 */
public enum Line3RedInfo {

    corinthians_itaquera(0, R.array.line_3_red_corinthians_itaquera_information, R.array.line_3_red_corinthians_itaquera_at_the_station),
    artur_alvin(1, R.array.line_3_red_artur_alvin_information, R.array.line_3_red_artur_alvin_at_the_station),
    patriarca(2, R.array.line_3_red_patriarca_information, R.array.line_3_red_patriarca_at_the_station),
    guilhermina_esperanca(3, R.array.line_3_red_guilhermina_esperanca_information, R.array.line_3_red_guilhermina_esperanca_at_the_station),
    vila_matilde(4, R.array.line_3_red_vila_matilde_information, R.array.line_3_red_vila_matilde_at_the_station),
    penha(5, R.array.line_3_red_penha_information, R.array.line_3_red_penha_at_the_station),
    carrao(6, R.array.line_3_red_carrao_information, R.array.line_3_red_carrao_at_the_station),
    tatuape(7, R.array.line_3_red_tatuape_information, R.array.line_3_red_tatuape_at_the_station),
    belem(8, R.array.line_3_red_belem_information, R.array.line_3_red_belem_at_the_station),
    bresser_mooca(9, R.array.line_3_red_bresser_mooca_information, R.array.line_3_red_bresser_mooca_at_the_station),
    bras(10, R.array.line_3_red_bras_information, R.array.line_3_red_bras_at_the_station),
    pedro_ii(11, R.array.line_3_red_pedro_ii_information, R.array.line_3_red_pedro_ii_at_the_station),
    se(12, R.array.line_3_red_se_information, R.array.line_3_red_se_at_the_station),
    anhangabau(13, R.array.line_3_red_anhangabau_information, R.array.line_3_red_anhangabau_at_the_station),
    republica(14, R.array.line_3_red_republica_information, R.array.line_3_red_republica_at_the_station),
    santa_cecilia(15, R.array.line_3_red_santa_cecilia_information, R.array.line_3_red_santa_cecilia_at_the_station),
    marechal_deodoro(16, R.array.line_3_red_marechal_deodoro_information, R.array.line_3_red_marechal_deodoro_at_the_station),
    palmeiras_barra_funda(17, R.array.line_3_red_palmeiras_barra_funda_information, R.array.line_3_red_palmeiras_barra_funda_at_the_station);

    private int index;
    private int resArrayInformation;
    private int resArrayAtTheStation;

    Line3RedInfo(int index, int resArrayInformation, int resArrayAtTheStation) {
        this.index = index;
        this.resArrayInformation = resArrayInformation;
        this.resArrayAtTheStation = resArrayAtTheStation;
    }

    public static Line3RedInfo getStationByPosition(int position) {
        Line3RedInfo type = null;

        for (int i = 0; i < Line3RedInfo.values().length; i++) {
            Line3RedInfo info = Line3RedInfo.values()[i];
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
