package View.Public_views;

import AppManager.UIManager;
import DB_Manager.Main_Data_Manager.EarnAndSpendMoney_DataManager;
import DB_Manager.Main_Data_Manager.Envoy_DataManager;
import DB_Manager.Main_Data_Manager.Export_Client_DataManager;
import DB_Manager.Main_Data_Manager.Import_Company_DataManager;
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
import java.util.regex.Pattern;

public class Earn_And_Spend_Money_ViewController implements Initializable, ViewControllerClass {
    //TODO| Class Variables *************************************************************************************************

    EarnAndSpendMoney_DataManager earnAndSpendMoney_dataManager =  new EarnAndSpendMoney_DataManager();
    Export_Client_DataManager export_client_dataManager = new Export_Client_DataManager();
    Import_Company_DataManager import_company_dataManager =  new Import_Company_DataManager();
    Envoy_DataManager envoy_dataManager = new Envoy_DataManager();
    ObservableList<JFXTextField> integerFields = FXCollections.observableArrayList();
    ObservableList<JFXTextField> doubleFields = FXCollections.observableArrayList();
    DecimalFormat df=new DecimalFormat("#.##");
    ObservableList<Earn_And_Spend_Bill> bill_operations_list = FXCollections.observableArrayList();
    ObservableList<Earn_And_Spend_Bill> review_operations_list = FXCollections.observableArrayList();
    Earn_And_Spend_Bill added_earn_and_spend_bill = new Earn_And_Spend_Bill();
    ObservableList<String> names_list = FXCollections.observableArrayList();
    ObservableList<String> kinds_list = FXCollections.observableArrayList();
    ObservableList<Earn_And_Spend_Bill> filtered_review_operations_list = FXCollections.observableArrayList();

    Earn_And_Spend_Bill selected_Review ;

    String logo_img_param_code = "Reports/report_images/pharma_logo_r.png";

    JRBeanCollectionDataSource collection_Data;


    int bill_code =0;


    String page_Status ="";



    //TODO| Design Elements *************************************************************************************************
    
    @FXML
    private AnchorPane root, labelCont, controlCont , mainCont , secondCont;
  
    @FXML
    private VBox add_box;

    @FXML
    private Label title_label;

    @FXML
    private JFXTextField bill_code_TxtF , bill_value_TxtF , bill_total_TxtF , bill_notes_TxtF
             , review_search_code_TxtF, review_search_notes_TxtF;

    @FXML
    private JFXComboBox<String> bill_kind_Comb, bill_name_Comb , review_search_kind_Comb
            , review_search_name_Comb ;

    @FXML
    private JFXDatePicker bill_date_Picker , review_search_fromDate_Picker , review_search_toDate_Picker;

    @FXML
    private JFXCheckBox review_select_all_Checkbox;

    @FXML
    private TableView<Earn_And_Spend_Bill> add_bill_Table , review_Table;

    @FXML
    private TableColumn<Earn_And_Spend_Bill, String>  bill_index_Colo , bill_kind_Colo , bill_name_Colo , bill_notes_Colo
    , review_index_Colo , review_kind_Colo , review_name_Colo , review_notes_Colo;

    @FXML
    private TableColumn<Earn_And_Spend_Bill, Double> bill_value_Colo , review_value_Colo;

    @FXML
    private TableColumn<Earn_And_Spend_Bill, Integer> review_code_Colo ;

    @FXML
    private TableColumn<Earn_And_Spend_Bill, Date> review_date_Colo ;

    @FXML
    private TableColumn<Earn_And_Spend_Bill, JFXCheckBox> review_select_Colo;

    @FXML
    private JFXButton earn_money_Btn , review_money_Btn, spend_money_Btn,
            bill_operation_add_Btn  , bill_operation_confirm_Btn , review_back_Btn
            , review_clean_Btn , review_print_Btn , review_delete_Btn ;

    //TODO| This Class Functions Control Actions  *************************************************************************************************

    private  void setup_control_page_buttons(){

        // earn money button
        earn_money_Btn.setOnAction(event -> {
            try {
                controlCont.setVisible(false);
                mainCont.setVisible(true);
                secondCont.setVisible(false);
                title_label.setText(" صفحة إضافة فاتورة جديدة إلي التحصيلات المالية ");
                page_Status = "earn" ;
                bill_operation_confirm_Btn.setDisable(true);
                bill_kind_Comb.setDisable(false);
                bill_name_Comb.setDisable(false);
                bill_date_Picker.setDisable(false);
                bill_code_TxtF.setDisable(false);
                bill_date_Picker.setValue(LocalDate.now());
                bill_total_TxtF.setText("");


                bill_code_TxtF.setText("");
                bill_code_TxtF.setStyle(UIManager.white_background);


                bill_operations_list = FXCollections.observableArrayList();
                add_bill_Table.setItems(bill_operations_list);
                reset_add_data();

                // For Kind List Data
                bill_kind_Comb.getItems().removeAll();
                kinds_list = FXCollections.observableArrayList();
                kinds_list.addAll("تحصيل من عميل" ,  "تحصيلات أخري"  );
                bill_kind_Comb.setItems(kinds_list);
                bill_kind_Comb.setValue("");

                add_box.setDisable(true);



            }catch (Exception e ){
                System.out.println("Error in Earn Action ||| " + e.getMessage());
            }

        });


        // spend money button
        spend_money_Btn.setOnAction(event -> {
            try {
                controlCont.setVisible(false);
                mainCont.setVisible(true);
                secondCont.setVisible(false);
                title_label.setText(" صفحة إضافة فاتورة جديدة إلي المصروفات  المالية ");
                page_Status = "spend" ;
                bill_operation_confirm_Btn.setDisable(true);
                bill_kind_Comb.setDisable(false);
                bill_name_Comb.setDisable(false);
                bill_date_Picker.setDisable(false);
                bill_code_TxtF.setDisable(false);

                bill_date_Picker.setValue(LocalDate.now());
                bill_total_TxtF.setText("");

                bill_code_TxtF.setText("");
                bill_code_TxtF.setStyle(UIManager.white_background);

                bill_operations_list = FXCollections.observableArrayList();
                add_bill_Table.setItems(bill_operations_list);
                reset_add_data();

                // For Kind List Data
                bill_kind_Comb.getItems().removeAll();
                kinds_list = FXCollections.observableArrayList();
                kinds_list.addAll("صرف لشركة" , "صرف لمندوب" ,   "مصاريف أخري"   );
                bill_kind_Comb.setItems(kinds_list);
                bill_kind_Comb.setValue("");

                add_box.setDisable(true);





            }catch (Exception e ){
                System.out.println("Error in Spend Action ||| " + e.getMessage());
            }

        });


        // review money button
        review_money_Btn.setOnAction(event -> {
            try {
                controlCont.setVisible(false);
                mainCont.setVisible(false);
                secondCont.setVisible(true);
                title_label.setText(" صفحة مراجعة كل الفواتير السابقة سواء للمصروفات او من التحصيلات ");
                review_delete_Btn.setVisible(false);

                reset_review_data();


            }catch (Exception e ){
                System.out.println("Error in Review Action ||| " + e.getMessage());
            }

        });

    }

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

    //TODO| This Class Functions Add Page Actions  *************************************************************************************************

    private void initialized_main_boxes_data() {

        // For Double Numeric TextFields
        doubleFields.addAll(
                bill_value_TxtF
        );
        doubleFields.forEach(field -> {
            field.setText("");
            field.textProperty().addListener(($, old_value, new_value) -> {
                System.out.println("New value | " + new_value);
                if (!new_value.matches("\\d*")) {
                    field.setText(new_value.replaceAll("[^\\d ,^\\. ]" , ""));
                }
            });
        });

        // For Integers Numeric TextFields
        integerFields.addAll(
               bill_code_TxtF
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
        add_bill_Table.getStylesheets().add("/Assets/Custom_CSS/custom_table_font.css");

        // Boxes Style
        bill_kind_Comb.setStyle("-fx-background-color: #f1f1f1 ;-fx-font-family: Cambria ;-fx-font-size: 18  ;  -fx-font-weight:bold");
        bill_name_Comb.setStyle("-fx-background-color: #f1f1f1 ;-fx-font-family: Cambria ;-fx-font-size: 18  ;  -fx-font-weight:bold");

    }

    private void setup_selectedAndCalculatedData() {

        //TODO| 1 - Selected Data
        //  For Detect Operation Names  And Set  Names
        bill_kind_Comb.setOnAction(event -> {
            try {
                String new_value = bill_kind_Comb.getValue();
                System.out.println("New value | " + new_value);

                // clients
                if (new_value.equals("تحصيل من عميل") ){
                    names_list = export_client_dataManager.load_clients_names_list();
                } // companies
                else  if ( new_value.equals("صرف لشركة")){
                    names_list = import_company_dataManager.load_companies_names_list();
                }  //envoys
                else if (new_value.equals("صرف لمندوب") ){
                    names_list = envoy_dataManager.load_envoys_names_list();
                } // else
                else {
                    names_list =  FXCollections.observableArrayList();
                }

                bill_name_Comb.setItems(names_list);
                TextFields.bindAutoCompletion(bill_name_Comb.getEditor(), bill_name_Comb.getItems());
                if (new_value.equals(null)){
                    add_box.setDisable(true);

                }
                else {
                    add_box.setDisable(false);

                }


            }catch (Exception e){
                add_box.setDisable(true);

                System.out.println("Error Occurs In setup_selectedAndCalculatedData :: bill_kind_Comb " + e.getMessage());
            }

        });

    }
    
    private void reset_add_data() {

        // main components

        if (names_list.size()== 0){
            bill_name_Comb.setValue("");

        }


        bill_name_Comb.setStyle(UIManager.white_background);

        bill_value_TxtF.setText("");
        bill_value_TxtF.setStyle(UIManager.white_background);

        bill_date_Picker.setStyle(UIManager.white_background);

        bill_notes_TxtF.setText("");



        add_bill_Table.refresh();


    }

    private void setupAddOperationTable() {

        bill_index_Colo.setCellValueFactory(
                (TableColumn.CellDataFeatures<Earn_And_Spend_Bill, String> $)
                        -> new ReadOnlyObjectWrapper(add_bill_Table.getItems().indexOf($.getValue()) + 1 + ""));


        bill_kind_Colo.setCellValueFactory(new PropertyValueFactory<>("kind"));
        bill_name_Colo.setCellValueFactory(new PropertyValueFactory<>("name"));
        bill_notes_Colo.setCellValueFactory(new PropertyValueFactory<>("notes"));
        bill_value_Colo.setCellValueFactory(new PropertyValueFactory<>("value"));



    }

    private boolean detectValidMainData() {
        String integerPattern = "([0-9]*)";
        String decimalPattern = "([0-9]*)\\.([0-9]*)";

        //TODO | name and  name list TextField ********************************************************
        boolean validIListName = true ;
        boolean validIName = !bill_name_Comb.getValue().isEmpty();

        if (names_list.size()>0){
             validIListName = names_list.contains(bill_name_Comb.getValue());

        }
        bill_name_Comb.setStyle(validIListName && validIName ? UIManager.white_background : UIManager.deepOrange_background);

        //TODO | value TextField ********************************************************
        boolean validValue = !bill_value_TxtF.getText().isEmpty();
        String value_number= bill_value_TxtF.getText();
        boolean value_match = Pattern.matches(decimalPattern, value_number) || Pattern.matches(integerPattern, value_number) ;

        bill_value_TxtF.setStyle(validValue && value_match ? UIManager.white_background : UIManager.deepOrange_background);

        //TODO | code TextField ********************************************************
        boolean validCode = !bill_code_TxtF.getText().isEmpty();
        bill_code_TxtF.setStyle(validCode ? UIManager.white_background : UIManager.deepOrange_background);

        //TODO | bill  code Exist Or No ********************************************************
        boolean validBillCodeExist = true ;
        if (validCode){
            validBillCodeExist = !earnAndSpendMoney_dataManager.detect_earn_spend_bill_next_code(Integer.parseInt(bill_code_TxtF.getText()));
            bill_code_TxtF.setStyle(validBillCodeExist ? UIManager.white_background : UIManager.deepOrange_background);
            if (!validBillCodeExist){
                UIManager.show_error_alert("لا يمكن ان تكرار رقم فاتورة مسجل من قبل اكتب رقم جديد من فضلك ");

            }
        }


        //TODO | date DatePicker ********************************************************

        boolean validDate = true;
        if (bill_date_Picker.getValue() == null ){
            validDate = false;
        }

        bill_date_Picker.getEditor().setStyle(validDate ? UIManager.white_background : UIManager.red_background);

        //TODO | all  ********************************************************

        return (validCode && validBillCodeExist && validIName && validValue && value_match && validIListName && validDate );
    }

    private void setup_add_page_buttons() {
        //  add item btn
        bill_operation_add_Btn.setOnAction(event -> {
            try {
                if (detectValidMainData()) {

                    added_earn_and_spend_bill = new Earn_And_Spend_Bill();
                    added_earn_and_spend_bill.setKind(bill_kind_Comb.getValue());
                    added_earn_and_spend_bill.setName(bill_name_Comb.getValue());
                    added_earn_and_spend_bill.setValue(Double.parseDouble(bill_value_TxtF.getText()));
                    added_earn_and_spend_bill.setNotes(bill_notes_TxtF.getText());
                    added_earn_and_spend_bill.setDate(UIManager.convertToDate(bill_date_Picker.getValue()));

                    bill_operations_list.add(added_earn_and_spend_bill);
                    add_bill_Table.setItems(bill_operations_list);
                    add_bill_Table.refresh();

                    double total_price =Double.parseDouble(bill_value_TxtF.getText().isEmpty() ?
                            "0.00" : bill_value_TxtF.getText()) +
                            Double.parseDouble(bill_total_TxtF.getText().isEmpty() ?
                                    "0.00" : bill_total_TxtF.getText());

                    bill_total_TxtF.setText(df.format(total_price));

                    reset_add_data();
                    bill_kind_Comb.setDisable(true);
                    if (names_list.size()>0){
                        bill_name_Comb.setDisable(true);

                    }
                    bill_date_Picker.setDisable(true);
                    bill_operation_confirm_Btn.setDisable(false);
                    bill_code_TxtF.setDisable(true);

                } else {
                    UIManager.show_error_alert("برجاء التأكد من اكتمال و صحة بيانات العناصر المظللة و المسبوقة ب *");

                }
            } catch (Exception e) {
                UIManager.show_error_alert("برجاء التأكد من اكتمال و صحة بيانات العناصر المظللة و المسبوقة ب *");

                System.out.println("Error In Add Item Bill Btn  bill_operation_add_Btn ::: " + e.getMessage());
            }

        });

        // confirm bill btn
        bill_operation_confirm_Btn.setOnAction(event -> {
            try {

                Alert before_confirm_bill_alert =
                        new Alert(Alert.AlertType.WARNING, "تنبيه سيتم تأكيد الفاتورة علي الشكل الحالي .. هل توافق ؟ ", ButtonType.CANCEL, ButtonType.OK);
                Optional<ButtonType> buttonTypeOptional = before_confirm_bill_alert.showAndWait();
                if (buttonTypeOptional.get() == ButtonType.CANCEL) {

                } else if (buttonTypeOptional.get() == ButtonType.OK) {


                        // detected next code
                        bill_code = Integer.parseInt(bill_code_TxtF.getText()) ;


                        //add data to bill database
                        bill_operations_list.forEach(bill_operation ->{
                            // add operation to bill data
                            bill_operation.setCode(bill_code);

                            bill_operation.setTotal(Double.parseDouble(bill_total_TxtF.getText().isEmpty() ?
                                    "0.00" : bill_total_TxtF.getText()));


                            earnAndSpendMoney_dataManager.add_bill(bill_operation);




                        });


                        // update client / company  data
                        if (page_Status.equals("earn")){
                           if (bill_kind_Comb.getValue().equals("تحصيل من عميل")){
                               Client curr_client = export_client_dataManager.load_client_by_name(bill_name_Comb.getValue());
                               curr_client.setBalance_received(curr_client.getBalance_received() +
                                       Double.parseDouble(bill_total_TxtF.getText().isEmpty() ? "0.00" : bill_total_TxtF.getText()));

                               curr_client.setBalance_remaining(curr_client.getCurrent_balance() - curr_client.getBalance_received());
                               export_client_dataManager.update_client(curr_client);

                           }


                        }
                        else if (page_Status.equals("spend")){

                             if (bill_kind_Comb.getValue().equals("صرف لشركة")){

                                Company curr_company = import_company_dataManager.load_company_by_name(bill_name_Comb.getValue());
                                curr_company.setBalance_paid(curr_company.getBalance_paid() +
                                        Double.parseDouble(bill_total_TxtF.getText().isEmpty() ? "0.00" : bill_total_TxtF.getText()));

                                curr_company.setBalance_remaining(curr_company.getCurrent_balance() - curr_company.getBalance_paid());

                                import_company_dataManager.update_company(curr_company);

                            }
                        }

                        // success message
                        UIManager.showAlert(" تم تأكيد و إضافة الفاتورة بنجاح و رقم الفاتورة هو ::  " + bill_code, AlertType.success, root, 0, 0);

                        //  print bill

                    try{
                        String path = "Reports/public_reports/earn_spend.jrxml";
                        collection_Data =
                                new JRBeanCollectionDataSource(bill_operations_list);

                         // param funcs
                        Map<String, Object> parameters = new HashMap<>();

                        if (page_Status.equals("spend")){
                            parameters.put("bill_header", "إيصال صرف");

                        }
                        else if (page_Status.equals("earn")){
                            parameters.put("bill_header", "إيصال تحصيل");

                        }
                        // print function

                        parameters.put("img_param", logo_img_param_code);

                        JasperDesign jd = JRXmlLoader.load(path);

                        JasperReport js = JasperCompileManager.compileReport(jd);

                        JasperPrint jp = JasperFillManager.fillReport(js, parameters, collection_Data);


                        JasperViewer.viewReport(jp, false);


                    }catch (Exception e){
                        System.out.println("Error In Print Earn_Spend :: " + e.getMessage());
                    }


                    // reset page

                        page_Status = "" ;
                        controlCont.setVisible(true);
                        mainCont.setVisible(false);
                        secondCont.setVisible(false);
                        add_box.setDisable(true);

                    title_label.setText("صفحــة التعاملات المالية (  صرف / تحصيل / مراجعة المصروفات / مراجعة التحصيلات )");

                    }
                    else {
                        UIManager.show_error_alert("لا يمكن ان تكون قيمة الخصم اكبر من قيمة الفاتورة أو تجاهل الخانات المظللة");

                    }


            }catch (Exception e){
                System.out.println("Error in bill confirm btn bill_operation_confirm_Btn ::   " + e.getMessage());
            }

        });

    }

    //TODO| This Class Functions Review Page Actions  *************************************************************************************************
    private void initialized_second_boxes_data() {


        // For Review Kind List Data
        ObservableList<String> Review_kinds_list = FXCollections.observableArrayList();
        Review_kinds_list.addAll("تحصيل من عميل" ,  "تحصيلات أخري"  ,
                "صرف لشركة" , "صرف لمندوب" ,   "مصاريف أخري"    );
        review_search_kind_Comb.setItems(Review_kinds_list);
        review_search_kind_Comb.setValue("");
        TextFields.bindAutoCompletion(review_search_kind_Comb.getEditor(), review_search_kind_Comb.getItems());

        // For Integer Numeric TextFields
        integerFields.addAll(
                review_search_code_TxtF
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
        review_Table.getStylesheets().add("/Assets/Custom_CSS/custom_table_font.css");

        // Boxes Style
        review_search_kind_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 18  ;  -fx-font-weight:bold");
        review_search_name_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 18  ;  -fx-font-weight:bold");

        // Pickers Style
        review_search_fromDate_Picker.setStyle("-fx-background-color: #ffffff ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 18  ;  -fx-font-weight:bold");
        review_search_toDate_Picker.setStyle("-fx-background-color: #ffffff ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 18  ;  -fx-font-weight:bold");

    }

    private void setup_second_selectedAndCalculatedData() {

        //TODO| 1 - Selected Data
        //  For Detect Search Review Operation Names  And Set  Names
        review_search_kind_Comb.setOnAction(event -> {
            try {
                String new_value = review_search_kind_Comb.getValue();
                System.out.println("New value ||| " + new_value);

                // clients
                if (new_value.equals("تحصيل من عميل") ){
                    names_list = export_client_dataManager.load_clients_names_list();
                } // companies
                else  if ( new_value.equals("صرف لشركة")){
                    names_list = import_company_dataManager.load_companies_names_list();
                } //envoys
                else if (new_value.equals("صرف لمندوب") ){
                    names_list = envoy_dataManager.load_envoys_names_list();
                } // else
                else {
                    names_list =  FXCollections.observableArrayList();
                }

                review_search_name_Comb.setItems(names_list);
                TextFields.bindAutoCompletion(review_search_name_Comb.getEditor(), review_search_name_Comb.getItems());


            }catch (Exception e){
                System.out.println("Error Occurs In setup_second_selectedAndCalculatedData :: review_search_kind_Comb " + e.getMessage());
            }

        });

    }

    private void reset_review_data() {
        // main components

        review_search_kind_Comb.setValue("");
        review_search_name_Comb.setValue("");

        review_search_code_TxtF.setText("");
        review_search_notes_TxtF.setText("");

        review_search_fromDate_Picker.setValue(null);
        review_search_toDate_Picker.setValue(null);

        // help component
        review_select_all_Checkbox.setSelected(true);
        review_delete_Btn.setVisible(false);

        // load table
        loadReviewData();


        review_Table.refresh();


    }

    private void loadReviewData() {
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
                review_operations_list = FXCollections.observableArrayList();
                review_operations_list = earnAndSpendMoney_dataManager.load_operations_data_list();

                Platform.runLater(() -> {
                    review_Table.setItems(review_operations_list);
                    root.getChildren().remove(dark_pane);

                });
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();

    }

    private void setupReviewTable() {

        review_index_Colo.setCellValueFactory(
                (TableColumn.CellDataFeatures<Earn_And_Spend_Bill, String> $)
                        -> new ReadOnlyObjectWrapper(review_Table.getItems().indexOf($.getValue()) + 1 + ""));
        review_select_Colo.setCellValueFactory(new PropertyValueFactory<>("isSelected_checkBox"));
        review_kind_Colo.setCellValueFactory(new PropertyValueFactory<>("kind"));
        review_code_Colo.setCellValueFactory(new PropertyValueFactory<>("code"));
        review_name_Colo.setCellValueFactory(new PropertyValueFactory<>("name"));
        review_value_Colo.setCellValueFactory(new PropertyValueFactory<>("value"));
        review_date_Colo.setCellValueFactory(new PropertyValueFactory<>("date"));
        review_notes_Colo.setCellValueFactory(new PropertyValueFactory<>("notes"));

        //TODO - setup action when clicking cell ***********************************************
        review_Table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        review_Table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                selected_Review = review_Table.getSelectionModel().getSelectedItem();

                review_Table.refresh();
            }

        });

        review_Table.setRowFactory($ -> {


            TableRow<Earn_And_Spend_Bill> row = new TableRow<Earn_And_Spend_Bill>() {
                @Override
                protected void updateItem(Earn_And_Spend_Bill review, boolean empty) {
                    try {

                        super.updateItem(review, empty);
                        if (review != null) {
                            if (review.getKind().equals("تحصيل من عميل") ||
                                    review.getKind().equals("تحصيلات أخري") ) {
                                if (selected_Review == review){
                                    setStyle(UIManager.blue_background);
                                }
                                else {
                                    setStyle(UIManager.greenLight_background);

                                }

                            } else if (review.getKind().equals("صرف لشركة") ||
                                    review.getKind().equals("صرف لمندوب") ||
                                    review.getKind().equals("مصاريف أخري")) {
                                if (selected_Review == review){
                                    setStyle(UIManager.blue_background);
                                }
                                else {
                                    setStyle(UIManager.pink_background);
                                }
                            }else setStyle("");
                        }

                    } catch (Exception e) {
                        System.out.println("error in row setupReviewTable ::: " + e.getMessage());
                    }

                }
            };

            return row;
        });


    }

    private  void setup_review_page_buttons(){


        // reset button
        review_clean_Btn.setOnAction(event -> {
            reset_review_data();
            initialized_second_boxes_data();

        });

        // select all and unselect all check action
        review_select_all_Checkbox.selectedProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            review_Table.getItems().forEach(review -> {
                review.getIsSelected_checkBox().setSelected(new_value);

            });
        });

        // print button
        review_print_Btn.setOnAction(event -> {
            try{
                String path ;
                Map<String, Object> parameters = new HashMap<>();

                if(review_search_code_TxtF.getText().length() > 0 && review_Table.getItems().size() > 0){
                     path = "Reports/public_reports/earn_spend.jrxml";
                    collection_Data =
                            new JRBeanCollectionDataSource(review_Table.getItems());

                    if (review_Table.getItems().get(0).getKind().contains("صرف") ||review_Table.getItems().get(0).getKind().contains("مصاريف")  ){
                        parameters.put("bill_header", "إيصال صرف");

                    }
                    else if (review_Table.getItems().get(0).getKind().contains("تحصيل") ||review_Table.getItems().get(0).getKind().contains("تحصيلات")  ){

                        parameters.put("bill_header", "إيصال تحصيل");

                    }

                }else {
                     path = "Reports/public_reports/earn_spend_rev.jrxml";
                    collection_Data =
                            new JRBeanCollectionDataSource(review_Table.getItems().filtered(rev ->rev.getIsSelected_checkBox().isSelected()));

                }


                // print function

                parameters.put("img_param", logo_img_param_code);

                JasperDesign jd = JRXmlLoader.load(path);

                JasperReport js = JasperCompileManager.compileReport(jd);

                JasperPrint jp = JasperFillManager.fillReport(js, parameters, collection_Data);


                JasperViewer.viewReport(jp, false);


            }catch (Exception e){
                System.out.println("Error In Print Rev Earn_Spend :: " + e.getMessage());
            }

        });

        // delete button
        review_delete_Btn.setOnAction(event -> {
            try {
                if (selected_Review != null) {
                    Alert before_delete_company_alert = new Alert(Alert.AlertType.WARNING,  "تحذير سيتم حذف بيانات هذه الفاتورة رقم " + selected_Review.getCode() , ButtonType.CANCEL, ButtonType.OK);
                    Optional<ButtonType> buttonTypeOptional = before_delete_company_alert.showAndWait();
                    if (buttonTypeOptional.get() == ButtonType.CANCEL) {
                        selected_Review = null;
                        reset_review_data();
                    } else if (buttonTypeOptional.get() == ButtonType.OK) {

                        // update client / company  data
                            if (selected_Review.getKind().equals("تحصيل من عميل")){
                                Client curr_client = export_client_dataManager.load_client_by_name(selected_Review.getName());
                                curr_client.setBalance_received(curr_client.getBalance_received() - selected_Review.getTotal());

                                curr_client.setBalance_remaining(curr_client.getCurrent_balance() - curr_client.getBalance_received());
                                export_client_dataManager.update_client(curr_client);

                            }
                            else if (selected_Review.getKind().equals("صرف لشركة")){

                                Company curr_company = import_company_dataManager.load_company_by_name(selected_Review.getName());
                                curr_company.setBalance_paid(curr_company.getBalance_paid() - selected_Review.getTotal());

                                curr_company.setBalance_remaining(curr_company.getCurrent_balance() - curr_company.getBalance_paid());

                                import_company_dataManager.update_company(curr_company);

                            }


                        Earn_And_Spend_Bill deleted_review = new Earn_And_Spend_Bill();
                        deleted_review.setCode(selected_Review.getCode());

                        if (earnAndSpendMoney_dataManager.delete_bill(deleted_review).equals(QueryState.success)) {
                            UIManager.showAlert("تم حذف البند  بنجاح", AlertType.success, root, 0, 0);
                            Platform.runLater(() -> {
                                loadReviewData();
                                review_Table.refresh();
                            });
                            selected_Review = null;
                            reset_review_data();
                            initialized_second_boxes_data();

                        } else {
                            UIManager.showAlert("تعذر حذف  البند حدد و حاول مرة أخري ", AlertType.error, root, 0, 0);
                        }

                    }

                } else {
                    UIManager.showAlert("برجاء تحديد عنصر من الجدول أولا", AlertType.error, root, 0, 0);

                }
            }catch (Exception e ){
                System.out.println("Error in Delete Action ||| " + e.getMessage());
            }

        });


        // back button
        review_back_Btn.setOnAction(event -> {
            try {
                controlCont.setVisible(true);
                mainCont.setVisible(false);
                secondCont.setVisible(false);

                title_label.setText("صفحــة التعاملات المالية (  صرف / تحصيل / مراجعة المصروفات / مراجعة التحصيلات )");

            }catch (Exception e ){
                System.out.println("Error in Back Action ||| " + e.getMessage());
            }

        });

    }

    private void filter_review_items() {

        Task task = new Task<Void>() {
            @Override
            public Void call() {

                if (review_operations_list.size() > 0) {
                    filtered_review_operations_list = review_operations_list;
                    System.out.println("filtered_review_operations_list | Size | " + filtered_review_operations_list.size());

                    //  1- Filter by review kind

                    if (review_search_kind_Comb.getValue().length() != 0) {
                        filtered_review_operations_list = filtered_review_operations_list.filtered
                                (review_operation -> replacedWordToArabic_Action(review_operation.getKind()).
                                        contains(replacedWordToArabic_Action(review_search_kind_Comb.getValue())));
                        review_delete_Btn.setVisible(false);

                        System.out.println("Filter | review_search_kind_Comb");
                    }

                    //  2- Filter by review Name

                    if (review_search_name_Comb.getValue().length() != 0) {
                        filtered_review_operations_list = filtered_review_operations_list.filtered
                                (review_operation -> replacedWordToArabic_Action(review_operation.getName()).
                                        contains(replacedWordToArabic_Action(review_search_name_Comb.getValue())));
                        review_delete_Btn.setVisible(false);

                        System.out.println("Filter | review_search_name_Comb");
                    }

                    //  3- Filter by review  Notes

                    if (review_search_notes_TxtF.getText().length() != 0) {
                        filtered_review_operations_list = filtered_review_operations_list.filtered
                                (review_operation -> replacedWordToArabic_Action(review_operation.getNotes()).
                                        contains(replacedWordToArabic_Action(review_search_notes_TxtF.getText())));
                        review_delete_Btn.setVisible(false);

                        System.out.println("Filter | review_search_notes_TxtF");
                    }

                    //  4- Filter by Code Name

                    if (review_search_code_TxtF.getText().length() != 0) {
                        filtered_review_operations_list = filtered_review_operations_list.filtered
                                (review_operation -> review_operation.getCode() == (Integer.parseInt(review_search_code_TxtF.getText())));
                        if(filtered_review_operations_list.size() > 0) {
                            review_delete_Btn.setVisible(true);

                        }else {
                            review_delete_Btn.setVisible(false);

                        }

                        System.out.println("Filter | review_search_code_TxtF");
                    }else {
                        review_delete_Btn.setVisible(false);

                    }

                    //  5- Filter by From Date and To Date Together

                    if (review_search_fromDate_Picker.getValue() != null && review_search_toDate_Picker.getValue() != null) {
                        filtered_review_operations_list = filtered_review_operations_list.filtered
                                (review_operation ->
                                        !UIManager.convertToDate(review_search_fromDate_Picker.getValue()).after(review_operation.getDate()) &&
                                                !UIManager.convertToDate(review_search_toDate_Picker.getValue()).before(review_operation.getDate()) );
                        review_delete_Btn.setVisible(false);

                        System.out.println("Filter | From Date and To Date Together");
                    }
                    //  6- Filter by From Date
                    else  if (review_search_fromDate_Picker.getValue() != null && review_search_toDate_Picker.getValue() == null) {
                        filtered_review_operations_list = filtered_review_operations_list.filtered
                                (review_operation ->
                                        !UIManager.convertToDate(review_search_fromDate_Picker.getValue()).after(review_operation.getDate()) );
                        review_delete_Btn.setVisible(false);

                        System.out.println("Filter | review_search_fromDate_Picker");
                    }
                    //  7- Filter by To Date
                    else if (review_search_fromDate_Picker.getValue() == null && review_search_toDate_Picker.getValue() != null) {
                        filtered_review_operations_list = filtered_review_operations_list.filtered
                                (review_operation ->
                                        !UIManager.convertToDate(review_search_toDate_Picker.getValue()).before(review_operation.getDate()) );
                        review_delete_Btn.setVisible(false);

                        System.out.println("Filter | review_search_toDate_Picker");
                    }

                    System.out.println("filtered_review_operations_list | Size : " + filtered_review_operations_list.size());
                    review_Table.setItems(filtered_review_operations_list);
                    review_Table.refresh();
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

        //TODO | Boxes_of_String

        // For Kind Search
        review_search_kind_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            review_search_code_TxtF.setText("");

            filter_review_items();
        });

        // For Name Search
        review_search_name_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            review_search_code_TxtF.setText("");

            filter_review_items();
        });

        //TODO | TextFields


        // For Notes  Search
        review_search_notes_TxtF.textProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            review_search_code_TxtF.setText("");

            filter_review_items();
        });

        // For Code Search
        review_search_code_TxtF.textProperty().addListener(($, old_value, new_value) -> {
            review_search_kind_Comb.setValue("");
            review_search_name_Comb.setValue("");
            review_search_fromDate_Picker.setValue(null);
            review_search_toDate_Picker.setValue(null);
            review_search_notes_TxtF.setText("");
            System.out.println("New value | " + new_value);
            filter_review_items();
        });

        //TODO | DatePickers

        ObservableList<JFXDatePicker> pickers = FXCollections.observableArrayList();
        pickers.addAll(review_search_fromDate_Picker, review_search_toDate_Picker);
        pickers.forEach(picker -> {
            picker.valueProperty().addListener(($, old_value, new_value) -> {
                System.out.println("New value | " + new_value);
                review_search_code_TxtF.setText("");
                filter_review_items();
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
        setup_control_page_buttons();
        setup_add_page_buttons();
        setup_review_page_buttons();
    }

    @Override
    public void layout_view() {
        initialized_main_boxes_data() ;
        initialized_second_boxes_data();

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
        setupAddOperationTable();
        setup_selectedAndCalculatedData();
        setupReviewTable();
        setup_second_selectedAndCalculatedData();
        setup_filterOptions();
    }

    @Override
    public void setup_menu_buttons() {

    }

}
