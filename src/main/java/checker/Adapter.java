package checker;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.VarType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.Optional;

/***
 * Collect necessary info for our analyze
 */

public class Adapter extends VoidVisitorAdapter<Object> {

    public ArrayList<MethodDeclaration> methods = new ArrayList();
    public ArrayList<String> usedMethods = new ArrayList();
    public ArrayList<CatchClause> catchClause = new ArrayList();
    public ArrayList<WhileStmt> whileStmts = new ArrayList();
    public ArrayList<ForStmt> forStmts = new ArrayList();
    public ArrayList<IfStmt> ifStmts = new ArrayList();
    public ArrayList<TryStmt> tryStmts = new ArrayList();

    @Override
    public void visit(MethodDeclaration n, Object arg) {
        methods.add(n);
        super.visit(n, arg);
    }


    @Override
    public void visit(ForStmt n, Object arg) {
        forStmts.add(n);
        super.visit(n, arg);
    }

    @Override
    public void visit(WhileStmt n, Object arg) {
        whileStmts.add(n);
        super.visit(n, arg);
    }

    @Override
    public void visit(MethodCallExpr n, Object arg) {

        if (!n.getScope().isPresent()||
                n.getScope().get().toString().equals("this")) {
            usedMethods.add(n.getNameAsString());
        }
        super.visit(n, arg);
    }

    @Override
    public void visit(IfStmt n, Object arg) {
        ifStmts.add(n);
        super.visit(n, arg);
    }

    @Override
    public void visit(CatchClause n, Object arg) {
        catchClause.add(n);

        super.visit(n, arg);
    }
    @Override
    public void visit(TryStmt n, Object arg) {
        tryStmts.add(n);
        super.visit(n, arg);
    }
}
