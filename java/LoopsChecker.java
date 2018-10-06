public class LoopsChecker extends StyleChecker {
    public void getError(String progLineCurrent, int lineIndexer, int lineNum) {
        errorTrace("Line "+lineNum+": ","Unneeded computation in loops. There may be unneeded computation in loops, where you call a method inside\n" +
                "a loop, but the return value is never used.\n");

    }
}
