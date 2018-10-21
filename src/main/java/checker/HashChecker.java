package checker;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;

public class HashChecker extends BaseChecker {

    /**
     *  Collect all method declared in the class,
     *  if equals declared and hashCode not declared,
     *  show error message
     * */
    @Override
    void check() {
        boolean haveEquals = false;
        boolean haveHashCode = false;
        int lineCode = -1;
        for(MethodDeclaration d: adapter.methods) {
            if (d.getNameAsString().equals("equals")) {
                NodeList<Parameter> p = d.getParameters();
                if(p.size()==1&&p.get(0).getType().toString().equals("Object")) {
                    haveEquals = true;
                    lineCode = d.getBegin().get().line;
                }
            }
            if (d.getNameAsString().equals("hashCode")&&d.getParameters().size()==0) haveHashCode = true;
            if (haveEquals&&haveHashCode) break;
        }
        if (haveEquals&&!haveHashCode) {
            error("[Error] Line "+lineCode+": This class overrides equals(Object), but does not override hashCode(). " +
                    "Therefore, the class may violate the invariant that equal objects must have equal hashcodes.");
        }
    }

}
