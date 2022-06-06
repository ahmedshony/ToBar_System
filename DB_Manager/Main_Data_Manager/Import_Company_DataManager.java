package DB_Manager.Main_Data_Manager;

import DB_Manager.Connect;
import Model.Enums.QueryState;
import Model.Main_Data.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;


public class Import_Company_DataManager {

    DecimalFormat df=new DecimalFormat("#.##");

    // TODO : For Company Controller Purpose

    // For Add New Company To companies DataBase
    public QueryState add_company(Company company) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;



        String query =
                "INSERT INTO `companies`(`company_code`, `company_name`, `company_address`," +
                        " `company_phone`, `company_current_balance`, `company_balance_paid`, `company_balance_remaining`)" ;

        query = query + "VALUES ( "+
                "'" + company.getCode() + "', "+
                "'" + company.getName() + "', "+
                "'" + company.getAddress() + "', "+
                "'" + company.getPhone() + "', "+
                "'" + company.getCurrent_balance() + "', "+
                "'" + company.getBalance_paid() + "', "+
                "'" + company.getBalance_remaining() + "' )";

        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
        }catch (SQLException e) {
            System.out.println("Import_Company_DataManager.add_company() | Error | " + e.getMessage());
        }
        finally {
            if(missionState == QueryState.success){
                System.out.println("Import_Company_DataManager.add_company() | Success");
            }
            try {
                // close connection
                System.out.println("Import_Company_DataManager.add_company()| Connection | Close");
            }catch (Exception e){
                System.out.println("Import_Company_DataManager.add_company() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }

    // For Update Company's Data From companies DataBase
    public QueryState update_company(Company company) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;

        String query =
                "UPDATE `companies` " +
                        " SET "+
                        " `companies`.`company_name` = '" + company.getName() +"' , "+
                        " `companies`.`company_address` = '" + company.getAddress() +"' , "+
                        " `companies`.`company_phone` = '" + company.getPhone() +"' , "+
                        " `companies`.`company_current_balance` = '" + company.getCurrent_balance() +"' , "+
                        " `companies`.`company_balance_paid` = '" + company.getBalance_paid() +"' , "+
                        " `companies`.`company_balance_remaining` = '" + company.getBalance_remaining() +"'";

                        query = query +
                "WHERE " +
                "`companies`.`company_code` = " + "'" + company.getCode() + "'";

        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
            System.out.println("Import_Company_DataManager.update_company() | Success | " );
        }catch (SQLException e) {
            System.out.println("Import_Company_DataManager.update_company() | Error | " + e.getMessage());
        }
        finally {
            try{
                // close connection
                System.out.println("Import_Company_DataManager.update_company() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Import_Company_DataManager.update_company() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }

    // For Delete Company's Data From companies DataBase
    public QueryState delete_company(Company company) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;

        String query =
                "DELETE FROM `companies` WHERE  `company_code` = '" + company.getCode() + "'";


        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
            System.out.println("Import_Company_DataManager.delete_company() | Success | " );
        }catch (SQLException e) {
            System.out.println("Import_Company_DataManager.delete_company() | Error | " + e.getMessage());
        }
        finally {
            try{
                // close connection
                System.out.println("Import_Company_DataManager.delete_company() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Import_Company_DataManager.delete_company() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }

    // For Load All Companies From companies DataBase
    public ObservableList<Company> load_companies_data_list() {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<Company> op_list = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM `companies` " +
                "ORDER BY `company_code` ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                Company company = new Company();

                company.setCode(rs.getInt("company_code"));
                company.setName(rs.getString("company_name"));
                company.setAddress(rs.getString("company_address"));
                company.setPhone(rs.getString("company_phone"));
                company.setCurrent_balance(Double.parseDouble(df.format(rs.getDouble("company_current_balance"))));
                company.setBalance_paid(Double.parseDouble(df.format(rs.getDouble("company_balance_paid"))));
                company.setBalance_remaining(Double.parseDouble(df.format(rs.getDouble("company_balance_remaining"))));
                company.getIsSelected_checkBox().setSelected(true);


                op_list.add(company);
            }
            System.out.println("Import_Company_DataManager.load_companies_data_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("Import_Company_DataManager.load_companies_data_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Import_Company_DataManager.load_companies_data_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Import_Company_DataManager.load_companies_data_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }

    // For Load All Companies Names From companies DataBase
    public ObservableList<String> load_companies_names_list() {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<String> op_list = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM `companies` " +
                "ORDER BY `company_code` ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
              String name ="";
              name = rs.getString("company_name") ;


                op_list.add(name);
            }
            System.out.println("Import_Company_DataManager.load_companies_names_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("Import_Company_DataManager.load_companies_names_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Import_Company_DataManager.load_companies_names_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Import_Company_DataManager.load_companies_names_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }


    /**********************************************************************/

    // TODO : For Import Bill Controller Purpose

    // For Detect Bill Is Exist Or Not On import company bills DataBase
    public boolean detect_import_bill_next_code(int code_num) {
        boolean code_status = false ;

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        String query = "SELECT EXISTS(SELECT * FROM import_company_bills WHERE import_code = "+ code_num + " LIMIT 1 ) AS code_status" ;

        try {

            rs = c.stmt.executeQuery(query);
            while (rs.next()){
                code_status = rs.getBoolean("code_status") ;
            }
            System.out.println("Import_Company_DataManager.detect_import_bill_next_code() | Size " + code_status) ;

        } catch (Exception e){
            System.out.println("Import_Company_DataManager.detect_import_bill_next_code() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Import_Company_DataManager.detect_import_bill_next_code() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Import_Company_DataManager.detect_import_bill_next_code() | Connection | " + e.getMessage());
            }
            return code_status;
        }
    }

    // For Add New Import Company Bill To import company bills DataBase
    public QueryState add_imported_bill(Import_Bill import_bill) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;

        String query =
                "INSERT INTO `import_company_bills`(`import_bill_item_code`, `import_code`, `import_bill_kind` , `import_pay_kind`," +
                        " `import_date`, `import_company_code`, `import_company_name`, " +
                        "`import_item_code`, `import_item_name`, `import_item_kind`," +
                        " `import_item_count`, `import_item_price`, `import_item_total_price` , `import_item_expire_date`, " +
                        "`import_bill_total_price`, `import_bill_total_discount`, `import_bill_total_paid`, " +
                        "`import_bill_final_total`, `import_bill_receipt_number`)" ;

        query = query + "VALUES ( "+
                "'" + import_bill.getBill_item_code() + "', "+
                "'" + import_bill.getCode() + "', "+
                "'" + import_bill.getBill_kind() + "', "+
                "'" + import_bill.getPay_kind() + "', "+
                "'" + import_bill.getDate() + "', "+
                "'" + import_bill.getCompany_code() + "', "+
                "'" + import_bill.getCompany_name() + "', "+
                "'" + import_bill.getItem_code() + "', "+
                "'" + import_bill.getItem_name() + "', "+
                "'" + import_bill.getItem_kind() + "', "+
                "'" + import_bill.getItem_count() + "', "+
                "'" + import_bill.getItem_price() + "', "+
                "'" + import_bill.getItem_total_price() + "', "+
                "'" + import_bill.getExpire_date() + "', "+
                "'" + import_bill.getBill_total_price() + "', "+
                "'" + import_bill.getBill_total_discount() + "', "+
                "'" + import_bill.getBill_total_paid() + "', "+
                "'" + import_bill.getBill_final_total() + "', "+
                "'" + import_bill.getBill_receipt_number() + "' )";

        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
        }catch (SQLException e) {
            System.out.println("Import_Company_DataManager.add_imported_bill() | Error | " + e.getMessage());
        }
        finally {
            if(missionState == QueryState.success){
                System.out.println("Import_Company_DataManager.add_imported_bill() | Success");
            }
            try {
                // close connection
                System.out.println("Import_Company_DataManager.add_imported_bill()| Connection | Close");
            }catch (Exception e){
                System.out.println("Import_Company_DataManager.add_imported_bill() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }


    // For Return  Company BY His Name From Companies DataBase
    public Company load_company_by_name(String name) {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        Company  company =new Company();
        String query = "SELECT * " +
                "FROM `companies` " +
                "WHERE  `company_name` LIKE '" + name +"'";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {

                company.setCode(rs.getInt("company_code"));
                company.setName(rs.getString("company_name"));
                company.setAddress(rs.getString("company_address"));
                company.setPhone(rs.getString("company_phone"));
                company.setCurrent_balance(Double.parseDouble(df.format(rs.getDouble("company_current_balance"))));
                company.setBalance_paid(Double.parseDouble(df.format(rs.getDouble("company_balance_paid"))));
                company.setBalance_remaining(Double.parseDouble(df.format(rs.getDouble("company_balance_remaining"))));
                company.getIsSelected_checkBox().setSelected(true);


            }
            System.out.println("Import_Company_DataManager.load_company_by_name() | Size " + company.getName()) ;

        } catch (Exception e){
            System.out.println("Import_Company_DataManager.load_company_by_name() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Import_Company_DataManager.load_company_by_name() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Import_Company_DataManager.load_company_by_name() | Connection | " + e.getMessage());
            }
            return company;
        }
    }


    // For Load All Imported Bills From Import Company Bills DataBase
    public ObservableList<Import_Bill> load_imported_bills_data_list() {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<Import_Bill> op_list = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM `import_company_bills` " +
                "ORDER BY `import_date`  ,`import_bill_item_code` DESC ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                Import_Bill import_bill = new Import_Bill();

                import_bill.setBill_item_code(rs.getInt("import_bill_item_code"));
                import_bill.setCode(rs.getInt("import_code"));
                import_bill.setCompany_code(rs.getInt("import_company_code"));
                import_bill.setItem_code(rs.getInt("import_item_code"));
                import_bill.setItem_count(rs.getDouble("import_item_count"));

                import_bill.setBill_kind(rs.getString("import_bill_kind"));
                import_bill.setPay_kind(rs.getString("import_pay_kind"));
                import_bill.setCompany_name(rs.getString("import_company_name"));
                import_bill.setItem_name(rs.getString("import_item_name"));
                import_bill.setItem_kind(rs.getString("import_item_kind"));
                import_bill.setBill_receipt_number(rs.getString("import_bill_receipt_number"));

                import_bill.setItem_price(Double.parseDouble(df.format(rs.getDouble("import_item_price"))));
                import_bill.setItem_total_price(Double.parseDouble(df.format(rs.getDouble("import_item_total_price"))));
                import_bill.setBill_total_price(Double.parseDouble(df.format(rs.getDouble("import_bill_total_price"))));
                import_bill.setBill_total_discount(Double.parseDouble(df.format(rs.getDouble("import_bill_total_discount"))));
                import_bill.setBill_total_paid(Double.parseDouble(df.format(rs.getDouble("import_bill_total_paid"))));
                import_bill.setBill_final_total(Double.parseDouble(df.format(rs.getDouble("import_bill_final_total"))));

                import_bill.setDate(rs.getDate("import_date"));
                import_bill.setExpire_date(rs.getDate("import_item_expire_date"));

                import_bill.getIsSelected_checkBox().setSelected(true);



                op_list.add(import_bill);
            }
            System.out.println("Import_Company_DataManager.load_imported_bills_data_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("Import_Company_DataManager.load_imported_bills_data_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Import_Company_DataManager.load_imported_bills_data_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Import_Company_DataManager.load_imported_bills_data_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }

    // For Delete Bill's Data From Imported Bills  DataBase
    public QueryState delete_bill(Import_Bill import_bill) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;

        String query =
                "DELETE FROM `import_company_bills` WHERE  `import_code` = '" + import_bill.getCode() + "'";


        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
            System.out.println("Import_Company_DataManager.delete_bill() | Success | " );
        }catch (SQLException e) {
            System.out.println("Import_Company_DataManager.delete_bill() | Error | " + e.getMessage());
        }
        finally {
            try{
                // close connection
                System.out.println("Import_Company_DataManager.delete_bill() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Import_Company_DataManager.delete_bill() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }


    // For Load All Imported Bills From Import Company Bills DataBase To Detected Company
    public ObservableList<Import_Bill> load_company_imported_bills_data_list(int company_code) {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<Import_Bill> op_list = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM `import_company_bills` " +
                "WHERE `import_company_code` = "+ company_code+
                " ORDER BY `import_date`  ,`import_bill_item_code` DESC ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                Import_Bill import_bill = new Import_Bill();

                import_bill.setBill_item_code(rs.getInt("import_bill_item_code"));
                import_bill.setCode(rs.getInt("import_code"));
                import_bill.setCompany_code(rs.getInt("import_company_code"));
                import_bill.setItem_code(rs.getInt("import_item_code"));
                import_bill.setItem_count(rs.getDouble("import_item_count"));

                import_bill.setBill_kind(rs.getString("import_bill_kind"));
                import_bill.setPay_kind(rs.getString("import_pay_kind"));
                import_bill.setCompany_name(rs.getString("import_company_name"));
                import_bill.setItem_name(rs.getString("import_item_name"));
                import_bill.setItem_kind(rs.getString("import_item_kind"));
                import_bill.setBill_receipt_number(rs.getString("import_bill_receipt_number"));

                import_bill.setItem_price(Double.parseDouble(df.format(rs.getDouble("import_item_price"))));
                import_bill.setItem_total_price(Double.parseDouble(df.format(rs.getDouble("import_item_total_price"))));
                import_bill.setBill_total_price(Double.parseDouble(df.format(rs.getDouble("import_bill_total_price"))));
                import_bill.setBill_total_discount(Double.parseDouble(df.format(rs.getDouble("import_bill_total_discount"))));
                import_bill.setBill_total_paid(Double.parseDouble(df.format(rs.getDouble("import_bill_total_paid"))));
                import_bill.setBill_final_total(Double.parseDouble(df.format(rs.getDouble("import_bill_final_total"))));

                import_bill.setDate(rs.getDate("import_date"));
                import_bill.setDate(rs.getDate("import_item_expire_date"));

                import_bill.getIsSelected_checkBox().setSelected(true);



                op_list.add(import_bill);
            }
            System.out.println("Import_Company_DataManager.load_imported_bills_data_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("Import_Company_DataManager.load_imported_bills_data_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Import_Company_DataManager.load_imported_bills_data_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Import_Company_DataManager.load_imported_bills_data_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }



}
