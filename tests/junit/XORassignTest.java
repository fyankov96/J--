package junit;

import junit.framework.TestCase;
import pass.XORassign;

public class XORassignTest extends TestCase {
    private XORassign xORassign;

    protected void setUp() throws Exception {
        super.setUp();
        xORassign = new XORassign();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testxORassign() {
        this.assertEquals(xORassign.assign(1, 3), 2);
        this.assertEquals(xORassign.assign(2, 2), 0);
        this.assertEquals(xORassign.assign(-5, 5), -2);
        this.assertEquals(xORassign.assign(4, -7), -3);
        this.assertEquals(xORassign.assign(-2, -4), 2);
        this.assertEquals(xORassign.assign(-2, -2), 0);
    }
}
