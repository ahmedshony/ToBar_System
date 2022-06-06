package View.Public_views;

import AppManager.UIManager;
import DB_Manager.Main_Data_Manager.Envoy_DataManager;
import DB_Manager.Main_Data_Manager.Export_Client_DataManager;
import Interfaces.ViewControllerClass;
import Model.Enums.AlertType;
import Model.Enums.QueryState;
import Model.Main_Data.Client;
import Model.Main_Data.Envoy;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class Envoy_ViewController implements Initializable, ViewControllerClass {
    //TODO| Class Variables *************************************************************************************************

    Envoy_DataManager envoy_dataManager = new Envoy_DataManager();
    Export_Client_DataManager export_client_dataManager = new Export_Client_DataManager();

    ObservableList<JFXTextField> integerFields = FXCollections.observableArrayList();
    ObservableList<Envoy> envoys_list = FXCollections.observableArrayList();
    ObservableList<Client> updated_clients_list = FXCollections.observableArrayList();
    ObservableList<Envoy> filtered_envoys = FXCollections.observableArrayList();

    String logo_img_param_code = "Reports/report_images/pharma_logo_r.png";

    JRBeanCollectionDataSource collection_Data;

    Envoy selected_envoy ;


    //TODO| Design Elements *************************************************************************************************

    @FXML
    private AnchorPane root , labelCont , mainCont ;

    @FXML
    private Label title_label;

    @FXML
    private JFXTextField envoy_name_TxtF , envoy_phone_TxtF , envoy_search_code_TxtF;

    @FXML
    private JFXComboBox<String> envoy_search_name_Comb;

    @FXML
    private JFXButton envoy_add_Btn   , envoy_edit_Btn, envoy_print_Btn, clean_Btn;

    @FXML
    private JFXCheckBox select_all_Checkbox;

    @FXML
    private TableView<Envoy> envoy_Table;

    @FXML
    private TableColumn<Envoy, String> envoy_index_Colo , envoy_code_Colo , envoy_name_Colo
            , envoy_phone_Colo  ;

    @FXML
    private TableColumn<Envoy, JFXCheckBox> envoy_selected_Colo;

    @FXML
    private TableColumn<Envoy, Double> envoy_current_balance_Colo ;


    //TODO| This Class Functions *************************************************************************************************

    public String replacedWordToArabic_Action(String oldWord) {
        String NewWord = "";
        try {

            String typingTxt = oldWord;
            // الحروف
            String replaceString = typingTxt.replace('أ', 'ا').replace('آ', 'ا').replace('إ', 'ا');//تبديل مشتقات حرف الالف
            replaceString = replaceString.replace('پ', 'ب');//تبديل مشتقات حرف الباء
            replaceString = replaceString.replace('چ', 'ج');//تبديل مشتقات حرف الجيم
            replaceString = replaceString.replace('ژ', 'ز');//تبديل مشتقات حرف الزين
            replaceString = replaceString.replace('?', 'ف');//تبديل مشتقات حرف الفاء
            replaceString = replaceString.replace('گ', 'ك');//تبديل مشتقات حرف الكاف
            replaceString = replaceString.replace('ة', 'ه');//تبديل مشتقات حرف الهاء
            replaceString = replaceString.replace('ؤ', 'و');//تبديل مشتقات حرف الواو
            replaceString = replaceString.replace('ى', 'ي');//تبديل مشتقات حرف الياء\


            // الارقاااااام
            replaceString = replaceString.replace('0', '0');//تبديل مشتقات حرف الياء
            replaceString = replaceString.replace('1', '1');//تبديل مشتقات حرف الياء
            replaceString = replaceString.replace('2', '2');//تبديل مشتقات حرف الياء
            replaceString = replaceString.replace('3', '3');//تبديل مشتقات حرف الياء
            replaceString = replaceString.replace('4', '4');//تبديل مشتقات حرف الياء
            replaceString = replaceString.replace('5', '5');//تبديل مشتقات حرف الياء
            replaceString = replaceString.replace('6', '6');//تبديل مشتقات حرف الياء
            replaceString = replaceString.replace('7', '7');//تبديل مشتقات حرف الياء
            replaceString = replaceString.replace('8', '8');//تبديل مشتقات حرف الياء
            replaceString = replaceString.replace('9', '9');//تبديل مشتقات حرف الياء

            NewWord = replaceString;
            System.out.println(">>>>>>>>>>>>>> " + replaceString);
        } catch (Exception e) {
            System.out.println("error in replacWordToArabic_Action ::: " + e.getMessage());
        }
        return NewWord;
    }

    private void initialized_boxes_data() {

        // For envoys Names
        ObservableList<String> names_list = FXCollections.observableArrayList();
        names_list.addAll(envoy_dataManager.load_envoys_names_list());
        envoy_search_name_Comb.setItems(names_list);
        envoy_search_name_Comb.setValue("");
        TextFields.bindAutoCompletion(envoy_search_name_Comb.getEditor(), envoy_search_name_Comb.getItems());


        // For Numeric TextFields
        integerFields.addAll(
                envoy_search_code_TxtF,
                envoy_phone_TxtF
        );
        integerFields.forEach(field -> {
            field.setText("");
            field.textProperty().addListener(($, old_value, new_value) -> {
                System.out.println("New value | " + new_value);
                if (!new_value.matches("\\d*")) {
                    field.setText(new_value.replaceAll("[^\\d]", ""));
                }
            });
        });

        // Table Style
        envoy_Table.getStylesheets().add("/Assets/Custom_CSS/custom_table_font.css");

        // Boxes Style
        envoy_search_name_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 19  ;  -fx-font-weight:bold");

    }

    private void reset_data() {
        // main components
        envoy_name_TxtF.setDisable(false);
        envoy_name_TxtF.setText("");
        envoy_name_TxtF.setStyle(UIManager.white_background);



        envoy_phone_TxtF.setText("");



        // search components
        envoy_search_code_TxtF.setText("");
        envoy_search_name_Comb.setValue("");

        // help component
        select_all_Checkbox.setSelected(true);

        // load table
        loadEnvoys();


        envoy_Table.refresh();

        selected_envoy =null ;





    }

    private void loadEnvoys() {
        Pane dark_pane = new Pane();

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                Platform.runLater(() -> {
                    root.getChildren().add(dark_pane);
                    dark_pane.setStyle("-fx-background-color : #b6d7ff; ");
                    dark_pane.setOpacity(0.5);
                    dark_pane.setLayoutX(root.getLayoutX());
                    dark_pane.setLayoutY(root.getLayoutY());
                    dark_pane.setMinSize(root.getWidth(), root.getHeight());
                    dark_pane.setPrefSize(root.getWidth(), root.getHeight());

                    JFXSpinner spinner = new JFXSpinner();
                    dark_pane.getChildren().add(spinner);
                    spinner.setLayoutX(root.getWidth() / 2 - 35);
                    spinner.setLayoutY(root.getHeight() / 2 - 35);
                    spinner.setMinSize(70, 70);

                    Label label = new Label();
                    dark_pane.getChildren().add(label);
                    label.setTextFill(Paint.valueOf("white"));
                    label.setTextAlignment(TextAlignment.RIGHT);
                    label.setFont(Font.font(17));

                    label.setLayoutX(root.getWidth() / 2 - 70);
                    label.setLayoutY(root.getHeight() / 2 + 35);
                    label.setMinSize(70, 70);
                });
                envoys_list = FXCollections.observableArrayList();
                envoys_list = envoy_dataManager.load_envoys_data_list();

                Platform.runLater(() -> {
                    envoy_Table.setItems(envoys_list);
                    root.getChildren().remove(dark_pane);

                });
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();

    }

    private void setupEnvoyTable() {

        envoy_index_Colo.setCellValueFactory(
                (TableColumn.CellDataFeatures<Envoy, String> $)
                        -> new ReadOnlyObjectWrapper(envoy_Table.getItems().indexOf($.getValue()) + 1 + ""));

        envoy_selected_Colo.setCellValueFactory(new PropertyValueFactory<>("isSelected_checkBox"));

        envoy_code_Colo.setCellValueFactory(new PropertyValueFactory<>("code"));
        envoy_name_Colo.setCellValueFactory(new PropertyValueFactory<>("name"));
        envoy_phone_Colo.setCellValueFactory(new PropertyValueFactory<>("phone"));

        envoy_current_balance_Colo.setCellValueFactory(new PropertyValueFactory<>("current_balance"));

        //TODO - setup action when clicking cell ***********************************************
        envoy_Table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        envoy_Table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2){
                selected_envoy = envoy_Table.getSelectionModel().getSelectedItem();

                envoy_name_TxtF.setText(selected_envoy.getName());
                envoy_name_TxtF.setDisable(true);
                envoy_phone_TxtF.setText(String.valueOf(selected_envoy.getPhone()));

            }

        });



    }

    private boolean detectValidMainData() {

        //TODO | item name TextField ********************************************************
        boolean validItemName = !envoy_name_TxtF.getText().isEmpty();
        envoy_name_TxtF.setStyle(validItemName ? UIManager.white_background : UIManager.deepOrange_background);


        return (validItemName);
    }

    private  void setup_page_buttons(){


        // reset button
        clean_Btn.setOnAction(event -> {
            reset_data();
            initialized_boxes_data();

        });

        // add button
        envoy_add_Btn.setOnAction(event -> {
            try {
                if (detectValidMainData()){

                    Envoy added_envoy = new Envoy();
                    added_envoy.setName(envoy_name_TxtF.getText());
                    added_envoy.setPhone(envoy_phone_TxtF.getText());
                    added_envoy.setCurrent_balance(0.00);

                    if (envoy_dataManager.add_envoy(added_envoy).equals(QueryState.success)) {
                        UIManager.showAlert("تم اضافة مندوب جديد بنجاح", AlertType.success, root, 0, 0);
                        Platform.runLater(() -> {
                            loadEnvoys();
                            envoy_Table.refresh();
                        });
                        reset_data();
                        initialized_boxes_data();

                    } else {
                        UIManager.showAlert("تعذر اضافة المندوب تأكد من عدم تكرار الاسم", AlertType.error, root, 0, 0);
                    }



                }else {
                    UIManager.show_error_alert("برجاء التأكد من اكتمال و صحة بيانات العناصر المظللة و المسبوقة ب *");

                }
            }catch (Exception e ){
                System.out.println("Error in Add Action ||| " + e.getMessage());
            }

        });

        // edit button
        envoy_edit_Btn.setOnAction(event -> {
            try {

                if (selected_envoy != null){
                    Alert before_edit_envoy_alert= new Alert(Alert.AlertType.WARNING,"تحذير سيتم تعديل بيانات هذه الشركة .. هل توافق ؟ " , ButtonType.CANCEL ,  ButtonType.OK);
                    Optional<ButtonType> buttonTypeOptional = before_edit_envoy_alert.showAndWait();
                    if (buttonTypeOptional.get() == ButtonType.CANCEL){
                        selected_envoy = null ;
                        reset_data();
                    }
                    else if (buttonTypeOptional.get() == ButtonType.OK){
                        if (detectValidMainData()){

                            Envoy edited_envoy = new Envoy();
                            edited_envoy.setCode(selected_envoy.getCode());
                            edited_envoy.setName(selected_envoy.getName());
                            edited_envoy.setPhone(envoy_phone_TxtF.getText());

                            edited_envoy.setCurrent_balance(selected_envoy.getCurrent_balance());

                            if (envoy_dataManager.update_envoy(edited_envoy).equals(QueryState.success)) {
                                UIManager.showAlert("تم تعديل بيانات المندوب  بنجاح", AlertType.success, root, 0, 0);
                                updated_clients_list = export_client_dataManager.load_clients_by_envoy(selected_envoy.getCode()) ;
                                updated_clients_list.forEach(client -> {
                                    client.setEnvoy_name(envoy_name_TxtF.getText());
                                    export_client_dataManager.update_client(client);
                                });
                                Platform.runLater(() -> {

                                    loadEnvoys();
                                    envoy_Table.refresh();
                                });
                                reset_data();
                                initialized_boxes_data();

                            } else {
                                UIManager.showAlert("تعذر تعديل بيانات المندوب تأكد من عدم تكرار الاسم", AlertType.error, root, 0, 0);
                            }



                        }else {
                            UIManager.show_error_alert("برجاء التأكد من اكتمال و صحة بيانات العناصر المظللة و المسبوقة ب *");

                        }
                    }

                }
                else {
                    UIManager.showAlert("برجاء تحديد عنصر من الجدول أولا", AlertType.error, root, 0, 0);

                }

            }catch (Exception e ){
                System.out.println("Error in Add Action ||| " + e.getMessage());
            }

        });
        // delete
        /*
                // delete button
                envoy_delete_Btn.setOnAction(event -> {
                    try {


                        if (selected_envoy != null){
                            updated_clients_list = export_client_dataManager.load_clients_by_envoy(selected_envoy.getCode()) ;

                            if(updated_clients_list.size() >0){
                                UIManager.showAlert("يجب عليك عدم ربط المندوب بأي عميل أولا لإتمام عملية الحذف ", AlertType.error, root, 0, 0);

                            }
                            else {
                                Alert  before_delete_envoy_alert= new Alert(Alert.AlertType.WARNING,"تحذير سيتم حذف بيانات هذا المندوب .. هل توافق ؟ " , ButtonType.CANCEL ,  ButtonType.OK);
                                Optional<ButtonType> buttonTypeOptional = before_delete_envoy_alert.showAndWait();
                                if (buttonTypeOptional.get() == ButtonType.CANCEL){
                                    selected_envoy = null ;
                                    reset_data();
                                }
                                else if (buttonTypeOptional.get() == ButtonType.OK){

                                    Envoy deleted_envoy = new Envoy();
                                    deleted_envoy.setCode(selected_envoy.getCode());

                                    if (envoy_dataManager.delete_envoy(deleted_envoy).equals(QueryState.success)) {
                                        UIManager.showAlert("تم حذف بيانات المندوب  بنجاح", AlertType.success, root, 0, 0);
                                        Platform.runLater(() -> {
                                            loadEnvoys();
                                            envoy_Table.refresh();
                                        });
                                        reset_data();
                                        initialized_boxes_data();

                                    } else {
                                        UIManager.showAlert("تعذر حذف بيانات المندوب حدد و حاول مرة أخري ", AlertType.error, root, 0, 0);
                                    }

                                }
                            }


                        }
                        else {
                            UIManager.showAlert("برجاء تحديد عنصر من الجدول أولا", AlertType.error, root, 0, 0);

                        }

                    }catch (Exception e ){
                        System.out.println("Error in Add Action ||| " + e.getMessage());
                    }

                });
        */
                // select all and unselect all check action
        select_all_Checkbox.selectedProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            envoy_Table.getItems().forEach(envoy -> {
                envoy.getIsSelected_checkBox().setSelected(new_value);

            });
        });

        // print button


        envoy_print_Btn.setOnAction(event -> {
            try{
                String path = "Reports/public_reports/envoys.jrxml";
                collection_Data =
                        new JRBeanCollectionDataSource(envoy_Table.getItems().filtered(envoy -> envoy.getIsSelected_checkBox().isSelected()));


                // print function

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("img_param", logo_img_param_code);

                JasperDesign jd = JRXmlLoader.load(path);

                JasperReport js = JasperCompileManager.compileReport(jd);

                JasperPrint jp = JasperFillManager.fillReport(js, parameters, collection_Data);


                JasperViewer.viewReport(jp, false);


            }catch (Exception e){
                System.out.println("Error In Print Clients :: " + e.getMessage());
            }

        });



    }

    private void filter_items() {

        Task task = new Task<Void>() {
            @Override
            public Void call() {

                if (envoys_list.size() > 0) {
                    filtered_envoys = envoys_list;
                    System.out.println("Filtered_envoys | Size | " + filtered_envoys.size());

                    //TODO|| 1- Filter by envoy Code  ***********************************************************************
                    if (envoy_search_code_TxtF.getText().length() != 0 ) {
                        filtered_envoys = filtered_envoys.filtered
                                (envoy -> replacedWordToArabic_Action(String.valueOf(envoy.getCode())).
                                        contains(replacedWordToArabic_Action(envoy_search_code_TxtF.getText())));
                        System.out.println("Filter | envoy_search_code_TxtF");
                    }

                    //TODO|| 2- Filter by envoy Name

                    if (envoy_search_name_Comb.getValue().length() != 0) {
                        filtered_envoys = filtered_envoys.filtered
                                (envoy -> replacedWordToArabic_Action(envoy.getName()).
                                        contains(replacedWordToArabic_Action(envoy_search_name_Comb.getValue())));
                        System.out.println("Filter | envoy_search_name_Comb");
                    }

                    System.out.println("Filtered_envoys | Size : " + filtered_envoys.size());
                    envoy_Table.setItems(filtered_envoys);
                    envoy_Table.refresh();
                } else {

                }

                return null;
            }
        };

        task.setOnSucceeded($ -> {

        });

        task.setOnFailed($ -> {
            System.out.println("Filter | Failed | ");
        });

        Thread thread = new Thread(task);
        thread.start();

    }

    private void setup_filterOptions() {

        //  For Code Search
        envoy_search_code_TxtF.textProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            filter_items();
        });

        // For Name Search
        envoy_search_name_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            filter_items();
        });

    }






    //TODO| InterFace Class Functions *************************************************************************************************

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        layout_view();
        setup_permissions();
        loadDB();
        setup_view();
        setup_menu_buttons();
        setup_page_buttons();
    }

    @Override
    public void layout_view() {
        initialized_boxes_data() ;

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
        loadEnvoys();

    }

    @Override
    public void setup_view() {
        setupEnvoyTable();
        setup_filterOptions();


    }

    @Override
    public void setup_menu_buttons(){

    }


}
