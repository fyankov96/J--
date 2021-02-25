package junit;

import junit.framework.TestCase;
import pass.RightShift;

public class RightShiftTest extends TestCase {
    private RightShift shr;

    protected void setUp() throws Exception {
        super.setUp();
        shr = new RightShift();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSHR() {
        this.assertEquals(shr.rightShift(2, 4), 0);
        this.assertEquals(shr.rightShift(-1, 1), -1);
        this.assertEquals(shr.rightShift(-1, 2), -1);
        this.assertEquals(shr.rightShift(-5, -2), -1);
        this.assertEquals(shr.rightShift(5, 2), 1);
        this.assertEquals(shr.rightShift(5, 4), 0);
        this.assertEquals(shr.rightShift(2315, 4), 144);
        this.assertEquals(shr.rightShift(5, -2), 0);

    }
    
}
