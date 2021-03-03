package junit;

import junit.framework.TestCase;
import pass.RightShiftAssignment;

public class RightShiftAssignmentTest extends TestCase {
    private RightShiftAssignment rightShiftAssignment;

    protected void setUp() throws Exception {
        super.setUp();
        rightShiftAssignment = new RightShiftAssignment();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testRightShiftAssignment() {
        this.assertEquals(rightShiftAssignment.rightShiftAssign(2, 4), 0);
        this.assertEquals(rightShiftAssignment.rightShiftAssign(-1, 1), -1);
        this.assertEquals(rightShiftAssignment.rightShiftAssign(-1, 2), -1);
        this.assertEquals(rightShiftAssignment.rightShiftAssign(-5, -2), -1);
        this.assertEquals(rightShiftAssignment.rightShiftAssign(5, 2), 1);
        this.assertEquals(rightShiftAssignment.rightShiftAssign(5, 4), 0);
        this.assertEquals(rightShiftAssignment.rightShiftAssign(2315, 4), 144);
        this.assertEquals(rightShiftAssignment.rightShiftAssign(5, -2), 0);
    }
    
}
