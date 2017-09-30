package br.com.bruno.meumetro.enums;

import lombok.Getter;

/**
 * Created by Bruno on 01/05/2017.
 */

@Getter
public enum DayType {

    SUNDAY(1, "Domingo"),
    MONDAY(2, "Segunda-feira"),
    TUESDAY(3, "Terça-feira"),
    WEDNESDAY(4, "Quarta-feira"),
    THURSDAY(5, "Quinta-feira"),
    FRIDAY(6, "Sexta-feira"),
    SATURDAY(7, "Sábado");

    private int day;
    private String dayName;

    DayType(int day, String dayName) {
        this.day = day;
        this.dayName = dayName;
    }

    public static DayType getDayTypeById(int day) {
        switch (day) {
            case 1:
                return SUNDAY;

            case 2:
                return MONDAY;

            case 3:
                return TUESDAY;

            case 4:
                return WEDNESDAY;

            case 5:
                return THURSDAY;

            case 6:
                return FRIDAY;

            case 7:
                return SATURDAY;

            default:
                return MONDAY;
        }
    }

    public static DayType getDayTypeByName(String day) {

        if (MONDAY.getDayName().equalsIgnoreCase(day)) {
            return MONDAY;
        }

        if (TUESDAY.getDayName().equalsIgnoreCase(day)) {
            return TUESDAY;
        }

        if (WEDNESDAY.getDayName().equalsIgnoreCase(day)) {
            return WEDNESDAY;
        }

        if (THURSDAY.getDayName().equalsIgnoreCase(day)) {
            return THURSDAY;
        }

        if (FRIDAY.getDayName().equalsIgnoreCase(day)) {
            return FRIDAY;
        }

        if (SATURDAY.getDayName().equalsIgnoreCase(day)) {
            return SATURDAY;
        }

        return SUNDAY;
    }
}
