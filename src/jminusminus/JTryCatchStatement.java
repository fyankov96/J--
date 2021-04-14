// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;
import java.util.ArrayList;


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
        tryBlock.analyze(context);

        // For iteration through both the catchblock and parameters
        int listSize = catchParams.size();


        for(int i = 0; i < listSize; i++) {
            this.context = new LocalContext(context);
            // Check for different catchparameters like NullPointerException

            LocalVariableDefn defn = new LocalVariableDefn(catchParams.get(i).type(), 
                this.context.nextOffset());
            defn.initialize();
            this.context.addEntry(catchParams.get(i).line(), catchParams.get(i).name(), defn);
            
            catchBlocks.get(i).analyze(this.context);
        }
    
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
        p.printf("<CatchParameter>\n");

        if(this.catchParams != null) {
            for(JFormalParameter param : this.catchParams) {
                p.indentRight();
                param.writeToStdOut(p);
                p.indentLeft();
            }
        }

        p.printf("</CatchParameter>\n");

        p.printf("<CatchBlocks>\n");

        if(this.catchBlocks != null) {

            for(JBlock block : this.catchBlocks) {
                p.printf("<CatchBlock>\n");
                p.indentRight();
                block.writeToStdOut(p);
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
