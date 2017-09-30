package br.com.bruno.meumetro.utils;

/**
 * Created by Bruno on 10/06/2017.
 */

public class ArrayUtils {

    public static CharSequence[] removeElements(CharSequence[] input, CharSequence deleteMe) {
        CharSequence[] result = new CharSequence[input.length - 1];
        int count = 0;

        for (int i = 0; i < input.length; i++) {
            if (!deleteMe.equals(input[i])) {
                result[count] = input[i];
                count++;
            }
        }

        return result;
    }

    public static Integer[] removeElements(Integer[] input, Integer deleteMe) {
        Integer[] result = new Integer[input.length - 1];
        int count = 0;

        for (int i = 0; i < input.length; i++) {
            if (!deleteMe.equals(input[i])) {
                result[count] = input[i];
                count++;
            }
        }

        return result;
    }
}
