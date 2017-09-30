package br.com.bruno.meumetro.enums.lines;

import br.com.bruno.meumetro.R;

/**
 * Created by Bruno on 03/09/2016.
 */
public enum Line10TurquoiseInfo {

    bras(0, R.array.line_10_turquoise_bras_information, R.array.line_10_turquoise_bras_at_the_station),
    juventos_mooca(1, R.array.line_10_turquoise_juventos_mooca_information, R.array.line_10_turquoise_juventos_mooca_at_the_station),
    ipiranga(2, R.array.line_10_turquoise_ipiranga_information, R.array.line_10_turquoise_ipiranga_at_the_station),
    tamanduatei(3, R.array.line_10_turquoise_tamanduatei_information, R.array.line_10_turquoise_tamanduatei_at_the_station),
    sao_caetano(4, R.array.line_10_turquoise_sao_caetano_do_sul_pref_water_braido_information, R.array.line_10_turquoise_sao_caetano_do_sul_pref_water_braido_at_the_station),
    utinga(5, R.array.line_10_turquoise_utinga_information, R.array.line_10_turquoise_utinga_at_the_station),
    prefeito_saladino(6, R.array.line_10_turquoise_prefeito_saladino_information, R.array.line_10_turquoise_prefeito_saladino_at_the_station),
    pref_celso_daniel_santo_andre(7, R.array.line_10_turquoise_pref_celso_daniel_santo_andre_information, R.array.line_10_turquoise_pref_celso_daniel_santo_andre_at_the_station),
    capuava(8, R.array.line_10_turquoise_capuava_information, R.array.line_10_turquoise_capuava_at_the_station),
    maua(9, R.array.line_10_turquoise_maua_information, R.array.line_10_turquoise_maua_at_the_station),
    guapituba(10, R.array.line_10_turquoise_guapituba_information, R.array.line_10_turquoise_guapituba_at_the_station),
    ribeirao_pires_a_bespalec(11, R.array.line_10_turquoise_ribeirao_pires_information, R.array.line_10_turquoise_ribeirao_pires_at_the_station),
    rio_grande_da_serra(12, R.array.line_10_turquoise_rio_grande_da_serra_information, R.array.line_10_turquoise_rio_grande_da_serra_at_the_station);

    private int index;
    private int resArrayInformation;
    private int resArrayAtTheStation;

    Line10TurquoiseInfo(int index, int resArrayInformation, int resArrayAtTheStation) {
        this.index = index;
        this.resArrayInformation = resArrayInformation;
        this.resArrayAtTheStation = resArrayAtTheStation;
    }

    public static Line10TurquoiseInfo getStationByPosition(int position) {
        Line10TurquoiseInfo type = null;
        switch (position) {
            case 0:
                type = bras;
                break;
            case 1:
                type = juventos_mooca;
                break;
            case 2:
                type = ipiranga;
                break;
            case 3:
                type = tamanduatei;
                break;
            case 4:
                type = sao_caetano;
                break;
            case 5:
                type = utinga;
                break;
            case 6:
                type = prefeito_saladino;
                break;
            case 7:
                type = pref_celso_daniel_santo_andre;
                break;
            case 8:
                type = capuava;
                break;
            case 9:
                type = maua;
                break;
            case 10:
                type = guapituba;
                break;
            case 11:
                type = ribeirao_pires_a_bespalec;
                break;
            case 12:
                type = rio_grande_da_serra;
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
