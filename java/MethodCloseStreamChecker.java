public class MethodCloseStreamChecker extends StyleChecker  {
    public void getError(String progLineCurrent, int lineIndexer, int lineNum) {
        errorTrace("Line "+lineNum+": ","Method may fail to close stream on exception. The method creates an IO stream object, does not assign it to\n" +
                "any fields, pass it to other methods, or return it, and does not appear to close it on all possible exception paths\n" +
                "out of the method. This may result in a file descriptor leak. It is generally a good idea to use a finally block to\n" +
                "ensure that streams are closed.");
    }
}
