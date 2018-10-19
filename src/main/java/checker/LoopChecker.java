package checker;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.WhileStmt;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LoopChecker extends BaseChecker {

    /**
     * Check for forStmt and WhileStmt,
     * first collect all return var name, then recursion for all Method Call Stmt
     * in the while loopStmt, if there no any var name appearance, show error message
     * for it.
     * */
    @Override
    void check() {


        for(ForStmt d: adapter.forStmts) {

            boolean neverUse = true;
            ArrayList<String> calResult = new ArrayList();
            ArrayList<MethodCallExpr> methodCall= new ArrayList();

            List<Node> a = d.getBody().getChildNodes();
            for(Node dd: a) {
                if(dd.getChildNodes().size()==0) break;
                Node aa = dd.getChildNodes().get(0);

                if(aa instanceof VariableDeclarationExpr) {
                    NodeList<VariableDeclarator> c =
                            ((VariableDeclarationExpr)aa).getVariables();


                    for(int i=0; i<c.size(); i++){

                        calResult.add(c.get(i).getName().toString());
                    }

                } else if (aa instanceof AssignExpr) {

                    calResult.add(((AssignExpr) aa).getTarget().toString());
                }
                else if(aa instanceof MethodCallExpr){
                    methodCall.add((MethodCallExpr) aa);
                }
            }
            for(MethodCallExpr c: methodCall) {
                if(checkSubNode(c.getArguments(), calResult)){
                    neverUse = false;
                }
            }

            if(calResult.size()!=0&&neverUse) {
                error("[Error] Line "+d.getBody().getBegin().get().line+
                        ": There may be unneeded computation in loops, where you call a method inside a loop, but the return value is never used.");
            }
        }

        for(WhileStmt d: adapter.whileStmts) {

            boolean neverUse = true;
            ArrayList<String> calResult = new ArrayList();
            ArrayList<MethodCallExpr> methodCall= new ArrayList();

            List<Node> a = d.getBody().getChildNodes();
            for(Node dd: a) {
                if(dd.getChildNodes().size()==0) break;
                Node aa = dd.getChildNodes().get(0);

                if(aa instanceof VariableDeclarationExpr) {
                    NodeList<VariableDeclarator> c =
                            ((VariableDeclarationExpr)aa).getVariables();


                    for(int i=0; i<c.size(); i++){

                        calResult.add(c.get(i).getName().toString());
                    }

                } else if (aa instanceof AssignExpr) {

                    calResult.add(((AssignExpr) aa).getTarget().toString());
                }
                else if(aa instanceof MethodCallExpr){
                    methodCall.add((MethodCallExpr) aa);
                }
            }
            for(MethodCallExpr c: methodCall) {
                if(checkSubNode(c.getArguments(), calResult)){
                    neverUse = false;
                }
            }

            if(calResult.size()!=0&&neverUse) {
                error("[Error] Line "+d.getBody().getBegin().get().line+
                        ": There may be unneeded computation in loops, where you call a method inside a loop, but the return value is never used.");
            }
        }

    }

    private boolean checkSubNode(NodeList<Expression> e, ArrayList<String> calResult) {
        for(int i=0; i<e.size(); i++) {
            if(e.get(i) instanceof BinaryExpr) {
                NodeList<Expression> ne =  new NodeList ();
                ne.add(((BinaryExpr) e.get(i)).getLeft());
                ne.add(((BinaryExpr) e.get(i)).getRight());

                if(checkSubNode(ne, calResult)) return true;
            } else if(e.get(i) instanceof MethodCallExpr){

             if(checkSubNode(((MethodCallExpr) e.get(i)).getArguments(), calResult)) return true;
            } else {

                if(calResult.contains(e.get(i).toString())) return true;
            }

        }
        return false;
    }

}
