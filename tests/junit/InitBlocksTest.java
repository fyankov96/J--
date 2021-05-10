// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package junit;

import junit.framework.TestCase;
import pass.InitBlocks;

public class InitBlocksTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testInitBlocks() {
        InitBlocks ib = new InitBlocks();

        this.assertEquals(ib.a, 3);
        this.assertEquals(ib.b, 3);
    }

}




