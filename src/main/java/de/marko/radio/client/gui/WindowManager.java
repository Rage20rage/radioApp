package de.marko.radio.client.gui;

public class WindowManager {

    private static WindowManager windowManager;
    public static WindowManager getInstance() {
        if(windowManager == null) {
            windowManager = new WindowManager();
        }
        return windowManager;
    }

    public void startMainWindow() {
        new MainWindow();

    }

}