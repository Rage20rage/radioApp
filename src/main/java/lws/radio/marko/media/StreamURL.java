package lws.radio.marko.media;

import java.util.HashMap;

public class StreamURL {

    private HashMap<Integer,RadioSender> radioSenderHashMap = new HashMap<>();
    private int radioSenderPointer;

    private static StreamURL streamURL;

    public static StreamURL getInstance() {
        if(streamURL == null) {
            streamURL = new StreamURL();
            streamURL.radioSenderHashMap.put(0,RadioSender.ILOVERADIO);
            streamURL.radioSenderHashMap.put(1,RadioSender.ILOVECHARTS);
            streamURL.radioSenderHashMap.put(2,RadioSender.ILOVE2DANCE);
            streamURL.radioSenderHashMap.put(3,RadioSender.ILOVETHEBATTLE);
            streamURL.radioSenderHashMap.put(4,RadioSender.ILOVEDREIST);
        }
        return streamURL;
    }

    public String getRadioURL(RadioSender sender) {
        String url = null;
        switch (sender) {
            case ILOVERADIO:url="http://stream01.iloveradio.de/iloveradio1.mp3"; radioSenderPointer = 0; break;
            case ILOVECHARTS:url="http://stream01.iloveradio.de/iloveradio9.mp3"; radioSenderPointer = 1; break;
            case ILOVE2DANCE:url="http://stream01.iloveradio.de/iloveradio2.mp3"; radioSenderPointer = 2; break;
            case ILOVETHEBATTLE:url="http://stream01.iloveradio.de/iloveradio3.mp3"; radioSenderPointer = 3; break;
            case ILOVEDREIST:url="http://stream01.iloveradio.de/iloveradio6.mp3"; radioSenderPointer = 4; break;
        }
        return url;
    }

    public String getRadioURL(Integer sender) {
        String url = null;
        switch (sender) {
            case 0:url="http://stream01.iloveradio.de/iloveradio1.mp3"; radioSenderPointer = 0; break;
            case 1:url="http://stream01.iloveradio.de/iloveradio9.mp3"; radioSenderPointer = 1; break;
            case 2:url="http://stream01.iloveradio.de/iloveradio2.mp3"; radioSenderPointer = 2; break;
            case 3:url="http://stream01.iloveradio.de/iloveradio3.mp3"; radioSenderPointer = 3; break;
            case 4:url="http://stream01.iloveradio.de/iloveradio6.mp3"; radioSenderPointer = 4; break;
        }
        return url;
    }

    public String getNextSender() {
        int newSenderNumber = radioSenderPointer + 1;
        if(newSenderNumber > radioSenderHashMap.size()) {
            newSenderNumber = 0;
        }
        return getRadioURL(newSenderNumber);
    }

    public String getPreviousSender() {
        int newSenderNumber = radioSenderPointer - 1;
        if(newSenderNumber < 0) {
            newSenderNumber = radioSenderHashMap.size();
        }
        return getRadioURL(newSenderNumber);
    }

}
