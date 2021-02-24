package junit;

import junit.framework.TestCase;
import pass.MultiLineComment;

public class MultiLineCommentTest extends TestCase {
    private MultiLineComment MLC;

    protected void setUp() throws Exception {
        super.setUp();
        MLC = new MultiLineComment();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testDivide() {
        this.assertEquals(MLC.ret(0, 42), 0);
        this.assertEquals(MLC.ret(42, 1), 42);
        this.assertEquals(MLC.ret(127, 3), 127);
    }    
}
