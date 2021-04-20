package jminusminus;

import java.util.ArrayList;

/**
 * The AST node for a for-each variable declaration.
 */

class JSingleVariableDeclaration extends JStatement {

    /** Variable type. */
    private Type type;

    /** Variable Modifiers. */
    private ArrayList<String> mods;

    /** Variable name. */
    private String name;

    /** Original Variable initializer. */
    private JExpression initializer;

    /** Analyzed Variable initializer. */
    private JStatement initialization;


    /**
     * Constructs an AST node for a for-each variable declaration given its line
     * number, name, and type.
     * 
     * @param line
     *            line in which the variable occurs in the source file.
     * @param name
     *            variable name.
     * @param type
     *            variable type.
     */

    public JSingleVariableDeclaration(int line, String name, Type type) {
        super(line);
        this.name = name;
        this.type = type;
        this.mods = null;
        this.initializer = null;
        this.initialization = null;
    }

    /**
     * Constructs an AST node for a for-each variable declaration given its line
     * number, name, and type.
     * 
     * @param line
     *            line in which the variable occurs in the source file.
     * @param name
     *            variable name.
     * @param type
     *            variable type.
     * @param mods
     *            the modifiers of the variable
     */

    public JSingleVariableDeclaration(int line, String name, Type type, ArrayList<String> mods) {
        super(line);
        this.name = name;
        this.type = type;
        this.mods = mods;
        this.initializer = null;
        this.initialization = null;
    }

    /**
     * Constructs an AST node for a for-each variable declaration given its line
     * number, name, and type.
     * 
     * @param line
     *            line in which the variable occurs in the source file.
     * @param name
     *            variable name.
     * @param type
     *            variable type.
     * @param mods
     *            the modifiers of the variable
     * @param initializer
     *            the initialization of the variable
     */

    public JSingleVariableDeclaration(int line, String name, Type type, ArrayList<String> mods, JExpression initializer) {
        super(line);
        this.name = name;
        this.type = type;
        this.mods = mods;
        this.initializer = initializer;
        this.initialization = null;
    }

    
    /**
     * Returns the variable's name.
     * 
     * @return the variable's name.
     */

    public String name() {
        return name;
    }

    /**
     * Returns the variable's type.
     * 
     * @return the variable's type.
     */

    public Type type() {
        return type;
    }

    /**
     * Return the modifiers
     * 
     * @return the mods ArrayList.
     */

    public ArrayList<String> mods() {
        return mods;
    }

    /**
     * Sets the type to the specified type.
     * 
     * @param newType
     *            the new type.
     * @return return the new type.
     */

    public Type setType(Type newType) {
        return type = newType;
    }

    /**
     * Determine whether the variable is valid to declare here.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JAST analyze(Context context) {        //Thomas: Report
        // Get the local variable declaration
        int offset = ((LocalContext) context).nextOffset();
        LocalVariableDefn defn = new LocalVariableDefn(type.resolve(context), offset);
        
        // Check for shadowing
        IDefn previousDefn = context.lookup(name);
        if (previousDefn != null
                && previousDefn instanceof LocalVariableDefn) {
            JAST.compilationUnit.reportSemanticError(line,
                    "The name " + name + " overshadows another local variable.");
        }

        // Then declare it in the local context and initialize it
        context.addEntry(line(), name, defn);

        // Initialization must be turned into assignment statement and analyzed
        if (initializer != null) {
            defn.initialize();
            JAssignOp assignOp = new JAssignOp(line(),
                                  new JVariable(line(), name), initializer);
            assignOp.isStatementExpression = true;
            initialization = new JStatementExpression(line(), assignOp).analyze(context);
        }

        return this;
    }

    /**
     * Generate the code for the declaration.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        initialization.codegen(output);
    }

    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JSingleVariableDeclaration line=\"%d\" name=\"%s\" "
                + "type=\"%s\"/>\n", line(), name, (type == null) ? "" : type
                .toString());
    }

}
