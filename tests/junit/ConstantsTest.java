// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package junit;

import junit.framework.TestCase;
import pass.Constants;

public class ConstantsTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testConstants() {
        Constants c = new Constants();

        this.assertEquals(c.arrayLiteral(), 2);
        this.assertEquals(c.y, 3);
        this.assertEquals(c.str, "string");
        this.assertEquals(c.method(), 6);
    }

}
