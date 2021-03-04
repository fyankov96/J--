package junit;

import junit.framework.TestCase;
import pass.BitwiseNot;

public class BitwiseNotTest extends TestCase {
    private BitwiseNot bitwiseNot;

    protected void setUp() throws Exception {
        super.setUp();
        bitwiseNot = new BitwiseNot();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testBitwiseNot() {
        this.assertEquals(bitwiseNot.bitwiseNot(2),-3);
        this.assertEquals(bitwiseNot.bitwiseNot(5),-6);
        this.assertEquals(bitwiseNot.bitwiseNot(60), -61);
    }
    
}
