import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.*;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ApplicationStatus.init(10, 0, 1, 10);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setTitle("Computing Assignment by AY");

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setTitle("Solution of y' = - y - x*x");


        MenuBar menuBar = new MenuBar();

        Menu modeMenu = new Menu("Mode");

        RadioMenuItem btnGraphics = new RadioMenuItem("Solutions");
        RadioMenuItem btnErrors = new RadioMenuItem("Errors");

        ToggleGroup group = new ToggleGroup();
        btnGraphics.setToggleGroup(group);
        btnErrors.setToggleGroup(group);
        btnGraphics.setSelected(true);

        modeMenu.getItems().addAll(btnGraphics, btnErrors);

        menuBar.getMenus().addAll(modeMenu);


        Button btnEuler = addVerticalButton("Euler", primScreenBounds);
        Button btnExact = addVerticalButton("Exact", primScreenBounds);
        Button btnImprovedEuler = addVerticalButton("Improved Euler", primScreenBounds);
        Button btnKutta = addVerticalButton("Kutta", primScreenBounds);
        Button btnClear = addVerticalButton("Clear", primScreenBounds);

        VBox vbButtons = new VBox(10);
        vbButtons.setAlignment(Pos.BOTTOM_CENTER);
        vbButtons.getChildren().addAll(btnExact, btnEuler, btnImprovedEuler, btnKutta, btnClear);

        btnGraphics.setOnAction(e -> {
            if (ApplicationStatus.currentState != ApplicationStatus.State.SOLUTION) {
                clearLineChart(lineChart);
                lineChart.setTitle("Solution of y' = - y - x*x");
                ApplicationStatus.currentState = ApplicationStatus.State.SOLUTION;
                btnExact.setVisible(true);
            }
        });

        btnErrors.setOnAction(e -> {
            if (ApplicationStatus.currentState != ApplicationStatus.State.ERROR) {
                clearLineChart(lineChart);
                lineChart.setTitle("Error analysis of y' = - y - x*x");
                ApplicationStatus.currentState = ApplicationStatus.State.ERROR;
                btnExact.setVisible(false);
            }
        });


        btnClear.setOnAction(e -> {
            clearLineChart(lineChart);
        });

        btnExact.setOnAction(e -> {
            addSeries(lineChart, ApplicationStatus.exactSolution, ApplicationStatus.isExactPresent, "Exact Solution");
            ApplicationStatus.isExactPresent = true;
        });

        btnEuler.setOnAction(e -> {
            if (ApplicationStatus.currentState == ApplicationStatus.State.SOLUTION) {
                addSeries(lineChart, ApplicationStatus.eulerMethod, ApplicationStatus.isEulerPresent, "Euler's Method");
                ApplicationStatus.isEulerPresent = true;
            } else if (ApplicationStatus.currentState == ApplicationStatus.State.ERROR) {
                addSeries(lineChart, ApplicationStatus.eulerError, ApplicationStatus.isEulerPresent, "Euler's Error");
                ApplicationStatus.isEulerPresent = true;
            }
        });

        btnImprovedEuler.setOnAction(e -> {
            if (ApplicationStatus.currentState == ApplicationStatus.State.SOLUTION) {
                addSeries(lineChart, ApplicationStatus.improvedEulerMethod, ApplicationStatus.isImprovedPresent, "Improved Euler");
                ApplicationStatus.isImprovedPresent = true;
            } else if (ApplicationStatus.currentState == ApplicationStatus.State.ERROR) {
                addSeries(lineChart, ApplicationStatus.improvedEulerError, ApplicationStatus.isImprovedPresent, "Improved Euler Error");
                ApplicationStatus.isImprovedPresent = true;
            }
        });

        btnKutta.setOnAction(e -> {
            if (ApplicationStatus.currentState == ApplicationStatus.State.SOLUTION) {
                addSeries(lineChart, ApplicationStatus.kuttaMethod, ApplicationStatus.isKuttaPresent, "Kutta Method");
                ApplicationStatus.isKuttaPresent = true;
            } else if (ApplicationStatus.currentState == ApplicationStatus.State.ERROR) {
                addSeries(lineChart, ApplicationStatus.kuttaError, ApplicationStatus.isKuttaPresent, "Kutta Error");
                ApplicationStatus.isKuttaPresent = true;
            }
        });

        BorderPane layout = new BorderPane();
        layout.setTop(menuBar);
        layout.setLeft(vbButtons);
        layout.setCenter(lineChart);
        Scene scene = new Scene(layout, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
        vbButtons.setPadding(new Insets(0, 0, 10, 10));
        primaryStage.setMaximized(true);
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(800);
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }

    private void addSeries(LineChart<Number, Number> lineChart, Grid solution, boolean presentFlag, final String string) {
        if (!presentFlag) {
            XYChart.Series series = new XYChart.Series();
            series.setName(string);
            for (int i = 0; i < solution.size; i++) {
                series.getData().add(new XYChart.Data(solution.AxisX[i], solution.AxisY[i]));
            }
            lineChart.getData().add(series);
        }
    }

    private void clearLineChart(LineChart lineChart) {
        lineChart.getData().clear();
        ApplicationStatus.clearGraphics();
    }

    private Button addVerticalButton(String name, Rectangle2D screenSize) {
        Button newButton = new Button(name);
        newButton.setMaxHeight(Double.MAX_VALUE);
        newButton.setPrefWidth(screenSize.getWidth() / 10);
        newButton.setAlignment(Pos.CENTER);
        return newButton;
    }
}
