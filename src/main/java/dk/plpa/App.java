package dk.plpa;


import dk.plpa.scheme.SchemeConfigurer;
import dk.plpa.scheme.SchemeProcedure;
import gnu.math.IntNum;


public class App {


    public static void main(String[] args) {

        SchemeConfigurer schemeConfigurer = new SchemeConfigurer("src/main/scheme/dk.plpa/factorial.scm");
        schemeConfigurer.configureSchemeEnvironment();

        int currentState = 0;
        while(currentState<10){
            SchemeProcedure incrementProcedure = new SchemeProcedure("increment");
            currentState = ((IntNum) incrementProcedure.apply1(currentState)).intValue();
            System.out.println(currentState);
        }
    }




}
