package dk.plpa.scheme;


public class AreCommandsLoadedSchemeProcedure {

    private static SchemeProcedure areCommandsLoadedProcedure = new SchemeProcedure("areCommandsLoaded");

    public static boolean areCommandsLoadedToRobot() {

        return (boolean) areCommandsLoadedProcedure.apply0();
    }
}
