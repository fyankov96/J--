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


    /* Variable update statements */
    private ArrayList<JStatement> initStatements;

    /* Variable declarations */
    private JVariableDeclaration initDeclarations;

    /* Termination expression */
    private JExpression condition;

    /* Step updates */
    private ArrayList<JStatement> stepStmts;

    /* The new context (built in analyze()) for defining variables used in the loop. */
    private LocalContext context;

    /**
     * Constructs an AST node for a while-statement given its line number, the
     * test expression, and the body.
     * 
     * @param line
     *            line in which the while-statement occurs in the source file.
     * @param initStatements
     *            the variable initialization statements
     * @param initDeclarations
     *            the variable declarations
     * @param condition
     *            the termination condition
     * @param stepStmts
     *            the step updates
     * @param body
     *            the body.
     */

    public JForStepStatement(int line, ArrayList<JStatement> initStatements, JVariableDeclaration initDeclarations,  
                             JExpression condition, ArrayList<JStatement> stepStmts, JStatement body) {
        super(line, body);
        this.initStatements = initStatements;
        this.initDeclarations = initDeclarations;
        this.condition = condition;
        this.stepStmts = stepStmts;
    }

    /**
     * Analysis involves analyzing the variable assignments or declarations, 
     * analyzing the termination condition and checking its type,
     * analyzing each update statement
     * and finally analyzing the body statement.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JForStepStatement analyze(Context context) {
        this.context = new LocalContext(context);

        if (initStatements != null){
            for (JStatement init : initStatements){
                init.analyze(this.context);
            }
        }
    
        if(initDeclarations != null) {
            initDeclarations.analyze(this.context);
        }

        if(condition != null) {
            condition = (JExpression) condition.analyze(this.context);
            condition.type().mustMatchExpected(line(), Type.BOOLEAN);
        }

        if(stepStmts != null) {
            for (JStatement stmt : stepStmts){
                stmt.analyze(this.context);
            }
        }

        body = (JStatement) body.analyze(this.context);
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
        p.printf("<JForStepStatement line=\"%d\">\n", line());
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
            p.indentRight();
            this.initDeclarations.writeToStdOut(p);
            p.indentLeft();
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
        if(this.stepStmts != null) {
            for(JStatement j : this.stepStmts) {
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


    /* Element declaration */
    private JVariableDeclaration declaration;

    /* Element Iterable */
    private JExpression iterable;

    /* The new context (built in analyze()) for defining variables used in the loop. */
    private LocalContext context;

    /**
     * Constructs an AST node for a for-each-statement given its line number, the
     * element declaration and iterable.
     * 
     * @param line
     *            line in which the while-statement occurs in the source file.
     * @param declaration
     *            the element declaration
     * @param iterable
     *            the iterable
     * @param body
     *            the body.
     */

    public JForEachStatement(int line, JVariableDeclaration declaration, JExpression iterable, JStatement body) {
        super(line, body);
        this.declaration = declaration;
        this.iterable = iterable;
    }

    /**
     * Analysis involves analyzing the declaration and the iterable.
     * Checking that the iterable is an array of the same components as the declaration
     * and analyzing the body statement.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JForEachStatement analyze(Context context) {
        this.context = new LocalContext(context);

        declaration = (JVariableDeclaration) declaration.analyze(this.context);
        iterable = (JExpression) iterable.analyze(this.context);

        if (!(iterable.type().isArray())) {
            JAST.compilationUnit.reportSemanticError(line(),
                "Attempting to iterate over a non-array type");
        }

        if(declaration.decls().get(0).type() != iterable.type().componentType()){
            JAST.compilationUnit.reportSemanticError(line(),
                "Using " + declaration.decls().get(0).type() + " type to iterate over " + iterable.type().componentType() + " array");
        }

        body = (JStatement) body.analyze(this.context);
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
        declaration.writeToStdOut(p);
        p.indentLeft();
        p.indentRight();
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
