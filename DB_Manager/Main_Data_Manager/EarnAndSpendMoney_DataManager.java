package DB_Manager.Main_Data_Manager;

import AppManager.UIManager;
import DB_Manager.Connect;
import Model.Enums.QueryState;
import Model.Main_Data.Earn_And_Spend_Bill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class EarnAndSpendMoney_DataManager {
    DecimalFormat df=new DecimalFormat("#.##");


    // TODO : For Earn And Spend Money Controller Purpose

    // For Add New Bill Operation To Bills DataBase
    public QueryState add_bill(Earn_And_Spend_Bill Earn_And_Spend_Bill) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;



        String query =
                "INSERT INTO `earn_and_spend_money`(`bill_operation_code`, `bill_code`, `bill_kind`, " +
                        "`bill_name`, `bill_value`, `bill_total`, `bill_date`, `bill_notes`) " ;

        query = query + "VALUES ( "+
                "'" + Earn_And_Spend_Bill.getOperation_code() + "', "+
                "'" + Earn_And_Spend_Bill.getCode() + "', "+
                "'" + Earn_And_Spend_Bill.getKind() + "', "+
                "'" + Earn_And_Spend_Bill.getName() + "', "+
                "'" + Earn_And_Spend_Bill.getValue() + "', "+
                "'" + Earn_And_Spend_Bill.getTotal() + "', "+
                "'" + Earn_And_Spend_Bill.getDate() + "', "+
                "'" + Earn_And_Spend_Bill.getNotes() + "' )";

        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
        }catch (SQLException e) {
            System.out.println("EarnAndSpendMoney_DataManager.add_bill() | Error | " + e.getMessage());
        }
        finally {
            if(missionState == QueryState.success){
                System.out.println("EarnAndSpendMoney_DataManager.add_bill() | Success");
            }
            try {
                // close connection
                System.out.println("EarnAndSpendMoney_DataManager.add_bill()| Connection | Close");
            }catch (Exception e){
                System.out.println("EarnAndSpendMoney_DataManager.add_bill() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }

    // For Delete Bill's Data From Bills DataBase
    public QueryState delete_bill(Earn_And_Spend_Bill Earn_And_Spend_Bill) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;

        String query =
                "DELETE FROM `earn_and_spend_money` WHERE  `bill_code` = '" + Earn_And_Spend_Bill.getCode() + "'";


        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
            System.out.println("EarnAndSpendMoney_DataManager.delete_bill() | Success | " +  Earn_And_Spend_Bill.getName());
        }catch (SQLException e) {
            System.out.println("EarnAndSpendMoney_DataManager.delete_bill() | Error | " + e.getMessage());
        }
        finally {
            try{
                // close connection
                System.out.println("EarnAndSpendMoney_DataManager.delete_bill() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("EarnAndSpendMoney_DataManager.delete_bill() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }

    // For Load All Operation Bills From Bills DataBase
    public ObservableList<Earn_And_Spend_Bill> load_operations_data_list() {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<Earn_And_Spend_Bill> op_list = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM `earn_and_spend_money` " +
                "ORDER BY `bill_date` DESC , `bill_code` DESC ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                Earn_And_Spend_Bill earn_and_spend_bill = new Earn_And_Spend_Bill();

                earn_and_spend_bill.setOperation_code(rs.getInt("bill_operation_code"));
                earn_and_spend_bill.setCode(rs.getInt("bill_code"));

                earn_and_spend_bill.setKind(rs.getString("bill_kind"));
                earn_and_spend_bill.setName(rs.getString("bill_name"));
                earn_and_spend_bill.setNotes(rs.getString("bill_notes"));
                earn_and_spend_bill.setValue(Double.parseDouble(df.format(rs.getDouble("bill_value"))));
                earn_and_spend_bill.setTotal(Double.parseDouble(df.format(rs.getDouble("bill_total"))));
                earn_and_spend_bill.setDate(rs.getDate("bill_date"));
                earn_and_spend_bill.getIsSelected_checkBox().setSelected(true);


                op_list.add(earn_and_spend_bill);
            }
            System.out.println("EarnAndSpendMoney_DataManager.load_operations_data_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("EarnAndSpendMoney_DataManager.load_operations_data_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("EarnAndSpendMoney_DataManager.load_operations_data_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("EarnAndSpendMoney_DataManager.load_operations_data_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }

    // For Detect Bill Is Exist Or Not On import company bills DataBase
    public boolean detect_earn_spend_bill_next_code(int code_num) {
        boolean code_status = false ;

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        String query = "SELECT EXISTS(SELECT * FROM earn_and_spend_money WHERE bill_code = "+ code_num + " LIMIT 1 ) AS code_status" ;

        try {

            rs = c.stmt.executeQuery(query);
            while (rs.next()){
                code_status = rs.getBoolean("code_status") ;
            }
            System.out.println("EarnAndSpendMoney_DataManager.detect_earn_spend_bill_next_code() | Size " + code_status) ;

        } catch (Exception e){
            System.out.println("EarnAndSpendMoney_DataManager.detect_earn_spend_bill_next_code() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("EarnAndSpendMoney_DataManager.detect_earn_spend_bill_next_code() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("EarnAndSpendMoney_DataManager.detect_earn_spend_bill_next_code() | Connection | " + e.getMessage());
            }
            return code_status;
        }
    }

    // TODO : For Other Controllers Purpose

    // For Load All Operation Bills From Spend Bills DataBase For Detected Company
    public ObservableList<Earn_And_Spend_Bill> load_company_spend_operations_data_list(String bill_name) {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<Earn_And_Spend_Bill> op_list = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM `earn_and_spend_money`" +
                "WHERE `bill_kind` LIKE 'صرف لشركة' AND `bill_name` LIKE '" + bill_name +"' "+
                "ORDER BY `bill_date` DESC , `bill_operation_code` DESC ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                Earn_And_Spend_Bill earn_and_spend_bill = new Earn_And_Spend_Bill();

                earn_and_spend_bill.setOperation_code(rs.getInt("bill_operation_code"));
                earn_and_spend_bill.setCode(rs.getInt("bill_code"));

                earn_and_spend_bill.setKind(rs.getString("bill_kind"));
                earn_and_spend_bill.setName(rs.getString("bill_name"));
                earn_and_spend_bill.setNotes(rs.getString("bill_notes"));
                earn_and_spend_bill.setValue(Double.parseDouble(df.format(rs.getDouble("bill_value"))));
                earn_and_spend_bill.setTotal(Double.parseDouble(df.format(rs.getDouble("bill_total"))));
                earn_and_spend_bill.setDate(rs.getDate("bill_date"));
                earn_and_spend_bill.getIsSelected_checkBox().setSelected(true);


                op_list.add(earn_and_spend_bill);
            }
            System.out.println("EarnAndSpendMoney_DataManager.load_operations_data_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("EarnAndSpendMoney_DataManager.load_operations_data_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("EarnAndSpendMoney_DataManager.load_operations_data_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("EarnAndSpendMoney_DataManager.load_operations_data_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }

    // For Load All Operation Bills From Earn Bills DataBase For Detected Client
    public ObservableList<Earn_And_Spend_Bill> load_client_earn_operations_data_list(String bill_name) {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<Earn_And_Spend_Bill> op_list = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM `earn_and_spend_money`" +
                "WHERE `bill_kind` LIKE 'تحصيل من عميل' AND `bill_name` LIKE '" + bill_name +"' "+
                "ORDER BY `bill_date` DESC , `bill_code` DESC ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                Earn_And_Spend_Bill earn_and_spend_bill = new Earn_And_Spend_Bill();

                earn_and_spend_bill.setOperation_code(rs.getInt("bill_operation_code"));
                earn_and_spend_bill.setCode(rs.getInt("bill_code"));

                earn_and_spend_bill.setKind(rs.getString("bill_kind"));
                earn_and_spend_bill.setName(rs.getString("bill_name"));
                earn_and_spend_bill.setNotes(rs.getString("bill_notes"));
                earn_and_spend_bill.setValue(Double.parseDouble(df.format(rs.getDouble("bill_value"))));
                earn_and_spend_bill.setTotal(Double.parseDouble(df.format(rs.getDouble("bill_total"))));
                earn_and_spend_bill.setDate(rs.getDate("bill_date"));
                earn_and_spend_bill.getIsSelected_checkBox().setSelected(true);


                op_list.add(earn_and_spend_bill);
            }
            System.out.println("EarnAndSpendMoney_DataManager.load_operations_data_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("EarnAndSpendMoney_DataManager.load_operations_data_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("EarnAndSpendMoney_DataManager.load_operations_data_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("EarnAndSpendMoney_DataManager.load_operations_data_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }


}
