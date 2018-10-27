package VIew;

import Controller.ApplicationStatus;
import Model.Grid;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class View {

    public static final String SOLUTION_TITLE = "Solution of y' = - y - x*x";
    public static final String ERROR_TITLE = "Error analysis of y' = - y - x*x";

    public static Button findButton(Node pane, String name)
    {
        if(pane instanceof Pane)
        {
            for (Node node :((Pane)pane).getChildren()){
                if((node instanceof Button) && ((Button)node).getText().compareTo(name) == 0)
                {
                    return (Button)node;
                }
            }
        }
        return null;
    }

    public static MenuBar InitMenuBar(LineChart<Number, Number> lineChart, BorderPane leftMenu)
    {
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

        Button btnExact = findButton(leftMenu.getBottom(), "Exact");

        btnGraphics.setOnAction(e -> {
            if (ApplicationStatus.currentState != ApplicationStatus.State.SOLUTION) {
                clearLineChart(lineChart);
                lineChart.setTitle(SOLUTION_TITLE);
                ApplicationStatus.currentState = ApplicationStatus.State.SOLUTION;
                if(btnExact != null)
                {
                    btnExact.setVisible(true);
                }
            }
        });

        btnErrors.setOnAction(e -> {
            if (ApplicationStatus.currentState != ApplicationStatus.State.ERROR) {
                clearLineChart(lineChart);
                lineChart.setTitle(ERROR_TITLE);
                ApplicationStatus.currentState = ApplicationStatus.State.ERROR;
                if(btnExact != null)
                {
                    btnExact.setVisible(false);
                }
            }
        });

        return menuBar;
    }

    public static BorderPane InitLeftMenu(LineChart<Number, Number> lineChart) {
        BorderPane leftMenu = new BorderPane();

        GridPane form = InitParametersForm(lineChart);

        leftMenu.setTop(form);

        VBox controlButtons = InitControlButtons(lineChart);

        leftMenu.setBottom(controlButtons);

        return leftMenu;
    }

    private static GridPane InitForm()
    {
        GridPane form = new GridPane();
        form.setAlignment(Pos.TOP_CENTER);
        form.setPadding(new Insets(10, 0, 0, 10));
        form.setHgap(10);
        form.setVgap(10);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(50, 50, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.CENTER);

        ColumnConstraints columnTwoConstrains = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        form.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        return form;
    }

    private static GridPane InitParametersForm(LineChart<Number, Number> lineChart) {
        GridPane form = InitForm();

        Label labelXStart = new Label("X0");
        TextField textXStart = addFloatNumericalTextField("" + ApplicationStatus.x0);
        setSpace(form, labelXStart, textXStart, 0);

        Label labelYStart = new Label("Y0");
        TextField textYStart = addFloatNumericalTextField("" + ApplicationStatus.y0);
        setSpace(form, labelYStart, textYStart, 1);

        Label labelXMax = new Label("X Max");
        TextField textXMax = addFloatNumericalTextField("" + ApplicationStatus.xMax);
        setSpace(form, labelXMax, textXMax, 2);

        Label labelSize = new Label("Size");
        TextField textSize = addIntegerNumericalTextField("" + ApplicationStatus.size);
        setSpace(form, labelSize, textSize, 3);

        Button newParameterButton = addVerticalButton("Set new parameters");
        form.add(newParameterButton, 0, 4, 2, 1);

        newParameterButton.setOnAction(e -> {
            if(!isFill(textXStart, textYStart, textXMax, textSize))
            {
                return;
            }
            if (ApplicationStatus.Recalculate(Integer.parseInt(textSize.getText()), Float.parseFloat(textXStart.getText()), Float.parseFloat(textYStart.getText()), Float.parseFloat(textXMax.getText()))) {
                clearLineChart(lineChart);
            }
        });
        return form;
    }

    private static boolean isFill(TextField textXStart, TextField textYStart, TextField textXMax, TextField textSize)
    {
        if (textXStart.getText().isEmpty()) {
            WarningWindow("Invalid init x", "Please fill X0 field");
            return false;
        }
        if (textYStart.getText().isEmpty()) {
            WarningWindow("Invalid init y", "Please fill Y0 field");
            return false;
        }
        if (textXMax.getText().isEmpty()) {

            WarningWindow("Invalid finish x", "Please fill X MAX field");
            return false;
        }
        if (textSize.getText().isEmpty()) {
            WarningWindow("Invalid size", "Please fill Size field");
            return false;
        }
        return true;
    }

    private static void WarningWindow(String headerText, String contentText)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    private static void setSpace(GridPane form, Label name, TextField textField, int row)
    {
        form.add(name, 0, row);
        form.add(textField, 1, row);
    }

    private static VBox InitControlButtons(LineChart<Number, Number> lineChart) {
        Button btnEuler = addVerticalButton("Euler");
        Button btnExact = addVerticalButton("Exact");
        Button btnImprovedEuler = addVerticalButton("Improved Euler");
        Button btnKutta = addVerticalButton("Kutta");
        Button btnClear = addVerticalButton("Clear");

        VBox vbButtons = new VBox(10);
        vbButtons.setAlignment(Pos.BOTTOM_CENTER);
        vbButtons.getChildren().addAll(btnExact, btnEuler, btnImprovedEuler, btnKutta, btnClear);
        vbButtons.setPadding(new Insets(0, 0, 10, 10));

        btnClear.setOnAction(e -> {
            clearLineChart(lineChart);
        });

        btnExact.setOnAction(e -> {
            ExactBtnAction(lineChart);
        });

        btnEuler.setOnAction(e -> {
            EulerBtnAction(lineChart);
        });

        btnImprovedEuler.setOnAction(e -> {
            ImprovedEulerBtnAction(lineChart);
        });

        btnKutta.setOnAction(e -> {
            KuttaBtnAction(lineChart);
        });

        return vbButtons;
    }

    private static void ExactBtnAction(LineChart<Number, Number> lineChart) {
        addSeries(lineChart, ApplicationStatus.exactSolution, ApplicationStatus.isExactPresent, "Exact Solution");
        ApplicationStatus.isExactPresent = true;
    }

    private static void EulerBtnAction(LineChart<Number, Number> lineChart) {
        if (ApplicationStatus.currentState == ApplicationStatus.State.SOLUTION) {
            addSeries(lineChart, ApplicationStatus.eulerMethod, ApplicationStatus.isEulerPresent, "Euler's Method");
            ApplicationStatus.isEulerPresent = true;
        } else if (ApplicationStatus.currentState == ApplicationStatus.State.ERROR) {
            addSeries(lineChart, ApplicationStatus.eulerError, ApplicationStatus.isEulerPresent, "Euler's Error");
            ApplicationStatus.isEulerPresent = true;
        }
    }

    private static void ImprovedEulerBtnAction(LineChart<Number, Number> lineChart) {
        if (ApplicationStatus.currentState == ApplicationStatus.State.SOLUTION) {
            addSeries(lineChart, ApplicationStatus.improvedEulerMethod, ApplicationStatus.isImprovedPresent, "Improved Euler");
            ApplicationStatus.isImprovedPresent = true;
        } else if (ApplicationStatus.currentState == ApplicationStatus.State.ERROR) {
            addSeries(lineChart, ApplicationStatus.improvedEulerError, ApplicationStatus.isImprovedPresent, "Improved Euler Error");
            ApplicationStatus.isImprovedPresent = true;
        }
    }

    private static void KuttaBtnAction(LineChart<Number, Number> lineChart) {
        if (ApplicationStatus.currentState == ApplicationStatus.State.SOLUTION) {
            addSeries(lineChart, ApplicationStatus.kuttaMethod, ApplicationStatus.isKuttaPresent, "Kutta Method");
            ApplicationStatus.isKuttaPresent = true;
        } else if (ApplicationStatus.currentState == ApplicationStatus.State.ERROR) {
            addSeries(lineChart, ApplicationStatus.kuttaError, ApplicationStatus.isKuttaPresent, "Kutta Error");
            ApplicationStatus.isKuttaPresent = true;
        }
    }

    public static void InitPrimaryStage(Stage primaryStage) {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setTitle("Computing Assignment by AY");
        primaryStage.setMaximized(true);
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(800);
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }

    private static TextField addFloatNumericalTextField(String initValue) {
        TextField textField = new TextField(initValue);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[\\-]?\\d{0,4}([\\.]\\d{0,4})?")) {
                textField.setText(oldValue);
            }
        });
        return textField;
    }

    private static TextField addIntegerNumericalTextField(String initValue) {
        TextField textField = new TextField(initValue);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,4}")) {
                textField.setText(oldValue);
            }
        });
        return textField;
    }

    public static LineChart<Number, Number> InitLineChar(String title) {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> newLineChart = new LineChart<Number, Number>(xAxis, yAxis);
        newLineChart.setTitle(title);
        return newLineChart;
    }

    private static void addSeries(LineChart<Number, Number> lineChart, Grid solution, boolean presentFlag, final String string) {
        if (!presentFlag) {
            XYChart.Series series = new XYChart.Series();
            series.setName(string);
            float[] axisX = solution.getAxisX();
            float[] axisY = solution.getAxisY();
            for (int i = 0; i < solution.getSize(); i++) {
                series.getData().add(new XYChart.Data(axisX[i], axisY[i]));
            }
            lineChart.getData().add(series);
        }
    }

    private static void clearLineChart(LineChart lineChart) {
        lineChart.getData().clear();
        ApplicationStatus.clearGraphics();
    }

    private static Button addVerticalButton(String name) {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        Button newButton = new Button(name);
        newButton.setMaxHeight(Double.MAX_VALUE);
        newButton.setPrefWidth(primScreenBounds.getWidth() / 10);
        newButton.setAlignment(Pos.CENTER);
        return newButton;
    }
}
