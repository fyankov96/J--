// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package junit;

import junit.framework.TestCase;
import pass.StaticAndMemberInitialization;

public class InitBlocksTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testInitBlocks() {
        StaticAndMemberInitialization c = new StaticAndMemberInitialization();

        this.assertEquals(c.a, 3);
        this.assertEquals(c.b, 3);
    }

}




