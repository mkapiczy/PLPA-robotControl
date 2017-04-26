package dk.plpa;

import dk.plpa.scheme.SchemeConfigurer;
import dk.plpa.scheme.SchemeProcedure;
import dk.plpa.utils.CommandMapper;
import dk.plpa.scheme.SchemeTypesMapper;

import java.util.List;

public class App2 {

    public static void main(String[] args) {

        SchemeConfigurer schemeConfigurer = new SchemeConfigurer("src/main/scheme/dk.plpa/simulation.scm");
        schemeConfigurer.configureSchemeEnvironment();

        SchemeProcedure loadProcedure = new SchemeProcedure("loadCommands");

        List<List<Object>> commands = CommandMapper.createACommandList();

        String s = SchemeTypesMapper.javaListOfListToSchemeListOfPairs(commands);
        loadProcedure.apply1(s);

        SchemeProcedure displayProcedure = new SchemeProcedure("printGlobalValues");
        displayProcedure.apply0();


    }


}
