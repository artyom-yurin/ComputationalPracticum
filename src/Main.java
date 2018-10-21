import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
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

        XYChart.Series series = new XYChart.Series();
        series.setName("Exact Solution");

        ExactSolution exactSolution = new ExactSolution(10, 0, 1, 10);
        for(int i = 0; i < exactSolution.size + 1; i++)
        {
            series.getData().add(new XYChart.Data(exactSolution.AxisX[i], exactSolution.AxisY[i]));
        }

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Euler's Method");

        EulerMethod eulerMethod= new EulerMethod(10, 0, 1, 10);
        for(int i = 0; i < eulerMethod.size + 1; i++)
        {
            series2.getData().add(new XYChart.Data(eulerMethod.AxisX[i], eulerMethod.AxisY[i]));
        }

        Scene scene = new Scene(lineChart, 400, 200);
        lineChart.getData().add(series);
        lineChart.getData().add(series2);
        primaryStage.setScene(scene);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setHeight(primScreenBounds.getHeight() / 2);
        primaryStage.setWidth(primScreenBounds.getWidth() / 2);
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }
}
