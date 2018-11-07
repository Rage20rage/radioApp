package de.marko.radio.server.Actions;

import de.marko.radio.server.MySQL.MySQL;
import de.marko.radio.server.main.Main;
import de.marko.radio.server.network.Connection;
import de.marko.radio.server.network.NetworkHandler;

import java.util.ArrayList;

public class RadioAction {

     private static String prefix = "Radio-";

     public static void executeAction(Connection connection, String action) {
         if(action.equals(prefix + "getRadioStations")) {
                long stationCount = MySQL.getInstance().countRadioStations();
             ArrayList<String> stationNames = MySQL.getInstance().getAllRadioStationNames();
             ArrayList<String> stationURLs = MySQL.getInstance().getAllRadioStationURLs();
             NetworkHandler.getInstance().send(connection, String.valueOf(stationCount));
             for (int i = 0; i < stationCount; i++) {
                 NetworkHandler.getInstance().send(connection, stationNames.get(i));
                 try { Thread.sleep(100); } catch (Exception e) { Main.logger.error(e.getMessage());}
                 NetworkHandler.getInstance().send(connection, stationURLs.get(i));
                 try { Thread.sleep(100); } catch (Exception e) { Main.logger.error(e.getMessage());}
             }
         }
     }

}
