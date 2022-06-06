package View.Public_views;

import AppManager.UIManager;
import DB_Manager.Main_Data_Manager.*;
import Interfaces.ViewControllerClass;
import Model.Enums.AlertType;
import Model.Enums.QueryState;
import Model.Main_Data.*;
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
import javafx.scene.layout.VBox;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Export_Client_ViewController implements Initializable, ViewControllerClass {
    //TODO| Class Variables *************************************************************************************************

    Export_Client_DataManager export_client_dataManager = new Export_Client_DataManager();
    EarnAndSpendMoney_DataManager earnAndSpendMoney_dataManager = new EarnAndSpendMoney_DataManager();
    Envoy_DataManager envoy_dataManager = new Envoy_DataManager();
    Items_DataManager items_dataManager = new Items_DataManager();
    ItemMove_DataManger itemMove_dataManger = new ItemMove_DataManger();
    ItemExpire_DataManger itemExpire_dataManger = new ItemExpire_DataManger();
    ObservableList<JFXTextField> integerFields = FXCollections.observableArrayList();
    ObservableList<JFXTextField> doubleFields = FXCollections.observableArrayList();
    ObservableList<Client> clients_list = FXCollections.observableArrayList();
    ObservableList<Client> filtered_clients = FXCollections.observableArrayList();
    ObservableList<String> items_names_list = FXCollections.observableArrayList();
    ObservableList<String> items_codes_list = FXCollections.observableArrayList();
    ObservableList<Export_Bill> bill_items_list = FXCollections.observableArrayList();
    ObservableList<Export_Bill> client_bills_list = FXCollections.observableArrayList();
    ObservableList<Earn_And_Spend_Bill> client_earn_list = FXCollections.observableArrayList();
    ObservableList<Export_Bill> filtered_export_bills = FXCollections.observableArrayList();
    ObservableList<Earn_And_Spend_Bill> filtered_earn_bills = FXCollections.observableArrayList();

    Export_Bill added_exported_bill_item = new Export_Bill();

    int current_item_index = 0;
    int bill_code =0;
    double checked_quantity = 0.0;

    DecimalFormat df = new DecimalFormat("#.##");

    Client selected_client ;

    Envoy selected_envoy ;

    Item selected_Item = null , current_Updated_Item = null;

    Item_Move current_Moved_Item = null ;

    Expire_Details exiting_Expire_Item = null  , current_Expire_Item = null  , checked_Expire_Item = null;

    String bill_kind_status= "export" ;

    String logo_img_param_code = "Reports/report_images/pharma_logo_r.png";

    JRBeanCollectionDataSource collection_Data;




    //TODO| Design Elements *************************************************************************************************

    @FXML
    private AnchorPane root , labelCont , mainCont ;

    @FXML
    private Label title_label;

    @FXML
    private JFXTextField client_name_TxtF , client_address_TxtF , client_phone_TxtF , client_search_code_TxtF;

    @FXML
    private JFXComboBox<String> client_kind_Comb , client_envoy_name_Comb , client_search_name_Comb
            , client_search_kind_Comb ,client_envoy_search_name_Comb;

    @FXML
    private JFXButton client_add_Btn , client_export_Btn , client_report_Btn
            , client_edit_Btn, client_print_Btn, clean_Btn , client_return_Btn;

    @FXML
    private JFXCheckBox select_all_Checkbox;

    @FXML
    private TableView<Client> client_Table;

    @FXML
    private TableColumn<Client, String> client_index_Colo , client_code_Colo , client_name_Colo ,
            client_kind_Colo , client_envoy_name_Colo, client_address_Colo
            , client_phone_Colo  ;

    @FXML
    private TableColumn<Client, JFXCheckBox> client_selected_Colo;

    @FXML
    private TableColumn<Client, Double> client_current_balance_Colo , client_balance_received_Colo , client_balance_remaining_Colo;

    //TODO| 2- Design Elements Second Screen  ( Import Bill )************************************************************************************** -

    @FXML
    private AnchorPane secondCont;

    @FXML
    private VBox add_bill_Cont;

    @FXML
    private JFXDatePicker bill_date_Picker , bill_item_expire_date_Picker;

    @FXML
    private JFXComboBox<String> bill_pay_kind_Comb, bill_item_name_Comb;

    @FXML
    private Label bill_item_kind_Label;


    @FXML
    private JFXTextField bill_item_code_TxtF, bill_item_count_TxtF, bill_item_price_TxtF,
            bill_item_total_price_TxtF, bill_client_code_TxtF,bill_envoy_code_TxtF ,
            bill_client_name_TxtF,bill_envoy_name_TxtF , bill_total_price_TxtF,
            bill_total_discount_TxtF, bill_total_received_TxtF, bill_receipt_number_TxtF
            , bill_final_total_TxtF , bill_code_TxtF , bill_item_buy_price_TxtF;

    @FXML
    private TableView<Export_Bill> bill_Table;

    @FXML
    private TableColumn<Export_Bill, String> bill_index_Colo, bill_item_code_Colo, bill_item_name_Colo, bill_item_kind_Colo;

    @FXML
    private TableColumn<Export_Bill, Integer> bill_item_count_Colo;

    @FXML
    private TableColumn<Export_Bill, Double> bill_item_buy_price_Colo ,bill_item_price_Colo, bill_item_total_price_Colo;

    @FXML
    private TableColumn<Import_Bill, Date> bill_item_expire_date_Colo;

    @FXML
    private JFXButton bill_item_add_Btn, bill_confirm_Btn;

    //TODO| 3- Design Elements Third Screen  ( Client Report )************************************************************************************** -

    @FXML
    private AnchorPane thirdCont;

    @FXML
    private JFXDatePicker export_review_search_fromDate_Picker , export_review_search_toDate_Picker;

    @FXML
    private TableView<Export_Bill> export_review_Table;

    @FXML
    private TableColumn<Export_Bill, String> export_review_index_Colo, export_review_client_Colo, export_review_pay_kind_Colo
            , export_review_item_Colo , export_review_item_kind_Colo , export_review_envoy_Colo;

    @FXML
    private TableColumn<Export_Bill, Integer> export_review_code_Colo , export_review_quantity_Colo;

    @FXML
    private TableColumn<Export_Bill, Double> export_review_price_Colo, export_review_total_price_Colo;

    @FXML
    private TableColumn<Export_Bill, Date>  export_review_date_Colo , export_review_expire_date_Colo;

    @FXML
    private TableView<Earn_And_Spend_Bill>  review_Table;

    @FXML
    private TableColumn<Earn_And_Spend_Bill, String>  review_index_Colo , review_kind_Colo , review_name_Colo , review_notes_Colo;

    @FXML
    private TableColumn<Earn_And_Spend_Bill, Double>  review_value_Colo;

    @FXML
    private TableColumn<Earn_And_Spend_Bill, Integer> review_code_Colo ;

    @FXML
    private TableColumn<Earn_And_Spend_Bill, Date> review_date_Colo ;

    @FXML
    private JFXButton export_review_clean_Btn, export_review_back_Btn , export_review_print_Btn;




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

        // For Kind List Data
        ObservableList<String> kinds_list = FXCollections.observableArrayList();
        kinds_list.addAll("مزارع", "تجاري");
        client_kind_Comb.setItems(kinds_list);
        client_kind_Comb.setValue("");

        client_search_kind_Comb.setItems(kinds_list);
        client_search_kind_Comb.setValue("");
        TextFields.bindAutoCompletion(client_search_kind_Comb.getEditor(), client_search_kind_Comb.getItems());

        // For client envoys Names
        ObservableList<String> envoy_names_list = FXCollections.observableArrayList();
        envoy_names_list.addAll(envoy_dataManager.load_envoys_names_list());
        client_envoy_name_Comb.setItems(envoy_names_list);
        client_envoy_name_Comb.setValue("");

        client_envoy_search_name_Comb.setItems(envoy_names_list);
        client_envoy_search_name_Comb.setValue("");
        TextFields.bindAutoCompletion(client_envoy_search_name_Comb.getEditor(), client_envoy_search_name_Comb.getItems());



        // For clients Names
        ObservableList<String> names_list = FXCollections.observableArrayList();
        names_list.addAll(export_client_dataManager.load_clients_names_list());
        client_search_name_Comb.setItems(names_list);
        client_search_name_Comb.setValue("");
        TextFields.bindAutoCompletion(client_search_name_Comb.getEditor(), client_search_name_Comb.getItems());



        // For Kind List Data
        ObservableList<String> paid_kinds_list = FXCollections.observableArrayList();
        paid_kinds_list.addAll("نقدي", "أجل", "شيك", "حوالة", "إيصال");
        bill_pay_kind_Comb.setItems(paid_kinds_list);
        bill_pay_kind_Comb.setValue("");
        TextFields.bindAutoCompletion(bill_pay_kind_Comb.getEditor(), bill_pay_kind_Comb.getItems());


        //  For Items Names
        items_names_list.addAll(items_dataManager.load_items_names_list());
        bill_item_name_Comb.setItems(items_names_list);
        bill_item_name_Comb.setValue("");
        TextFields.bindAutoCompletion(bill_item_name_Comb.getEditor(), bill_item_name_Comb.getItems());

        //  For Items Codes
        items_codes_list.addAll(items_dataManager.load_items_codes_list());
        TextFields.bindAutoCompletion(bill_item_code_TxtF, items_codes_list);


        // For Double Numeric TextFields
        doubleFields.addAll(
                bill_item_count_TxtF , bill_item_buy_price_TxtF , bill_item_price_TxtF , bill_total_received_TxtF , bill_total_discount_TxtF
        );
        doubleFields.forEach(field -> {
            field.setText("");
            field.textProperty().addListener(($, old_value, new_value) -> {
                System.out.println("New value | " + new_value);
                if (!new_value.matches("\\d*")) {
                    field.setText(new_value.replaceAll("[^\\d ,^\\. ]", ""));
                }
            });
        });

        // For Integers Numeric TextFields
        integerFields.addAll(
                client_search_code_TxtF, client_phone_TxtF ,
                bill_item_code_TxtF , bill_code_TxtF
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
        client_Table.getStylesheets().add("/Assets/Custom_CSS/custom_table_font.css");

        // Boxes Style
        client_search_name_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 19  ;  -fx-font-weight:bold");
        client_search_kind_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 19  ;  -fx-font-weight:bold");
        client_envoy_search_name_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 19  ;  -fx-font-weight:bold");
        client_kind_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 19  ;  -fx-font-weight:bold");
        client_envoy_name_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 19  ;  -fx-font-weight:bold");

    }

    private void reset_data() {
        // main components
        client_name_TxtF.setDisable(false);
        client_name_TxtF.setText("");
        client_name_TxtF.setStyle(UIManager.white_background);

        client_kind_Comb.setValue("");
        client_kind_Comb.setStyle(UIManager.white_background);

        client_envoy_name_Comb.setValue("");
        client_envoy_name_Comb.setStyle(UIManager.white_background);

        client_address_TxtF.setText("");

        client_phone_TxtF.setText("");



        // search components
        client_search_code_TxtF.setText("");
        client_search_name_Comb.setValue("");
        client_search_kind_Comb.setValue("");
        client_envoy_search_name_Comb.setValue("");

        // help component
        select_all_Checkbox.setSelected(true);

        // load table
        loadClients();


        client_Table.refresh();

        selected_client =null ;

        selected_envoy = null ;




    }

    private void loadClients() {
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
                clients_list = FXCollections.observableArrayList();
                clients_list = export_client_dataManager.load_clients_data_list();

                Platform.runLater(() -> {
                    client_Table.setItems(clients_list);
                    root.getChildren().remove(dark_pane);

                });
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();

    }

    private void setupClientTable() {

        client_index_Colo.setCellValueFactory(
                (TableColumn.CellDataFeatures<Client, String> $)
                        -> new ReadOnlyObjectWrapper(client_Table.getItems().indexOf($.getValue()) + 1 + ""));

        client_selected_Colo.setCellValueFactory(new PropertyValueFactory<>("isSelected_checkBox"));

        client_code_Colo.setCellValueFactory(new PropertyValueFactory<>("code"));
        client_name_Colo.setCellValueFactory(new PropertyValueFactory<>("name"));
        client_kind_Colo.setCellValueFactory(new PropertyValueFactory<>("kind"));
        client_envoy_name_Colo.setCellValueFactory(new PropertyValueFactory<>("envoy_name"));

        client_address_Colo.setCellValueFactory(new PropertyValueFactory<>("address"));
        client_phone_Colo.setCellValueFactory(new PropertyValueFactory<>("phone"));

        client_current_balance_Colo.setCellValueFactory(new PropertyValueFactory<>("current_balance"));
        client_balance_received_Colo.setCellValueFactory(new PropertyValueFactory<>("balance_received"));
        client_balance_remaining_Colo.setCellValueFactory(new PropertyValueFactory<>("balance_remaining"));

        //TODO - setup action when clicking cell ***********************************************
        client_Table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        client_Table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2){
                selected_client = client_Table.getSelectionModel().getSelectedItem();

                client_name_TxtF.setText(selected_client.getName());
                client_name_TxtF.setDisable(true);
                client_kind_Comb.setValue(selected_client.getKind());
                client_envoy_name_Comb.setValue(selected_client.getEnvoy_name());
                client_address_TxtF.setText(String.valueOf(selected_client.getAddress()));
                client_phone_TxtF.setText(String.valueOf(selected_client.getPhone()));

                selected_envoy = envoy_dataManager.load_envoy_by_code(selected_client.getEnvoy_code());

            }

        });



    }

    private boolean detectValidMainData() {
        String integerPattern = "([0-9]*)";
        String decimalPattern = "([0-9]*)\\.([0-9]*)";
        //TODO | client name TextField ********************************************************
        boolean validClientName = !client_name_TxtF.getText().isEmpty();
        client_name_TxtF.setStyle(validClientName ? UIManager.white_background : UIManager.deepOrange_background);

        //TODO | client kind Box ********************************************************
        boolean validClientKind = !client_kind_Comb.getValue().isEmpty() ;
        client_kind_Comb.setStyle(validClientKind ? UIManager.white_background : UIManager.deepOrange_background);

        //TODO | client envoy_name Box ********************************************************
        boolean validClientEnvoy_Name = !client_envoy_name_Comb.getValue().isEmpty() ;
        client_envoy_name_Comb.setStyle(validClientEnvoy_Name ? UIManager.white_background : UIManager.deepOrange_background);

        //TODO | all  ********************************************************

        return (validClientName && validClientKind && validClientEnvoy_Name );
    }

    private  void setup_main_page_buttons(){


        // reset button
        clean_Btn.setOnAction(event -> {
            reset_data();
            initialized_boxes_data();

        });

        // add button
        client_add_Btn.setOnAction(event -> {
            try {
                if (detectValidMainData()){

                    Client added_client = new Client();
                    added_client.setName(client_name_TxtF.getText());
                    added_client.setKind(client_kind_Comb.getValue());
                    added_client.setEnvoy_code(envoy_dataManager.load_envoy_by_name(client_envoy_name_Comb.getValue()).getCode());
                    added_client.setEnvoy_name(client_envoy_name_Comb.getValue());
                    added_client.setAddress(client_address_TxtF.getText());
                    added_client.setPhone(client_phone_TxtF.getText());
                    added_client.setCurrent_balance(0.00);
                    added_client.setBalance_received(0.00);
                    added_client.setBalance_remaining(0.00);

                    if (export_client_dataManager.add_client(added_client).equals(QueryState.success)) {
                        UIManager.showAlert("تم اضافة عميل جديد بنجاح", AlertType.success, root, 0, 0);
                        Platform.runLater(() -> {
                            loadClients();
                            client_Table.refresh();
                        });
                        reset_data();
                        initialized_boxes_data();

                    } else {
                        UIManager.showAlert("تعذر اضافة العميل تأكد من عدم تكرار الاسم", AlertType.error, root, 0, 0);
                    }



                }else {
                    UIManager.show_error_alert("برجاء التأكد من اكتمال و صحة بيانات العناصر المظللة و المسبوقة ب *");

                }
            }catch (Exception e ){
                System.out.println("Error in Add Action ||| " + e.getMessage());
            }

        });

        // edit button
        client_edit_Btn.setOnAction(event -> {
            try {

                if (selected_client != null){
                    Alert before_edit_client_alert= new Alert(Alert.AlertType.WARNING,"تحذير سيتم تعديل بيانات هذا العميل .. هل توافق ؟ " , ButtonType.CANCEL ,  ButtonType.OK);
                    Optional<ButtonType> buttonTypeOptional = before_edit_client_alert.showAndWait();
                    if (buttonTypeOptional.get() == ButtonType.CANCEL){
                        selected_client = null ;
                        reset_data();
                    }
                    else if (buttonTypeOptional.get() == ButtonType.OK){
                        if (detectValidMainData()){

                            Client edited_client = new Client();
                            edited_client.setCode(selected_client.getCode());
                            edited_client.setName(selected_client.getName());
                            edited_client.setKind(client_kind_Comb.getValue());
                            edited_client.setEnvoy_code(envoy_dataManager.load_envoy_by_name(client_envoy_name_Comb.getValue()).getCode());
                            edited_client.setEnvoy_name(client_envoy_name_Comb.getValue());
                            edited_client.setAddress(client_address_TxtF.getText());
                            edited_client.setPhone(client_phone_TxtF.getText());
                            edited_client.setCurrent_balance(selected_client.getCurrent_balance());
                            edited_client.setBalance_received(selected_client.getBalance_received());
                            edited_client.setBalance_remaining(selected_client.getBalance_remaining());

                            if (export_client_dataManager.update_client(edited_client).equals(QueryState.success)) {
                                UIManager.showAlert("تم تعديل بيانات العميل  بنجاح", AlertType.success, root, 0, 0);
                                Platform.runLater(() -> {
                                    loadClients();
                                    client_Table.refresh();
                                });
                                reset_data();
                                initialized_boxes_data();

                            } else {
                                UIManager.showAlert("تعذر تعديل بيانات العميل تأكد من عدم تكرار الاسم", AlertType.error, root, 0, 0);
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

        // delete button
         /*      client_delete_Btn.setOnAction(event -> {
            try {

                if (selected_client != null){
                    Alert  before_delete_client_alert= new Alert(Alert.AlertType.WARNING,"تحذير سيتم حذف بيانات هذا العميل .. هل توافق ؟ " , ButtonType.CANCEL ,  ButtonType.OK);
                    Optional<ButtonType> buttonTypeOptional = before_delete_client_alert.showAndWait();
                    if (buttonTypeOptional.get() == ButtonType.CANCEL){
                        selected_client = null ;
                        reset_data();
                    }
                    else if (buttonTypeOptional.get() == ButtonType.OK){

                        Client deleted_client = new Client();
                        deleted_client.setCode(selected_client.getCode());

                        if (export_client_dataManager.delete_client(deleted_client).equals(QueryState.success)) {
                            UIManager.showAlert("تم حذف بيانات العميل  بنجاح", AlertType.success, root, 0, 0);
                            Platform.runLater(() -> {
                                loadClients();
                                client_Table.refresh();
                            });
                            reset_data();
                            initialized_boxes_data();

                        } else {
                            UIManager.showAlert("تعذر حذف بيانات العميل حدد و حاول مرة أخري ", AlertType.error, root, 0, 0);
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
        // export button
        client_export_Btn.setOnAction(event -> {
            try {
                if (selected_client == null) {
                    UIManager.showAlert("يجب عليك تحديد عميل أولا لإنشاء فاتورة بيع ", AlertType.alarm, root, 0, 0);


                } else {

                    mainCont.setVisible(false);
                    secondCont.setVisible(true);
                    thirdCont.setVisible(false);

                    bill_date_Picker.setDisable(false);
                    bill_pay_kind_Comb.setDisable(false);
                    bill_date_Picker.setValue(LocalDate.now());
                    bill_pay_kind_Comb.setValue("");
                    bill_client_code_TxtF.setText(String.valueOf(selected_client.getCode()));
                    bill_client_name_TxtF.setText(selected_client.getName());
                    bill_envoy_code_TxtF.setText(String.valueOf(selected_client.getEnvoy_code()));
                    bill_envoy_name_TxtF.setText(selected_client.getEnvoy_name());
                    bill_items_list = FXCollections.observableArrayList();
                    bill_Table.setItems(bill_items_list);
                    bill_Table.refresh();
                    bill_total_received_TxtF.setDisable(true);
                    bill_total_discount_TxtF.setDisable(true);
                    bill_receipt_number_TxtF.setDisable(true);
                    bill_code_TxtF.setDisable(true);
                    bill_confirm_Btn.setDisable(true);
                    bill_kind_status = "export" ;
                    current_item_index = 0;

                    bill_item_code_TxtF.setText("");
                    bill_item_code_TxtF.setStyle(UIManager.white_background);

                    bill_item_name_Comb.setValue("");
                    bill_item_name_Comb.setStyle(UIManager.white_background);

                    bill_item_buy_price_TxtF.setText("");
                    bill_item_buy_price_TxtF.setStyle(UIManager.white_background);

                    bill_item_count_TxtF.setText("");
                    bill_item_count_TxtF.setStyle(UIManager.white_background);

                    bill_item_kind_Label.setText("نوع الوحدة");

                    bill_item_price_TxtF.setText("");
                    bill_item_price_TxtF.setStyle(UIManager.white_background);

                    bill_item_total_price_TxtF.setText("0.00");
                    title_label.setText("  إنشاء فاتورة بيع إلي  العميل >> " + selected_client.getName());

                }


            } catch (Exception e) {
                System.out.println("Error in export Action ||| " + e.getMessage());
            }

        });

        // return button
        client_return_Btn.setOnAction(event -> {
            try {
                if (selected_client == null) {
                    UIManager.showAlert("يجب عليك تحديد عميل أولا لإنشاء فاتورة مرتجع ", AlertType.alarm, root, 0, 0);


                } else {
                    Alert  before_delete_client_alert= new Alert(Alert.AlertType.WARNING,"تحذير سيتم إنشاء فاتورة مرتجع لهذا العميل .. هل توافق ؟ " , ButtonType.CANCEL ,  ButtonType.OK);
                    Optional<ButtonType> buttonTypeOptional = before_delete_client_alert.showAndWait();
                    if (buttonTypeOptional.get() == ButtonType.CANCEL){
                        selected_client = null ;
                        reset_data();
                    }
                    else if (buttonTypeOptional.get() == ButtonType.OK) {

                        mainCont.setVisible(false);
                        secondCont.setVisible(true);
                        thirdCont.setVisible(false);

                        bill_date_Picker.setDisable(false);
                        bill_pay_kind_Comb.setDisable(false);
                        bill_date_Picker.setValue(LocalDate.now());
                        bill_pay_kind_Comb.setValue("");
                        bill_client_code_TxtF.setText(String.valueOf(selected_client.getCode()));
                        bill_client_name_TxtF.setText(selected_client.getName());
                        bill_envoy_code_TxtF.setText(String.valueOf(selected_client.getEnvoy_code()));
                        bill_envoy_name_TxtF.setText(selected_client.getEnvoy_name());
                        bill_items_list = FXCollections.observableArrayList();
                        bill_Table.setItems(bill_items_list);
                        bill_Table.refresh();
                        bill_total_received_TxtF.setDisable(true);
                        bill_total_discount_TxtF.setDisable(true);
                        bill_receipt_number_TxtF.setDisable(true);
                        bill_confirm_Btn.setDisable(true);
                        bill_kind_status = "return";

                        bill_item_code_TxtF.setText("");
                        bill_item_code_TxtF.setStyle(UIManager.white_background);

                        bill_item_name_Comb.setValue("");
                        bill_item_name_Comb.setStyle(UIManager.white_background);

                        bill_item_buy_price_TxtF.setText("");
                        bill_item_buy_price_TxtF.setStyle(UIManager.white_background);

                        bill_item_count_TxtF.setText("");
                        bill_item_count_TxtF.setStyle(UIManager.white_background);

                        bill_item_kind_Label.setText("نوع الوحدة");

                        bill_item_price_TxtF.setText("");
                        bill_item_price_TxtF.setStyle(UIManager.white_background);

                        bill_item_total_price_TxtF.setText("0.00");
                        current_item_index = 0;
                        title_label.setText("  إنشاء فاتورة مرتجع من  العميل >> " + selected_client.getName());
                    }
                }


            } catch (Exception e) {
                System.out.println("Error in return Action ||| " + e.getMessage());
            }

        });

        // report button
        client_report_Btn.setOnAction(event -> {
            try {
                if (selected_client == null) {
                    UIManager.showAlert("يجب عليك تحديد عميل أولا للدخول لكشف الحساب الخاص به ", AlertType.alarm, root, 0, 0);


                } else {

                    mainCont.setVisible(false);
                    secondCont.setVisible(false);
                    thirdCont.setVisible(true);

                    export_review_search_fromDate_Picker.setValue(null);
                    export_review_search_toDate_Picker.setValue(null);

                    loadTables();

                    title_label.setText("  كشف حساب لفواتير البيع و المرتجعات و التحصيلات المالية من  العميل >>  " + selected_client.getName());

                }


            } catch (Exception e) {
                System.out.println("Error in Report Action ||| " + e.getMessage());
            }

        });

        // select all and unselect all check action
        select_all_Checkbox.selectedProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            client_Table.getItems().forEach(client -> {
               // client.getIsSelected_checkBox().setSelected(new_value);

            });
        });

        // print button

        client_print_Btn.setOnAction(event -> {
            try{
                String path = "Reports/public_reports/clients.jrxml";
                collection_Data =
                       new JRBeanCollectionDataSource(client_Table.getItems().filtered(client -> client.getIsSelected_checkBox().isSelected()));


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

                if (clients_list.size() > 0) {
                    filtered_clients = clients_list;
                    System.out.println("Filtered_clients | Size | " + filtered_clients.size());

                    //TODO|| 1- Filter by client Code  ***********************************************************************
                    if (client_search_code_TxtF.getText().length() != 0 ) {
                        filtered_clients = filtered_clients.filtered
                                (client -> replacedWordToArabic_Action(String.valueOf(client.getCode())).
                                        contains(replacedWordToArabic_Action(client_search_code_TxtF.getText())));
                        System.out.println("Filter | client_search_code_TxtF");
                    }

                    //TODO|| 2- Filter by client Name

                    if (client_search_name_Comb.getValue().length() != 0) {
                        filtered_clients = filtered_clients.filtered
                                (client -> replacedWordToArabic_Action(client.getName()).
                                        contains(replacedWordToArabic_Action(client_search_name_Comb.getValue())));
                        System.out.println("Filter | client_search_name_Comb");
                    }

                    //TODO|| 3- Filter by client Kind

                    if (client_search_kind_Comb.getValue().length() != 0) {
                        filtered_clients = filtered_clients.filtered
                                (client -> replacedWordToArabic_Action(client.getKind()).
                                        contains(replacedWordToArabic_Action(client_search_kind_Comb.getValue())));
                        System.out.println("Filter | client_search_kind_Comb");
                    }

                    //TODO|| 2- Filter by client Envoy_Name

                    if (client_envoy_search_name_Comb.getValue().length() != 0) {
                        filtered_clients = filtered_clients.filtered
                                (client -> replacedWordToArabic_Action(client.getEnvoy_name()).
                                        contains(replacedWordToArabic_Action(client_envoy_search_name_Comb.getValue())));
                        System.out.println("Filter | client_envoy_search_name_Comb");
                    }

                    System.out.println("Filtered_clients | Size : " + filtered_clients.size());
                    client_Table.setItems(filtered_clients);
                    client_Table.refresh();
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
        client_search_code_TxtF.textProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            filter_items();
        });

        // For Name Search
        client_search_name_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            filter_items();
        });

        // For Kind Search
        client_search_kind_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            filter_items();
        });


        // For Envoy Name Search
        client_envoy_search_name_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            filter_items();
        });


    }

    //TODO| 2-  This Class Functions   Second Screen  ( Import Bill ) **************************************************************************

    private boolean detectValidSecondData() {

        //TODO | pay kind Box ********************************************************
        boolean validPayKind = !bill_pay_kind_Comb.getValue().isEmpty();
        bill_pay_kind_Comb.setStyle(validPayKind ? UIManager.white_background : UIManager.deepOrange_background);


        //TODO | item code TextField ********************************************************
        boolean validItemCode = items_codes_list.contains(bill_item_code_TxtF.getText());
        bill_item_code_TxtF.setStyle(validItemCode ? UIManager.white_background : UIManager.deepOrange_background);

        //TODO | item name TextField ********************************************************
        boolean validItemName = items_names_list.contains(bill_item_name_Comb.getValue());
        bill_item_name_Comb.setStyle(validItemName ? UIManager.white_background : UIManager.deepOrange_background);


        //TODO | item count TextField ********************************************************
        boolean validItemCount = false;
        if (bill_item_count_TxtF.getText().isEmpty()) {
            bill_item_count_TxtF.setStyle(validItemCount ? UIManager.white_background : UIManager.deepOrange_background);

        } else {
            if (bill_kind_status.equals("export")){
                validItemCount =  Double.valueOf(bill_item_count_TxtF.getText()) <= selected_Item.getCurrent_quantity() &&
                        Double.valueOf(bill_item_count_TxtF.getText()) > 0.0 ;
                bill_item_count_TxtF.setStyle(validItemCount ? UIManager.white_background : UIManager.deepOrange_background);

                if (!validItemCount &&  Double.valueOf(bill_item_count_TxtF.getText()) > 0.0){
                    UIManager.show_error_alert( "  لا يمكن بيع اكثر من الكمية المتاحة حاليا لهذا الصنف و هي  ::  "  + selected_Item.getCurrent_quantity());

                }
            }
            else if (bill_kind_status.equals("return")) {
                validItemCount = Double.valueOf(bill_item_count_TxtF.getText()) > 0.0 ;
                bill_item_count_TxtF.setStyle(validItemCount ? UIManager.white_background : UIManager.deepOrange_background);


            }


        }

        //TODO | item but price TextField ********************************************************
        boolean validItemBuyPrice = false;
        if (bill_item_buy_price_TxtF.getText().isEmpty()) {
            bill_item_buy_price_TxtF.setStyle(validItemBuyPrice ? UIManager.white_background : UIManager.deepOrange_background);

        } else {
            validItemBuyPrice = Double.valueOf(bill_item_buy_price_TxtF.getText()) > 0;
            bill_item_buy_price_TxtF.setStyle(validItemBuyPrice ? UIManager.white_background : UIManager.deepOrange_background);

        }

        //TODO | item price TextField ********************************************************
        boolean validItemPrice = false;
        if (bill_item_price_TxtF.getText().isEmpty()) {
            bill_item_price_TxtF.setStyle(validItemPrice ? UIManager.white_background : UIManager.deepOrange_background);

        } else {
            validItemPrice = Double.valueOf(bill_item_price_TxtF.getText()) >= 0;
            bill_item_price_TxtF.setStyle(validItemPrice ? UIManager.white_background : UIManager.deepOrange_background);

        }

        //TODO | date DatePicker ********************************************************

        boolean validDate = true;
        if (bill_date_Picker.getValue() == null ){
            validDate = false;
        }

        //TODO | expire date DatePicker ********************************************************

        boolean validExpireDate = true;
        if (bill_item_expire_date_Picker.getValue() == null ){
            validExpireDate = false;
        }

        bill_item_expire_date_Picker.getEditor().setStyle(validExpireDate ? UIManager.white_background : UIManager.red_background);


        //TODO | all and final validate  ********************************************************


        return (validPayKind && validItemCode && validItemName && validItemCount && validItemBuyPrice && validItemPrice && validDate && validExpireDate);
    }

    private boolean detectValidThirdData() {

        //TODO | bill total discount code TextField ********************************************************
        boolean validTotalDiscount = Double.parseDouble(bill_total_discount_TxtF.getText().isEmpty() ?
                "0.00" : bill_total_discount_TxtF.getText()) <= Double.parseDouble(bill_total_price_TxtF.getText().isEmpty() ?
                "0.00" : bill_total_price_TxtF.getText());
        bill_total_discount_TxtF.setStyle(validTotalDiscount ? UIManager.white_background : UIManager.deepOrange_background);

        //TODO | bill receipt number  TextField ********************************************************
        boolean validReceiptNumber ;

        if (bill_pay_kind_Comb.getValue().equals("نقدي") || bill_pay_kind_Comb.getValue().equals("أجل")){
            validReceiptNumber = true ;
            bill_total_discount_TxtF.setStyle(validTotalDiscount ? UIManager.white_background : UIManager.deepOrange_background);

        }
        else{
            validReceiptNumber  = !bill_receipt_number_TxtF.getText().isEmpty();

            bill_receipt_number_TxtF.setStyle(validReceiptNumber ? UIManager.white_background : UIManager.deepOrange_background);

        }

        //TODO | bill  code TextField ********************************************************
        boolean validBillCode = !bill_code_TxtF.getText().isEmpty();
        bill_code_TxtF.setStyle(validBillCode ? UIManager.white_background : UIManager.deepOrange_background);

        //TODO | bill  code Exist Or No ********************************************************
        boolean validBillCodeExist = true ;
        if (validBillCode){
            validBillCodeExist = !export_client_dataManager.detect_export_bill_next_code(Integer.parseInt(bill_code_TxtF.getText()));
            bill_code_TxtF.setStyle(validBillCodeExist ? UIManager.white_background : UIManager.deepOrange_background);
            if (!validBillCodeExist){
                UIManager.show_error_alert("لا يمكن ان تكرار رقم فاتورة مسجل من قبل اكتب رقم جديد من فضلك ");

            }
        }


        //TODO | all and final validate  ********************************************************

        if (!validTotalDiscount){
            UIManager.show_error_alert("لا يمكن ان تكون قيمة الخصم اكبر من قيمة الفاتورة ");

        }
        if (!validReceiptNumber || !validBillCode){
            UIManager.show_error_alert("لا يمكن تجاهل الخانات المظللة");

        }


        return (validTotalDiscount && validReceiptNumber && validBillCode && validBillCodeExist);
    }

    private void setup_selectedAndCalculatedData() {

        //TODO| 1 - Selected Data
        //  For Item Code Search And Set Item Name And Item Kind
        bill_item_code_TxtF.setOnKeyReleased(event -> {
            String new_value = bill_item_code_TxtF.getText();
            System.out.println("New value | " + new_value);

            if (items_codes_list.contains(new_value)) {

                selected_Item = items_dataManager.load_item_by_code(Integer.parseInt(new_value));
                bill_item_name_Comb.setValue(selected_Item.getName());
                bill_item_kind_Label.setText(selected_Item.getKind());
                bill_item_buy_price_TxtF.setText(String.valueOf(selected_Item.getBuy_price()));
                if (selected_client.getKind().equals("مزارع")){
                    if (bill_pay_kind_Comb.getValue().equals("أجل")){
                        bill_item_price_TxtF.setText(String.valueOf(selected_Item.getFarmer_Installment_price()));
                    }
                    else {
                        bill_item_price_TxtF.setText(String.valueOf(selected_Item.getFarmer_cash_price()));

                    }
                }else if (selected_client.getKind().equals("تجاري")){
                    if (bill_pay_kind_Comb.getValue().equals("أجل")){
                        bill_item_price_TxtF.setText(String.valueOf(selected_Item.getTrade_Installment_price()));

                    }
                    else {
                        bill_item_price_TxtF.setText(String.valueOf(selected_Item.getTrade_cash_price()));

                    }
                }
            } else {

                bill_item_name_Comb.setValue("");
                bill_item_kind_Label.setText("نوع الوحدة");
                bill_item_buy_price_TxtF.setText("");


            }
        });


        //  For Item Name Search And Set Item Code And Item Kind
        bill_item_name_Comb.setOnKeyReleased(event -> {
            String new_value = bill_item_name_Comb.getValue();
            System.out.println("New value | " + new_value);

            if (items_names_list.contains(new_value)) {
                selected_Item = items_dataManager.load_item_by_name(new_value);

                bill_item_code_TxtF.setText(String.valueOf(selected_Item.getCode()));
                bill_item_kind_Label.setText(selected_Item.getKind());
                bill_item_buy_price_TxtF.setText(String.valueOf(selected_Item.getBuy_price()));

                if (selected_client.getKind().equals("مزارع")){
                    if (bill_pay_kind_Comb.getValue().equals("أجل")){
                        bill_item_price_TxtF.setText(String.valueOf(selected_Item.getFarmer_Installment_price()));
                    }
                    else {
                        bill_item_price_TxtF.setText(String.valueOf(selected_Item.getFarmer_cash_price()));

                    }
                }else if (selected_client.getKind().equals("تجاري")){
                    if (bill_pay_kind_Comb.getValue().equals("أجل")){
                        bill_item_price_TxtF.setText(String.valueOf(selected_Item.getTrade_Installment_price()));

                    }
                    else {
                        bill_item_price_TxtF.setText(String.valueOf(selected_Item.getTrade_cash_price()));

                    }
                }
            } else {
                bill_item_code_TxtF.setText("");
                bill_item_kind_Label.setText("نوع الوحدة");
                bill_item_buy_price_TxtF.setText("");

            }

        });

        //  For Change In Bill Pay Kind Status
        bill_pay_kind_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);

            if (new_value.equals("أجل")) {
                bill_total_received_TxtF.setDisable(false);



            } else {
                bill_total_received_TxtF.setDisable(true);

            }

            if (selected_Item != null){
                if (selected_client.getKind().equals("مزارع")){
                    if (bill_pay_kind_Comb.getValue().equals("أجل")){
                        bill_item_price_TxtF.setText(String.valueOf(selected_Item.getFarmer_Installment_price()));
                    }
                    else {
                        bill_item_price_TxtF.setText(String.valueOf(selected_Item.getFarmer_cash_price()));

                    }
                }else if (selected_client.getKind().equals("تجاري")){
                    if (bill_pay_kind_Comb.getValue().equals("أجل")){
                        bill_item_price_TxtF.setText(String.valueOf(selected_Item.getTrade_Installment_price()));

                    }
                    else {
                        bill_item_price_TxtF.setText(String.valueOf(selected_Item.getTrade_cash_price()));

                    }
                }
            }


        });


        //TODO| 2 - Calculated Data

        //  For Calculate Item Total Price While Count Change
        bill_item_count_TxtF.textProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);

            if (!new_value.isEmpty()) {
                double count = Double.parseDouble(new_value);
                double price = bill_item_price_TxtF.getText().isEmpty() ? 0.00 : Double.parseDouble(bill_item_price_TxtF.getText());
                double total = count * price;
                bill_item_total_price_TxtF.setText(total == 0 ? "0.00" : df.format(total));

            } else {
                bill_item_total_price_TxtF.setText("0.00");

            }

        });


        //  For Calculate Item Total Price While Price Change
        bill_item_price_TxtF.textProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);

            if (!new_value.isEmpty()) {
                double count = bill_item_count_TxtF.getText().isEmpty() ? 0 : Double.parseDouble(bill_item_count_TxtF.getText());
                double price = Double.parseDouble(new_value);
                double total = count * price;
                bill_item_total_price_TxtF.setText(total == 0 ? "0.00" : df.format(total));

            } else {
                bill_item_total_price_TxtF.setText("0.00");

            }

        });

        //  For Calculate Bill Final Total Price While Price Change
        bill_total_discount_TxtF.textProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);


            double total =Double.parseDouble(bill_total_price_TxtF.getText().isEmpty() ?
                    "0.00" : bill_total_price_TxtF.getText()) -
                    Double.parseDouble( (new_value.isEmpty() ? "0.00" : new_value));


            bill_final_total_TxtF.setText( df.format(total));
            if (!bill_pay_kind_Comb.getValue().equals("أجل")){
                bill_total_received_TxtF.setText(df.format(total));
            }


        });


    }

    private void reset_add_item_data() {

        bill_date_Picker.setDisable(true);
        bill_pay_kind_Comb.setDisable(true);

        // main components
        bill_item_code_TxtF.setText("");
        bill_item_code_TxtF.setStyle(UIManager.white_background);

        bill_item_name_Comb.setValue("");
        bill_item_name_Comb.setStyle(UIManager.white_background);

        bill_item_buy_price_TxtF.setText("");
        bill_item_buy_price_TxtF.setStyle(UIManager.white_background);

        bill_item_count_TxtF.setText("");
        bill_item_count_TxtF.setStyle(UIManager.white_background);

        bill_item_kind_Label.setText("نوع الوحدة");

        bill_item_price_TxtF.setText("");
        bill_item_price_TxtF.setStyle(UIManager.white_background);

        bill_item_total_price_TxtF.setText("0.00");

        bill_item_expire_date_Picker.setValue(null);
        bill_item_expire_date_Picker.setStyle(UIManager.white_background);


    }

    private void reset_bill_data() {

        bill_date_Picker.setDisable(false);
        bill_pay_kind_Comb.setDisable(false);

        // main components
        bill_client_code_TxtF.setText("");

        bill_client_name_TxtF.setText("");

        bill_envoy_code_TxtF.setText("");

        bill_envoy_name_TxtF.setText("");

        bill_total_price_TxtF.setText("");

        bill_total_discount_TxtF.setText("");
        bill_total_discount_TxtF.setStyle(UIManager.white_background);

        bill_code_TxtF.setText("");
        bill_code_TxtF.setStyle(UIManager.white_background);

        bill_total_received_TxtF.setText("");

        bill_receipt_number_TxtF.setText("");
        bill_receipt_number_TxtF.setStyle(UIManager.white_background);

        bill_final_total_TxtF.setText("");

        bill_item_expire_date_Picker.setValue(null);
        bill_item_expire_date_Picker.setStyle(UIManager.white_background);


        bill_items_list.clear();

        selected_Item = null ;

        selected_client = null ;

        secondCont.setVisible(false);
        mainCont.setVisible(true);
        thirdCont.setVisible(false);

        title_label.setText("العملاء ( كشف العملاء _ إضافة  عميل جديد _ تعديل و حذف و طباعة بيانات  عميل _ إنشاء فاتورة بيع )");


        reset_data();
        client_Table.refresh();

    }

    private void setupBillTable() {


        bill_index_Colo.setCellValueFactory(new PropertyValueFactory<>("index"));
        bill_item_code_Colo.setCellValueFactory(new PropertyValueFactory<>("item_code"));
        bill_item_count_Colo.setCellValueFactory(new PropertyValueFactory<>("item_count"));

        bill_item_name_Colo.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        bill_item_kind_Colo.setCellValueFactory(new PropertyValueFactory<>("item_kind"));

        bill_item_buy_price_Colo.setCellValueFactory(new PropertyValueFactory<>("item_buy_price"));
        bill_item_price_Colo.setCellValueFactory(new PropertyValueFactory<>("item_price"));
        bill_item_total_price_Colo.setCellValueFactory(new PropertyValueFactory<>("item_total_price"));

        bill_item_expire_date_Colo.setCellValueFactory(new PropertyValueFactory<>("expire_date"));


    }

    private void setup_second_page_buttons() {
        //  add item btn
        bill_item_add_Btn.setOnAction(event -> {
            try {
                if (detectValidSecondData()) {


                    if (bill_kind_status.equals("return")) {

                        current_item_index++;
                        added_exported_bill_item = new Export_Bill();
                        added_exported_bill_item.setItem_code(selected_Item.getCode());
                        added_exported_bill_item.setBill_kind(bill_kind_status);
                        added_exported_bill_item.setIndex(current_item_index);
                        added_exported_bill_item.setItem_count(Double.valueOf(bill_item_count_TxtF.getText()));
                        added_exported_bill_item.setItem_name(selected_Item.getName());
                        added_exported_bill_item.setItem_buy_price(Double.parseDouble(bill_item_buy_price_TxtF.getText()));
                        added_exported_bill_item.setItem_kind(selected_Item.getKind());
                        added_exported_bill_item.setItem_price(Double.parseDouble(bill_item_price_TxtF.getText()));
                        added_exported_bill_item.setItem_total_price(Double.parseDouble(bill_item_total_price_TxtF.getText()));
                        added_exported_bill_item.setExpire_date(UIManager.convertToDate(bill_item_expire_date_Picker.getValue()));

                        bill_items_list.add(added_exported_bill_item);
                        bill_Table.setItems(bill_items_list);
                        bill_Table.refresh();
                        // problem here
                        double total_price =Double.parseDouble(bill_item_total_price_TxtF.getText().isEmpty() ?
                                "0.00" : bill_item_total_price_TxtF.getText()) +
                                Double.parseDouble(bill_total_price_TxtF.getText().isEmpty() ?
                                        "0.00" : bill_total_price_TxtF.getText());

                        bill_total_price_TxtF.setText(df.format(total_price));

                        double final_total_price = total_price -
                                Double.parseDouble(bill_total_discount_TxtF.getText().isEmpty() ?
                                        "0.00" : bill_total_discount_TxtF.getText());


                        bill_final_total_TxtF.setText(df.format(final_total_price));

                        if (!bill_pay_kind_Comb.getValue().equals("أجل")){
                            bill_total_received_TxtF.setText(df.format(final_total_price));
                        }

                        reset_add_item_data();

                        bill_total_discount_TxtF.setDisable(false);
                        bill_code_TxtF.setDisable(false);
                        bill_receipt_number_TxtF.setDisable(false);
                        bill_confirm_Btn.setDisable(false);




                    }
                    else {
                        // 1 - find if same item with same expire date and update quantity
                        boolean exist_status = itemExpire_dataManger.detect_expire_item_is_exist(selected_Item.getCode() , UIManager.convertToDate(bill_item_expire_date_Picker.getValue()));

                        if (!exist_status ){
                            UIManager.show_error_alert("برجاء التأكد من وجود هذا الصنف بنفس تاريخ الصلاحية حاليا");

                        }else {
                            checked_Expire_Item =
                                    itemExpire_dataManger.load_expire_item_by_code_and_date(selected_Item.getCode()
                                            , UIManager.convertToDate(bill_item_expire_date_Picker.getValue()));
                            checked_quantity =  Double.valueOf(bill_item_count_TxtF.getText()) ;
                            System.out.println("het sezww :: "+  bill_Table.getItems().filtered(expired -> expired.getItem_code() == selected_Item.getCode()  &&
                                    expired.getExpire_date().equals(UIManager.convertToDate(bill_item_expire_date_Picker.getValue()))
                            ).size());
                            bill_Table.getItems().filtered(expired -> expired.getItem_code() == selected_Item.getCode()  &&
                                    expired.getExpire_date().equals(UIManager.convertToDate(bill_item_expire_date_Picker.getValue()))
                            ).forEach(expire_item ->{

                                checked_quantity = checked_quantity +  expire_item.getItem_count() ;

                            });


                            if (checked_Expire_Item.getQuantity() >= checked_quantity) {

                                current_item_index++;
                                added_exported_bill_item = new Export_Bill();
                                added_exported_bill_item.setItem_code(selected_Item.getCode());
                                added_exported_bill_item.setBill_kind(bill_kind_status);
                                added_exported_bill_item.setIndex(current_item_index);
                                added_exported_bill_item.setItem_count(Double.valueOf(bill_item_count_TxtF.getText()));
                                added_exported_bill_item.setItem_name(selected_Item.getName());
                                added_exported_bill_item.setItem_buy_price(Double.parseDouble(bill_item_buy_price_TxtF.getText()));
                                added_exported_bill_item.setItem_kind(selected_Item.getKind());
                                added_exported_bill_item.setItem_price(Double.parseDouble(bill_item_price_TxtF.getText()));
                                added_exported_bill_item.setItem_total_price(Double.parseDouble(bill_item_total_price_TxtF.getText()));
                                added_exported_bill_item.setExpire_date(UIManager.convertToDate(bill_item_expire_date_Picker.getValue()));

                                bill_items_list.add(added_exported_bill_item);
                                bill_Table.setItems(bill_items_list);
                                bill_Table.refresh();
                                // problem here
                                double total_price =Double.parseDouble(bill_item_total_price_TxtF.getText().isEmpty() ?
                                        "0.00" : bill_item_total_price_TxtF.getText()) +
                                        Double.parseDouble(bill_total_price_TxtF.getText().isEmpty() ?
                                                "0.00" : bill_total_price_TxtF.getText());

                                bill_total_price_TxtF.setText(df.format(total_price));

                                double final_total_price = total_price -
                                        Double.parseDouble(bill_total_discount_TxtF.getText().isEmpty() ?
                                                "0.00" : bill_total_discount_TxtF.getText());


                                bill_final_total_TxtF.setText(df.format(final_total_price));

                                if (!bill_pay_kind_Comb.getValue().equals("أجل")){
                                    bill_total_received_TxtF.setText(df.format(final_total_price));
                                }

                                reset_add_item_data();

                                bill_total_discount_TxtF.setDisable(false);
                                bill_code_TxtF.setDisable(false);
                                bill_receipt_number_TxtF.setDisable(false);
                                bill_confirm_Btn.setDisable(false);

                            }
                            else {
                                UIManager.show_error_alert("برجاء التأكد من كفاية الكمية لهذا الصنف بنفس تاريخ الصلاحية حاليا أولا");

                            }


                        }
                    }

                } else {
                    UIManager.show_error_alert("برجاء التأكد من اكتمال و صحة بيانات العناصر المظللة و المسبوقة ب *");

                }
            } catch (Exception e) {
                System.out.println("Error In Add Item Bill Btn  bill_item_add_Btn ::: " + e.getMessage());
            }

        });

        // confirm bill btn
        bill_confirm_Btn.setOnAction(event -> {
            try {

                Alert before_confirm_bill_alert =
                        new Alert(Alert.AlertType.WARNING, "تنبيه سيتم تأكيد الفاتورة علي الشكل الحالي .. هل توافق ؟ ", ButtonType.CANCEL, ButtonType.OK);
                Optional<ButtonType> buttonTypeOptional = before_confirm_bill_alert.showAndWait();
                if (buttonTypeOptional.get() == ButtonType.CANCEL) {

                } else if (buttonTypeOptional.get() == ButtonType.OK) {
                    if (detectValidThirdData()){

                        /// initialize parameters
                        double tot_before = selected_client.getCurrent_balance()
                                , received_before= selected_client.getBalance_received() ,
                                remaining_before = selected_client.getBalance_remaining();
                        // detected next code
                        bill_code =Integer.parseInt(bill_code_TxtF.getText()) ;



                        //add data to bill database
                        bill_items_list.forEach(bill_item ->{
                            // add item to bill data
                            bill_item.setCode(bill_code);
                            bill_item.setClient_code(selected_client.getCode());
                            bill_item.setEnvoy_code(selected_client.getEnvoy_code());

                            bill_item.setPay_kind(bill_pay_kind_Comb.getValue());
                            bill_item.setClient_name(selected_client.getName());
                            bill_item.setEnvoy_name(selected_client.getEnvoy_name());
                            bill_item.setBill_receipt_number(bill_receipt_number_TxtF.getText());

                            bill_item.setBill_total_price(Double.parseDouble(bill_total_price_TxtF.getText().isEmpty() ?
                                    "0.00" : bill_total_price_TxtF.getText()));

                            bill_item.setBill_total_discount(Double.parseDouble(bill_total_discount_TxtF.getText().isEmpty() ?
                                    "0.00" : bill_total_discount_TxtF.getText()));

                            bill_item.setBill_total_received(Double.parseDouble(bill_total_received_TxtF.getText().isEmpty() ?
                                    "0.00" : bill_total_received_TxtF.getText()));

                            bill_item.setBill_final_total(Double.parseDouble(bill_final_total_TxtF.getText().isEmpty() ?
                                    "0.00" : bill_final_total_TxtF.getText()));

                            bill_item.setDate(UIManager.convertToDate(bill_date_Picker.getValue()));

                            export_client_dataManager.add_exported_bill(bill_item);

                            // add and update in expire details

                            // 1 - find if same item with same expire date and update quantity
                            boolean exist_status = itemExpire_dataManger.detect_expire_item_is_exist(bill_item.getItem_code() , bill_item.getExpire_date());

                            // 1.1 - if update exiting record
                            if (exist_status){
                                exiting_Expire_Item =  itemExpire_dataManger.load_expire_item_by_code_and_date(bill_item.getItem_code() , bill_item.getExpire_date());
                                if (bill_kind_status.equals("return")){
                                    // ++
                                    exiting_Expire_Item.setQuantity(exiting_Expire_Item.getQuantity() + bill_item.getItem_count());

                                    itemExpire_dataManger.update_item_Expire(exiting_Expire_Item);


                                }else if(bill_kind_status.equals("export")) {
                                    // --
                                    exiting_Expire_Item.setQuantity(exiting_Expire_Item.getQuantity() - bill_item.getItem_count());
                                    //delete
                                    if (exiting_Expire_Item.getQuantity() == 0 ) {
                                        itemExpire_dataManger.delete_Item_Expire(exiting_Expire_Item) ;
                                    }
                                    // update
                                    else {
                                        itemExpire_dataManger.update_item_Expire(exiting_Expire_Item);
                                    }

                                }

                            }
                            // 1.2 - else add new record
                            else {
                                current_Expire_Item = new Expire_Details();
                                current_Expire_Item.setItem_code(bill_item.getItem_code());
                                current_Expire_Item.setItem_name(bill_item.getItem_name());
                                current_Expire_Item.setItem_kind(bill_item.getItem_kind());
                                current_Expire_Item.setQuantity(bill_item.getItem_count());
                                current_Expire_Item.setExpire_date(bill_item.getExpire_date());

                                itemExpire_dataManger.add_item_Expire(current_Expire_Item);

                            }




                            // add to item move
                            current_Moved_Item = new Item_Move();
                            current_Moved_Item.setItem_code(bill_item.getItem_code());
                            current_Moved_Item.setItem_name(bill_item.getItem_name());
                            current_Moved_Item.setItem_kind(bill_item.getItem_kind());
                            if (bill_kind_status.equals("export")){
                                current_Moved_Item.setKind("صادر");

                            }
                            else if (bill_kind_status.equals("return")){
                                current_Moved_Item.setKind("مرتجع عميل");

                            }
                            current_Moved_Item.setQuantity(bill_item.getItem_count());
                            current_Moved_Item.setPrice(bill_item.getItem_price());
                            current_Moved_Item.setBuy_price(bill_item.getItem_buy_price());
                            current_Moved_Item.setExpire_date(bill_item.getExpire_date());
                            current_Moved_Item.setBill_code(bill_code);
                            current_Moved_Item.setEnvoy_name(selected_client.getEnvoy_name());
                            current_Moved_Item.setDate(UIManager.convertToDate(bill_date_Picker.getValue()));
                            current_Moved_Item.setClient_name(selected_client.getName());

                            itemMove_dataManger.add_item_Move(current_Moved_Item);


                            // update each item quantity
                            current_Updated_Item = items_dataManager.load_item_by_code(bill_item.getItem_code());
                            if (bill_kind_status.equals("export")){
                                current_Updated_Item.setCurrent_quantity(current_Updated_Item.getCurrent_quantity() - bill_item.getItem_count());

                            }
                            else if (bill_kind_status.equals("return")){
                                current_Updated_Item.setCurrent_quantity(current_Updated_Item.getCurrent_quantity() + bill_item.getItem_count());

                            }
                            items_dataManager.update_item(current_Updated_Item);





                        });



                        // update client data

                        if (bill_kind_status.equals("export")){
                            selected_client.setCurrent_balance(selected_client.getCurrent_balance() +
                                    Double.parseDouble(bill_final_total_TxtF.getText().isEmpty() ? "0.00" : bill_final_total_TxtF.getText()));

                            selected_client.setBalance_received(selected_client.getBalance_received() +
                                    Double.parseDouble(bill_total_received_TxtF.getText().isEmpty() ? "0.00" : bill_total_received_TxtF.getText()));

                        }
                        else if (bill_kind_status.equals("return")){
                            selected_client.setCurrent_balance(selected_client.getCurrent_balance() -
                                    Double.parseDouble(bill_final_total_TxtF.getText().isEmpty() ? "0.00" : bill_final_total_TxtF.getText()));

                            selected_client.setBalance_received(selected_client.getBalance_received() -
                                    Double.parseDouble(bill_total_received_TxtF.getText().isEmpty() ? "0.00" : bill_total_received_TxtF.getText()));

                        }

                        selected_client.setBalance_remaining(selected_client.getCurrent_balance() - selected_client.getBalance_received());

                        export_client_dataManager.update_client(selected_client);

                        // update envoy data

                        if (bill_kind_status.equals("export")){
                            selected_envoy.setCurrent_balance(selected_envoy.getCurrent_balance() +
                                    Double.parseDouble(bill_final_total_TxtF.getText().isEmpty() ? "0.00" : bill_final_total_TxtF.getText()));

                        }
                        else if (bill_kind_status.equals("return")){
                            selected_envoy.setCurrent_balance(selected_envoy.getCurrent_balance() -
                                    Double.parseDouble(bill_final_total_TxtF.getText().isEmpty() ? "0.00" : bill_final_total_TxtF.getText()));

                        }


                        envoy_dataManager.update_envoy(selected_envoy);

                        // success message
                        UIManager.showAlert(" تم تأكيد و إضافة الفاتورة بنجاح و رقم الفاتورة هو ::  " + bill_code, AlertType.success, root, 0, 0);

                        //  print bill
                           try{
                               String path = "Reports/public_reports/export_bill_main.jrxml";
                               collection_Data =
                                       new JRBeanCollectionDataSource(bill_items_list);

                               // params function
                               String Bill_Report_Header = "";
                               if (bill_kind_status.equals("export")){
                                    Bill_Report_Header = "فاتورة بيع للعميل /  ";

                               }
                               else if (bill_kind_status.equals("return")){
                                    Bill_Report_Header = "فاتورة مرتجع من العميل /  ";

                               }

                               Bill_Report_Header += selected_client.getName();
                               // print function

                               Map<String, Object> parameters = new HashMap<>();
                               parameters.put("img_param", logo_img_param_code);
                               parameters.put("bill_header", Bill_Report_Header);
                               parameters.put("tot_before_param", tot_before);
                               parameters.put("received_before_param", received_before);
                               parameters.put("remaining_before_param", remaining_before);
                               parameters.put("tot_after_param", selected_client.getCurrent_balance());
                               parameters.put("received_after_param", selected_client.getBalance_received());
                               parameters.put("remaining_after_param", selected_client.getBalance_remaining());

                               JasperDesign jd = JRXmlLoader.load(path);

                               JasperReport js = JasperCompileManager.compileReport(jd);

                               JasperPrint jp = JasperFillManager.fillReport(js, parameters, collection_Data);


                               JasperViewer.viewReport(jp, false);


                           }catch (Exception e){
                               System.out.println("Error In Print Bill :: " + e.getMessage());
                           }

                        // reset page

                        reset_bill_data();

                    }
                    else {

                    }

                }
            }catch (Exception e){
                System.out.println("Error in bill confirm btn bill_confirm_Btn ::   " + e.getMessage());
            }

        });

    }

    //TODO| 3-  This Class Functions  Third Screen  ( Client Report ) **************************************************************************

    private void loadTables() {
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
                client_bills_list = FXCollections.observableArrayList();
                client_earn_list = FXCollections.observableArrayList();

                client_bills_list = export_client_dataManager.load_client_exported_bills_data_list(selected_client.getCode());
                client_earn_list = earnAndSpendMoney_dataManager.load_client_earn_operations_data_list(selected_client.getName());

                Platform.runLater(() -> {
                    export_review_Table.setItems(client_bills_list);
                    review_Table.setItems(client_earn_list);
                    root.getChildren().remove(dark_pane);

                });
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();

    }

    private void setupReportTables() {

        // bill table

        export_review_index_Colo.setCellValueFactory(
                (TableColumn.CellDataFeatures<Export_Bill, String> $)
                        -> new ReadOnlyObjectWrapper(export_review_Table.getItems().indexOf($.getValue()) + 1 + ""));
        export_review_code_Colo.setCellValueFactory(new PropertyValueFactory<>("code"));
        export_review_client_Colo.setCellValueFactory(new PropertyValueFactory<>("client_name"));
        export_review_pay_kind_Colo.setCellValueFactory(new PropertyValueFactory<>("pay_kind"));
        export_review_item_Colo.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        export_review_quantity_Colo.setCellValueFactory(new PropertyValueFactory<>("item_count"));
        export_review_item_kind_Colo.setCellValueFactory(new PropertyValueFactory<>("item_kind"));
        export_review_price_Colo.setCellValueFactory(new PropertyValueFactory<>("item_price"));
        export_review_total_price_Colo.setCellValueFactory(new PropertyValueFactory<>("item_total_price"));
        export_review_envoy_Colo.setCellValueFactory(new PropertyValueFactory<>("envoy_name"));
        export_review_date_Colo.setCellValueFactory(new PropertyValueFactory<>("date"));
        export_review_expire_date_Colo.setCellValueFactory(new PropertyValueFactory<>("expire_date"));

        export_review_Table.setRowFactory($ -> {


            TableRow<Export_Bill> row = new TableRow<Export_Bill>() {
                @Override
                protected void updateItem(Export_Bill review, boolean empty) {
                    try {

                        super.updateItem(review, empty);
                        if (review != null) {
                            if (review.getBill_kind().equals("export")  ) {
                                setStyle(UIManager.green_background);

                            }
                            else if (review.getBill_kind().equals("return")  ) {
                                setStyle(UIManager.orange_background);
                            }
                            else setStyle("");
                        }

                    } catch (Exception e) {
                        System.out.println("error in row setupReviewTable ::: " + e.getMessage());
                    }

                }
            };

            return row;
        });



        //  spend table
        review_index_Colo.setCellValueFactory(
                (TableColumn.CellDataFeatures<Earn_And_Spend_Bill, String> $)
                        -> new ReadOnlyObjectWrapper(review_Table.getItems().indexOf($.getValue()) + 1 + ""));
        review_kind_Colo.setCellValueFactory(new PropertyValueFactory<>("kind"));
        review_code_Colo.setCellValueFactory(new PropertyValueFactory<>("code"));
        review_name_Colo.setCellValueFactory(new PropertyValueFactory<>("name"));
        review_value_Colo.setCellValueFactory(new PropertyValueFactory<>("value"));
        review_date_Colo.setCellValueFactory(new PropertyValueFactory<>("date"));
        review_notes_Colo.setCellValueFactory(new PropertyValueFactory<>("notes"));


        // styles
        // Table Style
        export_review_Table.getStylesheets().add("/Assets/Custom_CSS/custom_table_font.css");
        review_Table.getStylesheets().add("/Assets/Custom_CSS/custom_table_font.css");
        // Pickers Style
        export_review_search_fromDate_Picker.setStyle("-fx-background-color: #ffffff ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 18  ;  -fx-font-weight:bold");
        export_review_search_toDate_Picker.setStyle("-fx-background-color: #ffffff ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 18  ;  -fx-font-weight:bold");


    }

    private void setup_third_page_buttons() {


        // reset button
        export_review_clean_Btn.setOnAction(event -> {

            export_review_search_fromDate_Picker.setValue(null);
            export_review_search_toDate_Picker.setValue(null);

            loadTables();

        });

        // back button
        export_review_back_Btn.setOnAction(event -> {
            try {

                mainCont.setVisible(true);
                secondCont.setVisible(false);
                thirdCont.setVisible(false);

                reset_data();
                title_label.setText("العملاء ( كشف العملاء _ إضافة  عميل جديد _ تعديل و حذف و طباعة بيانات  عميل _ إنشاء فاتورة بيع )");




            } catch (Exception e) {
                System.out.println("Error in Back Action ||| " + e.getMessage());
            }

        });


        // print button
        export_review_print_Btn.setOnAction(event -> {
            try{
                String path = "";

                path = "Reports/public_reports/export_client_report.jrxml";


                collection_Data =
                        new JRBeanCollectionDataSource(export_review_Table.getItems());

                // print function
                Map<String, Object> parameters = new HashMap<>();

                double sum = review_Table.getItems().stream()
                        .map(x -> x.getValue())
                        .reduce(0.00, Double::sum);

                parameters.put("img_param", logo_img_param_code);
                parameters.put("earn_spend_list", review_Table.getItems());
                parameters.put("tot_earn_spend", sum);
                parameters.put("bill_header",  "كشف حساب ل العميل / " + selected_client.getName());

                JasperDesign jd = JRXmlLoader.load(path);

                JasperReport js = JasperCompileManager.compileReport(jd);

                JasperPrint jp = JasperFillManager.fillReport(js, parameters, collection_Data);


                JasperViewer.viewReport(jp, false);


            }catch (Exception e){
                System.out.println("Error In Print Company Report :: " + e.getMessage());
            }

        });


    }

    private void filter_client_report() {

        Task task = new Task<Void>() {
            @Override
            public Void call() {

                if (client_bills_list.size() > 0) {
                    filtered_export_bills = client_bills_list;
                    System.out.println("filtered_export_bills | Size | " + filtered_export_bills.size());

                    //  1- Filter by From Date and To Date Together
                    if (export_review_search_fromDate_Picker.getValue() != null && export_review_search_toDate_Picker.getValue() != null) {
                        filtered_export_bills = filtered_export_bills.filtered
                                (review_operation ->
                                        !UIManager.convertToDate(export_review_search_fromDate_Picker.getValue()).after(review_operation.getDate()) &&
                                                !UIManager.convertToDate(export_review_search_toDate_Picker.getValue()).before(review_operation.getDate()) );
                        System.out.println("Filter | From Date and To Date Together");
                    }

                    //  2- Filter by From Date
                    else  if (export_review_search_fromDate_Picker.getValue() != null && export_review_search_toDate_Picker.getValue() == null) {
                        filtered_export_bills = filtered_export_bills.filtered
                                (review_operation ->
                                        !UIManager.convertToDate(export_review_search_fromDate_Picker.getValue()).after(review_operation.getDate()) );
                        System.out.println("Filter | review_search_fromDate_Picker");
                    }
                    //  3- Filter by To Date
                    else if (export_review_search_fromDate_Picker.getValue() == null && export_review_search_toDate_Picker.getValue() != null) {
                        filtered_export_bills = filtered_export_bills.filtered
                                (review_operation ->
                                        !UIManager.convertToDate(export_review_search_toDate_Picker.getValue()).before(review_operation.getDate()) );
                        System.out.println("Filter | review_search_toDate_Picker");
                    }

                    System.out.println("filtered_export_bills | Size : " + filtered_export_bills.size());
                    export_review_Table.setItems(filtered_export_bills);
                    export_review_Table.refresh();
                }
                else {

                }

                if (client_earn_list.size() > 0) {
                    filtered_earn_bills = client_earn_list;
                    System.out.println("filtered_export_bills | Size | " + filtered_earn_bills.size());

                    //  1- Filter by From Date and To Date Together
                    if (export_review_search_fromDate_Picker.getValue() != null && export_review_search_toDate_Picker.getValue() != null) {
                        filtered_earn_bills = filtered_earn_bills.filtered
                                (review_operation ->
                                        !UIManager.convertToDate(export_review_search_fromDate_Picker.getValue()).after(review_operation.getDate()) &&
                                                !UIManager.convertToDate(export_review_search_toDate_Picker.getValue()).before(review_operation.getDate()) );
                        System.out.println("Filter | From Date and To Date Together");
                    }

                    //  2- Filter by From Date
                    else  if (export_review_search_fromDate_Picker.getValue() != null && export_review_search_toDate_Picker.getValue() == null) {
                        filtered_earn_bills = filtered_earn_bills.filtered
                                (review_operation ->
                                        !UIManager.convertToDate(export_review_search_fromDate_Picker.getValue()).after(review_operation.getDate()) );
                        System.out.println("Filter | review_search_fromDate_Picker");
                    }
                    //  3- Filter by To Date
                    else if (export_review_search_fromDate_Picker.getValue() == null && export_review_search_toDate_Picker.getValue() != null) {
                        filtered_earn_bills = filtered_earn_bills.filtered
                                (review_operation ->
                                        !UIManager.convertToDate(export_review_search_toDate_Picker.getValue()).before(review_operation.getDate()) );
                        System.out.println("Filter | review_search_toDate_Picker");
                    }

                    System.out.println("filtered_earn_bills | Size : " + filtered_earn_bills.size());
                    review_Table.setItems(filtered_earn_bills);
                    review_Table.refresh();
                }
                else {

                }

                return null;
            }
        };

        task.setOnSucceeded($ -> {

        });

        task.setOnFailed($ -> {
            System.out.println("Filter | Failed | " + $.toString());
        });

        Thread thread = new Thread(task);
        thread.start();

    }

    private void setup_client_report_filterOptions() {


        //TODO | DatePickers

        ObservableList<JFXDatePicker> pickers = FXCollections.observableArrayList();
        pickers.addAll(export_review_search_fromDate_Picker, export_review_search_toDate_Picker);
        pickers.forEach(picker -> {
            picker.valueProperty().addListener(($, old_value, new_value) -> {
                System.out.println("New value | " + new_value);
                filter_client_report();
            });

            picker.getEditor().textProperty().addListener(($, oldValue, newValue) -> {
                if (newValue.length() == 0) {
                    picker.setValue(null);
                }
            });
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
        setup_main_page_buttons();
        setup_second_page_buttons();
        setup_third_page_buttons();
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
        loadClients();

    }

    @Override
    public void setup_view() {
        setupClientTable();
        setupBillTable();
        setupReportTables();
        setup_filterOptions();
        setup_client_report_filterOptions();
        setup_selectedAndCalculatedData();


    }

    @Override
    public void setup_menu_buttons(){

    }


}
