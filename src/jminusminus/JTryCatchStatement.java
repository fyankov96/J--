// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * The AST node for a try-catch-statement.
 */

class JTryCatchStatement extends JStatement {

    /**
     * The new context (built in analyze()) represented by this block.
     */
    private LocalContext context;

    /** The Try-Block */
    private JBlock tryBlock;

    /** The Catch expression */
    private ArrayList<JFormalParameter> catchParams;

    /** The Catch-Block */
    private ArrayList<JBlock> catchBlocks;

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

    public JTryCatchStatement(int line, JBlock tryBlock, ArrayList<JFormalParameter> catchParams, ArrayList<JBlock> catchBlocks, JBlock finallyBlock) {
        super(line);
        this.tryBlock = tryBlock;
        this.catchParams = catchParams;
        this.catchBlocks = catchBlocks;
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
        // Analyse try block
        tryBlock.analyze(context);

        // Convert the catchparams to the formats of exceptions
        ArrayList<Type> catchExceptions = new ArrayList<>();

        /*for(JFormalParameter catchParam : catchParams) {
            catchExceptions.add(("java.lang." + catchParam));
        }*/

        // For every catch param, analyse the catch block
        for(int i = 0; i < catchParams.size(); i++) {
            this.context = new LocalContext(context);

            // Check if current catchparam is valid
            //System.out.print(catchParams.get(i).type().toString());
            if (!(catchParams.get(i).type().equals(Type.typeFor(Throwable.class)))) {
                                JAST.compilationUnit.reportSemanticError(catchParams.get(i).line(),
                "Attempting to catch a non-throwable type");
            } else {
                this.context.addException(catchParams.get(i).line(), catchParams.get(i).type());
            
            }

            //System.out.println(context.methodContext().getExceptions().toString());
            // System.out.println(this.context.getExceptions().toString());
            //this.context.addEntry(catchParams.get(i).line(), catchParams.get(i).name(), 
            //new LocalVariableDefn(catchParams.get(i).type(), this.context.nextOffset()));
            catchBlocks.get(i).analyze(this.context);
        }
        
        // Analyse finally block
        if (finallyBlock != null) {
            this.context = new LocalContext(context);
            finallyBlock.analyze(this.context);
        }

        return this;
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
        /*String tryLabel = output.createLabel();
        String catchLabel = output.createLabel();
        String finallyLabel = output.createLabel();

        tryBlock.codegen(output,tryLabel,false);

        for(Block catchblock : catchBlocks) {
            catchblock.codegen(output);
            output.addLabel(catchLabel);
        }*/

        //if (finallyBlock !=)
        
        /*condition.codegen(output, elseLabel, false);
        thenPart.codegen(output);
        if (elsePart != null) {
            output.addBranchInstruction(GOTO, endLabel);
        }
        output.addLabel(elseLabel);
        if (elsePart != null) {
            elsePart.codegen(output);
            output.addLabel(endLabel);
        }*/
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

        p.printf("<CatchBlocks>\n");

        if(this.catchBlocks != null) {

            for(int i = 0; i < catchBlocks.size(); i++) {
                p.printf("<CatchParameter>\n");
                p.indentRight();
                catchParams.get(i).writeToStdOut(p);
                p.indentLeft();
                p.printf("</CatchParameter>\n");

                p.printf("<CatchBlock>\n");
                p.indentRight();
                catchBlocks.get(i).writeToStdOut(p);
                p.indentLeft();
                p.printf("</CatchBlock>\n");
            }
        }

        p.printf("</CatchBlocks>\n");
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
