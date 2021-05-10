package junit;

import junit.framework.TestCase;
import pass.InterfaceTestImpl1;
import pass.InterfaceTestImpl2;



public class InterfaceInvocationTest extends TestCase{
    private InterfaceTestImpl1 i1;
    private InterfaceTestImpl2 i2;

    protected void setUp() throws Exception {
        super.setUp();
        i1 = new InterfaceTestImpl1();
        i2 = new InterfaceTestImpl2();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testInvocation() {
        this.assertEquals(i1.method1(), 1);
        System.out.println(i1.method2(1));
        this.assertEquals(i1.method2(1), ">1");
        this.assertEquals(i1.method3(), 3);
        this.assertEquals(i2.method1(), -1);
        this.assertEquals(i2.method2(1), "<1");
        this.assertEquals(i2.method3(), -3);
        this.assertEquals(i2.method1(), -1);
        this.assertEquals(i2.method2(1), "<1");
        this.assertEquals(i2.method3(), -3);
    }      
}
