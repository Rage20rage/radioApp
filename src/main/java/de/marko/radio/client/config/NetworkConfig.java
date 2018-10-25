package de.marko.radio.client.config;

public class NetworkConfig {

    private static NetworkConfig networkConfig;

    public static NetworkConfig getConfig() {
        if(networkConfig == null) {
            networkConfig = new NetworkConfig();
        }
        return networkConfig;
    }

    public String getFullAddress() {
        ConfigHandler.getInstance().logger.trace("Gesamte Addresse wird gelesen...");
        return getAddress() + ":" + getPort();
    }

    public String getAddress() {
        ConfigHandler.getInstance().logger.debug("Server-URL wird gelesen...");
        return ConfigHandler.getInstance().getConfig("network", "serverURL");
    }

    public int getPort() {
        ConfigHandler.getInstance().logger.debug("Server-Port wird gelesen...");
        return Integer.parseInt(ConfigHandler.getInstance().getConfig("network", "serverPort"));
    }

}
