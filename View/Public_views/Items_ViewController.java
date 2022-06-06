package View.Public_views;

import AppManager.UIManager;
import DB_Manager.Main_Data_Manager.ItemExpire_DataManger;
import DB_Manager.Main_Data_Manager.Items_DataManager;
import Interfaces.ViewControllerClass;
import Model.Enums.AlertType;
import Model.Enums.QueryState;
import Model.Main_Data.Expire_Details;
import Model.Main_Data.Export_Bill;
import Model.Main_Data.Item;
import com.jfoenix.controls.*;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.regex.Pattern;

public class Items_ViewController implements Initializable, ViewControllerClass {
    //TODO| Class Variables *************************************************************************************************

    Items_DataManager items_dataManager = new Items_DataManager();
    ItemExpire_DataManger itemExpire_dataManger = new ItemExpire_DataManger();

    ObservableList<JFXTextField> integerFields = FXCollections.observableArrayList();
    ObservableList<JFXTextField> doubleFields = FXCollections.observableArrayList();
    ObservableList<Item> items_list = FXCollections.observableArrayList();
    ObservableList<Item> filtered_items = FXCollections.observableArrayList();

    ObservableList<Expire_Details> expires_list = FXCollections.observableArrayList();
    ObservableList<Expire_Details> filtered_expires = FXCollections.observableArrayList();

    String logo_img_param_code = "Reports/report_images/pharma_logo_r.png";
    JRBeanCollectionDataSource collection_Data;

    Item selected_Item ;



    //TODO| Design Elements ( ITEMS STORE ) *************************************************************************************************
    
    @FXML
    private AnchorPane root, labelCont, mainCont , secondCont;

    @FXML
    private Label title_label;


    @FXML
    private JFXTextField item_name_TxtF, item_farmer_cash_price_TxtF, item_trade_cash_price_TxtF,
            item_farmer_Installment_price_TxtF ,item_trade_Installment_price_TxtF ,item_buy_price_TxtF ,
            item_quantity_limit_TxtF, item_notes_TxtF, item_search_code_TxtF ;

    @FXML
    private JFXComboBox<String> item_kind_Comb, item_search_name_Comb;

    @FXML
    private TableView<Item> items_store_Table;

    @FXML
    private TableColumn<Item, CheckBox> item_selected_Colo;

    @FXML
    private TableColumn<Item, Integer>  item_code_Colo, item_quantity_limit_Colo;

    @FXML
    private TableColumn<Item, String>  item_index_Colo , item_name_Colo, item_kind_Colo, item_notes_Colo;

    @FXML
    private TableColumn<Item, Double> item_current_quantity_Colo, item_farmer_cash_price_Colo, item_trade_cash_price_Colo ,
            item_farmer_Installment_price_Colo , item_trade_Installment_price_Colo , item_buy_Colo
            ;

    @FXML
    private JFXButton item_edit_Btn, item_print_Btn, item_add_Btn  , item_expire_details_Btn ,clean_Btn;

    @FXML
    private JFXCheckBox select_all_Checkbox;


    //TODO| Design Elements ( EXPIRE DETAILS ) *************************************************************************************************
    @FXML
    private JFXTextField expire_search_code_TxtF;

    @FXML
    private JFXComboBox<String> expire_search_name_Comb;

    @FXML
    private TableView<Expire_Details> expire_Table;

    @FXML
    private TableColumn<Expire_Details, String>  expire_item_index_Colo , expire_item_name_Colo , expire_item_kind_Colo;

    @FXML
    private TableColumn<Expire_Details, Integer>   expire_item_code_Colo;

    @FXML
    private TableColumn<Expire_Details, Double> expire_item_quantity_Colo;

    @FXML
    private TableColumn<Expire_Details, Date> expire_date_Colo;

    @FXML
    private JFXButton expire_back_Btn , expire_clean_Btn;

    //TODO| Main Class Functions (Items Page)  *************************************************************************************************

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
        kinds_list.addAll("عبوة", "شيكارة" , "كيس" , "جركن" );
        item_kind_Comb.setItems(kinds_list);
        item_kind_Comb.setValue("");
        TextFields.bindAutoCompletion(item_kind_Comb.getEditor(), item_kind_Comb.getItems());


        // For Items Names
        ObservableList<String> names_list = FXCollections.observableArrayList();
        names_list.addAll(items_dataManager.load_items_names_list());
        item_search_name_Comb.setItems(names_list);
        item_search_name_Comb.setValue("");

        expire_search_name_Comb.setItems(names_list);
        expire_search_name_Comb.setValue("");

        TextFields.bindAutoCompletion(item_search_name_Comb.getEditor(), item_search_name_Comb.getItems());
        TextFields.bindAutoCompletion(expire_search_name_Comb.getEditor(), expire_search_name_Comb.getItems());

        // Integer For Numeric TextFields
        integerFields.addAll(
                item_search_code_TxtF , expire_search_code_TxtF

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

        // For Double Numeric TextFields
        doubleFields.addAll(
                item_quantity_limit_TxtF , item_farmer_cash_price_TxtF , item_trade_cash_price_TxtF ,
                item_trade_Installment_price_TxtF , item_farmer_Installment_price_TxtF ,item_buy_price_TxtF
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

        // Table Style
        items_store_Table.getStylesheets().add("/Assets/Custom_CSS/custom_table_font.css");
        expire_Table.getStylesheets().add("/Assets/Custom_CSS/custom_table_font.css");

        // Boxes Style
        item_kind_Comb.setStyle("-fx-background-color: #f1f1f1 ;-fx-font-family: Cambria ;-fx-font-size: 19  ;  -fx-font-weight:bold");
        item_search_name_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 19  ;  -fx-font-weight:bold");
        expire_search_name_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 19  ;  -fx-font-weight:bold");

    }

    private void reset_data() {
        // main components
        item_name_TxtF.setText("");
        item_name_TxtF.setDisable(false);
        item_name_TxtF.setStyle(UIManager.white_background);

        item_kind_Comb.setValue("");
        item_kind_Comb.setStyle(UIManager.white_background);

        item_farmer_cash_price_TxtF.setText("");
        item_farmer_cash_price_TxtF.setStyle(UIManager.white_background);

        item_trade_cash_price_TxtF.setText("");
        item_trade_cash_price_TxtF.setStyle(UIManager.white_background);

        item_farmer_Installment_price_TxtF.setText("");
        item_farmer_Installment_price_TxtF.setStyle(UIManager.white_background);

        item_trade_Installment_price_TxtF.setText("");
        item_trade_Installment_price_TxtF.setStyle(UIManager.white_background);


        item_buy_price_TxtF.setText("");
        item_buy_price_TxtF.setStyle(UIManager.white_background);

        item_quantity_limit_TxtF.setText("");
        item_quantity_limit_TxtF.setStyle(UIManager.white_background);

        item_notes_TxtF.setText("");
        item_notes_TxtF.setStyle(UIManager.white_background);


        // search components
        item_search_code_TxtF.setText("");
        item_search_name_Comb.setValue("");

        // help component
        select_all_Checkbox.setSelected(true);

        // load table
        loadItems();


        items_store_Table.refresh();

        selected_Item =null ;





    }

    private void loadItems() {
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
                items_list = FXCollections.observableArrayList();
                items_list = items_dataManager.load_items_data_list();

                Platform.runLater(() -> {
                    items_store_Table.setItems(items_list);
                    root.getChildren().remove(dark_pane);

                });
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();

    }

    private void setupItemTable() {

        item_index_Colo.setCellValueFactory(
                (TableColumn.CellDataFeatures<Item, String> $)
                        -> new ReadOnlyObjectWrapper(items_store_Table.getItems().indexOf($.getValue()) + 1 + ""));

        item_selected_Colo.setCellValueFactory(new PropertyValueFactory<>("isSelected_checkBox"));

        item_code_Colo.setCellValueFactory(new PropertyValueFactory<>("code"));
        item_name_Colo.setCellValueFactory(new PropertyValueFactory<>("name"));
        item_current_quantity_Colo.setCellValueFactory(new PropertyValueFactory<>("current_quantity"));
        item_kind_Colo.setCellValueFactory(new PropertyValueFactory<>("kind"));

        item_farmer_cash_price_Colo.setCellValueFactory(new PropertyValueFactory<>("farmer_cash_price"));
        item_trade_cash_price_Colo.setCellValueFactory(new PropertyValueFactory<>("trade_cash_price"));
        item_farmer_Installment_price_Colo.setCellValueFactory(new PropertyValueFactory<>("farmer_Installment_price"));
        item_trade_Installment_price_Colo.setCellValueFactory(new PropertyValueFactory<>("trade_Installment_price"));
        item_buy_Colo.setCellValueFactory(new PropertyValueFactory<>("buy_price"));
        item_quantity_limit_Colo.setCellValueFactory(new PropertyValueFactory<>("quantity_limit"));
        item_notes_Colo.setCellValueFactory(new PropertyValueFactory<>("notes"));

        //TODO - setup limit quantity ***********************************************


        items_store_Table.setRowFactory($ -> {


            TableRow<Item> row = new TableRow<Item>() {
                @Override
                protected void updateItem(Item item, boolean empty) {
                    try {

                        super.updateItem(item, empty);
                        if (item != null) {
                            if (item.getCurrent_quantity() > item.getQuantity_limit() ) {
                                setStyle("");

                            }
                            else    if (item.getCurrent_quantity() == item.getQuantity_limit() ) {
                                setStyle("");

                            }
                            else    if (item.getCurrent_quantity() < item.getQuantity_limit() ) {
                                setStyle(UIManager.red_background);

                            }
                            else setStyle("");
                        }

                    } catch (Exception e) {
                        System.out.println("error in row setupStoreTable ::: " + e.getMessage());
                    }

                }
            };

            return row;
        });


        //TODO - setup action when clicking cell ***********************************************
        items_store_Table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        items_store_Table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2){
                selected_Item = items_store_Table.getSelectionModel().getSelectedItem();

                item_name_TxtF.setText(selected_Item.getName());
                item_name_TxtF.setDisable(true);

                item_kind_Comb.setValue(selected_Item.getKind());
                item_farmer_cash_price_TxtF.setText(String.valueOf(selected_Item.getFarmer_cash_price()));
                item_trade_cash_price_TxtF.setText(String.valueOf(selected_Item.getTrade_cash_price()));
                item_farmer_Installment_price_TxtF.setText(String.valueOf(selected_Item.getFarmer_Installment_price()));
                item_trade_Installment_price_TxtF.setText(String.valueOf(selected_Item.getTrade_Installment_price()));
                item_buy_price_TxtF.setText(String.valueOf(selected_Item.getBuy_price()));
                item_quantity_limit_TxtF.setText(String.valueOf(selected_Item.getQuantity_limit()));
                item_notes_TxtF.setText(selected_Item.getNotes());


            }

        });



    }

    private boolean detectValidMainData() {
        String integerPattern = "([0-9]*)";
        String decimalPattern = "([0-9]*)\\.([0-9]*)";
        //TODO | item name TextField ********************************************************
        boolean validItemName = !item_name_TxtF.getText().isEmpty();
        item_name_TxtF.setStyle(validItemName ? UIManager.white_background : UIManager.deepOrange_background);

        //TODO | item kind Box ********************************************************
        boolean validItemKind = !item_kind_Comb.getValue().isEmpty() ;
        item_kind_Comb.setStyle(validItemKind ? UIManager.white_background : UIManager.deepOrange_background);

        //TODO | item farmer cash price TextField ********************************************************
        boolean validItemFarmerCashPrice = !item_farmer_cash_price_TxtF.getText().isEmpty();
        String farmer_cash_number= item_farmer_cash_price_TxtF.getText();
        boolean farmer_cash_match = Pattern.matches(decimalPattern, farmer_cash_number) || Pattern.matches(integerPattern, farmer_cash_number) ;

        item_farmer_cash_price_TxtF.setStyle(validItemFarmerCashPrice && farmer_cash_match ? UIManager.white_background : UIManager.deepOrange_background);

        //TODO | item trade cash price TextField ********************************************************
        boolean validItemTradeCashPrice = !item_trade_cash_price_TxtF.getText().isEmpty();
        String trade_cash_number= item_trade_cash_price_TxtF.getText();
        boolean trade_cash_match = Pattern.matches(decimalPattern, trade_cash_number) || Pattern.matches(integerPattern, trade_cash_number) ;

        item_trade_cash_price_TxtF.setStyle(validItemTradeCashPrice && trade_cash_match ? UIManager.white_background : UIManager.deepOrange_background);


        //TODO | item farmer installment price TextField ********************************************************
        boolean validItemFarmerInstallmentPrice = !item_farmer_Installment_price_TxtF.getText().isEmpty();
        String farmer_installment_number= item_farmer_Installment_price_TxtF.getText();
        boolean farmer_installment_match = Pattern.matches(decimalPattern, farmer_installment_number) || Pattern.matches(integerPattern, farmer_installment_number) ;

        item_farmer_Installment_price_TxtF.setStyle(validItemFarmerInstallmentPrice && farmer_installment_match ? UIManager.white_background : UIManager.deepOrange_background);

        //TODO | item trade installment price TextField ********************************************************
        boolean validItemTradeInstallmentPrice = !item_trade_Installment_price_TxtF.getText().isEmpty();
        String trade_installment_number= item_trade_Installment_price_TxtF.getText();
        boolean trade_installment_match = Pattern.matches(decimalPattern, trade_installment_number) || Pattern.matches(integerPattern, trade_installment_number) ;

        item_trade_Installment_price_TxtF.setStyle(validItemTradeInstallmentPrice && trade_installment_match ? UIManager.white_background : UIManager.deepOrange_background);

        //TODO | item buy price TextField ********************************************************
        boolean validItemBuyPrice = !item_buy_price_TxtF.getText().isEmpty();
        String  buy_number= item_buy_price_TxtF.getText();
        boolean buy_match = Pattern.matches(decimalPattern, buy_number) || Pattern.matches(integerPattern, buy_number) ;

        item_buy_price_TxtF.setStyle(validItemBuyPrice && buy_match ? UIManager.white_background : UIManager.deepOrange_background);

        //TODO | all  ********************************************************

        return (validItemName && validItemKind && validItemFarmerCashPrice && validItemTradeCashPrice &&
                validItemFarmerInstallmentPrice && validItemTradeInstallmentPrice &&farmer_cash_match && trade_cash_match &&
                farmer_installment_match && trade_installment_match && validItemBuyPrice && buy_match );
    }

    private  void setup_page_buttons(){


        // reset button
        clean_Btn.setOnAction(event -> {
            reset_data();
            initialized_boxes_data();

        });

        // add button
        item_add_Btn.setOnAction(event -> {
            try {
                if (detectValidMainData()){

                    Item added_item = new Item();
                    added_item.setName(item_name_TxtF.getText());
                    added_item.setKind(item_kind_Comb.getValue());
                    added_item.setFarmer_cash_price(Double.parseDouble(item_farmer_cash_price_TxtF.getText()));
                    added_item.setTrade_cash_price(Double.parseDouble(item_trade_cash_price_TxtF.getText()));
                    added_item.setFarmer_Installment_price(Double.parseDouble(item_farmer_Installment_price_TxtF.getText()));
                    added_item.setTrade_Installment_price(Double.parseDouble(item_trade_Installment_price_TxtF.getText()));
                    added_item.setBuy_price(Double.parseDouble(item_buy_price_TxtF.getText()));
                    added_item.setQuantity_limit(Double.valueOf(item_quantity_limit_TxtF.getText().isEmpty() ? "0.00" :item_quantity_limit_TxtF.getText()));
                    added_item.setNotes(item_notes_TxtF.getText().isEmpty() ? "" : item_notes_TxtF.getText());

                    if (items_dataManager.add_item(added_item).equals(QueryState.success)) {
                        UIManager.showAlert("تم اضافة صنف جديد بنجاح", AlertType.success, root, 0, 0);
                        Platform.runLater(() -> {
                           loadItems();
                            items_store_Table.refresh();
                        });
                         reset_data();
                        initialized_boxes_data();

                    } else {
                        UIManager.showAlert("تعذر اضافة الصنف تأكد من عدم تكرار الاسم", AlertType.error, root, 0, 0);
                    }



                }else {
                    UIManager.show_error_alert("برجاء التأكد من اكتمال و صحة بيانات العناصر المظللة و المسبوقة ب *");

                }
            }catch (Exception e ){
                System.out.println("Error in Add Action ||| " + e.getMessage());
            }

        });

        // edit button
        item_edit_Btn.setOnAction(event -> {
            try {

                if (selected_Item != null){
                    Alert  before_edit_item_alert= new Alert(Alert.AlertType.WARNING,"تحذير سيتم تعديل بيانات هذا الصنف .. هل توافق ؟ " , ButtonType.CANCEL ,  ButtonType.OK);
                    Optional<ButtonType> buttonTypeOptional = before_edit_item_alert.showAndWait();
                    if (buttonTypeOptional.get() == ButtonType.CANCEL){
                        selected_Item = null ;
                        reset_data();
                    }
                    else if (buttonTypeOptional.get() == ButtonType.OK){
                        if (detectValidMainData()){

                            Item edited_item = new Item();
                            edited_item.setCode(selected_Item.getCode());
                            edited_item.setName(selected_Item.getName());
                            edited_item.setKind(item_kind_Comb.getValue());
                            edited_item.setCurrent_quantity(selected_Item.getCurrent_quantity());
                            edited_item.setFarmer_cash_price(Double.parseDouble(item_farmer_cash_price_TxtF.getText()));
                            edited_item.setTrade_cash_price(Double.parseDouble(item_trade_cash_price_TxtF.getText()));
                            edited_item.setFarmer_Installment_price(Double.parseDouble(item_farmer_Installment_price_TxtF.getText()));
                            edited_item.setTrade_Installment_price(Double.parseDouble(item_trade_Installment_price_TxtF.getText()));
                            edited_item.setBuy_price(Double.parseDouble(item_buy_price_TxtF.getText()));
                            edited_item.setQuantity_limit(Double.valueOf(item_quantity_limit_TxtF.getText().isEmpty() ? "0.00" :item_quantity_limit_TxtF.getText()));
                            edited_item.setNotes(item_notes_TxtF.getText().isEmpty() ? "" : item_notes_TxtF.getText());

                            if (items_dataManager.update_item(edited_item).equals(QueryState.success)) {
                                UIManager.showAlert("تم تعديل بيانات الصنف  بنجاح", AlertType.success, root, 0, 0);
                                Platform.runLater(() -> {
                                    loadItems();
                                    items_store_Table.refresh();
                                });
                                reset_data();
                                initialized_boxes_data();

                            } else {
                                UIManager.showAlert("تعذر تعديل بيانات الصنف تأكد من عدم تكرار الاسم", AlertType.error, root, 0, 0);
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
        /*   item_delete_Btn.setOnAction(event -> {
            try {

                if (selected_Item != null){
                    Alert  before_edit_item_alert= new Alert(Alert.AlertType.WARNING,"تحذير سيتم حذف بيانات هذا الصنف .. هل توافق ؟ " , ButtonType.CANCEL ,  ButtonType.OK);
                    Optional<ButtonType> buttonTypeOptional = before_edit_item_alert.showAndWait();
                    if (buttonTypeOptional.get() == ButtonType.CANCEL){
                        selected_Item = null ;
                        reset_data();
                    }
                    else if (buttonTypeOptional.get() == ButtonType.OK){

                            Item deleted_item = new Item();
                            deleted_item.setCode(selected_Item.getCode());

                            if (items_dataManager.delete_item(deleted_item).equals(QueryState.success)) {
                                UIManager.showAlert("تم حذف بيانات الصنف  بنجاح", AlertType.success, root, 0, 0);
                                Platform.runLater(() -> {
                                    loadItems();
                                    items_store_Table.refresh();
                                });
                                reset_data();
                                initialized_boxes_data();

                            } else {
                                UIManager.showAlert("تعذر حذف بيانات الصنف حدد و حاول مرة أخري ", AlertType.error, root, 0, 0);
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
            items_store_Table.getItems().forEach(item -> {
                item.getIsSelected_checkBox().setSelected(new_value);

            });
        });

        // print button
        item_print_Btn.setOnAction(event -> {
            try{
                String path = "Reports/public_reports/items.jrxml";
                collection_Data =
                        new JRBeanCollectionDataSource(items_store_Table.getItems().filtered(item -> item.getIsSelected_checkBox().isSelected()));


                // print function

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("img_param", logo_img_param_code);

                JasperDesign jd = JRXmlLoader.load(path);

                JasperReport js = JasperCompileManager.compileReport(jd);

                JasperPrint jp = JasperFillManager.fillReport(js, parameters, collection_Data);


                JasperViewer.viewReport(jp, false);
/*
                PrinterJob job = PrinterJob.getPrinterJob();
                int selectedService = 0;
                selectedService = 0;
                PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
                printRequestAttributeSet.add(OrientationRequested.PORTRAIT);
                printRequestAttributeSet.add(MediaSizeName.ISO_A0);
                MediaSizeName mediaSizeName = MediaSize.findMedia(64,25, MediaPrintableArea.MM);
                printRequestAttributeSet.add(mediaSizeName);
                printRequestAttributeSet.add(new Copies(1));
                JRPrintServiceExporter exporter;
                exporter = new JRPrintServiceExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
                exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.TRUE);
                exporter.exportReport();
                job.print(printRequestAttributeSet);
*/

                // another solution  ro print directly

               /*
                try {


                    PrinterJob printerJob = PrinterJob.getPrinterJob();

                    PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
                    printerJob.defaultPage(pageFormat);

                    int selectedService = 0;

                    // printerNameShort اسم الطابعه علي الجهاز
                     String  printerNameShort =  "printer name" ;
                    AttributeSet attributeSet = new HashPrintServiceAttributeSet(new PrinterName(printerNameShort, null));

                    PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, attributeSet);

                    try {
                        printerJob.setPrintService(printService[selectedService]);

                    } catch (Exception e) {

                        System.out.println(e);
                    }
                    JRPrintServiceExporter exporter;
                    PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
                    printRequestAttributeSet.add(MediaSizeName.NA_LETTER);
                    printRequestAttributeSet.add(new Copies(1));

                    // these are deprecated
                    exporter = new JRPrintServiceExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                    exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService[selectedService]);
                    exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printService[selectedService].getAttributes());
                    exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
                    exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                    exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                    exporter.exportReport();

                } catch (JRException e) {
                    e.printStackTrace();
                }

                */

            }catch (Exception e){
                System.out.println("Error In Print Items :: " + e.getMessage());
            }

        });

        // expire button
        item_expire_details_Btn.setOnAction(event -> {
            try {
                mainCont.setVisible(false);
                secondCont.setVisible(true);

                reset_expire_data();
                title_label.setText("متابعة تفاصيل الكميات و تواريخ إنتهاء الصلاحية لكل الأصناف الموجوده حاليا");

            }catch (Exception e){
                System.out.println("ERROR OCCURS ON item_expire_details_Btn ::: "+ e.getMessage());
            }
        });




    }

    private void filter_items() {

        Task task = new Task<Void>() {
            @Override
            public Void call() {

                if (items_list.size() > 0) {
                    filtered_items = items_list;
                    System.out.println("Filtered_items | Size | " + filtered_items.size());

                    //TODO|| 1- Filter by Item Code  ***********************************************************************
                    if (item_search_code_TxtF.getText().length() != 0 ) {
                        filtered_items = filtered_items.filtered
                                (item -> String.valueOf(item.getCode()).contains(item_search_code_TxtF.getText()));
                        System.out.println("Filter | item_search_code_TxtF");
                    }

                    //TODO|| 2- Filter by Item Name

                    if (item_search_name_Comb.getValue().length() != 0) {
                        filtered_items = filtered_items.filtered
                                (item -> replacedWordToArabic_Action(item.getName()).
                                        contains(replacedWordToArabic_Action(item_search_name_Comb.getValue())));
                        System.out.println("Filter | item_search_name_Comb");
                    }

                    System.out.println("Filtered_items | Size : " + filtered_items.size());
                    items_store_Table.setItems(filtered_items);
                    items_store_Table.refresh();
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
        item_search_code_TxtF.textProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            filter_items();
        });

        // For Name Search
        item_search_name_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            filter_items();
        });

    }

    //TODO| Second Class Functions (Expire Page ) *************************************************************************************************

    private void reset_expire_data() {
        // main components


        // search components
        expire_search_code_TxtF.setText("");
        expire_search_name_Comb.setValue("");

        // help component

        // load table
        loadExpireItems();


        expire_Table.refresh();

    }

    private void loadExpireItems() {
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
                expires_list = FXCollections.observableArrayList();
                expires_list = itemExpire_dataManger.load_items_Expire_data_list();

                Platform.runLater(() -> {
                    expire_Table.setItems(expires_list);
                    root.getChildren().remove(dark_pane);

                });
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();

    }

    private void setupExpireItemTable() {

        expire_item_index_Colo.setCellValueFactory(
                (TableColumn.CellDataFeatures<Expire_Details, String> $)
                        -> new ReadOnlyObjectWrapper(expire_Table.getItems().indexOf($.getValue()) + 1 + ""));

        expire_item_code_Colo.setCellValueFactory(new PropertyValueFactory<>("item_code"));
        expire_item_name_Colo.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        expire_item_kind_Colo.setCellValueFactory(new PropertyValueFactory<>("item_kind"));

        expire_item_quantity_Colo.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        expire_date_Colo.setCellValueFactory(new PropertyValueFactory<>("expire_date"));

        //TODO - setup limit Expired ***********************************************


        expire_Table.setRowFactory($ -> {


            TableRow<Expire_Details> row = new TableRow<Expire_Details>() {
                @Override
                protected void updateItem(Expire_Details expire_details, boolean empty) {
                    try {

                        super.updateItem(expire_details, empty);
                        if (expire_details != null) {

                            int months_diff = Period.between(
                                    LocalDate.now().withDayOfMonth(1) ,
                            LocalDate.parse(expire_details.getExpire_date().toString()).withDayOfMonth(1) ).getMonths();


                            int years_diff = Period.between(
                                    LocalDate.now().withDayOfMonth(1) ,
                                    LocalDate.parse(expire_details.getExpire_date().toString()).withDayOfMonth(1) ).getYears();


                            if (years_diff <= 0 ) {

                                if (months_diff < 3 ) {
                                    setStyle(UIManager.pink_background);

                                }
                                else setStyle("");

                            }
                            else setStyle("");
                        }

                    } catch (Exception e) {
                        System.out.println("error in row setupExpireTable ::: " + e.getMessage());
                    }

                }
            };

            return row;
        });


    }

    private  void setup_second_page_buttons(){


        // reset button
        expire_clean_Btn.setOnAction(event -> {
            reset_expire_data();
            initialized_boxes_data();

        });


        // back button
        expire_back_Btn.setOnAction(event -> {
            try {
                mainCont.setVisible(true);
                secondCont.setVisible(false);

                reset_data();

                title_label.setText("الأصناف ( إضافة صنف _ تعديل  بيانات صنف _ حذف بيانات صنف _ طباعة تقرير بالأصناف )");

            }catch (Exception e){
                System.out.println("ERROR OCCURS ON expire_back_Btn ::: "+ e.getMessage());
            }
        });




    }

    private void filter_expires() {

        Task task = new Task<Void>() {
            @Override
            public Void call() {

                if (expires_list.size() > 0) {
                    filtered_expires = expires_list;
                    System.out.println("Filtered_expires | Size | " + filtered_expires.size());

                    //TODO|| 1- Filter by Item Code  ***********************************************************************
                    if (expire_search_code_TxtF.getText().length() != 0 ) {
                        filtered_expires = filtered_expires.filtered
                                (expire_details -> String.valueOf(expire_details.getItem_code()).contains(expire_search_code_TxtF.getText()));
                        System.out.println("Filter | expire_search_code_TxtF");
                    }

                    //TODO|| 2- Filter by Item Name

                    if (expire_search_name_Comb.getValue().length() != 0) {
                        filtered_expires = filtered_expires.filtered
                                (expire_details -> replacedWordToArabic_Action(expire_details.getItem_name()).
                                        contains(replacedWordToArabic_Action(expire_search_name_Comb.getValue())));
                        System.out.println("Filter | expire_search_name_Comb");
                    }

                    System.out.println("Filtered_expires | Size : " + filtered_expires.size());
                    expire_Table.setItems(filtered_expires);
                    expire_Table.refresh();
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

    private void setup_secondFilterOptions() {

        //  For Code Search
        expire_search_code_TxtF.textProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            filter_expires();
        });

        // For Name Search
        expire_search_name_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            filter_expires();
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
        setup_second_page_buttons();
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
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
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

        loadItems();
    }

    @Override
    public void setup_view() {
        setupItemTable();
        setup_filterOptions();

        setupExpireItemTable();
        setup_secondFilterOptions();


    }

    @Override
    public void setup_menu_buttons() {

    }

}
