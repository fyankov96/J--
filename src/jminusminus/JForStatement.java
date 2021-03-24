// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;
import java.util.ArrayList;

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
    private ArrayList<JStatement> initStatements;
    private ArrayList<JVariableDeclarator> initDeclarations;
    private JExpression condition;
    private ArrayList<JStatement> step;

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

    public JForStepStatement(int line, ArrayList<JStatement> initStatements, ArrayList<JVariableDeclarator> initDeclarations,  
                             JExpression condition, ArrayList<JStatement> step, JStatement body) {
        super(line, body);
        this.initStatements = initStatements;
        this.initDeclarations = initDeclarations;
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

        // TODO: Analyze arrayList of JStatement
         
        // initialization = (ArrayList<JStatement>) initialization.analyze(context);
        condition = (JExpression) condition.analyze(context);
        condition.type().mustMatchExpected(line(), Type.BOOLEAN);
        // step = (ArrayList<JStatement>) step.analyze(context);
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
        if(this.initStatements != null) {
            for(JStatement j : this.initStatements) {
                p.indentRight();
                j.writeToStdOut(p);
                p.indentLeft();
            }
        }
        if(this.initDeclarations != null) {
            for(JVariableDeclarator j : this.initDeclarations) {
                p.indentRight();
                j.writeToStdOut(p);
                p.indentLeft();
            }
        } 
        p.printf("</Initialization>\n");
        p.printf("<Condition>\n");
        p.indentRight();
        if(this.condition != null) {
            condition.writeToStdOut(p);
        }
        p.indentLeft();
        p.printf("</Condition>\n");
        p.printf("<Step>\n");
        if(this.step != null) {
            for(JStatement j : this.step) {
                p.indentRight();
                j.writeToStdOut(p);
                p.indentLeft();
            }
        }
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
    private JVariableDeclarator declaration;
    private JExpression iterable;

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

    public JForEachStatement(int line, JVariableDeclarator declaration, JExpression iterable, JStatement body) {
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
        declaration = (JVariableDeclarator) declaration.analyze(context);
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
