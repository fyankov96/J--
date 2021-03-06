// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

class JInterfaceDeclaration extends JAST implements JTypeDecl {

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

    /** Interface super type */
    private ArrayList<Type> interfaceSuperTypes;

    /** This interface type */
    private Type thisType;

    /** Context for this interface */

    private ClassContext context;

    /** Has explicit constructor? */

    private boolean hasExplicitConstructor;

    /** Instance fields of this interface. */

    private ArrayList<JFieldDeclaration> instanceFieldInitializations;

    public JInterfaceDeclaration(int line, ArrayList<String> mods, String name, ArrayList<Type> extend,
            ArrayList<JMember> interfaceBlock) {
        super(line);
        this.mods = mods;
        mods.add("interface");
        mods.add("abstract");
        this.name = name;
        this.interfaceSuperTypes = extend;
        this.interfaceBlock = interfaceBlock;
        this.staticFieldInitializations = this.interfaceBlock.stream()
            .filter(x -> x instanceof JFieldDeclaration)
            .map(x -> (JFieldDeclaration) x)
            .collect(Collectors.toCollection(ArrayList::new));
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
     * 
     * pass/Interface
     */

    public void declareThisType(Context context) {
        String packageName = JAST.compilationUnit.packageName();
        String qualifiedName = JAST.compilationUnit.packageName() == "" ? 
            name : JAST.compilationUnit.packageName() + "/" + name;
        

        CLEmitter partial = new CLEmitter(false);

        partial.addClass(mods, qualifiedName, Type.OBJECT.jvmName(), null, false);

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

        // Resolve superinterfaces
        interfaceSuperTypes = interfaceSuperTypes.stream().map(x -> x.resolve(this.context))
                .collect(Collectors.toCollection(ArrayList::new));


        //ArrayList<String> interfaceJVMNames = this.interfaceSuperTypes.stream().map(x -> x.jvmName())
        //        .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> interfaceJVMNames = this.interfaceSuperTypes.stream().map(x -> x.jvmName())
                .collect(Collectors.toCollection(ArrayList::new));
        
        // Create the (partial) class
        CLEmitter partial = new CLEmitter(false);

        // Add the class header to the partial class
        String qualifiedName = JAST.compilationUnit.packageName() == "" ? name
                : JAST.compilationUnit.packageName() + "/" + name;
        partial.addClass(mods, qualifiedName, Type.OBJECT.jvmName(), interfaceJVMNames, false);

        // Pre-analyze the members
        for (JMember member : interfaceBlock) {
            if (!(member instanceof JMethodDeclaration || member instanceof JFieldDeclaration)) {
                JAST.compilationUnit.reportSemanticError(line(), "Member %s is not a valid interface member",
                        member.toString());
            }
            if (member instanceof JMethodDeclaration) {
                JMethodDeclaration method = (JMethodDeclaration) member;
                if(!method.isStatic()) {
                    method.setAbstract();
                } //If the method is not static set it to abstract
                member = method;
            }   
            if (member instanceof JFieldDeclaration) {
                JFieldDeclaration field = (JFieldDeclaration) member;
                field.setStatic();
                field.setFinal();
                member = field;
            }   

            member.preAnalyze(this.context, partial);
        }

        // Get the Class rep for the (partial) class and make it
        // the representation for this type
        Type id = this.context.lookupType(name);
        if (id != null && !JAST.compilationUnit.errorHasOccurred()) {
            id.setClassRep(partial.toClass());
        }
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
        ArrayList<String> interfaceJVMNames = interfaceSuperTypes.stream().map(x -> x.jvmName())
                .collect(Collectors.toCollection(ArrayList::new));

        // Add the class header
        String qualifiedName = JAST.compilationUnit.packageName() == "" ? name
                : JAST.compilationUnit.packageName() + "/" + name;
        output.addClass(mods, qualifiedName, Type.OBJECT.jvmName(), interfaceJVMNames, false);

        // Generate code for the interface members
        for (JMember member : interfaceBlock) {
            ((JAST) member).codegen(output);
        }
        
        // Generate code for the static fields
        for (JFieldDeclaration staticField : staticFieldInitializations) {
            staticField.codegenInitializations(output);
        }
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
        if (interfaceSuperTypes != null) {
            p.println("<Extends>");
            p.indentRight();
            for (Type extended : interfaceSuperTypes) {
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
