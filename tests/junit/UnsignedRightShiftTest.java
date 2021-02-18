package junit;

import junit.framework.TestCase;
import pass.UnsignedRightShift;

public class UnsignedRightShiftTest extends TestCase {
    private UnsignedRightShift ushr;

    protected void setUp() throws Exception {
        super.setUp();
        ushr = new UnsignedRightShift();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testUSHR() {
        this.assertEquals(ushr.unsignedRightShift(2, 4), 0);
        this.assertEquals(ushr.unsignedRightShift(-1, 1), 2147483647);
        this.assertEquals(ushr.unsignedRightShift(-5, -2), 3);
        this.assertEquals(ushr.unsignedRightShift(5, 2), 1);
        this.assertEquals(ushr.unsignedRightShift(5, -2), 0);

    }
    
}
