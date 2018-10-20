package com.mandelag.manggis;

/**
 * Generalized version of InputStreamExtractor and ReaderExtractor.
 *
 * @author Keenan Mandela Gebze
 * @version 10 October 2018
 */
public class Extractor<T extends Readable> {

    private final Readable readable;
    private final Marker[] markers;

    public Extractor(Readable readable, Marker[] markers) {
        if (markers == null || markers.length <= 0) {
            throw new IllegalArgumentException("markers should not be empty.");
        }
        this.readable = readable;
        this.markers = markers;
    }

    public void extract() {
        int read;
        while ((read = readable.read() ) >= 0) {
            for (int x=0; x < markers.length; x++ ) {
                markers[x].listen(read);
            }
        }
    }
}
