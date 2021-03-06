package com.mandelag.manggis;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * Extractor tests.
 *
 * @author Keenan Mandela Gebze
 * @version 20 October 2018
 */
public class InputStreamExtractor {
    
    private InputStream inputStream;
    private InputStreamMarker[] markers = new InputStreamMarker[0];
    
    public InputStreamExtractor(InputStream inputStream, InputStreamMarker[] markers) {
        this.inputStream = inputStream;
        if (markers != null) {
            this.markers = markers;
        }
    }
    
    public void extract() throws IOException {
        int read;
        while ((read = inputStream.read() ) >= 0) {
            for (int x=0; x < markers.length; x++ ) {
                markers[x].listen(read);
            }
        }
    }
}
