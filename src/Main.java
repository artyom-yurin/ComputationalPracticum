import Controller.ApplicationStatus;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import static View.CommonView.*;
import static View.LeftMenuView.InitLeftMenu;
import static View.LineChartView.InitLineChar;
import static View.MenuView.InitMenuBar;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws CloneNotSupportedException {

        ApplicationStatus.init(10, 0, 1, 10, 11);

        BorderPane layout = new BorderPane();

        final LineChart<Number, Number> lineChart = InitLineChar(SOLUTION_TITLE);

        layout.setCenter(lineChart);

        BorderPane leftMenu = InitLeftMenu(lineChart);

        layout.setLeft(leftMenu);

        MenuBar menuBar = InitMenuBar(lineChart, leftMenu);

        layout.setTop(menuBar);

        Scene scene = new Scene(layout, 400, 200);

        Button btnSetParameters = findButton(leftMenu.getTop(), "Set new parameters");
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    if(btnSetParameters != null)
                    {
                        btnSetParameters.fire();
                    }
                }
            }
        });

        InitPrimaryStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
