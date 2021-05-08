package junit;

import junit.framework.TestCase;
import pass.Comparisons;

public class ComparisonTest extends TestCase {
    private Comparisons c;
    protected void setUp() throws Exception {
        super.setUp();
        c = new Comparisons();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testComparisonDoubleTrue() {
        this.assertTrue(c.lt(99.1, 99.9));
        this.assertTrue(c.gt(99.9, 99.1));
        this.assertTrue(c.le(99.1, 99.9));
        this.assertTrue(c.ge(99.9, 99.1));
        this.assertTrue(c.le(99.9, 99.9));
        this.assertTrue(c.ge(99.9, 99.9));
        this.assertTrue(c.eq(99.9, 99.9));
        this.assertTrue(c.neq(99.9, 999.1));
    }

    public void testComparisonDoubleFalse() {
        this.assertFalse(c.gt(99.1, 99.9));
        this.assertFalse(c.lt(99.9, 99.1));
        this.assertFalse(c.ge(99.1, 99.9));
        this.assertFalse(c.le(99.9, 99.1));
        this.assertFalse(c.gt(99.9, 99.9));
        this.assertFalse(c.lt(99.9, 99.9));
        this.assertFalse(c.neq(99.9, 99.1));
        this.assertFalse(c.eq(99.9, 99.9));
    }    



    public void testComparisonIntTrue() {
        this.assertTrue(c.lt((int) 0, (int) 9));
        this.assertTrue(c.gt((int) 9, (int) 0));
        this.assertTrue(c.le((int) 0, (int) 9));
        this.assertTrue(c.ge((int) 9, (int) 1));
        this.assertTrue(c.le((int) 9, (int) 9));
        this.assertTrue(c.ge((int) 9, (int) 9));
        this.assertTrue(c.eq((int) 9, (int) 9));
        this.assertTrue(c.neq((int) 9, (int) 9));
    }    

    public void testComparisonIntFalse() {
        this.assertFalse(c.gt((int) 0, (int) 9));
        this.assertFalse(c.lt((int) 9, (int) 0));
        this.assertFalse(c.ge((int) 0, (int) 9));
        this.assertFalse(c.le((int) 9, (int) 1));
        this.assertFalse(c.gt((int) 9, (int) 9));
        this.assertFalse(c.lt((int) 9, (int) 9));
        this.assertFalse(c.neq((int) 9, (int) 9));
        this.assertFalse(c.eq((int) 9, (int) 1));
    }    
}
