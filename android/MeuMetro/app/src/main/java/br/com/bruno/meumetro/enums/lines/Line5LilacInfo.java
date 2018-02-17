package br.com.bruno.meumetro.enums.lines;

import br.com.bruno.meumetro.R;

/**
 * Created by Bruno on 03/09/2016.
 */
public enum Line5LilacInfo {

    capao_redondo(0, R.array.line_5_lilac_capao_redendo_information, R.array.line_5_lilac_capao_redendo_at_the_station),
    campo_limpo(1, R.array.line_5_lilac_campo_limpo_information, R.array.line_5_lilac_campo_limpo_at_the_station),
    vila_das_belezas(2, R.array.line_5_lilac_vila_das_belezas_information, R.array.line_5_lilac_vila_das_belezas_at_the_station),
    giovanni_gronchi(3, R.array.line_5_lilac_giovanni_gronchi_information, R.array.line_5_lilac_giovanni_gronchi_at_the_station),
    santo_amaro(4, R.array.line_5_lilac_santo_amaro_information, R.array.line_5_lilac_santo_amaro_at_the_station),
    largo_treze(5, R.array.line_5_lilac_largo_treze_information, R.array.line_5_lilac_largo_treze_at_the_station),
    adolfo_pinheiros(6, R.array.line_5_lilac_adolfo_pinheiro_information, R.array.line_5_lilac_adolfo_pinheiro_at_the_station),
    alto_da_boa_vista(7, R.array.line_5_lilac_alto_da_boa_vista_information, R.array.line_5_lilac_alto_da_boa_vista_at_the_station),
    borba_gato(8, R.array.line_5_lilac_borba_gato_information, R.array.line_5_lilac_borba_gato_at_the_station),
    brooklin(9, R.array.line_5_lilac_brooklin_information, R.array.line_5_lilac_brooklin_at_the_station);

    private int index;
    private int resArrayInformation;
    private int resArrayAtTheStation;

    Line5LilacInfo(int index, int resArrayInformation, int resArrayAtTheStation) {
        this.index = index;
        this.resArrayInformation = resArrayInformation;
        this.resArrayAtTheStation = resArrayAtTheStation;
    }

    public static Line5LilacInfo getStationByPosition(int position) {
        Line5LilacInfo type = null;
        switch (position) {
            case 0:
                type = capao_redondo;
                break;
            case 1:
                type = campo_limpo;
                break;
            case 2:
                type = vila_das_belezas;
                break;
            case 3:
                type = giovanni_gronchi;
                break;
            case 4:
                type = santo_amaro;
                break;
            case 5:
                type = largo_treze;
                break;
            case 6:
                type = adolfo_pinheiros;
                break;
            case 7:
                type = alto_da_boa_vista;
                break;
            case 8:
                type = borba_gato;
                break;
            case 9:
                type = brooklin;
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
