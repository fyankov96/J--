// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;
import java.util.ArrayList;
import java.lang.Iterable;
import java.util.Iterator;

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
    private ArrayList<JStatement> stepStatements;

    /* The new context (built in analyze()) for defining variables used in the loop. */
    private LocalContext context;

    /* The block to hold the rewritten loop statement*/
    private JBlock whileBlock;

    
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
     * @param stepStatements
     *            the step updates
     * @param body
     *            the body.
     */

    public JForStepStatement(int line, ArrayList<JStatement> initStatements, JVariableDeclaration initDeclarations,
                             JExpression condition, ArrayList<JStatement> stepStatements, JStatement body) {
        super(line, body);
        this.initStatements = initStatements;
        this.initDeclarations = initDeclarations;
        this.condition = condition;
        this.stepStatements = stepStatements;
    }

    /**
     * Analysis involves analyzing the variable assignments or declarations, 
     * analyzing the termination condition and checking its type,
     * analyzing each update statement
     * and finally analyzing the body statement.
     * 
     * Possibly we rewrite this as a while-statement
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JBlock /*JForStepStatement*/ analyze(Context context) {  
        /*
        this.context = new LocalContext(context);

        if (initStatements != null && initStatements.size() > 0){
            for (JStatement statement : initStatements){
                statement.analyze(this.context);
            }
        }
    
        if(initDeclarations != null) {
            initDeclarations.analyze(this.context);
        }
        
        if(condition != null) {
            condition.analyze(this.context);
            condition.type().mustMatchExpected(line(), Type.BOOLEAN);
        }

        if(stepStatements != null && stepStatements.size() > 0) {
            for (JStatement statement : stepStatements){
                statement.analyze(this.context);
            }
        }

        body.analyze(this.context);
        return this;
        */
        
        //Rewriting to while-loop block
        ArrayList<JStatement> whileBlockStatements = new ArrayList<JStatement>();
        JWhileStatement whileStatement;
        
        //Add the initializer (if any) to the start of the block
        if (initStatements != null && initStatements.size() > 0){
            for (JStatement init : initStatements){
                whileBlockStatements.add(init);
            }
        } else if(initDeclarations != null) {
            whileBlockStatements.add((JStatement)initDeclarations);
        }
        
        //Unpack the body statements and add the step(s) to the end
        ArrayList<JStatement> whileBodyStatements = ((JBlock) body).statements();
        if(stepStatements != null && stepStatements.size() > 0) {
            for (JStatement statement : stepStatements){
                whileBodyStatements.add(statement);
            }
        }

        //Create the while-statement and add it to the block
        if(condition == null){
            condition = new JLiteralTrue(line());
        }
        whileStatement = new JWhileStatement(line(), condition, new JBlock(line(), whileBodyStatements));

        whileBlockStatements.add(whileStatement);
        
        //Finish and analyze the whileBlock
        whileBlock = new JBlock(line(), whileBlockStatements).analyze(context);

        return whileBlock;
    }

    /**
     * Generates code for the statement. For this, labels are required pointing
     * to the condition and the end of the loop.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        String test = output.createLabel();
        String out = output.createLabel();

        if (initStatements != null && initStatements.size() > 0){
            for (JStatement init : initStatements){
                init.codegen(output);
            }
        }
    
        if(initDeclarations != null) {
            initDeclarations.codegen(output);
        }

        //Condition label
        output.addLabel(test);

        //Jump to end label if condition is false
        if(condition == null) {
            (new JLiteralTrue(line())).codegen(output, out, false);
        }
        condition.codegen(output, out, false);

        body.codegen(output);

        if(stepStatements != null && stepStatements.size() > 0) {
            for (JStatement stmt : stepStatements){
                stmt.codegen(output);
            }
        }

        //Go back and test the condition
        output.addBranchInstruction(GOTO, test);

        //End label
        output.addLabel(out);
    }

    /**
     * {@inheritDoc}
     */
    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JForStepStatement line=\"%d\">\n", line());
        p.indentRight();
        p.printf("<Initialization>\n");
        if(initStatements != null && initStatements.size() > 0) {
            for(JStatement j : initStatements) {
                p.indentRight();
                j.writeToStdOut(p);
                p.indentLeft();
            }
        }
        if(initDeclarations != null) {
            p.indentRight();
            initDeclarations.writeToStdOut(p);
            p.indentLeft();
        } 
        p.printf("</Initialization>\n");
        p.printf("<Condition>\n");
        p.indentRight();
        if(condition != null) {
            condition.writeToStdOut(p);
        }
        p.indentLeft();
        p.printf("</Condition>\n");
        p.printf("<Step>\n");
        if(stepStatements != null && stepStatements.size() > 0) {
            for(JStatement j : stepStatements) {
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

    /* Iterating identifier */
    private JSingleVariableDeclaration identifier;

    /* Iterable expression */
    private JExpression iterable;

    /* The new context (built in analyze()) for defining variables used in the loop. */
    private LocalContext context;

    /* JBlock containing the rewritten loop done during analysis */
    private JBlock forStepBlock;

    /* Static numbers used to generate guaranteed unique variables used in rewriting.*/
    private static int iterableNum = 0;
    private static int iteratorNum = 0;

    /**
     * Constructs an AST node for a for-each-statement given its line number, the
     * iterating identifier and iterable.
     * 
     * @param line
     *            line in which the while-statement occurs in the source file.
     * @param identifier
     *            the iterating identifier
     * @param iterable
     *            the iterable
     * @param body
     *            the body.
     */

    public JForEachStatement(int line, JSingleVariableDeclaration identifier, JExpression iterable, JStatement body) {
        super(line, body);
        this.identifier = identifier;
        this.iterable = iterable;
        this.forStepBlock = null;
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

    public JBlock analyze(Context context) {  //Thomas: Report
        this.context = new LocalContext(context);

        identifier.analyze(this.context);
        iterable.analyze(this.context);
        
        /*
        if (!(iterable.type().isArray() || iterable.type().isSubType(Type.typeFor(Iterable.class)))) {
            JAST.compilationUnit.reportSemanticError(line(),
                "Attempting to iterate over a non-iterable type");
        }

        if(!identifier.type().equals(iterable.type().componentType())){
            JAST.compilationUnit.reportSemanticError(line(),
                "Using " + identifier.type() + " type to iterate over " + iterable.type().componentType() + " array");
        }

        body.analyze(this.context);
        */
        
        // Rewrite to a for-step block and analyze //Thomas: Report
        ArrayList<JStatement> blockStatements = new ArrayList<JStatement>();
        JSingleVariableDeclaration iterableDecl = null;
        JVariableDeclaration initDecl = null;
        JExpression condition = null;
        ArrayList<JStatement> loopSteps = new ArrayList<JStatement>();
        ArrayList<JStatement> forStepStatements = ((JBlock) body).statements();
        JSingleVariableDeclaration identAssign = null;
        
        if (iterable.type().isArray()) {
            // Create the iterable $a' = iterable and add it to the block statements
            String iterableName = generateIterableName();
            String iteratorName = generateIteratorName();
            iterableDecl = new JSingleVariableDeclaration(line(), iterableName, iterable.type(), new ArrayList<String>(), iterable); 
            blockStatements.add(iterableDecl);

            // Create the iterator (int $i' = 0 ; ...
            JVariableDeclarator init = new JVariableDeclarator(line, iteratorName, Type.INT, new JLiteralInt(line(), "0"));
            ArrayList<JVariableDeclarator> initList = new ArrayList<JVariableDeclarator>();
            initList.add(init);
            initDecl = new JVariableDeclaration(line, new ArrayList<String>(), initList);

            // Create the condition ... ; $i' < $a'.length ; ...
            JExpression lhs = new JVariable(line(), iteratorName);
            JExpression rhs = new JFieldSelection(line, new JVariable(line(), iterableName), "length");
            condition = new JLessThanOp(line(), lhs, rhs);

            // Create the step ... ; $i'++
            JPostIncrementOp jio = new JPostIncrementOp(line(), new JVariable(line(), iteratorName));
            jio.isStatementExpression = true;
            loopSteps.add(jio);

            // Create the identifier assignment: Type identifier = $a'[$i']
            identAssign = new JSingleVariableDeclaration(line(), identifier.name(), identifier.type(), identifier.mods(),
                                                                new JArrayExpression(line(), 
                                                                    new JVariable(line(), iterableName),
                                                                        new JVariable(line(), iteratorName)));
            forStepStatements.add(0, identAssign);

        } else if(iterable.type().isSubType(Type.typeFor(Iterable.class))) {
            // Create the iterator (I $i' = Expression.iterator() ; ...
            String iteratorName = generateIteratorName();
            
            ArrayList<JVariableDeclarator> initList = new ArrayList<JVariableDeclarator>();
            JExpression iterableInit = new JMessageExpression(line(), new JVariable(line, iteratorName), "iterator", new ArrayList());
            JVariableDeclarator init = new JVariableDeclarator(line(), iteratorName, Type.typeFor(Iterator.class), iterableInit);
            initList.add(init);
            initDecl = new JVariableDeclaration(line(), new ArrayList<String>(), initList);
            
            // Create the condition ... ; $i'.hasNext() ;
            JExpression iteratorExpr = new JVariable(line(), iteratorName);
            condition = new JMessageExpression(line(), iteratorExpr, "has_next", new ArrayList());

            // No step statements are required
            
            // Create the identifier Type identifier = $i'.next()
            JMessageExpression nextInvocation = new JMessageExpression(line, new JVariable(line, iteratorName), "next", new ArrayList());

            // Create the identifier assignment: Type identifier = $i'.next`()
            identAssign = new JSingleVariableDeclaration(line, identifier.name(), identifier.type(), identifier.mods(), nextInvocation);
            forStepStatements.add(0, identAssign);

        } else {
            JAST.compilationUnit.reportSemanticError(line(),
            "Unsupported foreach");
            return new JBlock(line, new ArrayList<JStatement>());
        }

        JForStepStatement forStepNode = new JForStepStatement(line(), null, initDecl, condition, loopSteps, new JBlock(line(), forStepStatements));
        blockStatements.add(forStepNode);
        this.forStepBlock = new JBlock(line(), blockStatements);
        this.forStepBlock = this.forStepBlock.analyze(context);

        return this.forStepBlock;
    }

    /**
     * Generate the next name of the iterable used for rewriting to
     * a for-step block.
     */
    private static String generateIterableName() {
        return "$a'" + iterableNum++;
    }

    /**
     * Generate the next name of the iterator used for rewriting to
     * a for-step block.
     */
    private static String generateIteratorName() {
        return "$i'" + iteratorNum++;
    }

    /**
     * Generates code for the for-each statement by generating the code for the analyzed for-step block.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        //Not required, codegen is done on the rewritten AST node.
    }

    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JForEachStatement line=\"%d\">\n", line());
        p.indentRight();
        identifier.writeToStdOut(p);
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
