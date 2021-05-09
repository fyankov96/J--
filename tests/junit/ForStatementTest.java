// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package junit;

import junit.framework.TestCase;
import pass.ForStatement;
import pass.ForEachStatement;

public class ForStatementTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testLoops() {
        ForStatement f = new ForStatement();
        ForEachStatement ff = new ForEachStatement();

        this.assertEquals(ff.sum(), 9);
        this.assertEquals(f.compute(), 22);
        this.assertEquals(ff.compute(), 5);
    }

}
