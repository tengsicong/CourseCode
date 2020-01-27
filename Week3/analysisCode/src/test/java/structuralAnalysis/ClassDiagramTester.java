package structuralAnalysis;

import org.junit.Test;

public class ClassDiagramTester {

    @Test
    public void analysisCodeClassDiagramTest(){
        ClassDiagram cd = new ClassDiagram("target/classes", true, true, "");
        System.out.println(cd.toString());
    }

}
