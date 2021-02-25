package junit;

import junit.framework.TestCase;
import pass.UnsignedRightShiftAssignment;

public class UnsignedRightShiftAssignmentTest extends TestCase {
    private UnsignedRightShiftAssignment unsignedRightShiftAssignment;

    protected void setUp() throws Exception {
        super.setUp();
        unsignedRightShiftAssignment = new UnsignedRightShiftAssignment();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testunsignedRightShiftAssignment() {
        this.assertEquals(unsignedRightShiftAssignment.unsignedRightShiftAssign(2, 4), 0);
        this.assertEquals(unsignedRightShiftAssignment.unsignedRightShiftAssign(-1, 1), 2147483647);
        this.assertEquals(unsignedRightShiftAssignment.unsignedRightShiftAssign(-5, -2), 3);
        this.assertEquals(unsignedRightShiftAssignment.unsignedRightShiftAssign(5, 2), 1);
        this.assertEquals(unsignedRightShiftAssignment.unsignedRightShiftAssign(5, -2), 0);
    }
}
