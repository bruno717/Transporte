package br.com.bruno.meumetro.enums.lines;

import br.com.bruno.meumetro.R;

/**
 * Created by Bruno on 03/09/2016.
 */
public enum Line1BlueInfo {

    jabaquara(0, R.array.line_1_blue_jabaquara_information, R.array.line_1_blue_jabaquara_at_the_station),
    conceicao(1, R.array.line_1_blue_conceicao_information, R.array.line_1_blue_conceicao_at_the_station),
    sao_judas(2, R.array.line_1_blue_sao_judas_information, R.array.line_1_blue_sao_judas_at_the_station),
    saude(3, R.array.line_1_blue_saude_information, R.array.line_1_blue_saude_at_the_station),
    praca_da_arvore(4, R.array.line_1_blue_praca_da_arvore_information, R.array.line_1_blue_praca_da_arvore_at_the_station),
    santa_cruz(5, R.array.line_1_blue_santa_cruz_information, R.array.line_1_blue_santa_cruz_at_the_station),
    vila_mariana(6, R.array.line_1_blue_vila_mariana_information, R.array.line_1_blue_vila_mariana_at_the_station),
    ana_rosa(7, R.array.line_1_blue_ana_rosa_information, R.array.line_1_blue_ana_rosa_at_the_station),
    paraiso(8, R.array.line_1_blue_paraiso_information, R.array.line_1_blue_paraiso_at_the_station),
    vergueiro(9, R.array.line_1_blue_vergueiro_information, R.array.line_1_blue_vergueiro_at_the_station),
    sao_joaquim(10, R.array.line_1_blue_sao_joaquim_information, R.array.line_1_blue_sao_joaquim_at_the_station),
    liberdade(11, R.array.line_1_blue_liberdade_information, R.array.line_1_blue_liberdade_at_the_station),
    se(12, R.array.line_1_blue_se_information, R.array.line_1_blue_se_at_the_station),
    sao_bento(13, R.array.line_1_blue_sao_bento_information, R.array.line_1_blue_sao_bento_at_the_station),
    luz(14, R.array.line_1_blue_luz_information, R.array.line_1_blue_luz_at_the_station),
    tiradentes(15, R.array.line_1_blue_tiradentes_information, R.array.line_1_blue_tiradentes_at_the_station),
    armenia(16, R.array.line_1_blue_armenia_information, R.array.line_1_blue_armenia_at_the_station),
    portuguesa_tiete(17, R.array.line_1_blue_portuguesa_tiete_information, R.array.line_1_blue_portuguesa_tiete_at_the_station),
    carandiru(18, R.array.line_1_blue_carandiru_information, R.array.line_1_blue_carandiru_at_the_station),
    santana(19, R.array.line_1_blue_santana_information, R.array.line_1_blue_santana_at_the_station),
    jardim_sao_paulo_ayrton_senna(20, R.array.line_1_blue_jardim_sao_paulo_ayrton_senna_information, R.array.line_1_blue_jardim_sao_paulo_ayrton_senna_at_the_station),
    parada_inglesa(21, R.array.line_1_blue_parada_inglesa_information, R.array.line_1_blue_parada_inglesa_at_the_station),
    tucuruvi(22, R.array.line_1_blue_tucuruvi_information, R.array.line_1_blue_tucuruvi_at_the_station);

    private int index;
    private int resArrayInformation;
    private int resArrayAtTheStation;

    Line1BlueInfo(int index, int resArrayInformation, int resArrayAtTheStation) {
        this.index = index;
        this.resArrayInformation = resArrayInformation;
        this.resArrayAtTheStation = resArrayAtTheStation;
    }

    public static Line1BlueInfo getStationByPosition(int position) {
        Line1BlueInfo type = null;
        switch (position) {
            case 0:
                type = jabaquara;
                break;
            case 1:
                type = conceicao;
                break;
            case 2:
                type = sao_judas;
                break;
            case 3:
                type = saude;
                break;
            case 4:
                type = praca_da_arvore;
                break;
            case 5:
                type = santa_cruz;
                break;
            case 6:
                type = vila_mariana;
                break;
            case 7:
                type = ana_rosa;
                break;
            case 8:
                type = paraiso;
                break;
            case 9:
                type = vergueiro;
                break;
            case 10:
                type = sao_joaquim;
                break;
            case 11:
                type = liberdade;
                break;
            case 12:
                type = se;
                break;
            case 13:
                type = sao_bento;
                break;
            case 14:
                type = luz;
                break;
            case 15:
                type = tiradentes;
                break;
            case 16:
                type = armenia;
                break;
            case 17:
                type = portuguesa_tiete;
                break;
            case 18:
                type = carandiru;
                break;
            case 19:
                type = santana;
                break;
            case 20:
                type = jardim_sao_paulo_ayrton_senna;
                break;
            case 21:
                type = parada_inglesa;
                break;
            case 22:
                type = tucuruvi;
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
