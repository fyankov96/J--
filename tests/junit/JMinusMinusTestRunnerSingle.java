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

public class JMinusMinusTestRunnerSingle {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(InterfaceInvocationTest.class);

        return suite;
    }

    /**
     * Runs the test suite using the textual runner.
     */

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
