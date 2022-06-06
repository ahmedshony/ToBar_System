package View.Public_views;
import AppManager.AppDefaults;
import AppManager.UIManager;
import DB_Manager.Permissions_Manager.UserManager;
import Interfaces.ViewControllerClass;
import Model.Enums.QueryState;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import Model.User_Model.User;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class Login_ViewController implements Initializable, ViewControllerClass {


    /***************************** FXML Fields **********************************/
    @FXML
    private AnchorPane main_anchor;
    @FXML
    private Pane main_pane;
    @FXML
    private JFXComboBox<String> userName_box;
    @FXML
    private JFXPasswordField password_field;
    @FXML
    private JFXButton login_button;


    /******************************** Fields ************************************/
    UserManager userManager = new UserManager();

    ObservableList<User> users_list = FXCollections.observableArrayList();
    ObservableList<String> userNames_list = FXCollections.observableArrayList();

    User currentUser = new User();


    /******************************** Initialize Method ********************************/
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.layout_view();
       this.loadDB();
     this.setup_view();


    }


    /******************************** UI Method ********************************/

    @Override
    public void layout_view() {

    }


    @Override
    public void setup_view() {
        setup_butons();
        setup_boxes();

    }

    @Override
    public void setup_menu_buttons() {

    }

    @Override
    public void setup_permissions() {
        try {
            String path = "";
            try {
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                path = "D:/Tobar_BACKUPS/TOBAR_" + date + ".sql";
            } catch (Exception e) {
                e.printStackTrace();
            }
            String dbUserName = "root";
            String dbPassword = "root1234";
            String dbName = "tobar_system";
            String executeCmd = "C:/AppServ/MySQL/bin/mysqldump.exe --no-defaults -u " + dbUserName + " -p" + dbPassword + " --add-drop-database -B " + dbName + " -r " + path;
            Process runtimeProcess;
            System.out.println(executeCmd);//this out put works in mysql shell
            runtimeProcess = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", executeCmd});
            int processComplete = runtimeProcess.waitFor();

        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getMessage();
            System.out.println("error occurs in backup");
        }

    }


    private void setup_butons() {

        password_field.setOnKeyPressed($ -> {
            if ($.getCode().equals(KeyCode.ENTER)) {
                currentUser = userManager.login_user(userName_box.getValue());
                AppDefaults.currentUser = currentUser;
                if (userName_box.getValue() == null || password_field.getText().isEmpty() || !currentUser.getPassword().equals(password_field.getText())) {
                    UIManager.show_error_alert("برجاء التأكد من إضافة اسم المستخدم و كلمة المرور بشكل صحيح");
                } else {
                    this.login_process();
                }
            }
        });
    }


    private void setup_boxes() {

        userName_box.setValue("");
        userName_box.setItems(userNames_list);
        userName_box.valueProperty().addListener(($, old_value, new_value) -> {
            binding_thread();
        });

    }


    /******************************** DB Method ********************************/
    @Override
    public void loadDB() {
        this.load_users();

    }


    private void load_users() {
        users_list = userManager.load_users();
        users_list.forEach(user -> {
            userNames_list.add(user.getUserName());
        });
    }

    /******************************** Convert DB ********************************/

    int i = 0;


    private void login_process() {

        Pane dark_pane = new Pane();
        AnchorPane dark_anchor = main_anchor;
        Platform.runLater(() -> {

            dark_anchor.getChildren().add(dark_pane);

            dark_pane.setStyle("-fx-background-color : #ffffff;");
            dark_pane.setOpacity(0.8);
            dark_pane.setLayoutX(dark_anchor.getLayoutX());
            dark_pane.setLayoutY(dark_anchor.getLayoutY());
            dark_pane.setMinSize(dark_anchor.getWidth(), dark_anchor.getHeight());
            dark_pane.setPrefSize(dark_anchor.getWidth(), dark_anchor.getHeight());

            JFXSpinner spinner = new JFXSpinner();
            dark_pane.getChildren().add(spinner);
            spinner.setLayoutX(dark_anchor.getWidth() / 2 - 35);
            spinner.setLayoutY(dark_anchor.getHeight() / 2 - 35);
            spinner.setMinSize(70, 70);

            javafx.scene.control.Label label = new javafx.scene.control.Label();
            dark_pane.getChildren().add(label);
            label.setText("تسجيل الدخول");
            label.setTextFill(Paint.valueOf("#42a3e4"));
            label.setTextAlignment(TextAlignment.RIGHT);
            label.setFont(Font.font(17));

            label.setLayoutX(dark_anchor.getWidth() / 2 - 70);
            label.setLayoutY(dark_anchor.getHeight() / 2 + 35);
            label.setMinSize(70, 70);

        });


        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                currentUser = userManager.login_user(userName_box.getValue());
                AppDefaults.currentUser = currentUser;
                return null;
            }
        };

        task.setOnSucceeded($ -> {
            System.out.println("Login | Sucsseded");

                UIManager.open_new_view(this, "home_view.fxml", "الصفحة الرئيسية");

            Platform.runLater(() -> {
                Stage currentStage = (Stage) userName_box.getScene().getWindow();
                currentStage.close();
            });

        });

        task.setOnFailed($ -> {
            System.out.println("Login | Failed");
        });
        Thread thread = new Thread(task);
        thread.start();
    }

    private void binding_thread() {
        Task task = new Task() {
            @Override
            protected Void call() throws Exception {
                String new_value = userName_box.getValue();

                if (new_value.length() != 0 && !userNames_list.contains(new_value)) {
                    ObservableList<String> userName_resultList = userNames_list.filtered(name -> name.contains(new_value));
                    if (userName_resultList.size() != 0) {
                        Platform.runLater(() -> {
                            userName_box.setItems(userName_resultList);
                            userName_box.show();
                        });
                    }
                } else {
                    Platform.runLater(() -> {
                        userName_box.setItems(userNames_list);
                    });
                }
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();


    }




}
