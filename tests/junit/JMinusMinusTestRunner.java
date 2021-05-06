// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package junit;

import java.io.File;


import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import pass.*;

/**
 * JUnit test suite for running the j-- programs in tests/pass.
 */

public class JMinusMinusTestRunner {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(HelloWorldTest.class);
        suite.addTestSuite(FactorialTest.class);
        suite.addTestSuite(GCDTest.class);
        suite.addTestSuite(SeriesTest.class);
        suite.addTestSuite(ClassesTest.class);
        suite.addTestSuite(DivisionTest.class);
        suite.addTestSuite(RemainderTest.class);
        suite.addTestSuite(UnsignedRightShiftTest.class);
        suite.addTestSuite(BitwiseAndTest.class);
        suite.addTestSuite(BitwiseNotTest.class);
        suite.addTestSuite(BitwiseOrTest.class);
        suite.addTestSuite(BitwiseXorTest.class);
        suite.addTestSuite(LeftShiftTest.class);
        suite.addTestSuite(RightShiftTest.class);
        suite.addTestSuite(UnaryPlusTest.class);
        suite.addTestSuite(MultiLineCommentTest.class);
        suite.addTestSuite(LogicalOrTest.class);
        suite.addTestSuite(ConditionalTest.class);
        suite.addTestSuite(NotEqualsTest.class);
        suite.addTestSuite(DivisionAssignmentTest.class);
        suite.addTestSuite(MultiplicationAssignmentTest.class);
        suite.addTestSuite(SubtractionAssignmentTest.class);
        suite.addTestSuite(RemainderAssignmentTest.class);
        suite.addTestSuite(LeftShiftAssignmentTest.class);
        suite.addTestSuite(RightShiftAssignmentTest.class);
        suite.addTestSuite(UnsignedRightShiftAssignmentTest.class);
        suite.addTestSuite(XORassignTest.class);
        suite.addTestSuite(PostIncrementTest.class);
        suite.addTestSuite(PreDecrementTest.class);
        suite.addTestSuite(ExceptionHandlingTest.class);
        return suite;
    }

    /**
     * Runs the test suite using the textual runner.
     */

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
