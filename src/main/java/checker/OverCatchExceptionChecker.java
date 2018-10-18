package checker;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ExpressionStmt;

import java.util.List;

public class OverCatchExceptionChecker extends BaseChecker {

    /**
     *  first find those over catch stmt (i.e. Exception or RuntimeException)
     *  Then loop for those children node, if has method call named exit or
     *  abort show error message for it;
     * */
    @Override
    void check() {
        for(CatchClause d: adapter.catchClause) {

            List<Node> ln = d.getBody().getChildNodes();
            for(Node n : ln) {
                if(n instanceof ExpressionStmt) {
                    if(n.getChildNodes().get(0) instanceof MethodCallExpr){
                        String name = ((MethodCallExpr) n.getChildNodes().get(0)).getNameAsString();
                        if(d.getParameter().getType().toString().equals("Exception")||
                                d.getParameter().getType().toString().equals("RuntimeException")) {
                            if (name.equals("abort") || name.equals("exit")) {
                                error("[Error] Line " + n.getChildNodes().get(0).getBegin().get().line + ": Developers are over-catching an exception " +
                                        "(i.e., catching very high-level exceptions, such as Exception or RunTimeException), and are calling abort or System.exit() in the catch block.");
                            }
                        }
                    }
                }
            }

        }
    }

}
