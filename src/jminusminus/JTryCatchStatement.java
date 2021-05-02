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

        for(JFormalParameter catchParam : catchParams) {
            catchParam.setType((new TypeName(catchParam.line(), "java.lang." + catchParam.type().toString())).resolve(context.methodContext()));
        }

        // For every catch param, analyse the catch block
        for(int i = 0; i < catchParams.size(); i++) {
            this.context = new LocalContext(context);
            catchParams.get(i).analyze(this.context);
            catchBlocks.get(i).analyze(this.context);

            /*catchParams.get(i).setType(new TypeName(catchParams.get(i).line(), 
            "java.lang." + catchParams.get(i).type().toString()));
            catchParams.get(i).type().resolve(this.context);*/

            // Check if current catchparam is valid
            if (!(catchParams.get(i).type().isSubType(Type.typeFor(Throwable.class)))) {
                                JAST.compilationUnit.reportSemanticError(catchParams.get(i).line(),
                "Attempting to catch a non-throwable type");
            } else {
                // Add to both localcontext and methodcontext (so tryblock can search for them)
                this.context.addException(catchParams.get(i).line(), catchParams.get(i).type());
                this.context.methodContext().addException(catchParams.get(i).line(), catchParams.get(i).type());

                // next off set for the context, if a new exception is added
                if (i > 0) {
                    this.context.methodContext().nextOffset();
                }

                System.out.println(this.context.methodContext().getExceptions().toString());
                System.out.println(this.context.getExceptions().toString());
            }

            //System.out.println(context.methodContext().getExceptions().toString());
            // System.out.println(this.context.getExceptions().toString());
            //this.context.addEntry(catchParams.get(i).line(), catchParams.get(i).name(), 
            //new LocalVariableDefn(catchParams.get(i).type(), this.context.nextOffset()));
           
        }
        
        // Analyse finally block
        if (finallyBlock != null) {
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


        // Add a start try label and generate code for try block
        output.addLabel(startTryLabel);
        tryBlock.codegen(output);
        
        // generate code for finally block, if it has one
        if(finallyBlock != null) {
            finallyBlock.codegen(output);
            output.addBranchInstruction(GOTO, endFinallyLabel);
            output.addLabel(endTryLabel);
        }

        for(int i = 0; i < catchBlocks.size(); i++) {
            output.addLabel(startCatchLabel + i);
            output.addNoArgInstruction(ASTORE_1);
            catchBlocks.get(i).codegen(output);
            output.addLabel(endCatchLabel + i);
            output.addExceptionHandler(startTryLabel, endTryLabel, startCatchLabel + i,
                this.context.methodContext().getExceptions().get(i).jvmName());
         
            if(finallyBlock != null) {
                finallyBlock.codegen(output);
                output.addBranchInstruction(GOTO, endFinallyLabel);
            }
        }


        if (finallyBlock != null) {
            output.addLabel(startFinallyLabel);
            output.addOneArgInstruction(ASTORE, this.context.methodContext().offset());
            // output.addLabel(startFinallyLabel + "+1");
            finallyBlock.codegen(output);
            output.addOneArgInstruction(ALOAD, this.context.methodContext().offset());
            output.addNoArgInstruction(ATHROW);
            output.addLabel(endFinallyLabel);

            output.addExceptionHandler(startTryLabel, endTryLabel, startFinallyLabel, null);

            for(int i = 0; i < catchBlocks.size(); i++) {
                output.addExceptionHandler(startCatchLabel + i, endCatchLabel + i, startFinallyLabel, null);
            }
            // output.addExceptionHandler(startFinallyLabel, startFinallyLabel + "+1", startFinallyLabel, null);
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
