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
        this.assertEquals(multiplicationAssignment.multiplyAssign(5.5, 10), 55);
        this.assertEquals(multiplicationAssignment.multiplyAssign(20, 3.3), 66);
        this.assertEquals(multiplicationAssignment.multiplyAssign(2.2, 3.3), 6.6);
    }

}
