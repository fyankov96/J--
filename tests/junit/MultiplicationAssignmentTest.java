package junit;

import junit.framework.TestCase;
import pass.MultiplicationAssignment;

public class MultiplicationAssignmentTest extends TestCase {
    private MultiplicationAssignment multiplicationAssignment;

    protected void setUp() throws Exception {
        super.setUp();
        multiplicationAssignment = new MultiplicationAssignment();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testdivisionAssignment() {
        this.assertEquals(multiplicationAssignment.multiplyAssign(0, 42), 0);
        this.assertEquals(multiplicationAssignment.multiplyAssign(42, 1), 42);
        this.assertEquals(multiplicationAssignment.multiplyAssign(127, 3), 381);
    }
    
}
