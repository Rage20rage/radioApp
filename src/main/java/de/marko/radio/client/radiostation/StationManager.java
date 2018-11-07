package de.marko.radio.client.radiostation;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class StationManager {

    private Logger logger = Logger.getRootLogger();

    private static StationManager stations;

    public static StationManager getInstance() {
        if(stations == null) {
            stations = new StationManager();
        }
        return stations;
    }

    private List<Station> stationList = new ArrayList<>();

    public List<Station> getStationList() {
        return stationList;
    }

    public void addStation(Station station) {
        logger.debug("Füge den Radiosender " + station.getName() + "zur Temporären Senderliste hinzu...");
        this.stationList.add(station);
        logger.debug("Radiosenden wurde hinzugefügt!");
    }
}
