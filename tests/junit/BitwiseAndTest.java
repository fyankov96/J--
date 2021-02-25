package junit;

import junit.framework.TestCase;
import pass.BitwiseAnd;

public class BitwiseAndTest extends TestCase {
    private BitwiseAnd bitwiseAnd;

    protected void setUp() throws Exception {
        super.setUp();
        bitwiseAnd = new BitwiseAnd();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testBitwiseAnd() {
        this.assertEquals(bitwiseAnd.bitwiseAnd(5, 7),5);
        this.assertEquals(bitwiseAnd.bitwiseAnd(60, 13),12);
        this.assertEquals(bitwiseAnd.bitwiseAnd(9, 8), 8);
    }
    
}
