package junit;

import junit.framework.TestCase;
import pass.NotEquals;

public class NotEqualsTest extends TestCase {
    private NotEquals notEquals;

    protected void setUp() throws Exception {
        super.setUp();
        notEquals = new NotEquals();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testNotEquals() {
        this.assertEquals(notEquals.notEquals(42, 42), false);
        this.assertEquals(notEquals.notEquals(42, 1), true);
    }
}
