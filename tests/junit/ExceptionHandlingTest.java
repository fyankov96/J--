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
        this.assertEquals(eh.tryCatch(), 0);

    }
    
    public void testExceptions() {
        System.out.println("Running Exception Tests...");
        this.assertEquals("Division by Zero should be triggered and caught", eh.divideByZero(), "Caught");
        this.assertEquals("Illegal Array access should be caught", eh.illegalAccess(), "Caught");
        this.assertEquals("Illegal Argument should not be thrown", eh.IllegalArgument("Illegal"), "Caught");
        this.assertEquals("Illegal Argument should be thrown", eh.IllegalArgument("Legal"), "Caught");
    }
}
