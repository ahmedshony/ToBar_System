package AppManager;

import com.jfoenix.controls.JFXSpinner;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class AlertManager {

    public Pane createLoadingPane (AnchorPane main_anchor, String text) {

        Pane darkPane = new Pane();
        main_anchor.getChildren().add(darkPane);


        darkPane.setStyle("-fx-background-color : #5abfa4; ");
        darkPane.setOpacity(0.6);
        darkPane.setLayoutX(main_anchor.getLayoutX());
        darkPane.setLayoutY(main_anchor.getLayoutY());
        darkPane.setMinSize(main_anchor.getWidth(), main_anchor.getHeight());
        darkPane.setPrefSize(main_anchor.getWidth(), main_anchor.getHeight());

        JFXSpinner spinner = new JFXSpinner();
        darkPane.getChildren().add(spinner);
        spinner.setLayoutX(main_anchor.getWidth() / 2 - 35);
        spinner.setLayoutY(main_anchor.getHeight() / 2 - 35);
        spinner.setMinSize(70, 70);

        javafx.scene.control.Label label = new javafx.scene.control.Label();
        darkPane.getChildren().add(label);
        label.setText(text);
        label.setTextFill(Paint.valueOf("white"));
        //label.setFont(name_label.getFont());
        label.setTextAlignment(TextAlignment.RIGHT);
        label.setFont(Font.font(17));

        label.setLayoutX(main_anchor.getWidth() / 2 - 70 );
        label.setLayoutY(main_anchor.getHeight() / 2 + 35 );
        label.setMinSize(70, 70);


        return darkPane;

    }





}
