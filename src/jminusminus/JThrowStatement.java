// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;


/**
 * The AST node for a throwexpression
 */

class JThrowStatement extends JStatement {

    /** Thrown expression. */
    private JExpression expr;

    /** Method for thrown expression */
    private Constructor constructor;

    /** Types of the arguments. */
    private Type[] argTypes;

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
        
        // Check if the expr is the type throwable
        if (!(expr.type().isSubType(Type.typeFor(Throwable.class))))  {
            JAST.compilationUnit.reportSemanticError(line(),
                "Attempting to throw a non-throwable type");
        }

        // Check if we are in an initialization blocks
        // If so, report a semantic error
        if (context.blockContext() != null) {
            JAST.compilationUnit.reportSemanticError(line(),
            "Attempting to throw in an initialization block");

        // Check if it exists in the local context, if it does, don't add it.
        } else if (context.getExceptions().contains(expr.type())) {
            JAST.compilationUnit.reportSemanticError(line, "Exception already exists: "
            + expr.type().toString());
        
        // Check if it exists in the methodcontext, but doesn't exist in the local context
        // then add to local context
        } else if (context.methodContext().getExceptions().contains(expr.type()) &&
                   !context.getExceptions().contains(expr.type())){
            context.addException(expr.line(), expr.type());

        // If it doesn't exist in both, add both local & method context.
        } else if (!context.methodContext().getExceptions().contains(expr.type()) && 
                   !context.getExceptions().contains(expr.type())){
            context.addException(expr.line(), expr.type());
            context.methodContext().addException(expr.line(), expr.type());
        }

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
        expr.codegen(output);
        output.addNoArgInstruction(ATHROW);
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
