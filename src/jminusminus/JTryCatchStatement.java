// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a try-catch-statement.
 */

class JTryCatchStatement extends JStatement {

    /** The Try-Block */
    private JBlock tryBlock;

    /** The Catch expression */
    private JFormalParameter catchParam;

    /** The Catch-Block */
    private JBlock catchBlock;

    /** The Finally-Block */
    private JBlock finallyBlock;

    /**
     * Constructs an AST node for an try-catch-statement given its line number, the try block,
     * the parameter to catch, the catch block and possibly a finally block.
     * 
     * @param line
     *            line in which the if-statement occurs in the source file.
     * @param tryBlock
     *            
     * @param catchParam
     *            
     * @param catchBlock
     *            
     * @param finallyBlock
     *            
     */

    public JTryCatchStatement(int line, JBlock tryBlock, JFormalParameter catchParam, JBlock catchBlock, JBlock finallyBlock) {
        super(line);
        this.tryBlock = tryBlock;
        this.catchParam = catchParam;
        this.catchBlock = catchBlock;
        this.finallyBlock = finallyBlock;
    }

    /**
     * Analyzing the try-catch-statement means analyzing its components and checking
     * that the test is a boolean.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JStatement analyze(Context context) {
        return null;
    }

    /**
     * Code generation for an if-statement. We generate code to branch over the
     * consequent if !test; the consequent is followed by an unconditonal branch
     * over (any) alternate.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {

    }

    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JTryCatchStatement line=\"%d\">\n", line());
        p.indentRight();
        p.printf("<TryBlock>\n");
        p.indentRight();
        tryBlock.writeToStdOut(p);
        p.indentLeft();
        p.printf("</TryBlock>\n");
        p.printf("<CatchParameter>\n");
        p.indentRight();
        catchParam.writeToStdOut(p);
        p.indentLeft();
        p.printf("</CatchParameter>\n");
        p.printf("<CatchBlock>\n");
        p.indentRight();
        catchBlock.writeToStdOut(p);
        p.indentLeft();
        p.printf("</CatchBlock>\n");
        if (finallyBlock != null) {
            p.printf("<FinallyBlock>\n");
            p.indentRight();
            finallyBlock.writeToStdOut(p);
            p.indentLeft();
            p.printf("</FinallyBlock>\n");
        }
        p.indentLeft();
        p.printf("</JTryCatchStatement>\n");
        
    }

}
