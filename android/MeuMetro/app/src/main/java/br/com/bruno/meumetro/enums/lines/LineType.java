package br.com.bruno.meumetro.enums.lines;

import br.com.bruno.meumetro.R;

/**
 * Created by Bruno on 04/09/2016.
 */
public enum LineType {

    LINE_1_BLUE("1", "Linha 1 Azul"),
    LINE_2_GREEN("2", "Linha 2 Verde"),
    LINE_3_RED("3", "Linha 3 Vermelha"),
    LINE_4_YELLOW("4", "Linha 4 Amarela"),
    LINE_5_LILAC("5", "Linha 5 Lilás"),
    LINE_7_RUBY("7", "Linha 7 Rubi"),
    LINE_8_DIAMOND("8", "Linha 8 Diamante"),
    LINE_9_EMERALD("9", "Linha 9 Esmeralda"),
    LINE_10_TURQUOISE("10", "Linha 10 Turquesa"),
    LINE_11_CORAL("11", "Linha 11 Coral"),
    LINE_12_SAPPHIRE("12", "Linha 12 Safira"),
    LINE_15_SILVER("15", "Linha 15 Prata");

    private String value;
    private String name;

    LineType(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static LineType getLineType(int type) {
        switch (type) {
            case 1:
                return LINE_1_BLUE;
            case 2:
                return LINE_2_GREEN;
            case 3:
                return LINE_3_RED;
            case 4:
                return LINE_4_YELLOW;
            case 5:
                return LINE_5_LILAC;
            case 7:
                return LINE_7_RUBY;
            case 8:
                return LINE_8_DIAMOND;
            case 9:
                return LINE_9_EMERALD;
            case 10:
                return LINE_10_TURQUOISE;
            case 11:
                return LINE_11_CORAL;
            case 12:
                return LINE_12_SAPPHIRE;
            case 15:
                return LINE_15_SILVER;
            default:
                return LINE_1_BLUE;
        }
    }

    public static LineType getLineTypeByPosition(int type) {
        switch (type) {
            case 0:
                return LINE_1_BLUE;
            case 1:
                return LINE_2_GREEN;
            case 2:
                return LINE_3_RED;
            case 3:
                return LINE_4_YELLOW;
            case 4:
                return LINE_5_LILAC;
            case 5:
                return LINE_7_RUBY;
            case 6:
                return LINE_8_DIAMOND;
            case 7:
                return LINE_9_EMERALD;
            case 8:
                return LINE_10_TURQUOISE;
            case 9:
                return LINE_11_CORAL;
            case 10:
                return LINE_12_SAPPHIRE;
            case 11:
                return LINE_15_SILVER;
            default:
                return LINE_1_BLUE;
        }
    }

    public static LineType getLineTypeByName(String name) {

        if (name.equalsIgnoreCase(LINE_1_BLUE.getName())) {
            return LINE_1_BLUE;
        }
        if (name.equalsIgnoreCase(LINE_2_GREEN.getName())) {
            return LINE_2_GREEN;
        }
        if (name.equalsIgnoreCase(LINE_3_RED.getName())) {
            return LINE_3_RED;
        }
        if (name.equalsIgnoreCase(LINE_4_YELLOW.getName())) {
            return LINE_4_YELLOW;
        }
        if (name.equalsIgnoreCase(LINE_5_LILAC.getName())) {
            return LINE_5_LILAC;
        }
        if (name.equalsIgnoreCase(LINE_7_RUBY.getName())) {
            return LINE_7_RUBY;
        }
        if (name.equalsIgnoreCase(LINE_8_DIAMOND.getName())) {
            return LINE_8_DIAMOND;
        }
        if (name.equalsIgnoreCase(LINE_9_EMERALD.getName())) {
            return LINE_9_EMERALD;
        }
        if (name.equalsIgnoreCase(LINE_10_TURQUOISE.getName())) {
            return LINE_10_TURQUOISE;
        }
        if (name.equalsIgnoreCase(LINE_11_CORAL.getName())) {
            return LINE_11_CORAL;
        }
        if (name.equalsIgnoreCase(LINE_12_SAPPHIRE.getName())) {
            return LINE_12_SAPPHIRE;
        }
        if (name.equalsIgnoreCase(LINE_15_SILVER.getName())) {
            return LINE_15_SILVER;
        }
        return LINE_1_BLUE;
    }

    public static Integer getDirectionLineResByLineType(LineType lineType) {

        switch (lineType) {
            case LINE_1_BLUE:
                return R.array.line_1_blue_direction;

            case LINE_2_GREEN:
                return R.array.line_2_green_direction;

            case LINE_3_RED:
                return R.array.line_3_red_direction;

            case LINE_4_YELLOW:
                return R.array.line_4_yellow_direction;

            case LINE_5_LILAC:
                return R.array.line_5_lilac_direction;

            case LINE_7_RUBY:
                return R.array.line_7_ruby_direction;

            case LINE_8_DIAMOND:
                return R.array.line_8_diamond_direction;

            case LINE_9_EMERALD:
                return R.array.line_9_emerald_direction;

            case LINE_10_TURQUOISE:
                return R.array.line_10_turquoise_direction;

            case LINE_11_CORAL:
                return R.array.line_11_coral_direction;

            case LINE_12_SAPPHIRE:
                return R.array.line_12_sapphire_direction;

            case LINE_15_SILVER:
                return R.array.line_15_silver_direction;
        }

        return R.array.line_1_blue_direction;
    }

    public static Integer getStationsByLineType(LineType lineType) {
        switch (lineType) {
            case LINE_1_BLUE:
                return R.array.line_1_blue;

            case LINE_2_GREEN:
                return R.array.line_2_green;

            case LINE_3_RED:
                return R.array.line_3_red;

            case LINE_4_YELLOW:
                return R.array.line_4_yellow;

            case LINE_5_LILAC:
                return R.array.line_5_lilac;

            case LINE_7_RUBY:
                return R.array.line_7_ruby;

            case LINE_8_DIAMOND:
                return R.array.line_8_diamond;

            case LINE_9_EMERALD:
                return R.array.line_9_emerald;

            case LINE_10_TURQUOISE:
                return R.array.line_10_turquoise;

            case LINE_11_CORAL:
                return R.array.line_11_coral;

            case LINE_12_SAPPHIRE:
                return R.array.line_12_sapphire;

            case LINE_15_SILVER:
                return R.array.line_15_silver;

            default:
                return R.array.line_1_blue;
        }
    }
}
