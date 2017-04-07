package dk.plpa;


import dk.plpa.scheme.SchemeConfigurer;
import dk.plpa.scheme.SchemeProcedure;


public class App {


    public static void main(String[] args) {

        SchemeConfigurer schemeConfigurer = new SchemeConfigurer("src/main/scheme/dk.plpa/factorial.scm");
        schemeConfigurer.configureSchemeEnvironment();

        SchemeProcedure factorial = new SchemeProcedure("factorial");
        Object x = factorial.apply1(3);

//        Object currentState = null;
//        boolean animationRunning = true;
//        while(animationRunning){
//             Object updatedStated = updateRobotState(currentState);
//        }

        System.out.println(x);
    }




}
