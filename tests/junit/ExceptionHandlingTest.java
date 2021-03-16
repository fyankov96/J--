package junit;

import junit.framework.TestCase;
import pass.ExceptionHandling;

public class ExceptionHandlingTest extends TestCase {
    private ExceptionHandling eh;

    protected void setUp() throws Exception {
        super.setUp();
        eh = new ExceptionHandling();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testDivide() {
        this.assertEquals(eh.TryCatch(), 0);
    }    
}
