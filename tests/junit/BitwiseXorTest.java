package junit;

import junit.framework.TestCase;
import pass.BitwiseXor;

public class BitwiseXorTest extends TestCase {
    private BitwiseXor bitwiseXor;

    protected void setUp() throws Exception {
        super.setUp();
        bitwiseXor = new BitwiseXor();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testDivide() {
        this.assertEquals(bitwiseXor.bitwiseXor(5, 7),2);
        this.assertEquals(bitwiseXor.bitwiseXor(60, 13),49);
        this.assertEquals(bitwiseXor.bitwiseXor(9, 8), 1);
    }
    
}
