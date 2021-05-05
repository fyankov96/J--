package junit;

import junit.framework.TestCase;
import pass.AndAssign;

public class AndAssignTest extends TestCase {
    private AndAssign andAss;

    protected void setUp() throws Exception {
        super.setUp();
        andAss = new AndAssign();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testOrAssign() {
        this.assertEquals(andAss.andAssign(40, 1), 0);
        this.assertEquals(andAss.andAssign(32, 31), 0);
        this.assertEquals(andAss.andAssign(32, 48), 32);
    }    
}