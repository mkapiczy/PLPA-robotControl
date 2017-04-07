package dk.plpa;


import dk.plpa.scheme.SchemeConfigurer;
import dk.plpa.scheme.SchemeProcedure;
import dk.plpa.utils.FileReader;


public class App {


    public static void main(String[] args) throws Throwable {

        String floor = FileReader.readFile("src/main/scheme/dk.plpa/FloorPlan.scm");

        SchemeConfigurer schemeConfigurer = new SchemeConfigurer("src/main/scheme/dk.plpa/FloorUtil.scm");
        schemeConfigurer.configureSchemeEnvironment();

        SchemeProcedure factorial = new SchemeProcedure("get-item");
        Object x = factorial.apply3(floor, 1, 5);

//        Object currentState = null;
//        boolean animationRunning = true;
//        while(animationRunning){
//             Object updatedStated = updateRobotState(currentState);
//        }

        System.out.println(x);
    }




}
