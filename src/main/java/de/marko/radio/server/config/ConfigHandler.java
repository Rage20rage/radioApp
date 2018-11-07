package de.marko.radio.server.config;

import de.marko.radio.server.main.Main;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;

public class ConfigHandler {

    Logger logger = Main.logger;

    private static ConfigHandler instance = new ConfigHandler();

    public static ConfigHandler getInstance() {
        return instance;
    }

    private File file;

    public void createConfigFile(String name) {
        logger.info("Erstelle Datei " + name + ".config, falls diese nicht Vorhanden ist...");
        file = new File("RadioServer/config/" + name + ".config");
        logger.info("Datei existiert nun!");
    }

    public void setConfig(String filename, String key, String value) {
        try {
            ArrayList<String> oldData = new ArrayList<>();
            logger.info("Überprüfe ob Config Datei vorhanden ist...");
            File file = new File("RadioServer/config/" + filename + ".config");
            if(file.exists()) {
                logger.info("Datei vorhanden!");
                logger.info("Lese aktuelle Zeilen der Datei...");
                logger.debug("Inizialisiere Config reader...");
                FileReader fileReader = new FileReader("RadioServer/config/" + filename + ".config");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                logger.info("Config " + key + " wird gelesen...");
                boolean continu = true;
                while (continu) {
                    String tmp = bufferedReader.readLine();
                    if (tmp == null || tmp.equals("")) {
                        continu = false;
                    } else {
                        oldData.add(tmp);
                    }
                }
                logger.info("Zeilen gelesen!");
            } else {
                logger.info("Datei nicht vorhanden!");
            }
            logger.info("Zeilen gelesen!");
            logger.debug("Inizialisiere Config writer...");
            FileWriter fileWriter = fileWriter = new FileWriter("RadioServer/config/" + filename + ".config");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            logger.debug("Daten werden geschrieben...");
            if(!oldData.isEmpty()) {
                for (String tmp :  oldData) {
                    bufferedWriter.write(tmp);
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.write(key + "=" + value);
            bufferedWriter.newLine();
            logger.debug("Daten wurden geschrieben!");
            logger.info("Config " + key + " wird gespeichtert...");
            bufferedWriter.flush();
            logger.info("Config wurde gespeichert!");
            logger.debug("Config writer wird geschlossen...");
            bufferedWriter.close();
            fileWriter.close();
            logger.debug("Config writer wurde geschlossen!");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public String getConfig(String filename, String key) {
        try {
            logger.debug("Inizialisiere Config reader...");
            FileReader fileReader = new FileReader("RadioServer/config/" + filename + ".config");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            logger.info("Config " + key + " wird gelesen...");
            boolean lastline = false;
            for(long i = 0; !lastline ; i++) {
                logger.debug("Suche richtige Zeile...");
                String line = bufferedReader.readLine();
                if(line == null) {
                    lastline = true;
                } else {
                    if(line.startsWith(key)) {
                        logger.debug("Richtige Zeile wurde gefunden!");
                        logger.debug("Trenne Value vom Key...");
                        boolean startRecordText = false;
                        for (char part : line.toCharArray()) {
                            if(startRecordText) {
                                stringBuilder.append(part);
                            }
                            if(part == 61) {
                                startRecordText = true;
                            }
                        }
                        logger.info("Config wurde gelesen!");
                        return stringBuilder.toString();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.fatal("Keinen Config eintrag zum Key " + key + " gefunden!");
        return null;
    }

}
