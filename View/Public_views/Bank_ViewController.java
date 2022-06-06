package View.Public_views;

import AppManager.UIManager;
import DB_Manager.Main_Data_Manager.Bank_DataManager;
import Interfaces.ViewControllerClass;
import Model.Enums.AlertType;
import Model.Enums.QueryState;
import Model.Main_Data.Item;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Bank_ViewController implements Initializable, ViewControllerClass {
    //TODO| Class Variables *************************************************************************************************

    Bank_DataManager bank_dataManager =  new Bank_DataManager();
    DecimalFormat df=new DecimalFormat("#.##");

    String logo_img_param_code = "Reports/report_images/pharma_logo_r.png";

    JRBeanCollectionDataSource collection_Data;
    ObservableList<Item> filtered_items = FXCollections.observableArrayList();



    //TODO| Design Elements *************************************************************************************************
    @FXML
    private AnchorPane root , labelCont , mainCont;

    @FXML
    private Label title_label;

    @FXML
    private ImageView with_store_down_image , with_store_up_image ,no_store_up_image , no_store_down_image;

    @FXML
    private JFXTextField actual_money_total_TxtF , with_store_profit_ratio_TxtF , clear_with_store_total_TxtF
            , export_remaining_total_TxtF , export_received_total_TxtF  , export_all_total_TxtF
            , import_remaining_total_TxtF , import_paid_total_TxtF , import_all_total_TxtF
            , earn_other_total_TxtF , earn_clients_total_TxtF , earn_all_total_TxtF
            , spend_other_total_TxtF , spend_companies_total_TxtF , spend_all_total_TxtF
            , actual_store_total_TxtF , no_store_profit_ratio_TxtF , clear_no_store_total_TxtF;

    @FXML
    private JFXButton bank_print_Btn;

    //TODO| This Class Functions *************************************************************************************************

    private void setup_Calculated_Values(){
        try {
            // for export bills
            export_all_total_TxtF.setText(df.format(bank_dataManager.calculate_export_all_total()));
            export_received_total_TxtF.setText(df.format(bank_dataManager.calculate_export_received_total()));
            export_remaining_total_TxtF.setText(df.format(bank_dataManager.calculate_export_remaining_total()));

            // for import bills
            import_all_total_TxtF.setText(df.format(bank_dataManager.calculate_import_all_total()));
            import_paid_total_TxtF.setText(df.format(bank_dataManager.calculate_import_paid_total()));
            import_remaining_total_TxtF.setText(df.format(bank_dataManager.calculate_import_remaining_total()));

            // for earn bills
            earn_all_total_TxtF.setText(df.format(bank_dataManager.calculate_earn_all_total()));
            earn_other_total_TxtF.setText(df.format(bank_dataManager.calculate_earn_other_total()));
            earn_clients_total_TxtF.setText(df.format(bank_dataManager.calculate_earn_clients_total()));

            // for spend bills
            spend_all_total_TxtF.setText(df.format(bank_dataManager.calculate_spend_all_total()));
            spend_other_total_TxtF.setText(df.format(bank_dataManager.calculate_spend_other_total()));
            spend_companies_total_TxtF.setText(df.format(bank_dataManager.calculate_spend_companies_total()));

            // for calculate clear with total
            double positive_clear_total =  ( bank_dataManager.calculate_export_all_total() + bank_dataManager.calculate_earn_other_total() +
                    (bank_dataManager.calculate_export_remaining_total() < 0.00 ? (bank_dataManager.calculate_export_remaining_total() * -1): 0.00  )        ) ;

            double negative_clear_total =  ( bank_dataManager.calculate_import_all_total() + bank_dataManager.calculate_spend_other_total() +
                    (bank_dataManager.calculate_import_remaining_total() < 0.00 ? (bank_dataManager.calculate_import_remaining_total() * -1): 0.00  )        ) ;

            double total_clear = positive_clear_total - negative_clear_total ;
            clear_with_store_total_TxtF.setText(df.format(total_clear));




            double profit = (total_clear /  positive_clear_total) * 100 ;

            if (profit >= 0.00){
                with_store_up_image.setVisible(true);
                with_store_down_image.setVisible(false);

            }else {
                with_store_up_image.setVisible(false);
                with_store_down_image.setVisible(true);

            }
            with_store_profit_ratio_TxtF.setText(" % " +df.format(profit));

            // for calculate clear no store total
            double positive_no_clear_total =  ( bank_dataManager.calculate_exported_sells_total() +bank_dataManager.calculate_import_returned_sells_total()
                    + bank_dataManager.calculate_earn_other_total() +
                    (bank_dataManager.calculate_export_remaining_total() < 0.00 ? (bank_dataManager.calculate_export_remaining_total() * -1): 0.00  )        ) ;

            double negative_no_clear_total =  ( bank_dataManager.calculate_export_returned_sells_total() + bank_dataManager.calculate_spend_other_total() +
                    (bank_dataManager.calculate_import_remaining_total() < 0.00 ? (bank_dataManager.calculate_import_remaining_total() * -1): 0.00  )        ) ;

            double total_no_clear = positive_no_clear_total - negative_no_clear_total ;
            clear_no_store_total_TxtF.setText(df.format(total_no_clear));




            double no_profit = (total_no_clear /  positive_no_clear_total) * 100 ;

            if (no_profit >= 0.00){
                no_store_up_image.setVisible(true);
                no_store_down_image.setVisible(false);

            }else {
                no_store_up_image.setVisible(false);
                no_store_down_image.setVisible(true);

            }
            no_store_profit_ratio_TxtF.setText(" % " +df.format(no_profit));

            // for calculate actual total
            double positive_actual_total =  ( bank_dataManager.calculate_export_received_total() + bank_dataManager.calculate_earn_other_total() ) ;

            double negative_actual_total =  ( bank_dataManager.calculate_import_paid_total() + bank_dataManager.calculate_spend_other_total()   ) ;

            double total_actual = positive_actual_total - negative_actual_total ;
            actual_money_total_TxtF.setText(df.format(total_actual));

            // for calculate store actual total
            double store_actual_total =  bank_dataManager.calculate_store_values_total() ;
            actual_store_total_TxtF.setText(df.format(store_actual_total));


        }catch (Exception e){
            System.out.println("Error occurs in setup_Calculated_Values :: " + e.getMessage());
        }
    }

    private  void setup_page_buttons(){

        // print button
        bank_print_Btn.setOnAction(event -> {
            try{
                String path = "Reports/public_reports/bank.jrxml";

                // print function

                collection_Data =
                        new JRBeanCollectionDataSource(filtered_items);

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("img_param", logo_img_param_code);

                parameters.put("first_total_profit", clear_no_store_total_TxtF.getText() );
                parameters.put("first_profit", no_store_profit_ratio_TxtF.getText() );
                parameters.put("store_total", actual_store_total_TxtF.getText());
                parameters.put("second_total_profit", clear_with_store_total_TxtF.getText());
                parameters.put("second_profit", with_store_profit_ratio_TxtF.getText());
                parameters.put("actual_total", actual_money_total_TxtF.getText());
                parameters.put("sale_total", export_all_total_TxtF.getText());
                parameters.put("sale_in", export_received_total_TxtF.getText());
                parameters.put("sale_out", export_remaining_total_TxtF.getText());
                parameters.put("buy_total", import_all_total_TxtF.getText());
                parameters.put("buy_in", import_paid_total_TxtF.getText());
                parameters.put("buy_out", import_remaining_total_TxtF.getText());
                parameters.put("earn_total", earn_all_total_TxtF.getText());
                parameters.put("earn_in", earn_clients_total_TxtF.getText());
                parameters.put("earn_out", earn_other_total_TxtF.getText());
                parameters.put("spend_total", spend_all_total_TxtF.getText());
                parameters.put("spend_in", spend_companies_total_TxtF.getText());
                parameters.put("spend_out", spend_other_total_TxtF.getText());

                JasperDesign jd = JRXmlLoader.load(path);

                JasperReport js = JasperCompileManager.compileReport(jd);

                JasperPrint jp = JasperFillManager.fillReport(js, parameters , collection_Data);


                JasperViewer.viewReport(jp, false);


            }catch (Exception e){
                System.out.println("Error In Print Items :: " + e.getMessage());
            }

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
        setup_Calculated_Values();
    }

    @Override
    public void setup_menu_buttons() {

    }

}
