package View.Public_views;

import AppManager.UIManager;
import DB_Manager.Main_Data_Manager.ItemMove_DataManger;
import DB_Manager.Main_Data_Manager.Items_DataManager;
import Interfaces.ViewControllerClass;
import Model.Enums.AlertType;
import Model.Enums.QueryState;
import Model.Main_Data.Item;
import Model.Main_Data.Item_Move;
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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
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
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class Items_Move_ViewController implements Initializable, ViewControllerClass {
    //TODO| Class Variables *************************************************************************************************


    Items_DataManager items_dataManager = new Items_DataManager();
    ItemMove_DataManger itemMove_dataManger = new ItemMove_DataManger();

    ObservableList<Item_Move> move_items_list = FXCollections.observableArrayList();
    ObservableList<Item_Move> filtered_move_items = FXCollections.observableArrayList();


    String logo_img_param_code = "Reports/report_images/pharma_logo_r.png";

    JRBeanCollectionDataSource collection_Data;


    //TODO| Design Elements *************************************************************************************************

    @FXML
    private AnchorPane root, labelCont, mainCont;

    @FXML
    private Label title_label;



    @FXML
    private JFXComboBox<String> itemMove_search_name_Comb , itemMove_search_envoyName_Comb , itemMove_search_moveKind_Comb
            ,itemMove_search_clientName_Comb ;


    @FXML
    private JFXDatePicker itemMove_search_fromDate_Picker , itemMove_search_toDate_Picker;


    @FXML
    private TableView<Item_Move> itemMove_Table;

    @FXML // checkbox
    private TableColumn<Item_Move, JFXCheckBox> itemMove_select_Colo;

    @FXML // int
    private TableColumn<Item_Move, Integer> itemMove_code_Colo , itemMove_billCode_Colo;

    @FXML // string
    private TableColumn<Item_Move, String> itemMove_index_Colo, itemMove_name_Colo , itemMove_moveKind_Colo , itemMove_kind_Colo
            , itemMove_envoyName_Colo , itemMove_clientName_Colo;

    @FXML   // double
    private TableColumn<Item_Move, Double> itemMove_quantity_Colo , itemMove_price_Colo  , itemMove_buy_price_Colo;


    @FXML // date
    private TableColumn<Item_Move, Date> itemMove_date_Colo , itemMove_expire_date_Colo;


    // view buttons
    @FXML
    private JFXButton itemMove_print_Btn , clean_Btn;

    @FXML
    private JFXCheckBox select_all_Checkbox;

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

        // For Move Kind List Data
        ObservableList<String> Move_kinds_list = FXCollections.observableArrayList();
        Move_kinds_list.addAll("وارد", "صادر" , "مرتجع شركة", "مرتجع عميل"   );
        itemMove_search_moveKind_Comb.setItems(Move_kinds_list);
        itemMove_search_moveKind_Comb.setValue("");
        TextFields.bindAutoCompletion(itemMove_search_moveKind_Comb.getEditor(), itemMove_search_moveKind_Comb.getItems());


        // For Items Names
        ObservableList<String> Items_names_list = FXCollections.observableArrayList();
        Items_names_list.addAll(items_dataManager.load_items_names_list());
        itemMove_search_name_Comb.setItems(Items_names_list);
        itemMove_search_name_Comb.setValue("");
        TextFields.bindAutoCompletion(itemMove_search_name_Comb.getEditor(), itemMove_search_name_Comb.getItems());

        // For Envoy Names
        ObservableList<String> envoy_names_list = FXCollections.observableArrayList();
        envoy_names_list.addAll(itemMove_dataManger.load_envoy_names_list());
        itemMove_search_envoyName_Comb.setItems(envoy_names_list);
        itemMove_search_envoyName_Comb.setValue("");
        TextFields.bindAutoCompletion(itemMove_search_envoyName_Comb.getEditor(), itemMove_search_envoyName_Comb.getItems());

        // For Clients Names
        ObservableList<String> clients_names_list = FXCollections.observableArrayList();
        clients_names_list.addAll(itemMove_dataManger.load_client_names_list());
        itemMove_search_clientName_Comb.setItems(clients_names_list);
        itemMove_search_clientName_Comb.setValue("");
        TextFields.bindAutoCompletion(itemMove_search_clientName_Comb.getEditor(), itemMove_search_clientName_Comb.getItems());


        // Table Style
        itemMove_Table.getStylesheets().add("/Assets/Custom_CSS/custom_table_font.css");

        // Boxes Style
        itemMove_search_clientName_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 19  ;  -fx-font-weight:bold");
        itemMove_search_envoyName_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 19  ;  -fx-font-weight:bold");
        itemMove_search_moveKind_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 19  ;  -fx-font-weight:bold");
        itemMove_search_name_Comb.setStyle("-fx-background-color: #f1f1f1 ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 19  ;  -fx-font-weight:bold");

        // Pickers Style
        itemMove_search_fromDate_Picker.setStyle("-fx-background-color: #ffffff ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 19  ;  -fx-font-weight:bold");
        itemMove_search_toDate_Picker.setStyle("-fx-background-color: #ffffff ; -fx-border-color :  black ;-fx-font-family: Cambria ;-fx-font-size: 19  ;  -fx-font-weight:bold");

    }

    private void reset_data() {
        // main components

        itemMove_search_name_Comb.setValue("");
        itemMove_search_envoyName_Comb.setValue("");
        itemMove_search_clientName_Comb.setValue("");
        itemMove_search_moveKind_Comb.setValue("");

        itemMove_search_fromDate_Picker.setValue(null);
        itemMove_search_toDate_Picker.setValue(null);

        // help component
        select_all_Checkbox.setSelected(true);

        // load table
        loadItemsMove();


        itemMove_Table.refresh();


    }

    private void loadItemsMove() {
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
                move_items_list = FXCollections.observableArrayList();
                move_items_list = itemMove_dataManger.load_items_Move_data_list();

                Platform.runLater(() -> {
                    itemMove_Table.setItems(move_items_list);
                    root.getChildren().remove(dark_pane);

                });
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();

    }

    private void setupItemTable() {

        itemMove_index_Colo.setCellValueFactory(
                (TableColumn.CellDataFeatures<Item_Move, String> $)
                        -> new ReadOnlyObjectWrapper(itemMove_Table.getItems().indexOf($.getValue()) + 1 + ""));
        itemMove_select_Colo.setCellValueFactory(new PropertyValueFactory<>("isSelected_checkBox"));
        itemMove_code_Colo.setCellValueFactory(new PropertyValueFactory<>("item_code"));
        itemMove_name_Colo.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        itemMove_moveKind_Colo.setCellValueFactory(new PropertyValueFactory<>("kind"));
        itemMove_billCode_Colo.setCellValueFactory(new PropertyValueFactory<>("bill_code"));
        itemMove_quantity_Colo.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        itemMove_kind_Colo.setCellValueFactory(new PropertyValueFactory<>("item_kind"));
        itemMove_price_Colo.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemMove_buy_price_Colo.setCellValueFactory(new PropertyValueFactory<>("buy_price"));
        itemMove_envoyName_Colo.setCellValueFactory(new PropertyValueFactory<>("envoy_name"));
        itemMove_date_Colo.setCellValueFactory(new PropertyValueFactory<>("date"));
        itemMove_clientName_Colo.setCellValueFactory(new PropertyValueFactory<>("client_name"));
        itemMove_expire_date_Colo.setCellValueFactory(new PropertyValueFactory<>("expire_date"));


        itemMove_Table.setRowFactory($ -> {


            TableRow<Item_Move> row = new TableRow<Item_Move>() {
                @Override
                protected void updateItem(Item_Move item, boolean empty) {
                    try {

                        super.updateItem(item, empty);
                        if (item != null) {
                            if (item.getKind().equals("وارد")) {
                                setStyle(UIManager.lightRed_background);
                            } else if (item.getKind().equals("صادر")) {

                                setStyle(UIManager.lightTeal_background);

                            } else if (item.getKind().equals("مرتجع عميل")) {
                                setStyle(UIManager.blue_background);

                            }
                            else if (item.getKind().equals("مرتجع شركة")) {
                                setStyle(UIManager.yellow_background);

                            }else setStyle("");
                        }

                    } catch (Exception e) {
                        System.out.println("error in row inttiallize ::: " + e.getMessage());
                    }

                }
            };

            return row;
        });


        }

    private  void setup_page_buttons(){


        // reset button
        clean_Btn.setOnAction(event -> {
            reset_data();
            initialized_boxes_data();

        });

        // select all and unselect all check action
        select_all_Checkbox.selectedProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            itemMove_Table.getItems().forEach(item -> {
                item.getIsSelected_checkBox().setSelected(new_value);

            });
        });

        // print button
        itemMove_print_Btn.setOnAction(event -> {
            try{
                String path = "Reports/public_reports/item_moves.jrxml";
                collection_Data =
                        new JRBeanCollectionDataSource(itemMove_Table.getItems().filtered(item_move -> item_move.getIsSelected_checkBox().isSelected()));


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

    private void filter_move_items() {

        Task task = new Task<Void>() {
            @Override
            public Void call() {

                if (move_items_list.size() > 0) {
                    filtered_move_items = move_items_list;
                    System.out.println("Filtered_Move_Items | Size | " + filtered_move_items.size());

                    //  1- Filter by Item Name

                    if (itemMove_search_name_Comb.getValue().length() != 0) {
                        filtered_move_items = filtered_move_items.filtered
                                (item_move -> replacedWordToArabic_Action(item_move.getItem_name()).
                                        contains(replacedWordToArabic_Action(itemMove_search_name_Comb.getValue())));
                        System.out.println("Filter | itemMove_search_name_Comb");
                    }

                    //  2- Filter by Envoy Name

                    if (itemMove_search_envoyName_Comb.getValue().length() != 0) {
                        filtered_move_items = filtered_move_items.filtered
                                (item_move -> replacedWordToArabic_Action(item_move.getEnvoy_name()).
                                        contains(replacedWordToArabic_Action(itemMove_search_envoyName_Comb.getValue())));
                        System.out.println("Filter | itemMove_search_envoyName_Comb");
                    }

                    //  3- Filter by Move  Kind

                    if (itemMove_search_moveKind_Comb.getValue().length() != 0) {
                        filtered_move_items = filtered_move_items.filtered
                                (item_move -> replacedWordToArabic_Action(item_move.getKind()).
                                        contains(replacedWordToArabic_Action(itemMove_search_moveKind_Comb.getValue())));
                        System.out.println("Filter | itemMove_search_moveKind_Comb");
                    }

                    //  4- Filter by Client Name

                    if (itemMove_search_clientName_Comb.getValue().length() != 0) {
                        filtered_move_items = filtered_move_items.filtered
                                (item_move -> replacedWordToArabic_Action(item_move.getClient_name()).
                                        contains(replacedWordToArabic_Action(itemMove_search_clientName_Comb.getValue())));
                        System.out.println("Filter | itemMove_search_clientName_Comb");
                    }

                            //  5- Filter by From Date and To Date Together

                    if (itemMove_search_fromDate_Picker.getValue() != null && itemMove_search_toDate_Picker.getValue() != null) {
                        filtered_move_items = filtered_move_items.filtered
                                (item_move ->
                                        !UIManager.convertToDate(itemMove_search_fromDate_Picker.getValue()).after(item_move.getDate()) &&
                                                !UIManager.convertToDate(itemMove_search_toDate_Picker.getValue()).before(item_move.getDate()) );
                        System.out.println("Filter | From Date and To Date Together");
                    }
                           //  6- Filter by From Date
                        else  if (itemMove_search_fromDate_Picker.getValue() != null && itemMove_search_toDate_Picker.getValue() == null) {
                            filtered_move_items = filtered_move_items.filtered
                                    (item_move ->
                                            !UIManager.convertToDate(itemMove_search_fromDate_Picker.getValue()).after(item_move.getDate()) );
                            System.out.println("Filter | itemMove_search_fromDate_Picker");
                        }
                         //  7- Filter by To Date
                       else if (itemMove_search_fromDate_Picker.getValue() == null && itemMove_search_toDate_Picker.getValue() != null) {
                            filtered_move_items = filtered_move_items.filtered
                                    (item_move ->
                                            !UIManager.convertToDate(itemMove_search_toDate_Picker.getValue()).before(item_move.getDate()) );
                            System.out.println("Filter | itemMove_search_toDate_Picker");
                        }

                    System.out.println("Filtered_Move_Items | Size : " + filtered_move_items.size());
                    itemMove_Table.setItems(filtered_move_items);
                    itemMove_Table.refresh();
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

        // For Name Search
        itemMove_search_name_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            filter_move_items();
        });

        // For Envoy Name Search
        itemMove_search_envoyName_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            filter_move_items();
        });

        // For Kind  Search
        itemMove_search_moveKind_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            filter_move_items();
        });

        // For Client Name Search
        itemMove_search_clientName_Comb.valueProperty().addListener(($, old_value, new_value) -> {
            System.out.println("New value | " + new_value);
            filter_move_items();
        });

        //TODO | DatePickers

        ObservableList<JFXDatePicker> pickers = FXCollections.observableArrayList();
        pickers.addAll(itemMove_search_fromDate_Picker, itemMove_search_toDate_Picker);
        pickers.forEach(picker -> {
            picker.valueProperty().addListener(($, old_value, new_value) -> {
                System.out.println("New value | " + new_value);
                filter_move_items();
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

        loadItemsMove();
    }

    @Override
    public void setup_view() {

        setupItemTable();
        setup_filterOptions();

    }
    @Override
    public void setup_menu_buttons(){

    }





}
