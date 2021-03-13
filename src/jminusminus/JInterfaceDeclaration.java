// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.util.ArrayList;

class JInterfaceDeclaration extends JAST implements JTypeDecl, JMember {

    /** Interface modifiers. */
    private ArrayList<String> mods;

    /** Interface name */
    private String name;
    private Type superType;

    /** Interface block */
    private ArrayList<JMember> interfaceBlock;

    /** super interface */
    private ArrayList<TypeName> extend;

    /** This interface type */
    private Type thisType;

    /** Context for this interface */

    private ClassContext context;

    /** Has explicit constructor? */

    private boolean hasExplicitConstructor;

    /** Instance fields of this interface. */

    private ArrayList<JFieldDeclaration> instanceFieldInitializations;

    public JInterfaceDeclaration(int line, ArrayList<String> mods, String name, ArrayList<TypeName> extend,
            ArrayList<JMember> interfaceBlock) {
        super(line);
        this.mods = mods;
        this.name = name;
        this.extend = extend;
        this.interfaceBlock = interfaceBlock;
        hasExplicitConstructor = false;
        instanceFieldInitializations = new ArrayList<JFieldDeclaration>();
    }

    /**
     * Return the interface name.
     * 
     * @return the interface name.
     */

    public String name() {
        return name;
    }

    public Type superType() {
        return superType;
    }

    /**
     * Return the type that this interface declaration defines.
     * 
     * @return the defined type.
     */

    public Type thisType() {
        return thisType;
    }

    /**
     * The initializations for instance fields (now expressed as assignment
     * statments).
     * 
     * @return the field declarations having initializations.
     */

    public ArrayList<JFieldDeclaration> instanceFieldInitializations() {
        return instanceFieldInitializations;
    }

    /**
     * Declare this interface in the parent (compilation unit) context.
     * 
     * @param context the parent (compilation unit) context.
     */

    public void declareThisType(Context context) {

    }

    /**
     * Pre-analyze the members of this declaration in the parent context.
     * Pre-analysis extends to the member headers (including method headers) but not
     * into the bodies.
     * 
     * @param context the parent (compilation unit) context.
     */

    public void preAnalyze(Context context) {

    }

    @Override
    public JAST analyze(Context context) {
        return this;
    }

    public void codegen(CLEmitter output) {

    }

    @Override
    public void preAnalyze(Context context, CLEmitter partial) {

    }

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JInterfaceDeclaration line=\"%d\" name=\"%s\">\n", line(), name);
        p.indentRight();
        if (context != null) {
            context.writeToStdOut(p);
        }
        if (mods != null) {
            p.println("<Modifiers>");
            p.indentRight();
            for (String mod : mods) {
                p.printf("<Modifier name=\"%s\"/>\n", mod);
            }
            p.indentLeft();
            p.println("</Modifiers>");
        }
        if (extend != null) {
            p.println("<Extends>");
            p.indentRight();
            for (TypeName extended : extend) {
                p.printf("<Extends name=\"%s\"/>\n", extended.toString());
            }
            p.indentLeft();
            p.println("</Extends>");
        }
        if (interfaceBlock != null) {
            p.println("<InterfaceBlock>");
            for (JMember member : interfaceBlock) {
                ((JAST) member).writeToStdOut(p);
            }
            p.println("</InterfaceBlock>");
        }
        p.indentLeft();
        p.println("</JInterfaceDeclaration>");
    }

}
