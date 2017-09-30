package br.com.bruno.meumetro.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * Created by Bruno on 21/05/2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Device {

    private Long id;
    private String idDevice;
    private String tokenDevice;

}
