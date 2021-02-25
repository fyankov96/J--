package junit;

import junit.framework.TestCase;
import pass.LeftShift;

public class LeftShiftTest extends TestCase {
    private LeftShift shl;

    protected void setUp() throws Exception {
        super.setUp();
        shl = new LeftShift();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSHL() {
        this.assertEquals(shl.leftShift(2, 4), 32);
        this.assertEquals(shl.leftShift(-1, 1), -2);
        this.assertEquals(shl.leftShift(-1, 2), -4);
        this.assertEquals(shl.leftShift(-5, -2), -1073741824);
        this.assertEquals(shl.leftShift(5, 2), 20);
        this.assertEquals(shl.leftShift(5, 4), 80);
        this.assertEquals(shl.leftShift(2315, 4), 37040);
        this.assertEquals(shl.leftShift(5, -2), 1073741824);

    }
    
}
