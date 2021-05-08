package junit;

import junit.framework.TestCase;
import pass.Division;

public class DivisionTest extends TestCase {
    private Division division;

    protected void setUp() throws Exception {
        super.setUp();
        division = new Division();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testDivide() {
        this.assertEquals(0, division.divide(0, 42));
        this.assertEquals(42, division.divide(42, 1));
        this.assertEquals(42, division.divide(127, 3));
        this.assertEquals(2, division.divide(5, 2));
        this.assertEquals(5, division.divide(-5, -1));
    }    
}
