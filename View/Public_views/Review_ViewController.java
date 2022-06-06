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

public class Review_ViewController implements Initializable, ViewControllerClass {
    //TODO| Class Variables *************************************************************************************************
    Export_Client_DataManager export_client_dataManager = new Export_Client_DataManager();
    Import_Company_DataManager import_company_dataManager = new Import_Company_DataManager();
    Envoy_DataManager envoy_dataManager = new Envoy_DataManager();
    Items_DataManager items_dataManager = new Items_DataManager();
    ItemMove_DataManger itemMove_dataManger = new ItemMove_DataManger();
    ItemExpire_DataManger itemExpire_dataManger = new ItemExpire_DataManger();

    ObservableList<JFXTextField> integerFields = FXCollections.observableArrayList();
    ObservableList<Export_Bill> exported_review_operations_list = FXCollections.observableArrayList();
    ObservableList<Export_Bill> filtered_review_export_operations_list = FXCollections.observableArrayList();
    DecimalFormat df=new DecimalFormat("#.##");
    ObservableList<Import_Bill> imported_review_operations_list = FXCollections.observableArrayList();
    ObservableList<Import_Bill> filtered_review_import_operations_list = FXCollections.observableArrayList();

    Set<String> export_check_quantity_list = new HashSet<String>();
    Set<String> export_check_expired_list = new HashSet<String>();

    Set<String> import_check_quantity_list = new HashSet<String>();
    Set<String> import_check_expired_list = new HashSet<String>();

    Expire_Details exiting_Expire_Item = null  , current_Expire_Item = null  , checked_Expire_Item = null;


    String logo_img_param_code = "Reports/report_images/pharma_logo_r.png";

    JRBeanCollectionDataSource collection_Data;

    Import_Bill selected_import_bill  ;
    Export_Bill selected_export_bill  ;

    //TODO| Design Elements   ( Control Pane) *************************************************************************************************

    @FXML
    private AnchorPane root , labelCont , exportCont , importCont, controlCont ;

    @FXML
    private Label title_label;


    @FXML
    private JFXButton import_review_Btn , export_review_Btn ;


    //TODO| 2- Design Elements Second Screen  ( Export Review )************************************************************************************** -

    @FXML
    private JFXDatePicker export_review_search_fromDate_Picker , export_review_search_toDate_Picker;

    @FXML
    private JFXComboBox<String> export_review_search_client_Comb, export_review_search_item_Comb
            , export_review_search_envoy_Comb , export_review_search_pay_kind_Comb , export_review_search_bill_kind_Comb;


    @FXML
    private JFXCheckBox export_review_select_all_Checkbox;

    @FXML
    private JFXTextField export_review_search_code_TxtF , export_review_total_price_TxtF , export_review_discount_TxtF ,
            export_review_received_TxtF , export_review_number_TxtF , export_review_final_total_TxtF;

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
    private TableColumn<Export_Bill, JFXCheckBox>  export_review_checkbox_Colo;

    @FXML
    private JFXButton export_review_back_Btn , export_review_print_Btn , export_review_print_report_Btn ,
            export_review_clean_Btn , export_review_delete_Btn;

    //TODO| 3- Design Elements Third Screen  ( Import Review )************************************************************************************** -

    @FXML
    private JFXDatePicker import_review_search_fromDate_Picker , import_review_search_toDate_Picker;

    @FXML
    private JFXComboBox<String> import_review_search_company_Comb, import_review_search_item_Comb , import_review_search_bill_kind_Comb
             , import_review_search_pay_kind_Comb;


    @FXML
    private JFXCheckBox import_review_select_all_Checkbox;

    @FXML
    private JFXTextField import_review_search_code_TxtF , import_review_total_price_TxtF , import_review_discount_TxtF ,
            import_review_paid_TxtF , import_review_number_TxtF , import_review_final_total_TxtF;

    @FXML
    private TableView<Import_Bill> import_review_Table;

    @FXML
    private TableColumn<Import_Bill, String> import_review_index_Colo, import_review_client_Colo, import_review_pay_kind_Colo
            , import_review_item_Colo , import_review_item_kind_Colo ;

    @FXML
    private TableColumn<Import_Bill, Integer> import_review_code_Colo , import_review_quantity_Colo;

    @FXML
    private TableColumn<Import_Bill, Double> import_review_price_Colo, import_review_total_price_Colo;

    @FXML
    private TableColumn<Import_Bill, Date>  import_review_date_Colo , import_review_expire_date_Colo;

    @FXML
    private TableColumn<Import_Bill, JFXCheckBox>  import_review_checkbox_Colo;

    @FXML
    private JFXButton import_review_back_Btn , import_review_print_Btn , import_review_print_report_Btn ,
            import_review_clean_Btn , import_review_delete_Btn;


    //TODO| 1 - This Class Functions ( Control Pane) **************************************************************************************************

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

    private  void setup_main_page_buttons(){


        // export review button
        export_review_Btn.setOnAction(event -> {
            try {
                controlCont.setVisible(false);
                exportCont.setVisible(true);
                importCont.setVisible(false);
                title_label.setText(" صفحة مراجعة جميع فواتير المبيعات و مرتجعات المبيعات السابقة تفصيليا ");

                reset_export_review_data();


            }catch (Exception e ){
                System.out.println("Error in Export Review Action ||| " + e.getMessage());
            }

        });

        // import review button
        import_review_Btn.setOnAction(event -> {
            try {
                controlCont.setVisible(false);
                exportCont.setVisible(false);
                importCont.setVisible(true);
                title_label.setText(" صفحة مراجعة جميع فواتير المشتريات و مرتجعات المشتريات السابقة تفصيليا ");
                reset_import_review_data();


            }catch (Exception e ){
                System.out.println("Error in Export Review Action ||| " + e.getMessage());
            }

        });



    }


    //TODO| 2-  This Class Functions   Second Screen  ( Export Review ) **************************************************************************

    private void initialized_export_review_boxes_data() {

        // For client envoys Names
        ObservableList<String> envoy_names_list = FXCollections.observableArrayList();
        envoy_names_list.addAll(envoy_dataManager.load_envoys_names_list());
        export_review_search_envoy_Comb.setItems(envoy_names_list);
        export_review_search_envoy_Comb.setValue("");

        TextFields.bindAutoCompletion(export_review_search_envoy_Comb.getEditor(), export_review_search_envoy_Comb.getItems());



        // For clients Names
        ObservableList<String> names_list = FXCollections.observableArrayList();
        names_list.addAll(export_client_dataManager.load_clients_names_list());
        export_review_search_client_Comb.setItems(names_list);
        export_review_search_client_Comb.setValue("");
        TextFields.bindAutoCompletion(export_review_search_client_Comb.getEditor(), export_review_search_client_Comb.getItems());


        //  For Items Names
        ObservableList<String> items_names_list = FXCollections.observableArrayList();
        items_names_list.addAll(items_dataManager.load_items_names_list());
        export_review_search_item_Comb.setItems(items_names_list);
        export_review_search_item_Comb.setValue("");
        TextFields.bindAutoCompletion(export_review_search_item_Comb.getEditor(), export_review_search_item_Comb.getItems());



        // For Kind List Data
        ObservableList<String> paid_kinds_list = FXCollections.observableArrayList();
        paid_kinds_list.addAll("نقدي", "أجل", "شيك", "حوالة", "إيصال");
        export_review_search_pay_kind_Comb.setItems(paid_kinds_list);
        export_review_search_pay_kind_Comb.setValue("");
        TextFields.bindAutoCompletion(export_review_search_pay_kind_Comb.getEditor(), export_review_search_pay_kind_Comb.getItems());

        // For Bill Kind List Data
        ObservableList<String> export_bill_kinds_list = FXCollections.observableArrayList();
        export_bill_kinds_list.addAll("فاتورة بيع",  "فاتورة مرتجع"  , "الكل");
        export_review_search_bill_kind_Comb.setItems(export_bill_kinds_list);
        export_review_search_bill_kind_Comb.setValue("");
        TextFields.bindAutoCompletion(export_review_search_bill_kind_Comb.getEditor(), export_review_search_bill_kind_Comb.getItems());


        // For Integer Numeric TextFields
        integerFields.addAll(
                export_review_search_code_TxtF
        );
        integerFields.forEach(field -> {
            field.setText("");
            field.textProperty().addListener(($, old_value, new_value) -> {
                System.out.println("New value | " + new_value);
                if (!new_value.matches("\\d*")) {
                    field.setText(new_value.replaceAll("[^\\d]" , ""));
                }
            });
        });

        // Table Style
        export_review_Table.getStylesheets().add("/Assets/Custom_CSS/custom_table_font.css");

        // Boxes Style
        export_review_search_client_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 17  ;  -fx-font-weight:bold");
        export_review_search_item_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 17  ;  -fx-font-weight:bold");
        export_review_search_envoy_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 17  ;  -fx-font-weight:bold");
        export_review_search_pay_kind_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 17  ;  -fx-font-weight:bold");
        export_review_search_bill_kind_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 17  ;  -fx-font-weight:bold");

        // Pickers Style
        export_review_search_fromDate_Picker.setStyle("-fx-background-color: #ffffff ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 18  ;  -fx-font-weight:bold");
        export_review_search_toDate_Picker.setStyle("-fx-background-color: #ffffff ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 18  ;  -fx-font-weight:bold");

    }

    private void reset_export_review_data() {

        // main components

        export_review_search_client_Comb.setValue("");
        export_review_search_item_Comb.setValue("");
        export_review_search_envoy_Comb.setValue("");
        export_review_search_pay_kind_Comb.setValue("");
        export_review_search_bill_kind_Comb.setValue("");
        export_review_search_fromDate_Picker.setValue(null);
        export_review_search_toDate_Picker.setValue(null);


        export_review_search_code_TxtF.setText("");
        export_review_delete_Btn.setVisible(false);

        // help component
        export_review_select_all_Checkbox.setSelected(true);

        // load table
        loadExportReviewData();


        export_review_Table.refresh();


    }

    private void loadExportReviewData() {
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
                exported_review_operations_list = FXCollections.observableArrayList();
                    exported_review_operations_list = export_client_dataManager.load_exported_bills_data_list();


                Platform.runLater(() -> {
                    export_review_Table.setItems(exported_review_operations_list);
                    root.getChildren().remove(dark_pane);

                });
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();

    }

    private void setupExportedReviewTable() {

        export_review_index_Colo.setCellValueFactory(
                (TableColumn.CellDataFeatures<Export_Bill, String> $)
                        -> new ReadOnlyObjectWrapper(export_review_Table.getItems().indexOf($.getValue()) + 1 + ""));
        export_review_checkbox_Colo.setCellValueFactory(new PropertyValueFactory<>("isSelected_checkBox"));
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


        //TODO - setup action when clicking cell ***********************************************
        export_review_Table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        export_review_Table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                selected_export_bill = export_review_Table.getSelectionModel().getSelectedItem();

            }

        });


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





    }

    private  void setup_export_review_page_buttons(){


        // reset button
        export_review_clean_Btn.setOnAction(event -> {
            reset_export_review_data();

        });

        // select all and unselect all check action
        export_review_select_all_Checkbox.selectedProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            export_review_Table.getItems().forEach(review -> {
                review.getIsSelected_checkBox().setSelected(new_value);

            });
        });

        // print rev button
        export_review_print_Btn.setOnAction(event -> {
            try{
                String path = "";
                if (export_review_search_code_TxtF.getText().length() > 0){
                    path = "Reports/public_reports/export_bill.jrxml";
                    collection_Data =
                            new JRBeanCollectionDataSource(export_review_Table.getItems());

                }
                else {
                    path = "Reports/public_reports/export_bill_rev.jrxml";
                    collection_Data =
                            new JRBeanCollectionDataSource(export_review_Table.getItems().filtered(rev ->rev.getIsSelected_checkBox().isSelected()));

                }



                // print function
                Map<String, Object> parameters = new HashMap<>();

                parameters.put("img_param", logo_img_param_code);


                    if (export_review_search_code_TxtF.getText().length() > 0){
                        if (export_review_Table.getItems().get(0).getBill_kind().equals("export")){
                            parameters.put("bill_header",  "فاتورة بيع للعميل / " + export_review_Table.getItems().get(0).getClient_name());

                        }else {
                            parameters.put("bill_header",  "فاتورة مرتجع من العميل / " + export_review_Table.getItems().get(0).getClient_name());

                        }

                    }
                    else {
                        parameters.put("bill_header", "كشف بالفواتير السابقة");

                    }




                JasperDesign jd = JRXmlLoader.load(path);

                JasperReport js = JasperCompileManager.compileReport(jd);

                JasperPrint jp = JasperFillManager.fillReport(js, parameters, collection_Data);


                JasperViewer.viewReport(jp, false);


            }catch (Exception e){
                System.out.println("Error In Print Rev  :: " + e.getMessage());
            }

        });

        // print rev report button
        export_review_print_report_Btn.setOnAction(event -> {
            try{
                String path = "";

                    path = "Reports/public_reports/export_bill_rev_report.jrxml";


                collection_Data =
                        new JRBeanCollectionDataSource(export_review_Table.getItems().filtered(rev ->rev.getIsSelected_checkBox().isSelected()));

                // print function
                Map<String, Object> parameters = new HashMap<>();

                parameters.put("img_param", logo_img_param_code);

                parameters.put("bill_header", "كشف بالفواتير السابقة");

                JasperDesign jd = JRXmlLoader.load(path);

                JasperReport js = JasperCompileManager.compileReport(jd);

                JasperPrint jp = JasperFillManager.fillReport(js, parameters, collection_Data);


                JasperViewer.viewReport(jp, false);


            }catch (Exception e){
                System.out.println("Error In Print Rev Report :: " + e.getMessage());
            }

        });

        // back button
        export_review_back_Btn.setOnAction(event -> {
            try {
                controlCont.setVisible(true);
                exportCont.setVisible(false);
                importCont.setVisible(false);
                title_label.setText("صفحة مراجعة الفواتير السابقة للمبيعات / المشتريات  / مرتجعات الشركات / مرتجعات العملاء");

            }catch (Exception e ){
                System.out.println("Error in Back Action ||| " + e.getMessage());
            }

        });

        // delete button
        export_review_delete_Btn.setOnAction(event -> {
            try {
                if (selected_export_bill != null) {
                    Alert before_delete_bill_alert = new Alert(Alert.AlertType.WARNING,  "تحذير سيتم حذف بيانات هذه الفاتورة رقم " + selected_export_bill.getCode() , ButtonType.CANCEL, ButtonType.OK);
                    Optional<ButtonType> buttonTypeOptional = before_delete_bill_alert.showAndWait();
                    if (buttonTypeOptional.get() == ButtonType.CANCEL) {
                        selected_export_bill = null;
                        reset_export_review_data();
                    } else if (buttonTypeOptional.get() == ButtonType.OK) {

                        // check all item quantity if its returned export
                        export_check_quantity_list = new HashSet<String>() ;
                        export_check_expired_list = new HashSet<String>();

                        if (selected_export_bill.getBill_kind().equals("return")){


                            System.out.println();
                            exported_review_operations_list
                                    .filtered(item -> item.getCode() == selected_export_bill.getCode()).forEach(bill_updated_item ->{



                                //  check items loop
                                  double item_total_bill = exported_review_operations_list
                                   .filtered(item -> item.getCode() == selected_export_bill.getCode()  && item.getItem_code() == bill_updated_item.getItem_code()).stream().mapToDouble(f-> f.getItem_count()).sum();

                                  Item  current_checked_Item = null;
                                  Expire_Details  current_checked_Expired_Item = null;

                                current_checked_Item = items_dataManager.load_item_by_code(bill_updated_item.getItem_code());

                                if (current_checked_Item.getCurrent_quantity() < item_total_bill){

                                    export_check_quantity_list.add(bill_updated_item.getItem_name());


                                }else {
                                    // check for expire dates
                                    // 1 - find if same item with same expire date and update quantity
                                    double item_expire_total_bill = exported_review_operations_list
                                            .filtered(item -> item.getCode() == selected_export_bill.getCode()  &&
                                                    item.getItem_code() == bill_updated_item.getItem_code() &&
                                                    item.getExpire_date() == bill_updated_item.getExpire_date()).stream().mapToDouble(f-> f.getItem_count()).sum();

                                    current_checked_Expired_Item = itemExpire_dataManger.load_expire_item_by_code_and_date(bill_updated_item.getItem_code()
                                            , bill_updated_item.getExpire_date());

                                    if (current_checked_Expired_Item.getQuantity() < item_expire_total_bill){
                                        export_check_expired_list.add(bill_updated_item.getItem_name());
                                    }

                                }



                            });

                        }



                        //// operation

                        if (export_check_quantity_list.size() == 0 && export_check_expired_list.size() == 0){
                            // UPDATE Client DATA
                            Client selected_Client = export_client_dataManager.load_client_by_name(selected_export_bill.getClient_name());

                            if (selected_export_bill.getBill_kind().equals("export")){
                                selected_Client.setCurrent_balance(selected_Client.getCurrent_balance() - selected_export_bill.getBill_final_total());

                                selected_Client.setBalance_received(selected_Client.getBalance_received() - selected_export_bill.getBill_total_received());

                                selected_Client.setBalance_remaining(selected_Client.getCurrent_balance() - selected_Client.getBalance_received());

                            }
                            else if (selected_export_bill.getBill_kind().equals("return")){
                                selected_Client.setCurrent_balance(selected_Client.getCurrent_balance() + selected_export_bill.getBill_final_total());

                                selected_Client.setBalance_received(selected_Client.getBalance_received() + selected_export_bill.getBill_total_received());

                                selected_Client.setBalance_remaining(selected_Client.getCurrent_balance() - selected_Client.getBalance_received());

                            }

                            export_client_dataManager.update_client(selected_Client);


                            exported_review_operations_list.filtered(item -> item.getCode() == selected_export_bill.getCode()).forEach(bill_updated_item ->{

                                // update expire items
                                // add and update in expire details

                                // 1 - find if same item with same expire date and update quantity
                                boolean exist_status = itemExpire_dataManger.detect_expire_item_is_exist(bill_updated_item.getItem_code() , bill_updated_item.getExpire_date());

                                // 1.1 - if update exiting record
                                if (exist_status){
                                    exiting_Expire_Item =  itemExpire_dataManger.load_expire_item_by_code_and_date(bill_updated_item.getItem_code() , bill_updated_item.getExpire_date());
                                    if (bill_updated_item.getBill_kind().equals("export")){
                                        // ++
                                        exiting_Expire_Item.setQuantity(exiting_Expire_Item.getQuantity() + bill_updated_item.getItem_count());

                                        itemExpire_dataManger.update_item_Expire(exiting_Expire_Item);


                                    }else if(bill_updated_item.getBill_kind().equals("return")) {
                                        // --
                                        exiting_Expire_Item.setQuantity(exiting_Expire_Item.getQuantity() - bill_updated_item.getItem_count());
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
                                    current_Expire_Item.setItem_code(bill_updated_item.getItem_code());
                                    current_Expire_Item.setItem_name(bill_updated_item.getItem_name());
                                    current_Expire_Item.setItem_kind(bill_updated_item.getItem_kind());
                                    current_Expire_Item.setQuantity(bill_updated_item.getItem_count());
                                    current_Expire_Item.setExpire_date(bill_updated_item.getExpire_date());

                                    itemExpire_dataManger.add_item_Expire(current_Expire_Item);

                                }



                                //  UPDATE ITEMS IN STORE

                                Item selected_Item = null , current_Updated_Item = null;

                                current_Updated_Item = items_dataManager.load_item_by_code(bill_updated_item.getItem_code());
                                if (selected_export_bill.getBill_kind().equals("export")){
                                    current_Updated_Item.setCurrent_quantity(current_Updated_Item.getCurrent_quantity() + bill_updated_item.getItem_count());

                                }
                                else if (selected_export_bill.getBill_kind().equals("return")){
                                    current_Updated_Item.setCurrent_quantity(current_Updated_Item.getCurrent_quantity() - bill_updated_item.getItem_count());

                                }
                                items_dataManager.update_item(current_Updated_Item);


                            });

                            // FOR ITEMS MOVES

                            if (selected_export_bill.getBill_kind().equals("export")){
                                itemMove_dataManger.delete_Item_Moves(selected_export_bill.getCode() , "صادر");
                            }
                            else if (selected_export_bill.getBill_kind().equals("return")){
                                itemMove_dataManger.delete_Item_Moves(selected_export_bill.getCode() , "مرتجع عميل");

                            }

                            // update envoy data
                            Envoy selected_envoy =  envoy_dataManager.load_envoy_by_code(selected_export_bill.getEnvoy_code()) ;

                            if (selected_export_bill.getBill_kind().equals("export")){
                                selected_envoy.setCurrent_balance(selected_envoy.getCurrent_balance() - selected_export_bill.getBill_final_total());

                            }
                            else if (selected_export_bill.getBill_kind().equals("return")){
                                selected_envoy.setCurrent_balance(selected_envoy.getCurrent_balance() + selected_export_bill.getBill_final_total());

                            }

                            envoy_dataManager.update_envoy(selected_envoy);


                            Export_Bill deleted_Bill= new Export_Bill();
                            deleted_Bill.setCode(selected_export_bill.getCode());

                            if (export_client_dataManager.delete_bill(deleted_Bill).equals(QueryState.success)) {
                                UIManager.showAlert("تم حذف بيانات الفاتورة  بنجاح", AlertType.success, root, 0, 0);
                                Platform.runLater(() -> {
                                    loadExportReviewData();
                                    export_review_Table.refresh();
                                });
                                selected_export_bill = null;
                                reset_export_review_data();

                            } else {
                                UIManager.showAlert("تعذر حذف بيانات الفاتورة حدد و حاول مرة أخري ", AlertType.error, root, 0, 0);
                            }
                        }else {
                            UIManager.showAlert("لا يمكن حذف بيانات الفاتورة بسبب عدم كفاية الكميات الحاليه بنفس تواريخ الصلاحية في الاصناف المحددة", AlertType.error, root, 0, 0);
                            if (export_check_quantity_list.size() > 0){
                                UIManager.showAlert( " الاصناف ناقصه الكمية هي : " + export_check_quantity_list, AlertType.alarm, root, 0, 300);

                            }
                            if (export_check_expired_list.size() > 0){
                                UIManager.showAlert( " الاصناف ناقصه تواريخ الصلاحيه هي : " + export_check_expired_list, AlertType.alarm, root, 0, 500);

                            }

                        }


                        ////

                    }

                } else {
                    UIManager.showAlert("برجاء تحديد عنصر من الجدول أولا", AlertType.error, root, 0, 0);

                }

            } catch (Exception e) {
                System.out.println("Error in Delete Action ||| " + e.getMessage());
            }

        });


    }

    private void filter_export_review() {

        Task task = new Task<Void>() {
            @Override
            public Void call() {

                if (exported_review_operations_list.size() > 0) {
                    filtered_review_export_operations_list = exported_review_operations_list;
                    System.out.println("exported_review_operations_list | Size | " + filtered_review_export_operations_list.size());

                    //  1- Filter by review client name

                    if (export_review_search_client_Comb.getValue().length() != 0) {
                        filtered_review_export_operations_list = filtered_review_export_operations_list.filtered
                                (review_operation -> replacedWordToArabic_Action(review_operation.getClient_name()).
                                        contains(replacedWordToArabic_Action(export_review_search_client_Comb.getValue())));
                        export_review_delete_Btn.setVisible(false);

                        System.out.println("Filter | export_review_search_client_Comb");
                    }

                    //  2- Filter by item Name

                    if (export_review_search_item_Comb.getValue().length() != 0) {
                        filtered_review_export_operations_list = filtered_review_export_operations_list.filtered
                                (review_operation -> replacedWordToArabic_Action(review_operation.getItem_name()).
                                        contains(replacedWordToArabic_Action(export_review_search_item_Comb.getValue())));
                        export_review_delete_Btn.setVisible(false);

                        System.out.println("Filter | export_review_search_item_Comb");
                    }

                    //  3- Filter by envoy name
                    if (export_review_search_envoy_Comb.getValue().length() != 0) {
                        filtered_review_export_operations_list = filtered_review_export_operations_list.filtered
                                (review_operation ->  replacedWordToArabic_Action(review_operation.getEnvoy_name()).
                                        contains(replacedWordToArabic_Action(export_review_search_envoy_Comb.getValue())));
                        export_review_delete_Btn.setVisible(false);

                        System.out.println("Filter | export_review_search_envoy_Comb");
                    }

                    //  4- Filter by pay kind
                    if (export_review_search_pay_kind_Comb.getValue().length() != 0) {
                        filtered_review_export_operations_list = filtered_review_export_operations_list.filtered
                                (review_operation -> replacedWordToArabic_Action(review_operation.getPay_kind()).
                                        contains(replacedWordToArabic_Action(export_review_search_pay_kind_Comb.getValue())));
                        export_review_delete_Btn.setVisible(false);

                        System.out.println("Filter | export_review_search_pay_kind_Comb");
                    }

                    //  5- Filter by code
                    if (export_review_search_code_TxtF.getText().length() != 0) {
                        filtered_review_export_operations_list = filtered_review_export_operations_list.filtered
                                (review_operation -> review_operation.getCode() == (Integer.parseInt(export_review_search_code_TxtF.getText())));

                        if (filtered_review_export_operations_list.size() > 0){

                            export_review_delete_Btn.setVisible(true);
                        }
                        else {
                            export_review_delete_Btn.setVisible(false);

                        }

                        System.out.println("Filter | export_review_search_code_TxtF");
                    }
                    else {
                        export_review_delete_Btn.setVisible(false);

                    }

                    //  6- Filter by From Date and To Date Together
                    if (export_review_search_fromDate_Picker.getValue() != null && export_review_search_toDate_Picker.getValue() != null) {
                        filtered_review_export_operations_list = filtered_review_export_operations_list.filtered
                                (review_operation ->
                                        !UIManager.convertToDate(export_review_search_fromDate_Picker.getValue()).after(review_operation.getDate()) &&
                                                !UIManager.convertToDate(export_review_search_toDate_Picker.getValue()).before(review_operation.getDate()) );
                        export_review_delete_Btn.setVisible(false);

                        System.out.println("Filter | From Date and To Date Together");
                    }

                    //  7- Filter by From Date
                    else  if (export_review_search_fromDate_Picker.getValue() != null && export_review_search_toDate_Picker.getValue() == null) {
                        filtered_review_export_operations_list = filtered_review_export_operations_list.filtered
                                (review_operation ->
                                        !UIManager.convertToDate(export_review_search_fromDate_Picker.getValue()).after(review_operation.getDate()) );
                        import_review_delete_Btn.setVisible(false);
                        System.out.println("Filter | review_search_fromDate_Picker");
                    }
                    //  8- Filter by To Date
                    else if (export_review_search_fromDate_Picker.getValue() == null && export_review_search_toDate_Picker.getValue() != null) {
                        filtered_review_export_operations_list = filtered_review_export_operations_list.filtered
                                (review_operation ->
                                        !UIManager.convertToDate(export_review_search_toDate_Picker.getValue()).before(review_operation.getDate()) );
                        export_review_delete_Btn.setVisible(false);
                        System.out.println("Filter | review_search_toDate_Picker");
                    }

                    //  9- Filter by Bill Kind

                    if (export_review_search_bill_kind_Comb.getValue().length() != 0) {
                        filtered_review_export_operations_list = filtered_review_export_operations_list.filtered
                                (review_operation -> review_operation.getBill_kind().
                                        contains(export_review_search_bill_kind_Comb.getValue().equals("فاتورة بيع") ? "export" :
                                                export_review_search_bill_kind_Comb.getValue().equals("فاتورة مرتجع") ? "return" :""));
                        export_review_delete_Btn.setVisible(false);

                        System.out.println("Filter | export_review_search_bill_kind_Comb");
                    }

                    System.out.println("filtered_review_export_operations_list | Size : " + filtered_review_export_operations_list.size());
                    export_review_Table.setItems(filtered_review_export_operations_list);
                    export_review_Table.refresh();
                } else {


                }

                return null;
            }
        };

        task.setOnSucceeded($ -> {
            if (export_review_search_code_TxtF.getText().length() !=0 && filtered_review_export_operations_list.size() > 0 ){

                    export_review_total_price_TxtF.setText(df.format(filtered_review_export_operations_list.get(0).getBill_total_price()));
                    export_review_discount_TxtF.setText(df.format(filtered_review_export_operations_list.get(0).getBill_total_discount()));
                    export_review_received_TxtF.setText(df.format(filtered_review_export_operations_list.get(0).getBill_total_received()));
                    export_review_final_total_TxtF.setText(df.format(filtered_review_export_operations_list.get(0).getBill_final_total()));
                    export_review_number_TxtF.setText(filtered_review_export_operations_list.get(0).getBill_receipt_number());



            }
            else {
                export_review_total_price_TxtF.setText("");
                export_review_discount_TxtF.setText("");
                export_review_received_TxtF.setText("");
                export_review_final_total_TxtF.setText("");

                export_review_number_TxtF.setText("");

            }

        });

        task.setOnFailed($ -> {
            System.out.println("Filter | Failed | " + $.getEventType().getName());
        });

        Thread thread = new Thread(task);
        thread.start();

    }

    private void setup_export_filterOptions() {

        //TODO | Boxes_of_String

        // For client Search
        export_review_search_client_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            export_review_search_code_TxtF.setText("");

            filter_export_review();
        });

        // For item Search
        export_review_search_item_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            export_review_search_code_TxtF.setText("");

            filter_export_review();
        });

        // For envoy Search
        export_review_search_envoy_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            export_review_search_code_TxtF.setText("");

            filter_export_review();
        });


        // For pay kind Search
        export_review_search_pay_kind_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            export_review_search_code_TxtF.setText("");

            filter_export_review();
        });

        // For bill kind Search
        export_review_search_bill_kind_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            export_review_search_code_TxtF.setText("");

            filter_export_review();
        });

        //TODO | TextFields

        // For Code Search
        export_review_search_code_TxtF.textProperty().addListener(($, old_value, new_value) -> {

            export_review_search_client_Comb.setValue("");
            export_review_search_pay_kind_Comb.setValue("");
            export_review_search_envoy_Comb.setValue("");
            export_review_search_item_Comb.setValue("");
            export_review_search_bill_kind_Comb.setValue("");

            export_review_search_fromDate_Picker.setValue(null);
            export_review_search_toDate_Picker.setValue(null);

            System.out.println("New value | " + new_value);

            filter_export_review();



        });

        //TODO | DatePickers

        ObservableList<JFXDatePicker> pickers = FXCollections.observableArrayList();
        pickers.addAll(export_review_search_fromDate_Picker, export_review_search_toDate_Picker);
        pickers.forEach(picker -> {
            picker.valueProperty().addListener(($, old_value, new_value) -> {
                System.out.println("New value | " + new_value);
                export_review_search_code_TxtF.setText("");
                filter_export_review();
            });

            picker.getEditor().textProperty().addListener(($, oldValue, newValue) -> {
                if (newValue.length() == 0) {
                    picker.setValue(null);
                }
            });
        });

    }

    //TODO| 3-  This Class Functions   Third Screen  ( Import Review ) **************************************************************************

    private void initialized_import_review_boxes_data() {


        // For Companies Names
        ObservableList<String> names_list = FXCollections.observableArrayList();
        names_list.addAll(import_company_dataManager.load_companies_names_list());
        import_review_search_company_Comb.setItems(names_list);
        import_review_search_company_Comb.setValue("");
        TextFields.bindAutoCompletion(import_review_search_company_Comb.getEditor(), import_review_search_company_Comb.getItems());


        //  For Items Names
        ObservableList<String> items_names_list = FXCollections.observableArrayList();
        items_names_list.addAll(items_dataManager.load_items_names_list());
        import_review_search_item_Comb.setItems(items_names_list);
        import_review_search_item_Comb.setValue("");
        TextFields.bindAutoCompletion(import_review_search_item_Comb.getEditor(), import_review_search_item_Comb.getItems());



        // For Kind List Data
        ObservableList<String> paid_kinds_list = FXCollections.observableArrayList();
        paid_kinds_list.addAll("نقدي", "أجل", "شيك", "حوالة", "إيصال");
        import_review_search_pay_kind_Comb.setItems(paid_kinds_list);
        import_review_search_pay_kind_Comb.setValue("");
        TextFields.bindAutoCompletion(import_review_search_pay_kind_Comb.getEditor(), import_review_search_pay_kind_Comb.getItems());


        // For Bill Kind List Data
        ObservableList<String> import_bill_kinds_list = FXCollections.observableArrayList();
        import_bill_kinds_list.addAll("فاتورة شراء", "فاتورة مرتجع" , "الكل");
        import_review_search_bill_kind_Comb.setItems(import_bill_kinds_list);
        import_review_search_bill_kind_Comb.setValue("");
        TextFields.bindAutoCompletion(import_review_search_bill_kind_Comb.getEditor(), import_review_search_bill_kind_Comb.getItems());



        // For Integer Numeric TextFields
        integerFields.addAll(
                import_review_search_code_TxtF
        );
        integerFields.forEach(field -> {
            field.setText("");
            field.textProperty().addListener(($, old_value, new_value) -> {
                System.out.println("New value | " + new_value);
                if (!new_value.matches("\\d*")) {
                    field.setText(new_value.replaceAll("[^\\d]" , ""));
                }
            });
        });

        // Table Style
        import_review_Table.getStylesheets().add("/Assets/Custom_CSS/custom_table_font.css");

        // Boxes Style
        import_review_search_company_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 17  ;  -fx-font-weight:bold");
        import_review_search_item_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 17  ;  -fx-font-weight:bold");
        import_review_search_pay_kind_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 17  ;  -fx-font-weight:bold");

        // Pickers Style
        import_review_search_fromDate_Picker.setStyle("-fx-background-color: #ffffff ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 18  ;  -fx-font-weight:bold");
        import_review_search_toDate_Picker.setStyle("-fx-background-color: #ffffff ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 18  ;  -fx-font-weight:bold");

    }

    private void reset_import_review_data() {

        // main components

        import_review_search_company_Comb.setValue("");
        import_review_search_item_Comb.setValue("");
        import_review_search_pay_kind_Comb.setValue("");
        import_review_search_bill_kind_Comb.setValue("");

        import_review_search_fromDate_Picker.setValue(null);
        import_review_search_toDate_Picker.setValue(null);


        import_review_search_code_TxtF.setText("");
        import_review_delete_Btn.setVisible(false);

        // help component
        import_review_select_all_Checkbox.setSelected(true);

        // load table
        loadImportReviewData();

        selected_import_bill = null ;

        import_review_Table.refresh();


    }

    private void loadImportReviewData() {
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
                imported_review_operations_list = FXCollections.observableArrayList();
                imported_review_operations_list = import_company_dataManager.load_imported_bills_data_list();



                Platform.runLater(() -> {
                    import_review_Table.setItems(imported_review_operations_list);
                    root.getChildren().remove(dark_pane);

                });
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();

    }

    private void setupImportedReviewTable() {

        import_review_index_Colo.setCellValueFactory(
                (TableColumn.CellDataFeatures<Import_Bill, String> $)
                        -> new ReadOnlyObjectWrapper(import_review_Table.getItems().indexOf($.getValue()) + 1 + ""));
        import_review_checkbox_Colo.setCellValueFactory(new PropertyValueFactory<>("isSelected_checkBox"));
        import_review_code_Colo.setCellValueFactory(new PropertyValueFactory<>("code"));
        import_review_client_Colo.setCellValueFactory(new PropertyValueFactory<>("company_name"));
        import_review_pay_kind_Colo.setCellValueFactory(new PropertyValueFactory<>("pay_kind"));
        import_review_item_Colo.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        import_review_quantity_Colo.setCellValueFactory(new PropertyValueFactory<>("item_count"));
        import_review_item_kind_Colo.setCellValueFactory(new PropertyValueFactory<>("item_kind"));
        import_review_price_Colo.setCellValueFactory(new PropertyValueFactory<>("item_price"));
        import_review_total_price_Colo.setCellValueFactory(new PropertyValueFactory<>("item_total_price"));
        import_review_date_Colo.setCellValueFactory(new PropertyValueFactory<>("date"));
        import_review_expire_date_Colo.setCellValueFactory(new PropertyValueFactory<>("expire_date"));


        //TODO - setup action when clicking cell ***********************************************
        import_review_Table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        import_review_Table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                selected_import_bill = import_review_Table.getSelectionModel().getSelectedItem();

            }

        });


        import_review_Table.setRowFactory($ -> {


            TableRow<Import_Bill> row = new TableRow<Import_Bill>() {
                @Override
                protected void updateItem(Import_Bill review, boolean empty) {
                    try {

                        super.updateItem(review, empty);
                        if (review != null) {
                            if (review.getBill_kind().equals("import")  ) {
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




    }

    private  void setup_import_review_page_buttons(){


        // reset button
        import_review_clean_Btn.setOnAction(event -> {
            reset_import_review_data();

        });

        // select all and unselect all check action
        import_review_select_all_Checkbox.selectedProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            import_review_Table.getItems().forEach(review -> {
                review.getIsSelected_checkBox().setSelected(new_value);

            });
        });

        // print rev button

        import_review_print_Btn.setOnAction(event -> {
            try{
                String path = "";


                if (import_review_search_code_TxtF.getText().length() > 0){
                    path = "Reports/public_reports/import_bill.jrxml";
                    collection_Data =
                            new JRBeanCollectionDataSource(import_review_Table.getItems());

                }
                else {
                    path = "Reports/public_reports/import_bill_rev.jrxml";
                    collection_Data =
                            new JRBeanCollectionDataSource(import_review_Table.getItems().filtered(rev ->rev.getIsSelected_checkBox().isSelected()));


                }


                // print function
                Map<String, Object> parameters = new HashMap<>();

                parameters.put("img_param", logo_img_param_code);


                if (import_review_search_code_TxtF.getText().length() > 0){
                    if (import_review_Table.getItems().get(0).getBill_kind().equals("import")){
                        parameters.put("bill_header",  "فاتورة شراء من الشركة / " + import_review_Table.getItems().get(0).getCompany_name());

                    }else {
                        parameters.put("bill_header",  "فاتورة مرتجع للشركة / " + import_review_Table.getItems().get(0).getCompany_name());

                    }

                }
                else {
                    parameters.put("bill_header", "كشف بالفواتير السابقة");

                }



                JasperDesign jd = JRXmlLoader.load(path);

                JasperReport js = JasperCompileManager.compileReport(jd);

                JasperPrint jp = JasperFillManager.fillReport(js, parameters, collection_Data);


                JasperViewer.viewReport(jp, false);


            }catch (Exception e){
                System.out.println("Error In Print Rev Import :: " + e.getMessage());
            }

        });


        // print rev report button
        import_review_print_report_Btn.setOnAction(event -> {
            try{
                String path = "";

                path = "Reports/public_reports/import_bill_rev_report.jrxml";


                collection_Data =
                        new JRBeanCollectionDataSource(import_review_Table.getItems().filtered(rev ->rev.getIsSelected_checkBox().isSelected()));

                // print function
                Map<String, Object> parameters = new HashMap<>();

                parameters.put("img_param", logo_img_param_code);

                parameters.put("bill_header", "كشف بالفواتير السابقة");

                JasperDesign jd = JRXmlLoader.load(path);

                JasperReport js = JasperCompileManager.compileReport(jd);

                JasperPrint jp = JasperFillManager.fillReport(js, parameters, collection_Data);


                JasperViewer.viewReport(jp, false);


            }catch (Exception e){
                System.out.println("Error In Print Rev Report :: " + e.getMessage());
            }

        });



        // back button
        import_review_back_Btn.setOnAction(event -> {
            try {
                controlCont.setVisible(true);
                exportCont.setVisible(false);
                importCont.setVisible(false);
                title_label.setText("صفحة مراجعة الفواتير السابقة للمبيعات / المشتريات ");

            }catch (Exception e ){
                System.out.println("Error in Back Action ||| " + e.getMessage());
            }

        });

        // delete button
        import_review_delete_Btn.setOnAction(event -> {
            try {
                if (selected_import_bill != null) {
                    Alert before_delete_bill_alert = new Alert(Alert.AlertType.WARNING,  "تحذير سيتم حذف بيانات هذه الفاتورة رقم " + selected_import_bill.getCode() , ButtonType.CANCEL, ButtonType.OK);
                    Optional<ButtonType> buttonTypeOptional = before_delete_bill_alert.showAndWait();
                    if (buttonTypeOptional.get() == ButtonType.CANCEL) {
                        selected_import_bill = null;
                        reset_import_review_data();
                    } else if (buttonTypeOptional.get() == ButtonType.OK) {


                        // check all item quantity if its returned export
                        import_check_quantity_list = new HashSet<String>();
                        import_check_expired_list = new HashSet<String>();

                        if (selected_import_bill.getBill_kind().equals("import")){
                            imported_review_operations_list.filtered(item -> item.getCode() == selected_import_bill.getCode()).forEach(bill_updated_item ->{

                                //  check items loop
                                double item_total_bill = imported_review_operations_list
                                        .filtered(item -> item.getCode() == selected_import_bill.getCode()  && item.getItem_code() == bill_updated_item.getItem_code()).stream().mapToDouble(f-> f.getItem_count()).sum();

                                Item  current_checked_Item = null;
                                Expire_Details current_checked_Expired_Item = null ;

                                current_checked_Item = items_dataManager.load_item_by_code(bill_updated_item.getItem_code());

                                if (current_checked_Item.getCurrent_quantity() < item_total_bill){

                                    import_check_quantity_list.add(bill_updated_item.getItem_name());


                                }else {
                                    // check for expire dates
                                    // 1 - find if same item with same expire date and update quantity
                                    double item_expire_total_bill = imported_review_operations_list
                                            .filtered(item -> item.getCode() == selected_import_bill.getCode()  &&
                                                    item.getItem_code() == bill_updated_item.getItem_code() &&
                                                    item.getExpire_date() == bill_updated_item.getExpire_date()).stream().mapToDouble(f-> f.getItem_count()).sum();

                                    current_checked_Expired_Item = itemExpire_dataManger.load_expire_item_by_code_and_date(bill_updated_item.getItem_code()
                                            , bill_updated_item.getExpire_date());

                                    if (current_checked_Expired_Item.getQuantity() < item_expire_total_bill){
                                        import_check_expired_list.add(bill_updated_item.getItem_name());
                                    }

                                }



                            });

                        }




                        //// operation
                        if (import_check_quantity_list.size() == 0 && import_check_expired_list.size() == 0){


                            // UPDATE COMPANY DATA
                        Company selected_Company = import_company_dataManager.load_company_by_name(selected_import_bill.getCompany_name());

                        if (selected_import_bill.getBill_kind().equals("import")){
                            selected_Company.setCurrent_balance(selected_Company.getCurrent_balance() - selected_import_bill.getBill_final_total());

                            selected_Company.setBalance_paid(selected_Company.getBalance_paid() - selected_import_bill.getBill_total_paid());

                            selected_Company.setBalance_remaining(selected_Company.getCurrent_balance() - selected_Company.getBalance_paid());

                        }
                        else if (selected_import_bill.getBill_kind().equals("return")){
                            selected_Company.setCurrent_balance(selected_Company.getCurrent_balance() + selected_import_bill.getBill_final_total());

                            selected_Company.setBalance_paid(selected_Company.getBalance_paid() + selected_import_bill.getBill_total_paid());

                            selected_Company.setBalance_remaining(selected_Company.getCurrent_balance() - selected_Company.getBalance_paid());

                        }

                        import_company_dataManager.update_company(selected_Company);


                        imported_review_operations_list.filtered(item -> item.getCode() == selected_import_bill.getCode()).forEach(bill_updated_item ->{

                            // update expire items
                            // add and update in expire details

                            // 1 - find if same item with same expire date and update quantity
                            boolean exist_status = itemExpire_dataManger.detect_expire_item_is_exist(bill_updated_item.getItem_code() , bill_updated_item.getExpire_date());

                            // 1.1 - if update exiting record
                            if (exist_status){
                                exiting_Expire_Item =  itemExpire_dataManger.load_expire_item_by_code_and_date(bill_updated_item.getItem_code() , bill_updated_item.getExpire_date());
                                if (bill_updated_item.getBill_kind().equals("return")){
                                    // ++
                                    exiting_Expire_Item.setQuantity(exiting_Expire_Item.getQuantity() + bill_updated_item.getItem_count());

                                    itemExpire_dataManger.update_item_Expire(exiting_Expire_Item);


                                }else if(bill_updated_item.getBill_kind().equals("import")) {
                                    // --
                                    exiting_Expire_Item.setQuantity(exiting_Expire_Item.getQuantity() - bill_updated_item.getItem_count());
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
                                current_Expire_Item.setItem_code(bill_updated_item.getItem_code());
                                current_Expire_Item.setItem_name(bill_updated_item.getItem_name());
                                current_Expire_Item.setItem_kind(bill_updated_item.getItem_kind());
                                current_Expire_Item.setQuantity(bill_updated_item.getItem_count());
                                current_Expire_Item.setExpire_date(bill_updated_item.getExpire_date());

                                itemExpire_dataManger.add_item_Expire(current_Expire_Item);

                            }

                            //  UPDATE ITEMS IN STORE

                            Item selected_Item = null , current_Updated_Item = null;

                            current_Updated_Item = items_dataManager.load_item_by_code(bill_updated_item.getItem_code());
                            if (selected_import_bill.getBill_kind().equals("import")){
                                current_Updated_Item.setCurrent_quantity(current_Updated_Item.getCurrent_quantity() - bill_updated_item.getItem_count());

                            }
                            else if (selected_import_bill.getBill_kind().equals("return")){
                                current_Updated_Item.setCurrent_quantity(current_Updated_Item.getCurrent_quantity() + bill_updated_item.getItem_count());

                            }
                            items_dataManager.update_item(current_Updated_Item);


                        });

                        // FOR ITEMS MOVES

                        if (selected_import_bill.getBill_kind().equals("import")){
                            itemMove_dataManger.delete_Item_Moves(selected_import_bill.getCode() , "وارد");
                        }
                        else if (selected_import_bill.getBill_kind().equals("return")){
                            itemMove_dataManger.delete_Item_Moves(selected_import_bill.getCode() , "مرتجع شركة");

                        }



                        Import_Bill deleted_Bill= new Import_Bill();
                        deleted_Bill.setCode(selected_import_bill.getCode());

                        if (import_company_dataManager.delete_bill(deleted_Bill).equals(QueryState.success)) {
                            UIManager.showAlert("تم حذف بيانات الفاتورة  بنجاح", AlertType.success, root, 0, 0);
                            Platform.runLater(() -> {
                                loadImportReviewData();
                                import_review_Table.refresh();
                            });
                            selected_import_bill = null;
                            reset_import_review_data();

                        } else {
                            UIManager.showAlert("تعذر حذف بيانات الفاتورة حدد و حاول مرة أخري ", AlertType.error, root, 0, 0);
                        }

                    } else {
                            UIManager.showAlert("لا يمكن حذف بيانات الفاتورة بسبب عدم كفاية الكميات الحاليه بنفس تواريخ الصلاحية في الاصناف المحددة", AlertType.error, root, 0, 0);
                            if (import_check_quantity_list.size() > 0){
                                UIManager.showAlert( " الاصناف ناقصه الكمية هي : " + import_check_quantity_list, AlertType.alarm, root, 0, 500);

                            }
                            if (import_check_expired_list.size() > 0){
                                UIManager.showAlert( " الاصناف ناقصه تواريخ الصلاحيه هي : " + import_check_expired_list, AlertType.alarm, root, 0, 300);

                            }

                        }

                } else {
                    UIManager.showAlert("برجاء تحديد عنصر من الجدول أولا", AlertType.error, root, 0, 0);

                }

                }

            } catch (Exception e) {
                System.out.println("Error in Delete Action ||| " + e.getMessage());
            }

        });

    }

    private void filter_import_review() {

        Task task = new Task<Void>() {
            @Override
            public Void call() {

                if (imported_review_operations_list.size() > 0) {
                    filtered_review_import_operations_list = imported_review_operations_list;
                    System.out.println("imported_review_operations_list | Size | " + filtered_review_import_operations_list.size());

                    //  1- Filter by review Company  name
                    if (import_review_search_company_Comb.getValue().length() != 0) {
                        filtered_review_import_operations_list = filtered_review_import_operations_list.filtered
                                (review_operation -> review_operation.getCompany_name().contains(import_review_search_company_Comb.getValue()));
                        import_review_delete_Btn.setVisible(false);
                        System.out.println("Filter | import_review_search_company_Comb ");
                    }

                    //  2- Filter by item Name

                    if (import_review_search_item_Comb.getValue().length() != 0) {
                        filtered_review_import_operations_list = filtered_review_import_operations_list.filtered
                                (review_operation -> review_operation.getItem_name().contains(import_review_search_item_Comb.getValue()));
                        import_review_delete_Btn.setVisible(false);
                        System.out.println("Filter | import_review_search_item_Comb");
                    }


                    //  3- Filter by pay kind
                    if (import_review_search_pay_kind_Comb.getValue().length() != 0) {
                        filtered_review_import_operations_list = filtered_review_import_operations_list.filtered
                                (review_operation -> review_operation.getPay_kind().contains(import_review_search_pay_kind_Comb.getValue()));
                        import_review_delete_Btn.setVisible(false);
                        System.out.println("Filter | import_review_search_pay_kind_Comb");
                    }

                    //  4- Filter by code
                    if (import_review_search_code_TxtF.getText().length() != 0) {
                        filtered_review_import_operations_list = filtered_review_import_operations_list.filtered
                                (review_operation -> review_operation.getCode() == (Integer.parseInt(import_review_search_code_TxtF.getText())));
                           if (filtered_review_import_operations_list.size() > 0){
                               import_review_delete_Btn.setVisible(true);

                           }else {
                               import_review_delete_Btn.setVisible(false);

                           }
                        System.out.println("Filter | import_review_search_code_TxtF");
                    }
                    else {
                        import_review_delete_Btn.setVisible(false);

                    }

                    //  5- Filter by From Date and To Date Together
                    if (import_review_search_fromDate_Picker.getValue() != null && import_review_search_toDate_Picker.getValue() != null) {
                        filtered_review_import_operations_list = filtered_review_import_operations_list.filtered
                                (review_operation ->
                                        !UIManager.convertToDate(import_review_search_fromDate_Picker.getValue()).after(review_operation.getDate()) &&
                                                !UIManager.convertToDate(import_review_search_toDate_Picker.getValue()).before(review_operation.getDate()) );
                        import_review_delete_Btn.setVisible(false);
                        System.out.println("Filter | From Date and To Date Together");
                    }

                    //  6- Filter by From Date
                    else  if (import_review_search_fromDate_Picker.getValue() != null && import_review_search_toDate_Picker.getValue() == null) {
                        filtered_review_import_operations_list = filtered_review_import_operations_list.filtered
                                (review_operation ->
                                        !UIManager.convertToDate(import_review_search_fromDate_Picker.getValue()).after(review_operation.getDate()) );
                        import_review_delete_Btn.setVisible(false);
                        System.out.println("Filter | review_search_fromDate_Picker");
                    }
                    //  7- Filter by To Date
                    else if (import_review_search_fromDate_Picker.getValue() == null && import_review_search_toDate_Picker.getValue() != null) {
                        filtered_review_import_operations_list = filtered_review_import_operations_list.filtered
                                (review_operation ->
                                        !UIManager.convertToDate(import_review_search_toDate_Picker.getValue()).before(review_operation.getDate()) );
                        import_review_delete_Btn.setVisible(false);
                        System.out.println("Filter | review_search_toDate_Picker");
                    }

                    //  8- Filter by Bill Kind

                    if (import_review_search_bill_kind_Comb.getValue().length() != 0) {
                        filtered_review_import_operations_list = filtered_review_import_operations_list.filtered
                                (review_operation -> review_operation.getBill_kind().
                                        contains(import_review_search_bill_kind_Comb.getValue().equals("فاتورة شراء") ? "import" :
                                                import_review_search_bill_kind_Comb.getValue().equals("فاتورة مرتجع") ? "return" :""));
                        import_review_delete_Btn.setVisible(false);

                        System.out.println("Filter | import_review_search_bill_kind_Comb");
                    }

                    System.out.println("filtered_review_import_operations_list | Size : " + filtered_review_import_operations_list.size());
                    import_review_Table.setItems(filtered_review_import_operations_list);
                    import_review_Table.refresh();
                } else {


                }

                return null;
            }
        };

        task.setOnSucceeded($ -> {
            if (import_review_search_code_TxtF.getText().length() !=0 && filtered_review_import_operations_list.size() > 0 ){

                import_review_total_price_TxtF.setText(df.format(filtered_review_import_operations_list.get(0).getBill_total_price()));
                import_review_discount_TxtF.setText(df.format(filtered_review_import_operations_list.get(0).getBill_total_discount()));
                import_review_paid_TxtF.setText(df.format(filtered_review_import_operations_list.get(0).getBill_total_paid()));
                import_review_final_total_TxtF.setText(df.format(filtered_review_import_operations_list.get(0).getBill_final_total()));
                import_review_number_TxtF.setText(filtered_review_import_operations_list.get(0).getBill_receipt_number());



            }
            else {
                import_review_total_price_TxtF.setText("");
                import_review_discount_TxtF.setText("");
                import_review_paid_TxtF.setText("");
                import_review_final_total_TxtF.setText("");

                import_review_number_TxtF.setText("");

            }

        });

        task.setOnFailed($ -> {
            System.out.println("Filter | Failed | " + $.toString());
        });

        Thread thread = new Thread(task);
        thread.start();

    }

    private void setup_import_filterOptions() {

        //TODO | Boxes_of_String

        // For Company Search
        import_review_search_company_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            import_review_search_code_TxtF.setText("");

            filter_import_review();
        });

        // For item Search
        import_review_search_item_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            import_review_search_code_TxtF.setText("");

            filter_import_review();
        });



        // For pay kind Search
        import_review_search_pay_kind_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            import_review_search_code_TxtF.setText("");

            filter_import_review();
        });

        // For bill kind Search
        import_review_search_bill_kind_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            import_review_search_code_TxtF.setText("");

            filter_import_review();
        });



        //TODO | TextFields

        // For Code Search
        import_review_search_code_TxtF.textProperty().addListener(($, old_value, new_value) -> {

            import_review_search_company_Comb.setValue("");
            import_review_search_pay_kind_Comb.setValue("");
            import_review_search_item_Comb.setValue("");
            import_review_search_bill_kind_Comb.setValue("");

            import_review_search_fromDate_Picker.setValue(null);
            import_review_search_toDate_Picker.setValue(null);

            System.out.println("New value | " + new_value);

            filter_import_review();



        });

        //TODO | DatePickers

        ObservableList<JFXDatePicker> pickers = FXCollections.observableArrayList();
        pickers.addAll(import_review_search_fromDate_Picker, import_review_search_toDate_Picker);
        pickers.forEach(picker -> {
            picker.valueProperty().addListener(($, old_value, new_value) -> {
                System.out.println("New value | " + new_value);
                import_review_search_code_TxtF.setText("");
                filter_import_review();
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
        setup_export_review_page_buttons();
        setup_import_review_page_buttons();

    }

    @Override
    public void layout_view() {
        initialized_export_review_boxes_data();
        initialized_import_review_boxes_data();

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
        setupExportedReviewTable();
        setupImportedReviewTable();
        setup_export_filterOptions();
        setup_import_filterOptions();

    }

    @Override
    public void setup_menu_buttons(){

    }


}
