package junit;

import junit.framework.TestCase;
import pass.SubtractionAssignment;

public class SubtractionAssignmentTest extends TestCase {
    private SubtractionAssignment subtractionAssignment;

    protected void setUp() throws Exception {
        super.setUp();
        subtractionAssignment = new SubtractionAssignment();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testsubtractionAssignment() {
        this.assertEquals(subtractionAssignment.subtractAssign(0, 42), -42);
        this.assertEquals(subtractionAssignment.subtractAssign(42, 1), 41);
        this.assertEquals(subtractionAssignment.subtractAssign(127, 3), 124);
        this.assertEquals(subtractionAssignment.subtractAssignDouble(5.5, 3), 2.5);
        this.assertEquals(subtractionAssignment.subtractAssignDouble(10, 4.5), 5.5);
        this.assertEquals(subtractionAssignment.subtractAssignDouble(5.5, 5.5), 0.0);

    }

}
