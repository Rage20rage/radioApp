package de.marko.radio.client.radiostation;

public class Station {

    public Station(String name, String url) {
        this.name = name;
        this.url = url;
    }

    private String url;
    private String name;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

}
