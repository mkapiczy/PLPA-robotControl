package dk.plpa.scheme;

import gnu.mapping.Environment;
import gnu.mapping.Procedure1;
import kawa.standard.Scheme;


public class SchemeProcedure extends Procedure1 {
    private String name;

    public SchemeProcedure(String name) {
        super(name);
        this.name = name;
    }

    public Object apply1(Object arg1) {
        Object result = Scheme.eval("(" + this.name + " " + arg1 + ")", Environment.getCurrent());
        return result;
    }

}
