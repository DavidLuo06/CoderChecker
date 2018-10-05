public class OverCatchingChecker  extends StyleChecker {
    public void getError(String progLineCurrent, int lineIndexer, int lineNum) {
        errorTrace("Line "+lineNum+": ",". Over-catching an exception with system-termination. Developers are over-catching an exception (i.e., catching\n" +
                "very high-level exceptions, such as Exception or RunTimeException), and are calling abort or System.exit() in\n" +
                "the catch block");
    }
}
