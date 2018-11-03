package br.com.bruno.meumetro.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Locale;

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
    private String cep;
    private String[] atTheStation;

    @JsonIgnore
    public String getLocationWithCep() {
        return String.format(Locale.getDefault(), "%s - %s", location, "São Paulo - SP");
    }
}
