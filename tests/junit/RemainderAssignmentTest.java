package junit;

import junit.framework.TestCase;
import pass.RemainderAssignment;

public class RemainderAssignmentTest extends TestCase {
    private RemainderAssignment remainderAssignment;

    protected void setUp() throws Exception {
        super.setUp();
        remainderAssignment = new RemainderAssignment();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testremainderAssignment() {
        this.assertEquals(remainderAssignment.remainderAssign(0, 42), 0);
        this.assertEquals(remainderAssignment.remainderAssign(42, 20), 2);
        this.assertEquals(remainderAssignment.remainderAssign(127, 3), 1);
    }
}
