// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package junit;

import junit.framework.TestCase;
import pass.ClassDeclaration;

public class ClassDeclarationTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testClassDeclarations() {
        ClassDeclaration c1 = new ClassDeclaration();
        ClassDeclaration c2 = new ClassDeclaration(2);
        this.assertEquals(c1.a, 1);
        this.assertEquals(c1.getB(), 2);

        this.assertEquals(c2.a, 2);
        c2.setA(3);
        this.assertEquals(c2.a, 3);
    }

}




