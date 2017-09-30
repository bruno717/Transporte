package br.com.bruno.meumetro.enums;

import lombok.Getter;

/**
 * Created by Bruno on 21/06/2017.
 */
@Getter
public enum StatusType {
    OFFICIAL("OFFICIAL"),
    USER("USER");

    private String type;

    StatusType(String type){
        this.type = type;
    }
}
