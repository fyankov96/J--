// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a throwexpression
 */

class JThrowStatement extends JStatement {

    /** Thrown expression. */
    private JExpression expr;

    /**
     * Constructs an AST node for a throw-statement given its line number and the expression
     * 
     * @param line
     *            line in which the while-statement occurs in the source file.
     * @param expr
     *            the expression.
     */

    public JThrowStatement(int line, JExpression expr) {
        super(line);
        this.expr = expr;
    }

    /**
     * Analysis involves...
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JThrowStatement analyze(Context context) {
        // Analyzing the expr makes the JThrowStatement use the imported libraries
        expr.analyze(context);
        return this;
    }

    /**
     * Generates code for the while loop.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        // if 
        // output.addNoArgInstruction(NEW);
        // output.addNoArgInstruction(DUP);
        // output.addNoArgInstruction(INVOKESPECIAL);
        // output.addNoArgInstruction(ATHROW);
    }

    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JThrowExpression line=\"%d\">\n", line());
        p.indentRight();
        expr.writeToStdOut(p);
        p.indentLeft();
        p.printf("</JThrowExpression>\n");
    }

}
