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
        this.assertEquals(0, divisionAssignment.divideAssign(0, 42));
        this.assertEquals(42, divisionAssignment.divideAssign(42, 1));
        this.assertEquals(42, divisionAssignment.divideAssign(127, 3), 42);
        this.assertEquals(22.2, divisionAssignment.divideAssignDouble(44.4, 2));
        this.assertEquals(4.0, divisionAssignment.divideAssignDouble(10.0, 2.5));

    }

}
