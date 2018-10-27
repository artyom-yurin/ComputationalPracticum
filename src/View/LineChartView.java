package View;

import Controller.ApplicationStatus;
import Model.Grid;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class LineChartView {
    public static LineChart<Number, Number> InitLineChar(String title) {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> newLineChart = new LineChart<Number, Number>(xAxis, yAxis);
        newLineChart.setTitle(title);
        return newLineChart;
    }

    protected static void addSeries(LineChart<Number, Number> lineChart, Grid solution, boolean presentFlag, final String string) {
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

    protected static void clearLineChart(LineChart lineChart) {
        lineChart.getData().clear();
        ApplicationStatus.clearGraphics();
    }
}
