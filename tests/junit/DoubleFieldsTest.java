package junit;

import junit.framework.TestCase;
import pass.DoubleFields;

public class DoubleFieldsTest extends TestCase {
    private DoubleFields df;
    protected void setUp() throws Exception {
        super.setUp();
        df = new DoubleFields(1.0, 1.5);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testDivide() {
        this.assertEquals(1.0, df.getF1());
        this.assertEquals(1.5, df.getF2());
        this.assertEquals(2.5, df.getF3());
        df.sumF1(1.0);
        df.mulF2(2.0);
        this.assertEquals(2.0, df.getF1());
        this.assertEquals(3.0, df.getF2());
        this.assertEquals(5.0, df.getF3());
    }    
}
