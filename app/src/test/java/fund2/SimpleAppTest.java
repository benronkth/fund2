
package fund2;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class SimpleAppTest {

    // Successful tests

    @Test public void assertTrueTest() {
        SimpleApp simpleApp = new SimpleApp();
        assertTrue(simpleApp.getTrue());
    }

    @Test public void assertFalseTest() {
        SimpleApp simpleApp = new SimpleApp();
        assertFalse(simpleApp.getFalse());
    }

}
