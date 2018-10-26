import javafx.application.Application;
import javafx.geometry.HPos;
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


        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setAlignment(Pos.CENTER);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(50, 50, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.CENTER);

        ColumnConstraints columnTwoConstrains = new ColumnConstraints(100,100, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        form.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        Label labelXStart = new Label("X 0");
        TextField textXStart = new TextField("" + ApplicationStatus.x0);
        form.add(labelXStart, 0, 0);
        form.add(textXStart, 1, 0);

        Label labelYStart = new Label("Y 0");
        TextField textYStart = new TextField("" + ApplicationStatus.y0);
        form.add(labelYStart, 0, 1);
        form.add(textYStart, 1, 1);

        Label labelXMax = new Label("X Max");
        TextField textXMax = new TextField("" + ApplicationStatus.xMax);
        form.add(labelXMax, 0, 2);
        form.add(textXMax, 1, 2);

        Label labelSize = new Label("Size");
        TextField textSize = new TextField("" + ApplicationStatus.size);
        form.add(labelSize, 0, 3);
        form.add(textSize, 1, 3);

        Button newParametrButton = addVerticalButton("Set new parameters", primScreenBounds);
        form.add(newParametrButton, 0, 4, 2, 1);

        VBox vbText = new VBox(10);
        vbText.setAlignment(Pos.TOP_CENTER);
        vbText.getChildren().addAll(form);

        BorderPane leftMenu = new BorderPane();
        leftMenu.setTop(vbText);
        leftMenu.setBottom(vbButtons);

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

        layout.setLeft(leftMenu);

        layout.setCenter(lineChart);
        Scene scene = new Scene(layout, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
        vbButtons.setPadding(new Insets(0, 0, 10, 10));
        vbText.setPadding(new Insets(10, 0, 0, 10));
        primaryStage.setMaximized(true);
        primaryStage.setMinHeight(500);
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
