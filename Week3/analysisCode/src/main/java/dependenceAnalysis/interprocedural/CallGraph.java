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

        callGraph = new Graph<Signature>();

        buildGraph();

    }

    protected void buildGraph(){
        for(ClassNode cn : classNodes.values()){
            for(MethodNode mn : cn.methods){
                // add mn to the call graph...
            }
        }
    }




    public String toString(){
        return callGraph.toString();
    }


    public Graph<Signature> getCallGraph(){
        return callGraph;
    }

}
