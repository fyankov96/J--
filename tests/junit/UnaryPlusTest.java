package junit;

import pass.UnaryPlus;
import junit.framework.TestCase;
import pass.Division;

public class UnaryPlusTest extends TestCase {
    private UnaryPlus up;

    protected void setUp() throws Exception {
        super.setUp();
        up = new UnaryPlus();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testUnaryPlus() {
        this.assertEquals(up.unaryPlus(5), 5);
        this.assertEquals(up.unaryPlus(-6), -6);
        this.assertEquals(up.unaryPlus(0), 0);
    }
    
}
