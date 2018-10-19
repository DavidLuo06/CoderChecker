package checker;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.type.VarType;

import java.util.ArrayList;
import java.util.List;

public class IfChecker extends BaseChecker {

    /**
     * Check for if and else if stmt and get their condition,
     * if the condition is true/false or an const bool var.
     * show error message for it.
     */
    @Override
    void check() {

        for(int i=0; i<adapter.ifStmts.size(); i++) {
            Expression ad = adapter.ifStmts.get(i).getCondition();
            if(ad instanceof BooleanLiteralExpr) {
                error("[Error] Line " + ad.getBegin().get().line + ": This condition always produces the same result as the value of the involved variable was narrowed before. " +
                        "Namely, the condition or the boolean variable in if/while always returns either true or false. Probably something else was meant or condition can be removed.");
            } else if(ad instanceof  NameExpr) {
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
                                if(vd.getCommonType().toString().toLowerCase().equals("boolean")) {
                                    VariableDeclarator vdd = vd.getVariables().get(0);
                                    if(vdd.getNameAsString().equals(((NameExpr) ad).getNameAsString())) {
                                        if(vdd.getInitializer().isPresent()) {
                                            if(vdd.getInitializer().get() instanceof BooleanLiteralExpr) {
                                                error("[Error] Line " + ad.getBegin().get().line + ": This condition always produces the same result as the value of the involved variable was narrowed before. " +
                                                        "Namely, the condition or the boolean variable in if/while always returns either true or false. Probably something else was meant or condition can be removed.");

                                            }
                                        }
                                    }
                                }
                                //if (((NameExpr) ad).getNameAsString().equals())
                            }
                        }
                    }
                }
            }
        }


    }
}