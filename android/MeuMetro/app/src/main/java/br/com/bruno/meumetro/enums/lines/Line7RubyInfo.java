package br.com.bruno.meumetro.enums.lines;

import br.com.bruno.meumetro.R;

/**
 * Created by Bruno on 03/09/2016.
 */
public enum Line7RubyInfo {

    luz(0, R.array.line_7_ruby_luz_information, R.array.line_7_ruby_luz_at_the_station),
    palmeiras_barra_funda(1, R.array.line_7_ruby_palmeiras_barra_funda_information, R.array.line_7_ruby_palmeiras_barra_funda_at_the_station),
    agua_branca(2, R.array.line_7_ruby_agua_branca_information, R.array.line_7_ruby_agua_branca_at_the_station),
    lapa(3, R.array.line_7_ruby_lapa_information, R.array.line_7_ruby_lapa_at_the_station),
    piqueri(4, R.array.line_7_ruby_piqueri_information, R.array.line_7_ruby_piqueri_at_the_station),
    pirituba(5, R.array.line_7_ruby_pirituba_information, R.array.line_7_ruby_pirituba_at_the_station),
    vila_clarice(6, R.array.line_7_ruby_vila_clarice_information, R.array.line_7_ruby_vila_clarice_at_the_station),
    jaragua(7, R.array.line_7_ruby_jaragua_information, R.array.line_7_ruby_jaragua_at_the_station),
    vila_aurora(8, R.array.line_7_ruby_vila_aurora_information, R.array.line_7_ruby_vila_aurora_at_the_station),
    perus(9, R.array.line_7_ruby_perus_information, R.array.line_7_ruby_perus_at_the_station),
    caieiras(10, R.array.line_7_ruby_caieiras_information, R.array.line_7_ruby_caieiras_at_the_station),
    franco_da_rocha(11, R.array.line_7_ruby_franco_da_rocha_information, R.array.line_7_ruby_franco_da_rocha_at_the_station),
    baltazar_fidelis(12, R.array.line_7_ruby_baltazar_fidelis_information, R.array.line_7_ruby_baltazar_fidelis_at_the_station),
    francisco_morato(13, R.array.line_7_ruby_francisco_morato_information, R.array.line_7_ruby_francisco_morato_at_the_station),
    botujuru(14, R.array.line_7_ruby_botujuru_information, R.array.line_7_ruby_botujuru_at_the_station),
    campo_limpo_paulista(15, R.array.line_7_ruby_campo_limpo_paulista_information, R.array.line_7_ruby_campo_limpo_paulista_at_the_station),
    varzea_paulista(16, R.array.line_7_ruby_varzea_paulista_information, R.array.line_7_ruby_varzea_paulista_at_the_station),
    jundiai(17, R.array.line_7_ruby_jundiai_information, R.array.line_7_ruby_jundiai_at_the_station);

    private int index;
    private int resArrayInformation;
    private int resArrayAtTheStation;

    Line7RubyInfo(int index, int resArrayInformation, int resArrayAtTheStation) {
        this.index = index;
        this.resArrayInformation = resArrayInformation;
        this.resArrayAtTheStation = resArrayAtTheStation;
    }

    public static Line7RubyInfo getStationByPosition(int position) {
        Line7RubyInfo type = null;

        for (int i = 0; i < Line7RubyInfo.values().length; i++) {
            Line7RubyInfo info = Line7RubyInfo.values()[i];
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
