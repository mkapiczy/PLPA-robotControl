package dk.plpa;

import dk.plpa.scheme.SchemeConfigurer;
import dk.plpa.scheme.SchemeProcedure;
import dk.plpa.utils.SchemeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App2 {

    public static void main(String[] args) {
        List<String> files = new ArrayList<>();
        files.add("src/main/scheme/dk.plpa/factorial.scm");
        SchemeConfigurer schemeConfigurer = new SchemeConfigurer(files);
        schemeConfigurer.configureSchemeEnvironment();

        SchemeProcedure loadProcedure = new SchemeProcedure("loadValues");
        Integer[] array = {1, 2, 3, 4, 5};

        loadProcedure.apply1(SchemeUtils.javaListToSchemeList(Arrays.asList(array)));

        SchemeProcedure displayProcedure = new SchemeProcedure("printGlobalValues");
        displayProcedure.apply0();


    }
}
