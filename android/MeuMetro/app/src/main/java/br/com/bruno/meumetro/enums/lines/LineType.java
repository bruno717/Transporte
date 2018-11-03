package br.com.bruno.meumetro.enums.lines;

import android.support.annotation.ArrayRes;

import br.com.bruno.meumetro.R;

/**
 * Created by Bruno on 04/09/2016.
 */
public enum LineType {

    LINE_1_BLUE(0, "1", "Linha 1 Azul", R.array.line_1_blue_direction, R.array.line_1_blue),
    LINE_2_GREEN(1, "2", "Linha 2 Verde", R.array.line_2_green_direction, R.array.line_2_green),
    LINE_3_RED(2, "3", "Linha 3 Vermelha", R.array.line_3_red_direction, R.array.line_3_red),
    LINE_4_YELLOW(3, "4", "Linha 4 Amarela", R.array.line_4_yellow_direction, R.array.line_4_yellow),
    LINE_5_LILAC(4, "5", "Linha 5 Lilás", R.array.line_5_lilac_direction, R.array.line_5_lilac),
    LINE_7_RUBY(5, "7", "Linha 7 Rubi", R.array.line_7_ruby_direction, R.array.line_7_ruby),
    LINE_8_DIAMOND(6, "8", "Linha 8 Diamante", R.array.line_8_diamond_direction, R.array.line_8_diamond),
    LINE_9_EMERALD(7, "9", "Linha 9 Esmeralda", R.array.line_9_emerald_direction, R.array.line_9_emerald),
    LINE_10_TURQUOISE(8, "10", "Linha 10 Turquesa", R.array.line_10_turquoise_direction, R.array.line_10_turquoise),
    LINE_11_CORAL(9, "11", "Linha 11 Coral", R.array.line_11_coral_direction, R.array.line_11_coral),
    LINE_12_SAPPHIRE(10, "12", "Linha 12 Safira", R.array.line_12_sapphire_direction, R.array.line_12_sapphire),
    LINE_13_JADE(11, "13", "Linha 13 Jade", R.array.line_13_jade_direction, R.array.line_13_jade),
    LINE_15_SILVER(12, "15", "Linha 15 Prata", R.array.line_15_silver_direction, R.array.line_15_silver);

    private int position;
    private String value;
    private String name;
    private int directionRes;
    private int seasonsRes;

    LineType(int position, String value, String name, @ArrayRes int directionRes, @ArrayRes int seasonsRes) {
        this.position = position;
        this.value = value;
        this.name = name;
        this.directionRes = directionRes;
        this.seasonsRes = seasonsRes;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int getDirectionRes() {
        return directionRes;
    }

    public int getSeasonsRes() {
        return seasonsRes;
    }

    public static LineType getLineType(int numberLine) {
        LineType type = null;

        LineType[] values = LineType.values();
        for (LineType lineType : values) {
            if (Integer.parseInt(lineType.value) == numberLine) {
                type = lineType;
                break;
            }
        }

        return type;
    }

    public static LineType getLineTypeByPosition(int position) {

        LineType type = null;

        LineType[] values = LineType.values();
        for (LineType lineType : values) {
            if (position == lineType.position) {
                type = lineType;
                break;
            }
        }

        return type;
    }

    public static LineType getLineTypeByName(String name) {

        LineType type = null;

        LineType[] values = LineType.values();
        for (LineType lineType : values) {
            if (name.equals(lineType.getName())) {
                type = lineType;
                break;
            }
        }

        return type;
    }
}
