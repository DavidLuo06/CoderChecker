package checker;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ExpressionStmt;

import java.util.List;

public class EmptyExceptionChecker extends BaseChecker {

    /**
     *  If the catch stmt empty (except for Comment),
     *  show error message for this line
     * */
    @Override
    void check() {
        for(CatchClause d: adapter.catchClause) {
            //d.getBody().getChildNodes()

            boolean empty = true;
            List<Node> ln = d.getBody().getChildNodes();
            for(Node n : ln) {
                if(!(n instanceof Comment))
                    empty = false;
            }
            if(empty)
                error("[Error] Line " + d.getBegin().get().line + ": There is no debug message when an exception occurs, which may cause debugging difficulties.");
        }
    }

}
