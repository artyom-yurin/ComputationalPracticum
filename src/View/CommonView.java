package View;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class CommonView {

    public static final String SOLUTION_TITLE = "Solution of y' = - y - x*x";
    public static final String ERROR_TITLE = "Errors of y' = - y - x*x";
    public static final String ERROR_ANALYSIS_TITLE = "Error analysis of y' = - y - x*x";

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

    public static void InitPrimaryStage(Stage primaryStage) {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setTitle("Computing Assignment by AY");
        primaryStage.setMaximized(true);
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(800);
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }






}
