// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.util.ArrayList;

class JInterfaceDeclaration extends JAST implements JTypeDecl, JMember {

    /** Interface modifiers. */
    private ArrayList<String> mods;

    /** Interface name */
    private String name;

    /** Interface superType */
    private Type superType;

    /** Static (class) fields of this class. */
    private ArrayList<JFieldDeclaration> staticFieldInitializations;

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
        String packageName = JAST.compilationUnit.packageName();

        String qualifiedName = packageName.equals("") ? name : packageName.replace(".", "/") + "/" + name;

        CLEmitter partial = new CLEmitter(false);

        partial.addClass(mods, qualifiedName, Type.OBJECT.jvmName(), null, false); // Object for superClass, just for
                                                                                   // now
        thisType = Type.typeFor(partial.toClass());
        context.addType(line, thisType);
    }

    /**
     * 
     * @param context the parent (compilation unit) context.
     */

    @Override
    public void preAnalyze(Context context) {
        // Construct a class context
        this.context = new ClassContext(this, context);

        // Resolve superclass
        superType = superType.resolve(this.context);

    }

    @Override
    public JAST analyze(Context context) {
        // Analyze all members
        for (JMember member : interfaceBlock) {
            ((JAST) member).analyze(this.context);
        }

        // Copy declared fields for purposes of initialization.
        for (JMember member : interfaceBlock) {
            if (member instanceof JFieldDeclaration) {
                JFieldDeclaration fieldDecl = (JFieldDeclaration) member;
                if (fieldDecl.mods().contains("static")) {
                    staticFieldInitializations.add(fieldDecl);
                } else {
                    JAST.compilationUnit.reportSemanticError(line(),
                            "Field declaration is not a static member, interfaces may only have static field declarations",
                            member.toString());
                }
            }
        }
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
