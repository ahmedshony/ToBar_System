package AppManager;

import Model.Enums.AlertType;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;

import java.awt.*;

import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import java.time.LocalDate;
import java.time.format.*;
import java.text.SimpleDateFormat;
import java.io.*;

import javafx.scene.layout.AnchorPane;


public class UIManager {

    /********************************msgs********************************/
    final static String error_msg = "خطأ";
    final static String success_msg = "تم بنجاح";
    final static String empty_fields_msg = "برجاء التأكد من ملء الخانات الفارغة";
    public final static String networkError_msg = "خطأ - برجاء التأكد من اتصالك بالشبكة";
    public final static String movementError_msg = "تعذر إضافة النشرة";
    public final static String movementSuccess_msg = "تم إضافة النشرة بنجاح";

    public final static String NamozgBianatRatingMaxError_msg = "لقد تعديت الحد الاقصي في احدي النتائج";

    public final static String printError_msg = "خطــأ بالطبــاعه";




    /********************************colors********************************/
    public final static String default_style = "-fx-background-color: white";
    public final static String highlight_style = "-fx-background-color: #ea7979";
    public  final static String green_color_style = "-fx-background-color : #5abfa4";


    /********************************fields********************************/
    public static double screen_width = 0;
    public static double screen_height = 0;
    public static final double upper_lower_screenPadding = 94;


    public static String red_background = "-fx-background-color: #d35a5a;";
    public static String white_background = "-fx-background-color: #f1f1f1;";
    public static String black_background = "-fx-background-color: #293035;";
    public static String green_background = "-fx-background-color: #5bc9ad;";
    public static String blue_background = "-fx-background-color: #42a5f5;";
    public static String orange_background = "-fx-background-color: #f1681e;";
    public static String yellow_background = "-fx-background-color: #fffc00;";
    public static String lemon_background = "-fx-background-color: #c6ff00;";
    public static String pink_background = "-fx-background-color: #ef9a9a;";
    public static String lightTeal_background = "-fx-background-color: #00bfa5;";
    public static String lightRed_background = "-fx-background-color: #ff8a80;";


    // grand
    public static String teal_background = "-fx-background-color: #004d40;"; // main color app
    public static String gray_background = "-fx-background-color: #616161;";
    public static String greenLight_background = "-fx-background-color: #a5d6a7;";
    public static String deepOrange_background = "-fx-background-color: #ff3b00;";

    public static String redMsg_style = "-fx-background-color: #ad4c4c;"
            + "-fx-background-radius: 5;"
            + "-fx-font-family: Tajawal Light;"
            + "-fx-font-size: 14;";


    public static String yellowMsg_style = "-fx-background-color: #c5b155;"
            + "-fx-background-radius: 5;"
            + "-fx-font-family: Tajawal Light;"
            + "-fx-font-size: 14;";


    public static String greenMsg_style = "-fx-background-color: #5bc9ad;"
            + "-fx-background-radius: 5;"
            + "-fx-font-family: Tajawal Light;"
            + "-fx-font-size: 14;";



    /********************************Layout********************************/
    public static void get_screen_size() {
        UIManager.screen_height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        UIManager.screen_height -= UIManager.upper_lower_screenPadding;
        UIManager.screen_width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        System.out.println("Height = " + screen_height + "Width = " + screen_width);
    }



    /********************************open/close view********************************/
    public static  void close_current_view(Pane parentPane){
        Stage currentStage = (Stage) parentPane.getScene().getWindow();
        currentStage.close();
    }

    public static Object open_new_view(Object current_class, String path, String title) {

        FXMLLoader loader = new FXMLLoader(current_class.getClass().getResource(path));
        Parent root = null;
        try {
            root = loader.load();
            Stage newStage = new Stage();
            Scene newScene = new Scene(root);

            newStage.setScene(newScene);
            newStage.centerOnScreen();
            newStage.setResizable(true);
            newStage.setTitle(title);
            newStage.getIcons().add(new Image("/Assets/icons/public_icons/ux.png"));

            newStage.show();
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /********************************alerts********************************/
    public static void show_error_alert(String text){
        Notifications.create()
                .title("خطأ")
                .text(text)
                .hideAfter(Duration.seconds(2.0))
                .darkStyle()
                .position(Pos.CENTER)
                .showError();
    }
    public static void show_error_alert_BAROCDE(String text){
        Notifications.create()
                .title("خطأ")
                .text(text)
                .hideAfter(Duration.seconds(5.0))
                .darkStyle()
                .position(Pos.CENTER)
                .showError();
    }


    public static void show_warning_alert(String title, String text){
        Notifications.create()
                .title(title)
                .text(text)
               // .hideAfter(Duration.seconds(1.5))
                .darkStyle()
                .position(Pos.CENTER)
                .showWarning();
    }


    public static void show_confirm_alert(String title, String text ){
        Notifications.create()
                .title(title)
                .text(text)
            //    .hideAfter(Duration.seconds(1.5))
                .position(Pos.CENTER)
                .showConfirm();
    }

    public  static  AnchorPane main_anchor = new AnchorPane();

    public static void showAlert (String msg ,AlertType alertType, AnchorPane main_anchor, int xPoint , int yPoint){
        javafx.scene.control.Label toastLabel = new Label();
        Platform.runLater(()->{
            main_anchor.getChildren().add(toastLabel);
            toastLabel.setText(msg);

            toastLabel.setLayoutX(xPoint == 0 ? 628-(500/2) : xPoint);
            toastLabel.setLayoutY(yPoint == 0 ? 476-(40/2) : yPoint);
           // toastLabel.setGraphic(movemnetSearch_button.getGraphic());


            toastLabel.setMinSize(500, 40);
            toastLabel.setPrefSize(500 , 40);
            toastLabel.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            toastLabel.setTextAlignment(TextAlignment.CENTER);
            toastLabel.setTextFill(Paint.valueOf("white"));


            if (alertType.equals(AlertType.success)){
                toastLabel.setStyle(UIManager.greenMsg_style + " -fx-alignment: center;");
               // toastLabel.setGraphic(success_button.getGraphic());
            }else if (alertType.equals(AlertType.alarm)){
                toastLabel.setStyle(UIManager.yellowMsg_style + " -fx-alignment: center;");
               // toastLabel.setGraphic(alarm_button.getGraphic());
            }else if (alertType.equals(AlertType.error)){
                toastLabel.setStyle(UIManager.redMsg_style + " -fx-alignment: center;");
                //toastLabel.setGraphic(error_button.getGraphic());
            }


            TranslateTransition translateTransitional_1 = new TranslateTransition(Duration.seconds(2), toastLabel);
            translateTransitional_1.setByY(-20);
            translateTransitional_1.play();

            translateTransitional_1.setOnFinished(event1 -> {
                TranslateTransition translateTransitional_2 = new TranslateTransition(Duration.seconds(2), toastLabel);
                translateTransitional_2.setByY(20);
                translateTransitional_2.play();
                translateTransitional_2.setOnFinished(event2 -> {
                    main_anchor.getChildren().remove(toastLabel);
                });
            });
        });
    }


    public static void showLongAlert (String msg ,AlertType alertType, AnchorPane main_anchor){
        javafx.scene.control.Label toastLabel = new Label();
        Platform.runLater(()->{
            main_anchor.getChildren().add(toastLabel);
            toastLabel.setText(msg);

            toastLabel.setLayoutX(628-(500/2) );
            toastLabel.setLayoutY(476-(40/2));
            // toastLabel.setGraphic(movemnetSearch_button.getGraphic());


            toastLabel.setMinSize(500, 40);
            toastLabel.setPrefSize(500 , 40);
            toastLabel.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            toastLabel.setTextAlignment(TextAlignment.CENTER);
            toastLabel.setTextFill(Paint.valueOf("white"));


            if (alertType.equals(AlertType.success)){
                toastLabel.setStyle(UIManager.greenMsg_style + " -fx-alignment: center;");
                // toastLabel.setGraphic(success_button.getGraphic());
            }else if (alertType.equals(AlertType.alarm)){
                toastLabel.setStyle(UIManager.yellowMsg_style + " -fx-alignment: center;");
                // toastLabel.setGraphic(alarm_button.getGraphic());
            }else if (alertType.equals(AlertType.error)){
                toastLabel.setStyle(UIManager.redMsg_style + " -fx-alignment: center;");
                //toastLabel.setGraphic(error_button.getGraphic());
            }


            TranslateTransition translateTransitional_1 = new TranslateTransition(Duration.seconds(2), toastLabel);
            translateTransitional_1.setByY(-20);
            translateTransitional_1.play();

            translateTransitional_1.setOnFinished(event1 -> {
                TranslateTransition translateTransitional_2 = new TranslateTransition(Duration.seconds(2), toastLabel);
                translateTransitional_2.setByY(20);
                translateTransitional_2.play();
                translateTransitional_2.setOnFinished(event2 -> {
                    main_anchor.getChildren().remove(toastLabel);
                });
            });
        });
    }

    public static void showLongAlert_count (String msg ,AlertType alertType, AnchorPane main_anchor){
        javafx.scene.control.Label toastLabel = new Label();
        Platform.runLater(()->{
            main_anchor.getChildren().add(toastLabel);
            toastLabel.setText(msg);
            toastLabel.setLayoutX(628-(500/2) );
            toastLabel.setLayoutY(476-(40/2));
            // toastLabel.setGraphic(movemnetSearch_button.getGraphic());


            toastLabel.setMinSize(500, 40);
            toastLabel.setPrefSize(500 , 40);
            toastLabel.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            toastLabel.setTextAlignment(TextAlignment.CENTER);
            toastLabel.setTextFill(Paint.valueOf("white"));


            if (alertType.equals(AlertType.success)){
                toastLabel.setStyle(UIManager.greenMsg_style + " -fx-alignment: center;");
                // toastLabel.setGraphic(success_button.getGraphic());
            }else if (alertType.equals(AlertType.alarm)){
                toastLabel.setStyle(UIManager.yellowMsg_style + " -fx-alignment: center;");
                // toastLabel.setGraphic(alarm_button.getGraphic());
            }else if (alertType.equals(AlertType.error)){
                toastLabel.setStyle(UIManager.redMsg_style + " -fx-alignment: center;");
                //toastLabel.setGraphic(error_button.getGraphic());
            }


            TranslateTransition translateTransitional_1 = new TranslateTransition(Duration.seconds(.25), toastLabel);
            translateTransitional_1.setByY(-20);
            translateTransitional_1.play();

            translateTransitional_1.setOnFinished(event1 -> {
                TranslateTransition translateTransitional_2 = new TranslateTransition(Duration.seconds(.25), toastLabel);
                translateTransitional_2.setByY(20);
                translateTransitional_2.play();
                translateTransitional_2.setOnFinished(event2 -> {
                    main_anchor.getChildren().remove(toastLabel);
                });
            });


        });
    }







    /*********************************************************************/


    public static final LocalDate convertToLocalDate(java.sql.Date date) {
        LocalDate l_date;
        if (date == null){
            l_date = null ;
        }
        else
        {
            DateTimeFormatter foramtter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            l_date = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date), foramtter);
        }

        return l_date;
    }
    //mbasuony
    public static final LocalDate convertToLocalDateUtil(java.util.Date date) {
        DateTimeFormatter foramtter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date), foramtter);
    }


    public static java.sql.Date convertToDate(LocalDate localDate) {
        java.sql.Date date;
        if (localDate == null){
            date =null;
        }
        else {
          date  = java.sql.Date.valueOf(localDate);

        }
        return date;
    }


    public static void typeNumbersOnly(JFXTextField textField) {
        textField.textProperty().addListener(($, oldString, newString) -> {
            if (!newString.matches("\\d*")) {
                textField.setText(newString.replaceAll("[^\\d]", ""));
            }
        });
    }

    public static void typeOldNoOnly(JFXTextField textField) {
        textField.textProperty().addListener(($, oldString, newString) -> {
            if (!newString.matches("\\d*") && !newString.contains("م")) {
                textField.setText(newString.replaceAll("[^\\d]", ""));
            }
        });
    }

    public static int convertToInt(String str) {
        int converted_str = 0 ;
        if (str.length() != 0){
            try{
                str = str.replaceAll("[^\\d]", "");
                converted_str = Integer.valueOf(str);
            }catch (Exception e){
                System.out.println("can't convert to Int " + e.getMessage());
            }
        }
        return converted_str;
    }


    public static String convert_chars(String given_text){
        String output_txt = "";


        if(given_text.endsWith("ي")){

        }

        if(given_text.endsWith("ى")){
            output_txt = given_text.replace("ى", "ي");
        }

        if(given_text.endsWith("ه")){
            output_txt = given_text.replace("ه", "ة");
        }

        if(given_text.endsWith("ة")){
            output_txt = given_text.replace("ة", "ه");
        }

        return output_txt;
    }

    public static void control_highlight_field (JFXTextField textField, boolean on) {
        textField.setStyle("-fx-background-color : " + (on? "#d35a5a" : "#f1f1f1") + ";");
    }


   // diff = (height-weight-100)*-1;





}



















