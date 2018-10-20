package com.mandelag.manggis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Generalized version of InputStreamExtractor and ReaderExtractor.
 *
 * @author Keenan Mandela Gebze
 * @version 10 October 2018
 */
public class Extractor {

    private final InputStream inputStream;
    private final List<Marker> markers;

    public Extractor(InputStream inputStream, List<Marker> markers) {
        if (markers == null || markers.isEmpty()) {
            throw new IllegalArgumentException("markers should not be empty.");
        }
        this.inputStream = inputStream;
        this.markers = markers;
    }

    public void extract() throws IOException {
        int read;
        while ((read = inputStream.read() ) >= 0) {
            int tmp = read;
            markers.forEach((marker) -> {
                marker.listen(tmp);
            });
        }
    }
}
