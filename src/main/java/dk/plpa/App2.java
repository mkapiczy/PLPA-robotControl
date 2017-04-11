package dk.plpa;

import dk.plpa.scheme.SchemeConfigurer;
import dk.plpa.scheme.SchemeProcedure;
import gnu.math.IntNum;

import java.util.List;

public class App2 {

    public static void main(String[] args) {

        SchemeConfigurer schemeConfigurer = new SchemeConfigurer("src/main/scheme/dk.plpa/factorial.scm");
        schemeConfigurer.configureSchemeEnvironment();

        int initialState = 0;
        SchemeProcedure incrementProcedure = new SchemeProcedure("incrementAtOnce");
        List<IntNum> schemeResult = (List) incrementProcedure.apply1(initialState);

        for(IntNum step : schemeResult){
            System.out.println(step);
        }

    }
}
