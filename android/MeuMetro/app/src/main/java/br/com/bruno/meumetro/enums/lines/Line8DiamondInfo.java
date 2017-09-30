package br.com.bruno.meumetro.enums.lines;

import br.com.bruno.meumetro.R;

/**
 * Created by Bruno on 03/09/2016.
 */
public enum Line8DiamondInfo {

    julio_prestes(0, R.array.line_8_diamond_julio_prestes_information, R.array.line_8_diamond_julio_prestes_at_the_station),
    palmeiras_barra_funda(1, R.array.line_8_diamond_palmeiras_barra_funda_information, R.array.line_8_diamond_palmeiras_barra_funda_at_the_station),
    lapa(2, R.array.line_8_diamond_lapa_information, R.array.line_8_diamond_lapa_at_the_station),
    domingos_de_moraes(3, R.array.line_8_diamond_domingos_de_moraes_information, R.array.line_8_diamond_domingos_de_moraes_at_the_station),
    imperatriz_leopoldina(4, R.array.line_8_diamond_imperatriz_leopoldina_information, R.array.line_8_diamond_imperatriz_leopoldina_at_the_station),
    presidente_altino(5, R.array.line_8_diamond_presidente_altino_information, R.array.line_8_diamond_presidente_altino_at_the_station),
    osasco(6, R.array.line_8_diamond_osasco_information, R.array.line_8_diamond_osasco_at_the_station),
    comandante_sampaio(7, R.array.line_8_diamond_comandante_sampaio_information, R.array.line_8_diamond_comandante_sampaio_at_the_station),
    quitauna(8, R.array.line_8_diamond_quitauna_information, R.array.line_8_diamond_quitauna_at_the_station),
    general_miguel_costa(9, R.array.line_8_diamond_general_miguel_costa_information, R.array.line_8_diamond_general_miguel_costa_at_the_station),
    carapicuiba(10, R.array.line_8_diamond_carapicuiba_information, R.array.line_8_diamond_carapicuiba_at_the_station),
    santa_terezinha(11, R.array.line_8_diamond_santa_terezinha_information, R.array.line_8_diamond_santa_terezinha_at_the_station),
    antonio_joao(12, R.array.line_8_diamond_antonio_joao_information, R.array.line_8_diamond_antonio_joao_at_the_station),
    barueri(13, R.array.line_8_diamond_barueri_information, R.array.line_8_diamond_barueri_at_the_station),
    jardim_belval(14, R.array.line_8_diamond_jardim_belval_information, R.array.line_8_diamond_jardim_belval_at_the_station),
    jardim_silveira(15, R.array.line_8_diamond_jardim_silveira_information, R.array.line_8_diamond_jardim_silveira_at_the_station),
    jandira(16, R.array.line_8_diamond_jandira_information, R.array.line_8_diamond_jandira_at_the_station),
    sagrado_coracao(17, R.array.line_8_diamond_sagrado_coracao_information, R.array.line_8_diamond_sagrado_coracao_at_the_station),
    eng_cardoso(18, R.array.line_8_diamond_eng_cardoso_information, R.array.line_8_diamond_eng_cardoso_at_the_station),
    itapevi(19, R.array.line_8_diamond_itapevi_information, R.array.line_8_diamond_itapevi_at_the_station),
    santa_rita(20, R.array.line_8_diamond_santa_rita_information, R.array.line_8_diamond_santa_rita_at_the_station),
    amador_bueno(21, R.array.line_8_diamond_amador_bueno_information, R.array.line_8_diamond_amador_bueno_at_the_station);

    private int index;
    private int resArrayInformation;
    private int resArrayAtTheStation;

    Line8DiamondInfo(int index, int resArrayInformation, int resArrayAtTheStation) {
        this.index = index;
        this.resArrayInformation = resArrayInformation;
        this.resArrayAtTheStation = resArrayAtTheStation;
    }

    public static Line8DiamondInfo getStationByPosition(int position) {
        Line8DiamondInfo type = null;
        switch (position) {
            case 0:
                type = julio_prestes;
                break;
            case 1:
                type = palmeiras_barra_funda;
                break;
            case 2:
                type = lapa;
                break;
            case 3:
                type = domingos_de_moraes;
                break;
            case 4:
                type = imperatriz_leopoldina;
                break;
            case 5:
                type = presidente_altino;
                break;
            case 6:
                type = osasco;
                break;
            case 7:
                type = comandante_sampaio;
                break;
            case 8:
                type = quitauna;
                break;
            case 9:
                type = general_miguel_costa;
                break;
            case 10:
                type = carapicuiba;
                break;
            case 11:
                type = santa_terezinha;
                break;
            case 12:
                type = antonio_joao;
                break;
            case 13:
                type = barueri;
                break;
            case 14:
                type = jardim_belval;
                break;
            case 15:
                type = jardim_silveira;
                break;
            case 16:
                type = jandira;
                break;
            case 17:
                type = sagrado_coracao;
                break;
            case 18:
                type = eng_cardoso;
                break;
            case 19:
                type = itapevi;
                break;
            case 20:
                type = santa_rita;
                break;
            case 21:
                type = amador_bueno;
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
