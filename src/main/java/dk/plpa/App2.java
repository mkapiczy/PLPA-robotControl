package dk.plpa;

import dk.plpa.scheme.SchemeConfigurer;
import dk.plpa.scheme.SchemeProcedure;
import dk.plpa.utils.SchemeUtils;

import java.util.Arrays;

public class App2 {

    public static void main(String[] args) {

        SchemeConfigurer schemeConfigurer = new SchemeConfigurer("src/main/scheme/dk.plpa/factorial.scm");
        schemeConfigurer.configureSchemeEnvironment();

        SchemeProcedure loadProcedure = new SchemeProcedure("loadValues");
        Integer[] array = {1, 2, 3, 4, 5};

        loadProcedure.apply1(SchemeUtils.javaListToSchemeList(Arrays.asList(array)));

        SchemeProcedure displayProcedure = new SchemeProcedure("printGlobalValues");
        displayProcedure.apply0();


    }
}
