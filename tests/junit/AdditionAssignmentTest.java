package junit;

import junit.framework.TestCase;
import pass.AdditionAssignment;

public class AdditionAssignmentTest extends TestCase {
    private AdditionAssignment additionAssignment;

    protected void setUp() throws Exception {
        super.setUp();
        additionAssignment = new AdditionAssignment();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testadditionAssignment() {
        this.assertEquals(additionAssignment.addAssign(0, 42), 42);
        this.assertEquals(additionAssignment.addAssign(42, 1), 43);
        this.assertEquals(additionAssignment.addAssign(127, 3), 130);
    }
    
}
