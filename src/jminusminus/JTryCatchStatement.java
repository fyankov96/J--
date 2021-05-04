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

                //System.out.println(this.context.methodContext().getExceptions().toString());
                //System.out.println(this.context.getExceptions().toString());
            }          
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
        //System.out.println("adding startTryLabel: " + output.pc());
        output.addLabel(startTryLabel);
        //System.out.println("generate code for tryblock: " + output.pc());
        tryBlock.codegen(output);
        
        // generate code for finally block (when something goes wrong), if it has one
        if(finallyBlock != null) {
            //System.out.println("adding endTryLabel: " + output.pc());
            output.addLabel(endTryLabel);
            //System.out.println("generate code for finallyblock: " + output.pc());
            finallyBlock.codegen(output);
            //System.out.println("jump to endfinallylabel: " + output.pc());
            output.addBranchInstruction(GOTO, endFinallyLabel);
        }

        // generate code for each catchblock
        for(int i = 0; i < catchBlocks.size(); i++) {
            //System.out.println("adding startcatchLabel" + i  + ": " + output.pc());
            output.addLabel(startCatchLabel + i);
            //System.out.println("AStore_1: " + output.pc());
            output.addNoArgInstruction(ASTORE_1);
            //System.out.println("generate code for catch block" + i + ": " + output.pc());
            catchBlocks.get(i).codegen(output);
            //System.out.println("adding endcatchLabel" + i  + ": " + output.pc());
            output.addLabel(endCatchLabel + i);
            //System.out.println("adding exceptionhandler for catchblock" + i  + ": " + output.pc());
            output.addExceptionHandler(startTryLabel, endTryLabel, startCatchLabel + i,
                catchParams.get(i).type().jvmName());

            // output.addExceptionHandler(startCatchLabel + i, endCatchLabel + i, startFinallyLabel, null);
           
         
            // generate code for finally block (when something goes wrong), if it has one
            if(finallyBlock != null) {
                //System.out.println("generate code for finallyblock: " + output.pc());
                finallyBlock.codegen(output);
                //System.out.println("jump to endfinallylabel: " + output.pc());
                output.addBranchInstruction(GOTO, endFinallyLabel);  
            }
        }

        // generate code for finally block, if it has one
        if (finallyBlock != null) {
            //System.out.println("adding startFinallyLabel: " + output.pc());
            output.addLabel(startFinallyLabel);
            //System.out.println("AStore and it's offset: " + output.pc());
            output.addOneArgInstruction(ASTORE, this.context.methodContext().offset());
            // output.addLabel(startFinallyLabel + "+1");
            //System.out.println("generate code for finallyblock: " + output.pc());
            finallyBlock.codegen(output);
            //System.out.println("ALoad and it's offset: " + output.pc());
            output.addOneArgInstruction(ALOAD, this.context.methodContext().offset());
            //System.out.println("Athrow: " + output.pc());
            output.addNoArgInstruction(ATHROW);
            //System.out.println("adding endfinallyLabel: " + output.pc());
            output.addLabel(endFinallyLabel);
           
            //System.out.println("adding exceptionhandler for finallyblock: " + output.pc());
            output.addExceptionHandler(startTryLabel, endTryLabel, startFinallyLabel, null);
            

            for(int i = 0; i < catchBlocks.size(); i++) {
                //System.out.println("adding exceptionhandler for catchblock" + i + ": " + output.pc());
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
