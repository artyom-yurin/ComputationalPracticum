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

    int size = 10;
    int x0 = 10;
    int y0 = -10;
    int xMax = 15;

    @Override
    public void start(Stage primaryStage) {
        ApplicationStatus.init();
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

        ExactSolution exactSolution = new ExactSolution(size, x0, y0, xMax);

        btnExact.setOnAction(e -> {
            ApplicationStatus.isExactPresent = addSeries(lineChart, exactSolution, ApplicationStatus.isExactPresent, "Exact Solution");
        });

        EulerMethod eulerMethod = new EulerMethod(size, x0, y0, xMax);
        Grid eulerError = GetError(eulerMethod, exactSolution, size);
        btnEuler.setOnAction(e -> {
            if (ApplicationStatus.currentState == ApplicationStatus.State.SOLUTION) {
                ApplicationStatus.isEulerPresent = addSeries(lineChart, eulerMethod, ApplicationStatus.isEulerPresent, "Euler's Method");
            } else if (ApplicationStatus.currentState == ApplicationStatus.State.ERROR) {
                ApplicationStatus.isEulerPresent = addSeries(lineChart, eulerError, ApplicationStatus.isEulerPresent, "Euler's Error");
            }
        });

        ImprovedEulerMethod improvedEulerMethod = new ImprovedEulerMethod(size, x0, y0, xMax);
        Grid improvedEulerError = GetError(improvedEulerMethod, exactSolution, size);
        btnImprovedEuler.setOnAction(e -> {
            if (ApplicationStatus.currentState == ApplicationStatus.State.SOLUTION) {
                ApplicationStatus.isImprovedPresent = addSeries(lineChart, improvedEulerMethod, ApplicationStatus.isImprovedPresent, "Improved Euler");
            } else if (ApplicationStatus.currentState == ApplicationStatus.State.ERROR) {
                ApplicationStatus.isImprovedPresent = addSeries(lineChart, improvedEulerError, ApplicationStatus.isImprovedPresent, "Improved Euler Error");
            }
        });

        KuttaMethod kuttaMethod = new KuttaMethod(size, x0, y0, xMax);
        Grid kuttaError = GetError(kuttaMethod, exactSolution, size);
        btnKutta.setOnAction(e -> {
            if (ApplicationStatus.currentState == ApplicationStatus.State.SOLUTION) {
                ApplicationStatus.isKuttaPresent = addSeries(lineChart, kuttaMethod, ApplicationStatus.isKuttaPresent, "Kutta Method");
            } else if (ApplicationStatus.currentState == ApplicationStatus.State.ERROR) {
                ApplicationStatus.isKuttaPresent = addSeries(lineChart, kuttaError, ApplicationStatus.isKuttaPresent, "Kutta Error");
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

    private Grid GetError(Grid someMethod, Grid exactSolution, int size) {
        Grid error = new Grid(size);
        for (int i = 0; i < error.size; i++) {
            error.AxisX[i] = exactSolution.AxisX[i];
            error.AxisY[i] = exactSolution.AxisY[i] - someMethod.AxisY[i];
        }
        return error;
    }

    private boolean addSeries(LineChart<Number, Number> lineChart, Grid solution, boolean presentFlag, final String string) {
        if (!presentFlag) {
            XYChart.Series series = new XYChart.Series();
            series.setName(string);
            for (int i = 0; i < solution.size; i++) {
                series.getData().add(new XYChart.Data(solution.AxisX[i], solution.AxisY[i]));
            }
            lineChart.getData().add(series);
            presentFlag = true;
        }
        return presentFlag;
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
