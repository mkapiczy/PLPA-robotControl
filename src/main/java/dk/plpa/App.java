package dk.plpa;


import dk.plpa.gui.floorComponents.FloorPane;
import dk.plpa.gui.views.AnimationView;
import dk.plpa.gui.views.CommandsListView;
import dk.plpa.gui.views.RobotProgrammingView;
import dk.plpa.gui.views.ViewsDimensions;
import dk.plpa.scheme.SchemeConfigurer;
import dk.plpa.utils.FloorPaneMapper;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;


public class App extends Application {

    private Stage theStage;
    private AnimationView animationView;
    private RobotProgrammingView robotProgrammingView;
    private CommandsListView commandsListView;

    public static void main(String[] args) {
        SchemeConfigurer schemeConfigurer = new SchemeConfigurer();
        schemeConfigurer.configureSchemeEnvironment();

        launch(args);
    }


    @Override
    public void start(Stage theStage) throws Exception {
        this.animationView = new AnimationView(ViewsDimensions.ANIMATION_VIEW_WIDTH, ViewsDimensions.VIEWS_HEIGHT);
        this.robotProgrammingView = new RobotProgrammingView(ViewsDimensions.ROBOT_PROGRAMMING_VIEW_WIDTH, ViewsDimensions.VIEWS_HEIGHT);
        this.commandsListView = new CommandsListView(ViewsDimensions.COMMANDS_LIST_VIEW_WIDTH, ViewsDimensions.VIEWS_HEIGHT);
        this.theStage = theStage;
        this.animationView.setRobotProgrammingView(this.robotProgrammingView);
        this.animationView.setCommandsListView(this.commandsListView);
        this.robotProgrammingView.setAnimationView(this.animationView);
        this.robotProgrammingView.setCommandsListView(this.commandsListView);

        setUpViews();

        robotProgrammingView.askUserToChooseRobotStartingPosition();

        theStage.show();
    }

    private void setUpViews() {
        Group rootView = new Group();

        Scene scene = new Scene(rootView);
        theStage.setTitle("Robot Control");
        theStage.setScene(scene);
        theStage.setResizable(false);

        FloorPane floor = FloorPaneMapper.createFloor();
        animationView.setBackground(floor);

        animationView.setUpViewElements();
        commandsListView.setUpViewElements();
        robotProgrammingView.setUpViewElements();

        SplitPane sidePane = new SplitPane(robotProgrammingView, commandsListView);
        SplitPane mainPane = new SplitPane(animationView, sidePane);

        rootView.getChildren().add(mainPane);
    }


}
