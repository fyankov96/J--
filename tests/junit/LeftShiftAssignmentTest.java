package junit;

import junit.framework.TestCase;
import pass.LeftShiftAssignment;

public class LeftShiftAssignmentTest extends TestCase {
    private LeftShiftAssignment leftShiftAssignment;

    protected void setUp() throws Exception {
        super.setUp();
        leftShiftAssignment = new LeftShiftAssignment();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testleftShiftAssignment() {
        this.assertEquals(leftShiftAssignment.leftShiftAssign(2, 4), 32);
        this.assertEquals(leftShiftAssignment.leftShiftAssign(-1, 1), -2);
        this.assertEquals(leftShiftAssignment.leftShiftAssign(-1, 2), -4);
        this.assertEquals(leftShiftAssignment.leftShiftAssign(-5, -2), -1073741824);
        this.assertEquals(leftShiftAssignment.leftShiftAssign(5, 2), 20);
        this.assertEquals(leftShiftAssignment.leftShiftAssign(5, 4), 80);
        this.assertEquals(leftShiftAssignment.leftShiftAssign(2315, 4), 37040);
        this.assertEquals(leftShiftAssignment.leftShiftAssign(5, -2), 1073741824);
    }
    
}
