// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package junit;

import junit.framework.TestCase;
import jminusminus.JavaCCMain;
import java.io.File;

/**
 * JUnit test case for the JavaCC parser.
 */

public class JavaCCParserTestCustom extends TestCase {

    /**
     * Construct a JavaCCParserTest object.
     */

    public JavaCCParserTestCustom() {
        super("JUnit test case for the parser");
    }

    /**
     * Run the parser against each pass-test file under the folder specified by
     * PASS_TESTS_DIR property.
     */

    public void testPass() {
        File passTestsDir = new File(System.getProperty("PASS_TESTS_DIR"));
        //File passTestsDir = new File("C:\\Users\\thoma\\Kandidat4\\CompilerConstruction\\j--/tests/fail");

        File[] files = passTestsDir.listFiles();
        String toTest = "ForEachStatement.java";
        boolean errorHasOccurred = false;
        for (int i = 0; files != null && i < files.length; i++) {
            if (files[i].toString().endsWith(toTest)) {
                String[] args = null;
                System.out.printf("Running javacc parser on %s ...\n\n", files[i].toString());
                args = new String[] { "-p", files[i].toString() };
                JavaCCMain.main(args);
                System.out.printf("\n\n");

                // true even if a single test fails
                errorHasOccurred |= JavaCCMain.errorHasOccurred();
            }
        }

        // We want all tests to pass
        assertFalse(errorHasOccurred);
    }

    /**
     * Entry point.
     * 
     * @param args command-line arguments.
     */

    public static void main(String[] args) {
        junit.textui.TestRunner.run(JavaCCParserTestCustom.class);
    }

}
