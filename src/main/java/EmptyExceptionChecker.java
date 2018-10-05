public class EmptyExceptionChecker  extends StyleChecker {
    public void getError(String progLineCurrent, int lineIndexer, int lineNum) {
        errorTrace("Line "+lineNum+": ","Empty exception. There is no debug message when an exception occurs, which may cause debugging difficulties.");
    }
}
