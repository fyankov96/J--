package junit;

import junit.framework.TestCase;
import pass.BitwiseOr;

public class BitwiseOrTest extends TestCase {
    private BitwiseOr bitwiseOr;

    protected void setUp() throws Exception {
        super.setUp();
        bitwiseOr = new BitwiseOr();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testBitwiseOr() {
        this.assertEquals(bitwiseOr.bitwiseOr(5, 7),7);
        this.assertEquals(bitwiseOr.bitwiseOr(60, 13),61);
        this.assertEquals(bitwiseOr.bitwiseOr(9, 8), 9);
    }
    
}
