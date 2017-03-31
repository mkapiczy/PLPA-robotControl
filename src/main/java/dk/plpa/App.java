package dk.plpa;


import gnu.mapping.Environment;
import kawa.standard.Scheme;

import static kawa.standard.Scheme.registerEnvironment;


public class App {
    public static void main(String[] args) {
        registerEnvironment();
        Environment.setCurrent(new Scheme().getEnvironment());
        Object x;

        x = Scheme.eval("(+ 3 2)", Environment.getCurrent());

        System.out.println(x);
    }

}
