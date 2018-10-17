package checker;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.ExpressionStmt;

import java.util.ArrayList;
import java.util.List;

public class StringEqualChecker extends BaseChecker {

    @Override
    void check() {
        // avoid for repeat error hint
        List<Integer> lineCode = new ArrayList();
        List<BinaryExpr> node = cu.getNodesByType(BinaryExpr.class);
        for(BinaryExpr bin: node) {
            if (bin.getOperator().equals(BinaryExpr.Operator.EQUALS)||
                    bin.getOperator().equals(BinaryExpr.Operator.NOT_EQUALS)) {
                if(bin.getLeft() instanceof NullLiteralExpr || bin.getRight() instanceof NullLiteralExpr) {
                    continue;
                }
                if(bin.getLeft() instanceof StringLiteralExpr) {
                    int code = bin.getBegin().get().line;
                    if(!lineCode.contains(code)) lineCode.add(code);
                } else if(bin.getLeft() instanceof NameExpr) {
                    if(checkForStringType((NameExpr) bin.getLeft())) {
                        int code = bin.getBegin().get().line;
                        if(!lineCode.contains(code)) lineCode.add(code);
                    }
                }
            }
        }
        for(Integer i: lineCode) {
            error("[Error] Line " + i + ": This code compares java.lang.String objects for reference equality using the == or != operators." +
                    " Unless both strings are either constants in a source file, or have been interned using the String.intern() method," +
                    " the same string value may be represented by two different String objects. Consider using the equals(Object) method instead.");
        }
    }


    private boolean checkForStringType(NameExpr ad) {
        if(ad instanceof NameExpr) {
            Node xh = ad;
            while(true) {
                // search upper class stmt for this variable
                if(!xh.getParentNode().isPresent()) {
                    break;
                }
                xh = xh.getParentNode().get();
                if(xh instanceof CompilationUnit) {
                    break;
                }
                List<Node> c = xh.getChildNodes();
                for(Node nd: c) {
                    if(nd instanceof ExpressionStmt) {
                        Node decNode = nd.getChildNodes().get(0);
                        if (decNode instanceof VariableDeclarationExpr) {
                            VariableDeclarationExpr vd = ((VariableDeclarationExpr) decNode).asVariableDeclarationExpr();
                            if(vd.getCommonType().toString().toLowerCase().equals("string")) {
                                VariableDeclarator vdd = vd.getVariables().get(0);
                                if(vdd.getNameAsString().equals(((NameExpr) ad).getNameAsString())) {
                                   return true;
                                }
                            }
                            //if (((NameExpr) ad).getNameAsString().equals())
                        }
                    }
                }
            }
        }
        return false;
    }

}
