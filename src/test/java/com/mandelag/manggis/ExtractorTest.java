package com.mandelag.manggis;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Keenan Gebze (@mandelag)
 */
public class ExtractorTest {

    @Test
    public void extractTextReaderTest() {
        List<String> extracted = new LinkedList<>();
        ReaderMarker namaExtract = new ReaderMarker("NPSN : ","</li>", str -> extracted.add(str));
        try (Reader r = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("060000.SD"), "UTF8"))) {
            ReaderExtractor extractor = new ReaderExtractor(r, new ReaderMarker[]{namaExtract});
            extractor.extract();
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(3476, extracted.size());
    }

    @Test
    public void extractReaderTest() throws UnsupportedEncodingException {
        List<String> extracted = new LinkedList<>();
        Marker namaExtract = new Marker("NPSN : ".getBytes("UTF-8"),"</li>".getBytes("UTF-8"), bytes -> extracted.add(new String(bytes, Charset.forName("UTF-8"))));
        List<Marker> markers = new ArrayList<>();
        markers.add(namaExtract);
        try (InputStream inputStream = new BufferedInputStream(this.getClass().getClassLoader().getResourceAsStream("060000.SD"))) {
            Extractor extractor = new Extractor(inputStream, markers);
            extractor.extract();
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(3476, extracted.size());
    }

    @Test
    public void invalidExtractorConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Extractor(new ByteArrayInputStream(new byte[256]), null));
        assertThrows(IllegalArgumentException.class, () -> new Extractor(new ByteArrayInputStream(new byte[256]), new ArrayList<Marker>(10)));
    }

}
