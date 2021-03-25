package junit;

import junit.framework.TestCase;
import pass.OrAssign;

public class OrAssignTest extends TestCase {
    private OrAssign orass;

    protected void setUp() throws Exception {
        super.setUp();
        orass = new OrAssign();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testOrAssign() {
        this.assertEquals(orass.orAssign(true), true);
        this.assertEquals(orass.orAssign(false), false);
    }    
}