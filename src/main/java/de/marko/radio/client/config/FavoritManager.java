package de.marko.radio.client.config;

import de.marko.radio.client.main.Main;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;

public class FavoritManager {

    Logger logger = Main.logger;

    private static FavoritManager favoritManager = new FavoritManager();

    public static FavoritManager getInstance() {
        if(favoritManager == null) {
            favoritManager = new FavoritManager();
            favoritManager.createDir();
        }
        return favoritManager;
    }

    File file = new File("RadioAPP/data/favorits.data");

    private void createDir() {
        File dataDir = new File("RadioAPP/data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    public boolean isAFavorit(String stationName) {
        logger.info("Frage ab ob " + stationName + " ein Favorit ist...");
        if(getAllFavorits().contains(stationName)) {
            logger.info(stationName + " ist ein Favorit!");
            return true;
        }
        logger.info(stationName + " ist kein Favorit!");
        return false;
    }

    public void putInFavorits(String stationName) {
        logger.info("Füge " + stationName + " zu den Favoriten hinzu...");
        try {
            ArrayList<String> favorits = getAllFavorits();
            logger.debug("Inizialisiere den Writer...");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            logger.debug("Beginne die Favoriten in den Writer zu schreiben...");
            for (String favorit : favorits) {
                bufferedWriter.write(favorit);
                bufferedWriter.newLine();
            }
            bufferedWriter.write(stationName);
            bufferedWriter.newLine();
            logger.debug("Schreibe aus dem Writer in die Datei...");
            bufferedWriter.flush();
            logger.debug("Datei geschrieben!");
            logger.debug("Schließe den Writer...");
            bufferedWriter.close();
            logger.debug("Writer geschlossen!");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.info(stationName + " wurde den Favoriten hinzugefügt!");
    }

    public ArrayList<String> getAllFavorits() {
        logger.info("Hole alle Favoriten...");
        ArrayList<String> favorits = new ArrayList<>();
        try {
            logger.debug("Inizialisiere den Reader...");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            boolean continu = true;
            int count = 0;
            logger.debug("Hole Favoriten von der Datei...");
            while (continu) {
                String data = bufferedReader.readLine();
                if(data == null || data.equals("")) {
                    continu = false;
                } else {
                    favorits.add(data);
                    count++;
                }
            }
            logger.debug("Es wurden " + count + " Favoriten gelesen!");
            logger.debug("Schließe den Reader...");
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.info("Alle Favoriten wurden geholt!");
        return favorits;
    }

    public void removeFromFavorit(String stationName) {
        logger.info("Entferne " + stationName + " von den Favoriten...");
        try {
            ArrayList<String> favorits = getAllFavorits();
            logger.debug("Inizialisiere den Writer...");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            logger.debug("Schreibe alle Favoriten in den Writer, sofern es sich nicht um " + stationName + " handelt...");
            for (String favorit : favorits) {
                if(!favorit.equals(stationName)) {
                    bufferedWriter.write(favorit);
                    bufferedWriter.newLine();
                }
            }
            logger.debug("Schreibe Daten vom Writer in die Datei...");
            bufferedWriter.flush();
            logger.debug("Schließe den Writer...");
            bufferedWriter.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.info(stationName + " wurde von den Favoriten entfernt!");
    }

}
