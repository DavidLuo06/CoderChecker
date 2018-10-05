public class ClassHashCodeChecker extends StyleChecker{
    public void getError(String progLineCurrent, int lineIndexer, int lineNum) {



        errorTrace("Line "+lineNum+": ",". Class defines equals() but not hashCode(). This class overrides equals(Object), but does not override hashCode().\n" +
                "Therefore, the class may violate the invariant that equal objects must have equal hashcodes.");
    }
}
