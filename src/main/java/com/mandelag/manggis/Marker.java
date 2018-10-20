package com.mandelag.manggis;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generalized version of InputStreamExtractor and ReaderExtractor.
 *
 * @author Keenan Mandela Gebze
 * @version 10 October 2018
 */
public class Marker {

    private final byte[] startBytes;
    private final byte[] stopBytes;
    private final Consumer<byte[]> callback;
    private int cursor = 0;
    private boolean extractMode = false;
    private ByteArrayOutputStream buffer = new ByteArrayOutputStream(1024);

    public Marker(byte[] start, byte[] stop, Consumer<byte[]> callback) {
        this.startBytes = start;
        this.stopBytes = stop;
        this.callback = callback;
    }

    public void listen(int a) {
        if(!extractMode) {
            if (a == startBytes[cursor]) {
                cursor++;
                if (cursor == startBytes.length) {
                    extractMode = true;
                    cursor = 0;
                }
            } else {
                cursor = 0;
            }
        } else {
            buffer.write(a);
            if (a == stopBytes[cursor]) {
                cursor++;
                if (cursor == stopBytes.length) {
                    extractMode = false;
                    cursor = 0;
                    try {
                        buffer.flush();
                        callback.accept(buffer.toByteArray());
                    }catch(IOException e){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ""+e);
                    }
                    buffer.reset();
                }
            }else {
                cursor = 0;
            }
        }
    }
}
