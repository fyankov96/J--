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
        this.assertEquals(orass.orAssign(3, 10), 11);
        this.assertEquals(orass.orAssign(3, 3), 3);
        this.assertEquals(orass.orAssign(31, 32), 63);
        this.assertEquals(orass.orAssign(29, 33), 61);
    }    
}