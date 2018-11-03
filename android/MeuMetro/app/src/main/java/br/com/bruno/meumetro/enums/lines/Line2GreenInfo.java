package br.com.bruno.meumetro.enums.lines;

import br.com.bruno.meumetro.R;

/**
 * Created by Bruno on 03/09/2016.
 */
public enum Line2GreenInfo {

    vila_prudente(0, R.array.line_2_green_vila_prudente_information, R.array.line_2_green_vila_prudente_at_the_station),
    tamanduatei(1, R.array.line_2_green_tamanduatei_information, R.array.line_2_green_tamanduatei_at_the_station),
    sacoma(2, R.array.line_2_green_sacoma_information, R.array.line_2_green_sacoma_at_the_station),
    alto_do_ipiranga(3, R.array.line_2_green_alto_do_ipiranga_information, R.array.line_2_green_alto_do_ipiranga_at_the_station),
    santos_imigrantes(4, R.array.line_2_green_santos_imigrantes_information, R.array.line_2_green_santos_imigrantes_at_the_station),
    chacara_klabin(5, R.array.line_2_green_chacara_klabin_information, R.array.line_2_green_chacara_klabin_at_the_station),
    ana_rosa(6, R.array.line_2_green_ana_rosa_information, R.array.line_2_green_ana_rosa_at_the_station),
    paraiso(7, R.array.line_2_green_paraiso_information, R.array.line_2_green_paraiso_at_the_station),
    brigadeiro(8, R.array.line_2_green_brigadeiro_information, R.array.line_2_green_brigadeiro_at_the_station),
    trianon_masp(9, R.array.line_2_green_trianon_masp_information, R.array.line_2_green_trianon_masp_at_the_station),
    consolacao(10, R.array.line_2_green_consolacao_information, R.array.line_2_green_consolacao_at_the_station),
    clinicas(11, R.array.line_2_green_clinicas_information, R.array.line_2_green_clinicas_at_the_station),
    s_n_sra_de_fatima_sumare(12, R.array.line_2_green_snsra_de_fatima_information, R.array.line_2_green_snsra_de_fatima_at_the_station),
    vila_madalena(13, R.array.line_2_green_vila_madalena_information, R.array.line_2_green_vila_madalena_at_the_station);

    private int index;
    private int resArrayInformation;
    private int resArrayAtTheStation;

    Line2GreenInfo(int index, int resArrayInformation, int resArrayAtTheStation) {
        this.index = index;
        this.resArrayInformation = resArrayInformation;
        this.resArrayAtTheStation = resArrayAtTheStation;
    }

    public static Line2GreenInfo getStationByPosition(int position) {
        Line2GreenInfo type = null;

        for (int i = 0; i < Line2GreenInfo.values().length; i++) {
            Line2GreenInfo info = Line2GreenInfo.values()[i];
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
