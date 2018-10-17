package java;

public class CatchBlockLoggingChecker extends StyleChecker {
    public void getError(String progLineCurrent, int lineIndexer, int lineNum) {
        errorTrace("Line "+lineNum+": ","Inadequate logging information in catch blocks. Developers usually rely on logs for error diagnostics when\n" +
                "exceptions occur. However, sometimes, duplicate logging statements in different catch blocks of the same try\n" +
                "block may cause debugging difficulties since the logs fail to tell which exception occurred.\n");
    }
}
