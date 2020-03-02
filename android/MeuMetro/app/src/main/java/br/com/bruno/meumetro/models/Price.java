package br.com.bruno.meumetro.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {

    private String entire;
    private String half;
    private String integration;

}
