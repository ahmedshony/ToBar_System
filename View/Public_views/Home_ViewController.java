package View.Public_views;

import AppManager.UIManager;
import Interfaces.ViewControllerClass;
import Model.Enums.AlertType;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Home_ViewController implements Initializable, ViewControllerClass {
    //TODO| Class Variables *************************************************************************************************
    double val = 175;
    boolean kind = true;
    String path = null;
    String filename;


    //TODO| Design Elements *************************************************************************************************

    @FXML
    private AnchorPane root , labelCont , menuCont;

    @FXML
    private Label title_label;

    // menu image
    @FXML
    private ImageView menu_btn_img;
    // menu bttons
    @FXML
    private JFXButton  menu_store_Btn , menu_items_move_Btn
             , menu_import_company_Btn , menu_envoy_Btn
            , menu_export_client_Btn , menu_earn_and_spend_money_Btn , menu_reviews_Btn
            , menu_bank_Btn , save_direct_Btn , save_local_Btn;


    //TODO| This Class Functions *************************************************************************************************
 private void play_audio() {
        AudioClip note = new AudioClip(this.getClass().getResource("ring2.mp3").toString());
        note.play();
    }

    //TODO| InterFace Class Functions *************************************************************************************************

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

              //    play_audio();
                  
                  
              } catch (Exception e) {
                  System.out.println("home in here " + e);
              }

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

        layout_view();
        setup_permissions();
        setup_view();
        setup_menu_buttons();


    }

    @Override
    public void layout_view() {

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

    @Override
    public void loadDB() {

    }

    @Override
    public void setup_view() {
        TranslateTransition translateTransitiond = new TranslateTransition(Duration.seconds(.1), menuCont);
        translateTransitiond.setByX(-val);
        translateTransitiond.play();

    }

    @Override
    public void setup_menu_buttons() {

        save_direct_Btn.setOnAction(event -> {
            try {
                String path = "";
                try {
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    path = "E:/Tobar_BACKUPS/TOBAR_" + date + ".sql";
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
                UIManager.showAlert("تم الحفظ بنجاح", AlertType.success, root, 0, 0);

            } catch (Exception ex) {
                ex.printStackTrace();
                ex.getMessage();
                System.out.println("error");
            }

        });

        save_local_Btn.setOnAction(event -> {
            try {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = directoryChooser.showDialog(null);
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                if (selectedDirectory == null) {
                    //No Directory selected
                    System.out.println("haha");
                    path = "E:/Tobar_BACKUPS/TOBAR_" + date + ".sql";

                } else {
                    System.out.println(selectedDirectory.getAbsolutePath());
                    System.out.println("one" + path);
                    path = selectedDirectory.getAbsolutePath();

                    path = path.replace('\\', '/');
                    System.out.println("two" + path);

                    path = path + "/TOBAR_" + date + ".sql";
                    System.out.println("three" + path);

                    String dbUserName = "root";
                    String dbPassword = "root1234";
                    String dbName = "tobar_system";
                    String executeCmd = "C:/AppServ/MySQL/bin/mysqldump.exe --no-defaults -u " + dbUserName + " -p" + dbPassword + " --add-drop-database -B " + dbName + " -r " + path;
                    Process runtimeProcess;
                    try {
                        System.out.println(executeCmd);//this out put works in mysql shell
                        runtimeProcess = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", executeCmd});
                        int processComplete = runtimeProcess.waitFor();

                        if (processComplete == 0) {
                            System.out.println("Backup created successfully");
                            UIManager.showAlert("تم الحفظ بنجاح", AlertType.success, root, 0, 0);

                        } else {
                            System.out.println("Could not create the backup");
                            UIManager.showAlert("يوجد مشكله ما اعد المحاوله مره اخري", AlertType.error, root, 0, 0);


                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        ex.getMessage();
                        System.out.println("error");
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        menu_btn_img.setOnMouseClicked(event -> {
            menu_btn_img.setDisable(true);
            if (kind == true) {
                menuCont.setLayoutY(0);
                menuCont.setLayoutX(0);
                TranslateTransition translateTransitiondd = new TranslateTransition(Duration.seconds(.5), menuCont);
                translateTransitiondd.setByX(val);
                translateTransitiondd.play();
                kind = false;
            } else {
                menuCont.setLayoutY(0);
                menuCont.setLayoutX(0);
                TranslateTransition translateTransitiondd = new TranslateTransition(Duration.seconds(.5), menuCont);
                translateTransitiondd.setByX(-val);
                translateTransitiondd.play();
                kind = true;

            }
            menu_btn_img.setDisable(false);

        });

        

        menu_store_Btn.setOnAction(event -> {
            try {
                Items_ViewController nextView = (Items_ViewController)
                        UIManager.open_new_view(this, "store_view.fxml", "صفحــة الأصنـــاف");

            }catch (Exception e){
                System.out.println("Error Occurs In setup_menu_buttons() || menu_store_Btn .. is ::   "+ e.getMessage());
            }


        });

        menu_items_move_Btn.setOnAction(event -> {
            try {
                Items_Move_ViewController nextView = (Items_Move_ViewController)
                        UIManager.open_new_view(this, "items_move_view.fxml", "صفحــة حـــركــة الأصنـــاف");

            }catch (Exception e){
                System.out.println("Error Occurs In setup_menu_buttons() || menu_items_move_Btn .. is ::   "+ e.getMessage());
            }


        });

        menu_import_company_Btn.setOnAction(event -> {
            try {
                Import_Company_ViewController nextView = (Import_Company_ViewController)
                        UIManager.open_new_view(this, "import_company_view.fxml", "صفحــة الشركات و إنشاء فواتير التوريد من الشركات");

            }catch (Exception e){
                System.out.println("Error Occurs In setup_menu_buttons() || menu_import_company_Btn .. is ::   "+ e.getMessage());
            }


        });

        menu_export_client_Btn.setOnAction(event -> {
            try {
                Export_Client_ViewController nextView = (Export_Client_ViewController)
                        UIManager.open_new_view(this, "export_client_view.fxml", "صفحــة العملاء و إنشاء فواتير البيع إلي العملاء");

            }catch (Exception e){
                System.out.println("Error Occurs In setup_menu_buttons() || menu_export_client_Btn .. is ::   "+ e.getMessage());
            }


        });

        menu_envoy_Btn.setOnAction(event -> {
            try {
                Envoy_ViewController nextView = (Envoy_ViewController)
                        UIManager.open_new_view(this, "envoy_view.fxml", "صفحــة المناديب");

            }catch (Exception e){
                System.out.println("Error Occurs In setup_menu_buttons() || menu_envoy_Btn .. is ::   "+ e.getMessage());
            }


        });

        menu_earn_and_spend_money_Btn.setOnAction(event -> {
            try {
                Earn_And_Spend_Money_ViewController nextView = (Earn_And_Spend_Money_ViewController)
                        UIManager.open_new_view(this, "earn_and_spend_money_view.fxml", "صفحــة التعاملات المالية ");

            }catch (Exception e){
                System.out.println("Error Occurs In setup_menu_buttons() || menu_earn_and_spend_money_Btn .. is ::   "+ e.getMessage());
            }


        });

        menu_reviews_Btn.setOnAction(event -> {
            try {
                Review_ViewController nextView = (Review_ViewController)
                        UIManager.open_new_view(this, "reviews_view.fxml", " صفحة مراجعة الفواتير السابقة  ");

            }catch (Exception e){
                System.out.println("Error Occurs In setup_menu_buttons() || menu_reviews_Btn .. is ::   "+ e.getMessage());
            }


        });

        menu_bank_Btn.setOnAction(event -> {
            try {
                Bank_ViewController nextView = (Bank_ViewController)
                        UIManager.open_new_view(this, "bank_view.fxml", " صفحة متابعة الأرصدة والأرباح  ");

            }catch (Exception e){
                System.out.println("Error Occurs In setup_menu_buttons() || menu_bank_Btn .. is ::   "+ e.getMessage());
            }


        });

       



    }





}
