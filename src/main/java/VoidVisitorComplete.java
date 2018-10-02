

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author i
 * @time 2018.10.1
 */
public class VoidVisitorComplete {
    public static void main(String[] args) throws IOException {
        //creates   file input stream for the file to upload
        FileInputStream fileInputStream =null;
        try {
            fileInputStream = new FileInputStream("Test.java");
            CompilationUnit compilationUnit = JavaParser.parse(fileInputStream);

            System.out.println("=======import ========");
            for (ImportDeclaration importDeclaration:compilationUnit.getImports()){
                System.out.println(importDeclaration.getName());
            }

            System.out.println("=======Methods ========");
            compilationUnit.accept(new MethodVisitor(),null);
            List<String> methodNames = new ArrayList<>();
            VoidVisitor<List<String>> methodNameCollector = new MethodNameCollector();
            methodNameCollector.visit(compilationUnit,methodNames);
            methodNames.forEach(n-> System.out.println("method Name collected:"+n));

        } catch (FileNotFoundException e) {
            System.out.println("#######.java file not found#######");
            e.printStackTrace();
        } finally {
            //close inputStream
            if(fileInputStream!=null){
                fileInputStream.close();
            }
        }
    }
    /**
     * Simple visitor implementation for visiting MethodDeclaration nodes.
     */
    private static class MethodVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(MethodDeclaration n, Void arg) {
            /* here you can access the attributes of the method.
             this method will be called for all methods in this
             CompilationUnit, including inner class methods */
            System.out.println(n.getName());
            super.visit(n, arg);
        }
    }
    private static class MethodNameCollector extends VoidVisitorAdapter<List<String>>{
        @Override
        public void visit(MethodDeclaration n, List<String> arg) {
            super.visit(n, arg);
            arg.add(n.getNameAsString());
        }
    }


}
