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
        this.context = new LocalContext(context);
        tryBlock.analyze(context);

        //
        for(JFormalParameter catchParam : catchParams) {
            catchParam.setType((new TypeName(catchParam.line(), "java.lang." + catchParam.type().toString())).resolve(context.methodContext()));
        }

        // If theres no catchblocks due to only try-finally cases
        if (!catchBlocks.isEmpty()) {
            this.context.methodContext().nextOffset();

            // For every catch param, analyse the catch block
            for(int i = 0; i < catchParams.size(); i++) {
                this.context = new LocalContext(context);
                catchParams.get(i).analyze(this.context);
                catchBlocks.get(i).analyze(this.context);
 
                // Check if current catchparam is valid
                if (catchParams.get(i).type().isSubType(Type.typeFor(Throwable.class))) {
                    // Add to both localcontext and methodcontext (so tryblock can search for them
                    this.context.addException(catchParams.get(i).line(), catchParams.get(i).type());
                    this.context.methodContext().addException(catchParams.get(i).line(), catchParams.get(i).type());          
                } else {
                    JAST.compilationUnit.reportSemanticError(catchParams.get(i).line(),
                    "Attempting to catch a non-throwable type");
                }          
            }
        }
        
        
        // Analyse finally block
        if (finallyBlock != null) {
            this.context.methodContext().nextOffset();
            this.context = new LocalContext(context);
            finallyBlock.analyze(this.context);
        }

        return this;
    }

    /**
     * Code generation for an trycatch-statement. 
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        // Making all necessary jump labels
        String startTryLabel = output.createLabel();
        String endTryLabel = output.createLabel();
        String startCatchLabel = output.createLabel();
        String endCatchLabel = output.createLabel();
        String startFinallyLabel = output.createLabel();
        String endFinallyLabel = output.createLabel();

        // Add a start try label, generate code for try block, add end try label
        output.addLabel(startTryLabel);
        tryBlock.codegen(output);
        output.addLabel(endTryLabel);
        
        // generate code for finally block (when something goes wrong), if it has one
        if(finallyBlock != null) {
            finallyBlock.codegen(output);
        }
        // Go to end of try-catch-finally, and return
        output.addBranchInstruction(GOTO, endFinallyLabel);

        // generate code for each catchblock
        for(int i = 0; i < catchBlocks.size(); i++) {
            output.addLabel(startCatchLabel + i);

             // Go to handler, if caught exception
            output.addNoArgInstruction(ASTORE_1);

            catchBlocks.get(i).codegen(output);
            output.addLabel(endCatchLabel + i);

            // Add all exception handlers regarding catch; catchtype = exception from catchparam
            output.addExceptionHandler(startTryLabel, endTryLabel, startCatchLabel + i,
                catchParams.get(i).type().jvmName());
         
            // generate code for finally block (when something goes wrong), if it has one
            if(finallyBlock != null) {
                finallyBlock.codegen(output);
            }
            // Go to end of try-catch-finally, and return
            output.addBranchInstruction(GOTO, endFinallyLabel);  
        }

        // generate code for finally block, if it has one
        if (finallyBlock != null) {
            output.addLabel(startFinallyLabel);

            // Beggining of finally block. 
            output.addOneArgInstruction(ASTORE, this.context.methodContext().offset());
            finallyBlock.codegen(output);
            output.addOneArgInstruction(ALOAD, this.context.methodContext().offset());

            // Rethrow caught exception
            output.addNoArgInstruction(ATHROW);
        }
        output.addLabel(endFinallyLabel);
          
        // Add all exception handlers regarding finally 
        if (finallyBlock != null) {
            output.addExceptionHandler(startTryLabel, endTryLabel, startFinallyLabel, null);
            
            // Loops through all catch blocks and add exception handlers
            for(int i = 0; i < catchBlocks.size(); i++) {
                output.addExceptionHandler(startCatchLabel + i, endCatchLabel + i, startFinallyLabel, null);  
            }
        }
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
