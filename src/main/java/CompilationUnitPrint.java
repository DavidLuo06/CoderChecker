import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author i
 * @time 2018.10.1
 */
public class CompilationUnitPrint {
    public static void main(String[] args) throws IOException {
        //creates   file input stream for the file to upload
        FileInputStream fileInputStream =null;
        try {
            fileInputStream = new FileInputStream("Test.java");
            CompilationUnit compilationUnit = JavaParser.parse(fileInputStream);
            System.out.println(compilationUnit.toString());
            System.out.println("=======import ========");
            for (ImportDeclaration importDeclaration:compilationUnit.getImports()){
                System.out.println(importDeclaration.getName());
            }
            System.out.println("=======class ========");
            System.out.println(compilationUnit.getClass().getName());

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
}
