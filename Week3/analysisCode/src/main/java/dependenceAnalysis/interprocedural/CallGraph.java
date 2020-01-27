package dependenceAnalysis.interprocedural;


import dependenceAnalysis.util.Graph;
import dependenceAnalysis.util.Signature;
import org.objectweb.asm.tree.*;
import util.ASMClassReader;

import java.io.File;
import java.util.*;

/**
 * Created by neilwalkinshaw on 26/07/2018.
 */
public class CallGraph {

    // Map of inheritance tree, from class to set of sub-classes.
    protected Map<ClassNode,Set<ClassNode>> subclasses;

    // Map to link class names to corresponding ClassNode objects
    protected Map<String,ClassNode> classNodes;

    // Graph structure to store call graph. Nodes are method Signature objects
    protected Graph<Signature> callGraph;



    /**
     * Constructor, taking as input root-directory (String) for project.
     * @param root
     */
    public CallGraph(String root){

        File dir = new File(root);

        classNodes = new HashMap<String,ClassNode>();

        ASMClassReader acr = new ASMClassReader();

        // Traverse root directory structure and obtain all classes.
        List<ClassNode> classes = acr.processDirectory(dir,"");

        for(ClassNode cn : classes){
            classNodes.put(cn.signature,cn);
        }

        subclasses = new HashMap<ClassNode, Set<ClassNode>>();

        //For each class,
        buildInheritanceTrees(classes);

        callGraph = new Graph<Signature>();

        buildGraph();

    }

    protected void buildGraph(){
        for(ClassNode cn : classNodes.values()){
            for(MethodNode mn : cn.methods){
                process(cn,mn);
            }
        }
    }


    /**
     * From the list classes, map each class to the set of sub-classes.
     * This is accomplished by, for every class X, identifying its super-class (ClassNode.superName),
     * obtaining the set of sub-classes for that super-class, and adding X to that set.
     * Should store inheritance tree in subClasses map (from parent class to set of sub-classes).
     *
     * @param classes
     */
    private void buildInheritanceTrees(List<ClassNode> classes) {
        // INSERT CODE HERE.
    }


    protected void process(ClassNode owner,MethodNode current){
        Signature fromSig = new Signature(owner.name,current.name,current.desc);
        InsnList instructions = current.instructions;
        for(int i = 0; i<instructions.size(); i++){
            AbstractInsnNode instruction = instructions.get(i);
            //If the instruction is a call to another method...
            if(instruction.getType() == AbstractInsnNode.METHOD_INSN){
                MethodInsnNode call = (MethodInsnNode) instruction;
                Signature target = new Signature(call.owner,call.name,call.desc);
                String targetClassName = target.getOwner();
                if(!classNodes.containsKey(targetClassName))
                    continue;
                ClassNode targetClass = classNodes.get(targetClassName);
                Collection<MethodNode> targetsForCall = getAllCandidates(target, targetClass);
                for(MethodNode mn : targetsForCall){
                    Signature targetSig = new Signature(targetClassName,mn.name,mn.desc);
                    callGraph.addNode(fromSig);
                    callGraph.addNode(targetSig);
                    callGraph.addEdge(fromSig,targetSig);
                }
            }
        }
    }


    /**
     * for a given target method in a target class, check whether there are other potential
     * targets within the class hierarchy below targetClass, and add these to the call graph
     * (stored in the callGraph class attribute).
     * @param target
     * @param targetClass
     * @return
     */
    private Collection<MethodNode> getAllCandidates(Signature target, ClassNode targetClass) {
        //INSERT CODE HERE.
        return null;
    }


    public String toString(){
        return callGraph.toString();
    }


    public Graph<Signature> getCallGraph(){
        return callGraph;
    }

}
