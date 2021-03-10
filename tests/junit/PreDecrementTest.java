package junit;

import junit.framework.TestCase;
import pass.PreDecrement;

public class PreDecrementTest extends TestCase {
    private PreDecrement preDecrement;

    protected void setUp() throws Exception {
        super.setUp();
        preDecrement= new PreDecrement();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testDivide() {
        this.assertEquals(preDecrement.preDecrement(6),5);
        this.assertEquals(preDecrement.preDecrement(0),-1);
    }    
}
