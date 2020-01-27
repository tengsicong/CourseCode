package dependenceAnalysis.interprocedural;

import org.junit.Test;

public class CallGraphTester {

    @Test
    public void callGraphTester(){
        CallGraph cg = new CallGraph("target/classes");
        System.out.println(cg.getCallGraph());
    }
}
