public class ConditionChecker  extends StyleChecker {
    public void getError(String progLineCurrent, int lineIndexer, int lineNum) {
        errorTrace("Line "+lineNum+": ","Condition has no effect. This condition always produces the same result as the value of the involved variable\n" +
                "was narrowed before. Namely, the condition or the boolean variable in if/while always returns either true or\n" +
                "false. Probably something else was meant or condition can be removed. ");
    }
}
