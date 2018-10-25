package de.marko.radio.client.config;

public class VersionController {
    private static VersionController ourInstance = new VersionController();

    public static VersionController getInstance() {
        return ourInstance;
    }

    private VersionController() {
    }
}
