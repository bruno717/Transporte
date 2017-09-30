package br.com.bruno.meumetro.models;

import lombok.Data;

/**
 * Created by Bruno on 03/09/2016.
 */
@Data
public class Station {

    private String name;
    private String operationSundayToFriday;
    private String operationSaturday;
    private String location;
    private String[] atTheStation;
}
