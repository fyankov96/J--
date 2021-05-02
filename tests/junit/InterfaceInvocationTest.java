package junit;

import junit.framework.TestCase;
import pass.InterfaceInvocationTest;


public class InterfaceInvocationTest extends TestCase{
    private Impl1 i1;
    private Impl2 i2;

    protected void setUp() throws Exception {
        super.setUp();
        i1 = new Impl1();
        i2 = new Impl2();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testInvocation() {
        this.assertEquals(i1.method1(), 1);
        this.assertEquals(i1.method2(1), ">1");
        this.assertEquals(i1.method3(), 3);
        this.assertEquals(i2.method1(), -1);
        this.assertEquals(i2.method2(1), "<1");
        this.assertEquals(i2.method3(), -3);
        this.assertEquals(i2.method1(), -11);
        this.assertEquals(i2.method2(1), "<1");
        this.assertEquals(i2.method3(), -3);
    }      
}
