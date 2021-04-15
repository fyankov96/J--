package jminusminus;

/**
 * The AST node for a for-each variable declaration.
 */

class JForEachVariable extends JAST {

    /** Modifiers. */
    private boolean isFinal;

    /** variable name. */
    private String name;

    /** variable type. */
    private Type type;

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

    public JForEachVariable(int line, String name, Type type) {
        super(line);
        this.name = name;
        this.type = type;
        this.isFinal = false;
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
     * @param isFinal
     *            a flag for whether the variable is final
     */

    public JForEachVariable(int line, String name, Type type, boolean isFinal) {
        super(line);
        this.name = name;
        this.type = type;
        this.isFinal = isFinal;
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
     * Return the flag isFinal.
     * 
     * @return the isFinal boolean.
     */

    public boolean isFinal() {
        return isFinal;
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

    public JAST analyze(Context context) {
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
        //Thomas: Report
        // Then initialize it and declare it in the local context
        defn.initialize();
        context.addEntry(line, name, defn);
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
        //TODO implement   
    }

    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JForEachVariable line=\"%d\" name=\"%s\" "
                + "type=\"%s\"/>\n", line(), name, (type == null) ? "" : type
                .toString());
    }

}
