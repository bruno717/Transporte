package br.com.bruno.meumetro.enums.lines;

import br.com.bruno.meumetro.R;

/**
 * Created by Bruno on 03/09/2016.
 */
public enum Line15SilverInfo {

    vila_prudente(0, R.array.line_15_silver_vila_prudente_information, R.array.line_15_silver_vila_prudente_at_the_station),
    oratorio(1, R.array.line_15_silver_oratorio_information, R.array.line_15_silver_oratorio_at_the_station),
    sao_lucas(2, R.array.line_15_silver_sao_lucas_information, R.array.line_15_silver_sao_lucas_at_the_station),
    camilo_haddad(3, R.array.line_15_silver_camilo_haddad_information, R.array.line_15_silver_camilo_haddad_at_the_station),
    vila_tolstoi(4, R.array.line_15_silver_vila_tolstoi_information, R.array.line_15_silver_vila_tolstoi_at_the_station),
    vila_uniao(5, R.array.line_15_silver_vila_uniao_information, R.array.line_15_silver_vila_uniao_at_the_station),
    jardim_planalto(6, R.array.line_15_silver_jardim_planalto_information, R.array.line_15_silver_jardim_planalto_at_the_station),
    sapopemba(7, R.array.line_15_silver_sapopemba_information, R.array.line_15_silver_sapopemba_at_the_station),
    fazenda_da_juta(8, R.array.line_15_silver_fazenda_da_juta_information, R.array.line_15_silver_fazenda_da_juta_at_the_station),
    sao_mateus(9, R.array.line_15_silver_sao_mateus_information, R.array.line_15_silver_sao_mateus_at_the_station);

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

        for (int i = 0; i < Line15SilverInfo.values().length; i++) {
            Line15SilverInfo info = Line15SilverInfo.values()[i];
            if (info.index == position) {
                type = info;
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
