package junit;

import junit.framework.TestCase;
import pass.DivisionAssignment;

public class DivisionAssignmentTest extends TestCase {
    private DivisionAssignment divisionAssignment;

    protected void setUp() throws Exception {
        super.setUp();
        divisionAssignment = new DivisionAssignment();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testdivisionAssignment() {
        this.assertEquals(divisionAssignment.divideAssign(0, 42), 0);
        this.assertEquals(divisionAssignment.divideAssign(42, 1), 42);
        this.assertEquals(divisionAssignment.divideAssign(127, 3), 42);
    }
    
}
