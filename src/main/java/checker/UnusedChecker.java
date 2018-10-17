package checker;

import com.github.javaparser.ast.body.MethodDeclaration;

public class UnusedChecker extends BaseChecker {

    /**
     *  first collect all method declared in this code file,
     *  then collect method call which use field this or empty
     *  compare this two collection, the different method never
     *  been called. show error message for it.
     * */
    @Override
    void check() {
        for(MethodDeclaration d: adapter.methods) {
            if(!adapter.usedMethods.contains(d.getNameAsString())) {

                // filter main, clone, equals, hasCode method
                if(d.getDeclarationAsString().contains("public")) continue;
                if(!d.getNameAsString().equals("main") && !d.getNameAsString().equals("clone")
                &&!d.getNameAsString().equals("equals") && !d.getNameAsString().equals("hashCode")) {
                    error("[Error] Line " + d.getBegin().get().line + ": method " + d.getNameAsString() +
                            " never used in the code.");
                }
            }
        }
    }

}
