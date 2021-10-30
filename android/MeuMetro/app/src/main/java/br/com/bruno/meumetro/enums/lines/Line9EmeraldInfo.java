package br.com.bruno.meumetro.enums.lines;

import br.com.bruno.meumetro.R;

/**
 * Created by Bruno on 03/09/2016.
 */
public enum Line9EmeraldInfo {

    osasco(0, R.array.line_9_emerald_osasco_information, R.array.line_9_emerald_osasco_at_the_station),
    presidente_altino(1, R.array.line_9_emerald_presidente_altino_information, R.array.line_9_emerald_presidente_altino_at_the_station),
    ceasa(2, R.array.line_9_emerald_ceasa_information, R.array.line_9_emerald_ceasa_at_the_station),
    villa_lobos_jaguare(3, R.array.line_9_emerald_villa_lobos_jaguare_information, R.array.line_9_emerald_villa_lobos_jaguare_at_the_station),
    cidade_universitaria(4, R.array.line_9_emerald_cidade_universitaria_information, R.array.line_9_emerald_cidade_universitaria_at_the_station),
    pinheiros(5, R.array.line_9_emerald_pinheiros_information, R.array.line_9_emerald_pinheiros_at_the_station),
    hebraica_reboucas(6, R.array.line_9_emerald_hebraica_reboucas_information, R.array.line_9_emerald_hebraica_reboucas_at_the_station),
    cidade_jardim(7, R.array.line_9_emerald_cidade_jardim_information, R.array.line_9_emerald_cidade_jardim_at_the_station),
    vila_olimpia(8, R.array.line_9_emerald_vila_olimpia_information, R.array.line_9_emerald_vila_olimpia_at_the_station),
    berrini(9, R.array.line_9_emerald_berrini_information, R.array.line_9_emerald_berrini_at_the_station),
    morumbi(10, R.array.line_9_emerald_morumbi_information, R.array.line_9_emerald_morumbi_at_the_station),
    granja_julieta(11, R.array.line_9_emerald_granja_julieta_information, R.array.line_9_emerald_granja_julieta_at_the_station),
    joao_dias(12, R.array.line_9_emerald_joao_dias_information, R.array.line_9_emerald_joao_dias_at_the_station),
    santo_amaro(13, R.array.line_9_emerald_santo_amaro_information, R.array.line_9_emerald_santo_amaro_at_the_station),
    socorro(14, R.array.line_9_emerald_socorro_information, R.array.line_9_emerald_socorro_at_the_station),
    jurubatuba(15, R.array.line_9_emerald_jurubatuba_information, R.array.line_9_emerald_jurubatuba_at_the_station),
    autodromo(16, R.array.line_9_emerald_autodromo_information, R.array.line_9_emerald_autodromo_at_the_station),
    primavera_interlagos(17, R.array.line_9_emerald_primavera_interlagos_information, R.array.line_9_emerald_primavera_interlagos_at_the_station),
    grajau(18, R.array.line_9_emerald_grajau_information, R.array.line_9_emerald_grajau_at_the_station),
    bruno_covas(19, R.array.line_9_emerald_bruno_covas_information, R.array.line_9_emerald_bruno_covas_at_the_station);

    private int index;
    private int resArrayInformation;
    private int resArrayAtTheStation;

    Line9EmeraldInfo(int index, int resArrayInformation, int resArrayAtTheStation) {
        this.index = index;
        this.resArrayInformation = resArrayInformation;
        this.resArrayAtTheStation = resArrayAtTheStation;
    }

    public static Line9EmeraldInfo getStationByPosition(int position) {
        Line9EmeraldInfo type = null;

        for (int i = 0; i < Line9EmeraldInfo.values().length; i++) {
            Line9EmeraldInfo info = Line9EmeraldInfo.values()[i];
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
