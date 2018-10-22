import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Computing Assignment by AY");

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        final LineChart<Number,Number> lineChart = new LineChart<Number, Number>(xAxis,yAxis);
        lineChart.setTitle("Solution of y' = - y - x");

        /*BorderPane border = new BorderPane();
        border.setPadding(new Insets(20, 0, 20, 20));*/

        Button btnEuler = new Button("Euler");
        Button btnExact = new Button("Exact");
        Button btnImprovedEuler = new Button("Improved Euler");
        Button btnKutta = new Button("Kutta");
        Button btnClear = new Button("Clear");

        btnEuler.setMaxWidth(Double.MAX_VALUE);
        btnExact.setMaxWidth(Double.MAX_VALUE);
        btnImprovedEuler.setMaxWidth(Double.MAX_VALUE);
        btnKutta.setMaxWidth(Double.MAX_VALUE);
        btnClear.setMaxWidth(Double.MAX_VALUE);

        VBox vbButtons = new VBox();
        vbButtons.setSpacing(10);
        vbButtons.setPadding(new Insets(0, 20, 10, 20));
        vbButtons.getChildren().addAll(btnEuler, btnExact, btnImprovedEuler, btnKutta, btnClear);

        btnClear.setOnAction(e -> {
            lineChart.setAnimated(false);
            lineChart.getData().clear();
            lineChart.setAnimated(true);
        });

        XYChart.Series series = new XYChart.Series();
        series.setName("Exact Solution");

        ExactSolution exactSolution = new ExactSolution(10, 0, 1, 10);
        for(int i = 0; i < exactSolution.size; i++)
        {
            series.getData().add(new XYChart.Data(exactSolution.AxisX[i], exactSolution.AxisY[i]));
        }

        btnExact.setOnAction(e -> {
            if(!lineChart.getData().contains(series))
            {
                lineChart.getData().add(series);
            }
        });

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Euler's Method");

        EulerMethod eulerMethod= new EulerMethod(10, 0, 1, 10);
        for(int i = 0; i < eulerMethod.size; i++)
        {
            series2.getData().add(new XYChart.Data(eulerMethod.AxisX[i], eulerMethod.AxisY[i]));
        }

        btnEuler.setOnAction(e -> {
            if(!lineChart.getData().contains(series2))
            {
                lineChart.getData().add(series2);
            }
        });

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Improved Euler");

        ImprovedEulerMethod improvedEulerMethod = new ImprovedEulerMethod(10, 0, 1, 10);
        for(int i = 0; i < improvedEulerMethod.size; i++)
        {
            series3.getData().add(new XYChart.Data(improvedEulerMethod.AxisX[i], improvedEulerMethod.AxisY[i]));
        }

        btnImprovedEuler.setOnAction(e -> {
            if(!lineChart.getData().contains(series3))
            {
                lineChart.getData().add(series3);
            }
        });

        XYChart.Series series4 = new XYChart.Series();
        series4.setName("Kutta Method");

        KuttaMethod kuttaMethod = new KuttaMethod(10, 0, 1, 10);
        for(int i = 0; i < kuttaMethod.size; i++)
        {
            series4.getData().add(new XYChart.Data(kuttaMethod.AxisX[i], kuttaMethod.AxisY[i]));
        }

        btnKutta.setOnAction(e -> {
            if(!lineChart.getData().contains(series4))
            {
                lineChart.getData().add(series4);
            }
        });

        StackPane layout= new StackPane();
        Scene scene = new Scene(layout, 400, 200);
        layout.getChildren().add(lineChart);
        layout.getChildren().add(vbButtons);
        primaryStage.setScene(scene);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setHeight(primScreenBounds.getHeight() / 2);
        primaryStage.setWidth(primScreenBounds.getWidth() / 2);
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }
}
