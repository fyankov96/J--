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
        this.assertEquals(andAss.andAssign(true), true);
        this.assertEquals(andAss.andAssign(false), false);
    }    
}