package com.mandelag.manggis;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;
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
    public void extractTextTest() {
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

}
