

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.JsonPrinter;
import javassist.compiler.ast.Variable;

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
//
//            System.out.println("=======import ========");
////            compilationUnit.getMetaModel().
//            for (ImportDeclaration importDeclaration:compilationUnit.getImports()){
//                System.out.println(importDeclaration.getName());
//            }
//            System.out.println("=======class ========");
//            System.out.println(compilationUnit.getTypes().get(0).getName());
            //find bug1
            // list all methods
            System.out.println("=======methods ========");
            compilationUnit.accept(new MethodVisitor(),null);
            //get all
            List<String> methodNames = new ArrayList<>();
            VoidVisitor<List<String>> methodNameCollector = new MethodNameCollector();
            methodNameCollector.visit(compilationUnit,methodNames);

            if (methodNames.contains("equals")&&!methodNames.contains("hashCode")){
                System.out.println("=======Bugs 1: Class defines equals() but not hashCode() ========");
            }
            //list all field
            System.out.println("=======fieldsDeclaration ========");
            List<FieldDeclaration> filedDecs = new ArrayList<>();
            VoidVisitor<List<FieldDeclaration>> fieldCollector = new FieldCollector();
            fieldCollector.visit(compilationUnit,filedDecs);
            for(FieldDeclaration fieldDeclaration:filedDecs){
                System.out.println(fieldDeclaration.toString());
            }
            //list all variables
            System.out.println("=======Variables ========");
            List<VariableDeclarationExpr> variableDeclarationExprs = new ArrayList<>();
            VoidVisitor variableDeclarationExprCollector = new VariableDeclarationExprCollector();
            variableDeclarationExprCollector.visit(compilationUnit,variableDeclarationExprs);
            for (VariableDeclarationExpr expr:variableDeclarationExprs){
                System.out.println(expr.toString());
            }
            //find bug 4
            //get ifstatment
            System.out.println("=======ifstmt ========");
            List<IfStmt> IfStmts = new ArrayList<>();
            VoidVisitor<List<IfStmt>> IfStmtCollector = new ExpressEqualsCollector();
            IfStmtCollector.visit(compilationUnit,IfStmts);
            IfStmts.forEach(n-> System.out.println(n));

//            for(Expression expression:){
//
//            }

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
    private static class ExpressEqualsCollector extends VoidVisitorAdapter<List<IfStmt>>{

        @Override
        public void visit(IfStmt n, List<IfStmt> arg) {
            super.visit(n, arg);
            arg.add(n);
        }
    }
    private static class FieldCollector extends  VoidVisitorAdapter<List<FieldDeclaration>>{
        @Override
        public void visit(FieldDeclaration n, List<FieldDeclaration> arg) {
            super.visit(n,arg);
            arg.add(n);
        }
    }
    private static class VariableDeclarationExprCollector extends  VoidVisitorAdapter<List<VariableDeclarationExpr>>{
        @Override
        public void visit(VariableDeclarationExpr n, List<VariableDeclarationExpr> arg) {
            super.visit(n, arg);
            arg.add(n);
        }
    }



}
