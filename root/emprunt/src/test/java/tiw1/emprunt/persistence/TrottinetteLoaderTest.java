package tiw1.emprunt.persistence;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class TrottinetteLoaderTest {

    @Test
    public void testTrottinetteListCreation() {
        try {
            TrottinetteLoader.load();
            Map ts = TrottinetteLoader.getTrottinettes();
            System.out.println(ts.size() + " trottinettes trouvées");
            assertNotNull(ts);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail(ex.getMessage());
        }
    }
}