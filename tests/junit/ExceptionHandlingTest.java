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

    public void testTryCatch() {
        boolean x = false;
        try{
            eh.tryCatch();
        }
        catch(IndexOutOfBoundsException e) {
            x = true;
        }
        this.assertTrue(x);
    }
    
    public void testExceptions() {
        System.out.println("Running Exception Tests...");
        
        this.assertEquals(this.eh.divideByZero(), "Caught");
        this.assertEquals(this.eh.illegalAccess(), "Caught");
        this.assertEquals(this.eh.illegalArgument("Illegal"), "Caught");
        this.assertEquals(this.eh.illegalArgument("Legal"), "Not Caught");
    }
}
