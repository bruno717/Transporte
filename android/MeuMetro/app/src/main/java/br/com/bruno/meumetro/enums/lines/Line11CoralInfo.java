package br.com.bruno.meumetro.enums.lines;

import br.com.bruno.meumetro.R;

/**
 * Created by Bruno on 03/09/2016.
 */
public enum Line11CoralInfo {

    luz(0, R.array.line_11_coral_luz_information, R.array.line_11_coral_luz_at_the_station),
    bras(1, R.array.line_11_coral_bras_information, R.array.line_11_coral_bras_at_the_station),
    tatuape(2, R.array.line_11_coral_tatuape_information, R.array.line_11_coral_tatuape_at_the_station),
    corinthians_itaquera(3, R.array.line_11_coral_corinthians_itaquera_information, R.array.line_11_coral_corinthians_itaquera_at_the_station),
    dom_bosco(4, R.array.line_11_coral_dom_bosco_information, R.array.line_11_coral_dom_bosco_at_the_station),
    jose_bonifacio(5, R.array.line_11_coral_jose_bonifacio_information, R.array.line_11_coral_jose_bonifacio_at_the_station),
    guaianases(6, R.array.line_11_coral_guaianases_information, R.array.line_11_coral_guaianases_at_the_station),
    antonio_gianetti_neto(7, R.array.line_11_coral_antonio_gianetti_neto_information, R.array.line_11_coral_antonio_gianetti_neto_at_the_station),
    ferraz_de_vasconcelos(8, R.array.line_11_coral_ferraz_de_vasconcelos_information, R.array.line_11_coral_ferraz_de_vasconcelos_at_the_station),
    poa(9, R.array.line_11_coral_poa_information, R.array.line_11_coral_poa_at_the_station),
    calmon_viana(10, R.array.line_11_coral_calmon_viana_information, R.array.line_11_coral_calmon_viana_at_the_station),
    suzano(11, R.array.line_11_coral_suzano_information, R.array.line_11_coral_suzano_at_the_station),
    jundiapeba(12, R.array.line_11_coral_jundiapeba_information, R.array.line_11_coral_jundiapeba_at_the_station),
    bras_cubas(13, R.array.line_11_coral_bras_cubas_information, R.array.line_11_coral_bras_cubas_at_the_station),
    mogi_das_cruzes(14, R.array.line_11_coral_mogi_das_cruzes_information, R.array.line_11_coral_mogi_das_cruzes_at_the_station),
    estudantes(15, R.array.line_11_coral_estudantes_information, R.array.line_11_coral_estudantes_at_the_station);

    private int index;
    private int resArrayInformation;
    private int resArrayAtTheStation;

    Line11CoralInfo(int index, int resArrayInformation, int resArrayAtTheStation) {
        this.index = index;
        this.resArrayInformation = resArrayInformation;
        this.resArrayAtTheStation = resArrayAtTheStation;
    }

    public static Line11CoralInfo getStationByPosition(int position) {
        Line11CoralInfo type = null;

        for (int i = 0; i < Line11CoralInfo.values().length; i++) {
            Line11CoralInfo info = Line11CoralInfo.values()[i];
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
