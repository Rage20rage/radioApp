package de.marko.radio.client.main;

import de.marko.radio.client.gui.WindowManager;
import de.marko.radio.client.listener.KeyListener;
import de.marko.radio.client.network.Network;
import okhttp3.OkHttpClient;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class Main {

    public static Logger logger = Logger.getRootLogger();

    public static void main(String[] args) {
        logger.setLevel(Level.INFO);
        for (int i = 0; i < args.length; i++) {
            if(args[i].equalsIgnoreCase("debug")) {
                logger.setLevel(Level.ALL);
                logger.info("Debug wurde aktiviert!");
            }
        }
        Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FATAL);
        Inizialiser.inizialise();
        Network.getInstance().initStreams();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                Network.getInstance().networkHandler.disconnect();
            }
        });
        for (int i = 0; i < Network.getInstance().stationNames.size(); i++) {
            System.out.println(Network.getInstance().stationNames.get(i) + " : " + Network.getInstance().stationURLs.get(i));
        }
        WindowManager.getInstance().startMainWindow();
        java.util.logging.Logger nativeLogger = java.util.logging.Logger.getLogger(GlobalScreen.class.getPackage().getName());
        nativeLogger.setLevel(java.util.logging.Level.OFF);
        try {
            Thread.sleep(500);
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            logger.error(e.getMessage());
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        GlobalScreen.addNativeKeyListener(new KeyListener());
    }

}
