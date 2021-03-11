// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a for-statement.
 */


abstract class JForStatement extends JStatement {
    protected JStatement body;

    public JForStatement(int line, JStatement body) {
        super(line);
        this.body = body;
    }
} 


class JForStepStatement extends JForStatement {


    /** Test expression. */
    private JStatement initialization;
    private JExpression condition;
    private JStatement step;

    /**
     * Constructs an AST node for a while-statement given its line number, the
     * test expression, and the body.
     * 
     * @param line
     *            line in which the while-statement occurs in the source file.
     * @param condition
     *            test expression.
     * @param body
     *            the body.
     */

    public JForStepStatement(int line, JStatement initialization, JExpression condition, JStatement step, JStatement body) {
        super(line, body);
        this.initialization = initialization;
        this.condition = condition;
        this.step = step;
    }

    /**
     * Analysis involves analyzing the test, checking its type and analyzing the
     * body statement.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JForStepStatement analyze(Context context) {
        initialization = (JStatement) initialization.analyze(context);
        condition = (JExpression) condition.analyze(context);
        condition.type().mustMatchExpected(line(), Type.BOOLEAN);
        step = (JStatement) step.analyze(context);
        body = (JStatement) body.analyze(context);
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
        //TODO implement
    }

    /**
     * {@inheritDoc}
     */
    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JForStatement line=\"%d\">\n", line());
        p.indentRight();
        p.printf("<Initialization>\n");
        p.indentRight();
        initialization.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Initialization>\n");
        p.printf("<Condition>\n");
        p.indentRight();
        condition.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Condition>\n");
        p.printf("<Step>\n");
        p.indentRight();
        step.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Step>\n");
        p.printf("<Body>\n");
        p.indentRight();
        body.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Body>\n");
        p.indentLeft();
        p.printf("</JForStatement>\n");
    }

}

class JForEachStatement extends JForStatement {


    /** Test expression. */
    private JStatement declaration;
    private JExpression iterable;
    private JStatement body;

    /**
     * Constructs an AST node for a while-statement given its line number, the
     * test expression, and the body.
     * 
     * @param line
     *            line in which the while-statement occurs in the source file.
     * @param condition
     *            test expression.
     * @param body
     *            the body.
     */

    public JForEachStatement(int line, JStatement declaration, JExpression iterable, JStatement body) {
        super(line, body);
        this.declaration = declaration;
        this.iterable = iterable;
    }

    /**
     * Analysis involves analyzing the test, checking its type and analyzing the
     * body statement.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JForEachStatement analyze(Context context) {
        declaration = (JStatement) declaration.analyze(context);
        iterable = (JExpression) iterable.analyze(context);
        //TODO (how to check that it is an iterable?) iterable.type().mustMatchExpected(line(), );
        body = (JStatement) body.analyze(context);
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
        //TODO implement
    }

    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JForEachStatement line=\"%d\">\n", line());
        p.indentRight();
        p.printf("<Declaration>\n");
        p.indentRight();
        declaration.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Declaration>\n");
        p.printf("<Iterable>\n");
        p.indentRight();
        iterable.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Iterable>\n");
        p.printf("<Body>\n");
        p.indentRight();
        body.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Body>\n");
        p.indentLeft();
        p.printf("</JForEachStatement>\n");
    }

}
