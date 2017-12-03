package br.com.bruno.meumetro.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Created by Bruno on 03/12/2017.
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Place {

    private String icon;
    private String name;

    @JsonProperty("place_id")
    private String placeId;

    @JsonProperty("vicinity")
    private String address;

}
