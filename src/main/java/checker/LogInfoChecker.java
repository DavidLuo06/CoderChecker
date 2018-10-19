package checker;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ExpressionStmt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogInfoChecker extends BaseChecker {

    /**
     *  Use an hash map to store which try the catch is
     *  Then loop each item of the hash map
     *  if it has over 1 item, that means this try has
     *  over 1 catch, compare them with each other
     *  and search for the same string info
     *  When same string info appearance show error message
     *  for the same log.
     * */

    @Override
    void check() {
        HashMap<Integer, ArrayList<CatchClause>> myCatchCollection = new HashMap();
        for(CatchClause d: adapter.catchClause) {
            int index = d.getParentNode().get().getBegin().get().line;

            ArrayList<CatchClause> n = myCatchCollection.get(index);
            if(n == null) {
                n = new ArrayList();
                n.add(d);
                myCatchCollection.put(index, n);
            } else {

                n.add(d);
            }
        }

        for(int index: myCatchCollection.keySet()) {
            if(myCatchCollection.get(index).size()>1) {
                ArrayList<String> s = new ArrayList();
                for(CatchClause c: myCatchCollection.get(index)) {
                    List<Node> ln = c.getBody().getChildNodes();
                    for (Node n : ln) {
                        if(n instanceof ExpressionStmt) {
                            if(n.getChildNodes().get(0) instanceof MethodCallExpr) {
                                MethodCallExpr me = (MethodCallExpr) n.getChildNodes().get(0);
                                if(me.getNameAsString().toLowerCase().contains("log")) {
                                    // if its an log method combile the method name and message
                                    String sign = me.getNameAsString()+"-"+me.getArguments();
                                    if(s.contains(sign)) {
                                        error("[Error] Line " + me.getBegin().get().line + ": Developers usually rely on logs for error diagnostics when exceptions occur. However, sometimes, " +
                                                "duplicate logging statements in different catch blocks of the same try block may cause debugging difficulties since the logs fail to tell which exception occurred.");
                                    } else {
                                        s.add(sign);
                                    }

                                }
                            }
                        }
                    }
                }
                // more than one catch for the try
            }
        }
    }

}
