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

public class Main extends Application {

    enum State {SOLUTION, ERROR}

    private State currentState;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        currentState = State.SOLUTION;
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
        btnGraphics.setOnAction(e -> {
            if(currentState != State.SOLUTION)
            {
                lineChart.getData().clear();
                lineChart.setTitle("Solution of y' = - y - x*x");
                currentState = State.SOLUTION;
            }
        });

        btnErrors.setOnAction(e -> {
            if(currentState != State.ERROR) {
                lineChart.getData().clear();
                lineChart.setTitle("Error analysis of y' = - y - x*x");
                currentState = State.ERROR;
            }
        });

        Button btnEuler = addVerticalButton("Euler", primScreenBounds);
        Button btnExact = addVerticalButton("Exact", primScreenBounds);
        Button btnImprovedEuler = addVerticalButton("Improved Euler", primScreenBounds);
        Button btnKutta = addVerticalButton("Kutta", primScreenBounds);
        Button btnClear = addVerticalButton("Clear", primScreenBounds);

        VBox vbButtons = new VBox(10);
        vbButtons.setAlignment(Pos.BOTTOM_CENTER);
        vbButtons.getChildren().addAll(btnEuler, btnExact, btnImprovedEuler, btnKutta, btnClear);

        btnClear.setOnAction(e -> {
            lineChart.setAnimated(false);
            lineChart.getData().clear();
            lineChart.setAnimated(true);
        });

        XYChart.Series series = new XYChart.Series();
        series.setName("Exact Solution");

        ExactSolution exactSolution = new ExactSolution(10, 10, -10, 15);
        for (int i = 0; i < exactSolution.size; i++) {
            series.getData().add(new XYChart.Data(exactSolution.AxisX[i], exactSolution.AxisY[i]));
        }

        btnExact.setOnAction(e -> {
            if (!lineChart.getData().contains(series)) {
                lineChart.getData().add(series);
            }
        });

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Euler's Method");

        EulerMethod eulerMethod = new EulerMethod(10, 10, -10, 15);
        for (int i = 0; i < eulerMethod.size; i++) {
            series2.getData().add(new XYChart.Data(eulerMethod.AxisX[i], eulerMethod.AxisY[i]));
        }

        btnEuler.setOnAction(e -> {
            if (!lineChart.getData().contains(series2)) {
                lineChart.getData().add(series2);
            }
        });

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Improved Euler");

        ImprovedEulerMethod improvedEulerMethod = new ImprovedEulerMethod(10, 10, -10, 15);
        for (int i = 0; i < improvedEulerMethod.size; i++) {
            series3.getData().add(new XYChart.Data(improvedEulerMethod.AxisX[i], improvedEulerMethod.AxisY[i]));
        }

        btnImprovedEuler.setOnAction(e -> {
            if (!lineChart.getData().contains(series3)) {
                lineChart.getData().add(series3);
            }
        });

        XYChart.Series series4 = new XYChart.Series();
        series4.setName("Kutta Method");

        KuttaMethod kuttaMethod = new KuttaMethod(10, 10, -10, 15);
        for (int i = 0; i < kuttaMethod.size; i++) {
            series4.getData().add(new XYChart.Data(kuttaMethod.AxisX[i], kuttaMethod.AxisY[i]));
        }

        btnKutta.setOnAction(e -> {
            if (!lineChart.getData().contains(series4)) {
                lineChart.getData().add(series4);
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

    private Button addVerticalButton(String name, Rectangle2D screenSize) {
        Button newButton = new Button(name);
        newButton.setMaxHeight(Double.MAX_VALUE);
        newButton.setMinWidth(screenSize.getWidth() / 10);
        newButton.setAlignment(Pos.CENTER);
        return newButton;
    }

    private Button addHorizontalButton(String name, Rectangle2D screenSize) {
        Button newButton = new Button(name);
        newButton.setStyle("-fx-focus-color: transparent;");
        newButton.setMaxHeight(Double.MAX_VALUE);
        return newButton;
    }
}
