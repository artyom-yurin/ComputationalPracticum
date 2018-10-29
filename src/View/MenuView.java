package View;

import Controller.ApplicationStatus;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import static View.CommonView.*;
import static View.LineChartView.clearLineChart;

public class MenuView {
    public static MenuBar InitMenuBar(LineChart<Number, Number> lineChart, BorderPane leftMenu)
    {
        MenuBar menuBar = new MenuBar();

        Menu modeMenu = new Menu("Mode");

        RadioMenuItem btnGraphics = new RadioMenuItem("Solutions");
        RadioMenuItem btnErrors = new RadioMenuItem("Errors");
        RadioMenuItem btnErrorAnalysis = new RadioMenuItem("Analysis Error");

        ToggleGroup group = new ToggleGroup();
        btnGraphics.setToggleGroup(group);
        btnErrors.setToggleGroup(group);
        btnErrorAnalysis.setToggleGroup(group);
        btnGraphics.setSelected(true);

        modeMenu.getItems().addAll(btnGraphics, btnErrors, btnErrorAnalysis);

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

        btnErrorAnalysis.setOnAction(e -> {
            if (ApplicationStatus.currentState != ApplicationStatus.State.ANALYSIS_ERROR) {
                clearLineChart(lineChart);
                lineChart.setTitle(ERROR_ANALYSIS_TITLE);
                ApplicationStatus.currentState = ApplicationStatus.State.ANALYSIS_ERROR;
                if(btnExact != null)
                {
                    btnExact.setVisible(false);
                }
            }
        });

        return menuBar;
    }

}
