package br.com.bruno.meumetro.models;

import java.util.List;

import lombok.Data;

/**
 * Created by Bruno on 21/05/2017.
 */

@Data
public class Message {

    private String title;
    private String simpleDescription;
    private List<String> bigDescription;
    private Integer type;
    private List<Line> lines;
    private String date;

}
