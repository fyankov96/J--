package junit;

import junit.framework.TestCase;
import pass.Conditional;

public class ConditionalTest extends TestCase {
    private Conditional cond;

    protected void setUp() throws Exception {
        super.setUp();
        cond = new Conditional();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testDivide() {
        this.assertEquals(cond.cond(true), 1);
        this.assertEquals(cond.cond(false), 2);
    }    
}
