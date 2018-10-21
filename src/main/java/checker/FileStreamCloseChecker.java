package checker;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.TryStmt;

import java.util.List;

public class FileStreamCloseChecker extends BaseChecker {

    /**
     *  first search for the stream class which not assign or pass to other method,
     *  show error message for it , then search for stream class that assign and
     *  there no close code in final block
     * */
    @Override
    void check() {
        for(TryStmt ts: adapter.tryStmts) {
            List<Node> nl = ts.getTryBlock().getChildNodes();
            for(Node n: nl) {
                if(n instanceof ExpressionStmt) {
                    Node c = n.getChildNodes().get(0);
                    if(c instanceof ObjectCreationExpr){
                        // stream that not assign
                        if(((ObjectCreationExpr) c).getType().toString().toLowerCase().contains("stream")) {
                            error("[Error] Line " + c.getBegin().get().line + ":  The method creates an IO stream object, does not assign it to any fields, " +
                                    "pass it to other methods, or return it, and does not appear to close it on all possible exception paths out of the method. " +
                                    "This may result in a file descriptor leak. It is generally a good idea to use a finally block to ensure that streams are closed. ");

                        }
                    } else if (c instanceof AssignExpr) {
                        if(!((AssignExpr)c).getValue().toString().toLowerCase().contains("stream")) continue;
                        if(!ts.getFinallyBlock().isPresent()) {
                            error("[Error] Line " + c.getBegin().get().line + ":  The method creates an IO stream object, does not assign it to any fields, " +
                                    "pass it to other methods, or return it, and does not appear to close it on all possible exception paths out of the method. " +
                                    "This may result in a file descriptor leak. It is generally a good idea to use a finally block to ensure that streams are closed. ");
                        } else {
                            List<Node> ndd = ts.getFinallyBlock().get().getChildNodes();
                            boolean notClose = true;
                            for(Node n2: ndd) {
                                if(n2 instanceof ExpressionStmt) {
                                    if(n2.getChildNodes().get(0) instanceof MethodCallExpr){
                                        MethodCallExpr md = (MethodCallExpr) n2.getChildNodes().get(0);

                                        if(md.getScope().isPresent()&&md.getScope().get().toString().equals(((AssignExpr)c).getTarget().toString())) {
                                            if(md.getName().toString().equals("close")) {
                                                notClose = false;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if(notClose) {
                                error("[Error] Line " + c.getBegin().get().line + ":  The method creates an IO stream object, does not assign it to any fields, " +
                                        "pass it to other methods, or return it, and does not appear to close it on all possible exception paths out of the method. " +
                                        "This may result in a file descriptor leak. It is generally a good idea to use a finally block to ensure that streams are closed. ");

                            }
                        }
                    }
                }
            }
        }
    }

}
