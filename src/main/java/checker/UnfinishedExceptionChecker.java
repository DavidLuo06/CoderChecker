package checker;

import com.github.javaparser.ast.stmt.CatchClause;

public class UnfinishedExceptionChecker extends BaseChecker {

    /**
     * just check for the catch stmt content,
     * if it contains TODO OR FIXME, show error
     * message for it.
     */
    @Override
    void check() {
        for(CatchClause d: adapter.catchClause) {
            String catchContent = d.getBody().toString().replace("{", "").replace("}", "").trim();
            if(catchContent.contains("TODO")||catchContent.equals("FIXME")) {
                error("[Error] Line " + d.getBegin().get().line + ": There is a comment such as TODO or FIXME in the catch block of exceptions.");
            }
        }
    }

}
