// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * This abstract base class is the AST node for a comparison expression. 
 */

abstract class JComparison extends JBooleanBinaryExpression {

    /**
     * Constructs an AST node for a comparison expression.
     * 
     * @param line
     *            line in which the expression occurs in the source file.
     * @param operator
     *            the comparison operator.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    protected JComparison(int line, String operator, JExpression lhs,
            JExpression rhs) {
        super(line, operator, lhs, rhs);
    }

    /**
     * The analysis of a comparison operation consists of analyzing its two
     * operands, and making sure they both have the same numeric type.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        lhs.type().mustMatchOneOf(line(), Type.INT, Type.DOUBLE);
        rhs.type().mustMatchExpected(line(), lhs.type());
        type = Type.BOOLEAN;
        return this;
    }

}

/**
 * The AST node for a greater-than (&gt;) expression. Implements 
 * short-circuiting branching.
 */

class JGreaterThanOp extends JComparison {

    /**
     * Constructs an AST node for a greater-than expression given its line
     * number, and the lhs and rhs operands.
     * 
     * @param line
     *            line in which the greater-than expression occurs in the source
     *            file.
     * @param lhs
     *            lhs operand.
     * @param rhs
     *            rhs operand.
     */

    public JGreaterThanOp(int line, JExpression lhs, JExpression rhs) {
        super(line, ">", lhs, rhs);
    }

    /**
     * Branching code generation for &gt; operation.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     * @param targetLabel
     *            target for generated branch instruction.
     * @param onTrue
     *            should we branch on true?
     */

    public void codegen(CLEmitter output, String targetLabel, boolean onTrue) {
        lhs.codegen(output);
        rhs.codegen(output);
        if(lhs.type() == Type.DOUBLE) { //Transform a double comparison in an integer comparison
            output.addNoArgInstruction(onTrue ? DCMPG : DCMPL); //Compares doubles (1 if lhs > rhs, 0 if equal, -1 otherwise)
            output.addBranchInstruction(onTrue ? IFGT : IFLE,
            targetLabel);
        } else if(lhs.type() == Type.INT) {
            output.addBranchInstruction(onTrue ? IF_ICMPGT : IF_ICMPLE,
            targetLabel);
        }
    }

}

/**
 * The AST node for a less-than-or-equal-to (&lt;=) expression. Implements
 * short-circuiting branching.
 */

class JLessEqualOp extends JComparison {

    /**
     * Constructs an AST node for a less-than-or-equal-to expression given its
     * line number, and the lhs and rhs operands.
     * 
     * @param line
     *            line in which the less-than-or-equal-to expression occurs in
     *            the source file.
     * @param lhs
     *            lhs operand.
     * @param rhs
     *            rhs operand.
     */

    public JLessEqualOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "<=", lhs, rhs);
    }

    /**
     * Branching code generation for &lt;= operation.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     * @param targetLabel
     *            target for generated branch instruction.
     * @param onTrue
     *            should we branch on true?
     */

    public void codegen(CLEmitter output, String targetLabel, boolean onTrue) {
        lhs.codegen(output);
        rhs.codegen(output);
        if(lhs.type() == Type.DOUBLE) { //Transform a double comparison in an integer comparison
            output.addNoArgInstruction(onTrue ? DCMPL : DCMPG); //Compares doubles (1 if lhs > rhs, 0 if equal, -1 otherwise)
            output.addBranchInstruction(onTrue ? IFLE : IFGT,
            targetLabel);
        } else if(lhs.type() == Type.INT) {
            output.addBranchInstruction(onTrue ? IF_ICMPLE : IF_ICMPGT,
            targetLabel);
        }
    }

}

/**
 * The AST node for a less-than (<) expression. Implements
 * short-circuiting branching.
 */

class JLessThanOp extends JComparison {

    /**
     * Constructs an AST node for a less-than expression given its
     * line number, and the lhs and rhs operands.
     * 
     * @param line
     *            line in which the less-than expression occurs in
     *            the source file.
     * @param lhs
     *            lhs operand.
     * @param rhs
     *            rhs operand.
     */

    public JLessThanOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "<", lhs, rhs);
    }

    /**
     * Branching code generation for < operation.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     * @param targetLabel
     *            target for generated branch instruction.
     * @param onTrue
     *            should we branch on true?
     */

    public void codegen(CLEmitter output, String targetLabel, boolean onTrue) {
        lhs.codegen(output);
        rhs.codegen(output);
        if(lhs.type() == Type.DOUBLE) { //Transform a double comparison in an integer comparison
            output.addNoArgInstruction(onTrue ? DCMPL : DCMPG); //Compares doubles (1 if lhs > rhs, 0 if equal, -1 otherwise)
            output.addBranchInstruction(onTrue ? IFLT : IFGE,
            targetLabel);
        } else if(lhs.type() == Type.INT) {
            output.addBranchInstruction(onTrue ? IF_ICMPLT : IF_ICMPGE,
            targetLabel);
        }
    }

}

/**
 * The AST node for a greater-than-or-equal-to (>=) expression. Implements
 * short-circuiting branching.
 */

class JGreaterEqualOp extends JComparison {

    /**
     * Constructs an AST node for a greater-than-or-equal-to expression given its
     * line number, and the lhs and rhs operands.
     * 
     * @param line
     *            line in which the greater-than-or-equal-to expression occurs in
     *            the source file.
     * @param lhs
     *            lhs operand.
     * @param rhs
     *            rhs operand.
     */

    public JGreaterEqualOp(int line, JExpression lhs, JExpression rhs) {
        super(line, ">=", lhs, rhs);
    }

    /**
     * Branching code generation for &lt;= operation.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     * @param targetLabel
     *            target for generated branch instruction.
     * @param onTrue
     *            should we branch on true?
     */

    public void codegen(CLEmitter output, String targetLabel, boolean onTrue) {
        lhs.codegen(output);
        rhs.codegen(output);
        if(lhs.type() == Type.DOUBLE) { //Transform a double comparison in an integer comparison
            output.addNoArgInstruction(onTrue ? DCMPG : DCMPL); //Compares doubles (1 if lhs > rhs, 0 if equal, -1 otherwise)
            output.addBranchInstruction(onTrue ? IFGE : IFLT,
            targetLabel);
        } else if(lhs.type() == Type.INT) {
            output.addBranchInstruction(onTrue ? IF_ICMPGE : IF_ICMPLT,
            targetLabel);
        }

    }

}