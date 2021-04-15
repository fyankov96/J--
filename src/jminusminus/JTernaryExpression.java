package jminusminus;
import static jminusminus.CLConstants.*;

public class JTernaryExpression extends JExpression{
        /** The condition to Evaluate */
        protected JExpression condition;
    
        /** The expression returned when condition is true */
        protected JExpression trueExpr;

        /** The expression returned when condition is false */
        protected JExpression falseExpr;

        protected JTernaryExpression(int line, JExpression condition, JExpression trueExpr, JExpression falseExpr) {
            super(line);
            this.condition = condition;
            this.trueExpr = trueExpr;
            this.falseExpr = falseExpr;
        }


    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JTernaryExpression line=\"%d\" type=\"%s\"\n", line(), ((type == null) ? "" : type.toString()));
        p.indentRight();
        p.printf("<condition>\n");
        p.indentRight();
        condition.writeToStdOut(p);
        p.indentLeft();
        p.printf("</condition>\n");
        p.printf("<trueExpr>\n");
        p.indentRight();
        trueExpr.writeToStdOut(p);
        p.indentLeft();
        p.printf("</trueExpr>\n");
        p.printf("<falseExpr>\n");
        p.indentRight();
        falseExpr.writeToStdOut(p);
        p.indentLeft();
        p.printf("</falseExpr>\n");
        p.indentLeft();
        p.printf("</JBinaryExpression>\n");
    }


    /**
     * Analyzing an equality expression means analyzing its operands and
     * checking that the types match.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        condition = (JExpression) condition.analyze(context);
        trueExpr = (JExpression) trueExpr.analyze(context);
        falseExpr = (JExpression) falseExpr.analyze(context);
        condition.type().mustMatchExpected(line(), Type.BOOLEAN);
        trueExpr.type().mustMatchExpected(line(), falseExpr.type());
        type = trueExpr.type();
        return this;
    }


    public void codegen(CLEmitter output) {
        String elseLabel = output.createLabel();
        String endLabel = output.createLabel();
        condition.codegen(output, elseLabel, false);
        trueExpr.codegen(output);
        if (falseExpr != null) {
            output.addBranchInstruction(GOTO, endLabel);
        }
        output.addLabel(elseLabel);
        if (falseExpr != null) {
            falseExpr.codegen(output);
            output.addLabel(endLabel);
        }
    }
}
