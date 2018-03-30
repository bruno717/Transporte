package br.com.bruno.meumetro.enums;

/**
 * Created by Bruno on 28/08/2016.
 */
public enum ConnectionType {

    HOMOLOGATION("http://192.168.1.4:8080/"),
    PRODUCTION("https://meumetro.herokuapp.com/");

    private String url;

    ConnectionType(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
