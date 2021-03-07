package junit;

import junit.framework.TestCase;
import pass.PostIncrement;

public class PostIncrementTest extends TestCase {
    private PostIncrement postIncrement;

    protected void setUp() throws Exception {
        super.setUp();
        postIncrement= new PostIncrement();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testDivide() {
        this.assertEquals(postIncrement.postIncrement(5),5);
        this.assertEquals(postIncrement.postIncrement(0),0);
    }    
}
