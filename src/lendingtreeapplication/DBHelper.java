/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendingtreeapplication;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author prith
 */
public class DBHelper {

    DBUtility dBUtility = new DBUtility();

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "LENDING_TREE";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public boolean checkDBExists() {

        boolean result = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            ResultSet resultSet = conn.getMetaData().getCatalogs();

            while (resultSet.next()) {

                String databaseName = resultSet.getString(1).toUpperCase();
                System.out.println(databaseName);
                if (databaseName.equals(DB_NAME)) {
                    result = true;
                }
            }
            resultSet.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public void createDatabase() throws SQLException {

        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 4: Execute a query
            System.out.println("Creating database...");
            conn = getDBConnection("");
            stmt = conn.createStatement();

            String sql = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } finally {
            //finally block used to close resources
            if (stmt != null) {
                stmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        }//end try
    }

    public void createDbCustomerTable() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String createCustomerTableSQL = "CREATE TABLE IF NOT EXISTS DBCUSTOMERS("
                + "CUSTOMER_ID INT(5) AUTO_INCREMENT NOT NULL, "
                + "FIRST_NAME VARCHAR(20) NOT NULL, "
                + "LAST_NAME VARCHAR(20) NOT NULL, "
                + "ADDRESS VARCHAR(50), "
                + "EMAIL_ID VARCHAR(50) NOT NULL, "
                + "PASSWORD VARCHAR(20) NOT NULL, "
                + "PHONE_NUMBER VARCHAR(20) NOT NULL, "
                + "CURRENT_EXPENSES INT(10), "
                + "CREATED_DATE DATE NOT NULL, "
                + "UNIQUE (CUSTOMER_ID), " + "PRIMARY KEY (EMAIL_ID)"
                + ")";

        String createEmployerTableSQL = "CREATE TABLE IF NOT EXISTS DBEMPLOYERDETAILS("
                + "CUSTOMER_ID INT(5) NOT NULL, "
                + "EMPLOYER_NAME VARCHAR(20) NOT NULL, "
                + "EMPLOYER_ADDRESS VARCHAR(50) NOT NULL, "
                + "PAY_SLIPS MEDIUMBLOB NOT NULL, "
                + "WORKING_FROM DATE NOT NULL, "
                + "SALARY VARCHAR(20) NOT NULL, "
                + "TAX_NUMBER VARCHAR(20) NOT NULL, "
                + "UNIQUE (TAX_NUMBER), " + "PRIMARY KEY (CUSTOMER_ID)"
                + ")";

        System.out.println("-------- MySQL JDBC Connection Testing ------------");

        try {
            dbConnection = getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(createCustomerTableSQL);
            // execute the SQL stetement
            statement.execute(createCustomerTableSQL);
            System.out.println("Table \"DBCUSTOMERS\" is created!");

            statement.execute(createEmployerTableSQL);
            System.out.println("Table \"DBEMPLOYERDETAILS\" is created!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public void createDbLenderTable() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String createCustomerTableSQL = "CREATE TABLE IF NOT EXISTS DBLENDERS("
                + "LENDER_ID INT(5) AUTO_INCREMENT NOT NULL, "
                + "NAME_OF_LENDER VARCHAR(20) NOT NULL, "
                + "ADDRESS VARCHAR(100) NOT NULL, "
                + "EMAIL_ID VARCHAR(50) NOT NULL, "
                + "PASSWORD VARCHAR(20) NOT NULL, "
                + "PHONE_NUMBER VARCHAR(20) NOT NULL, "
                + "BANK_RATING DOUBLE(4,2) NOT NULL, "
                + "CURRENT_FINANCIAL_STATUS VARCHAR(20) NOT NULL, "
                + "CREATED_DATE DATE NOT NULL, "
                + "UNIQUE (LENDER_ID), " + "PRIMARY KEY (EMAIL_ID)"
                + ")";

        System.out.println("-------- MySQL JDBC Connection Testing ------------");

        try {
            dbConnection = getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(createCustomerTableSQL);
            // execute the SQL stetement
            statement.execute(createCustomerTableSQL);
            System.out.println("Table \"DBLENDERS\" is created!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public void createDbAdminTable() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String createAdminTableSQL = "CREATE TABLE IF NOT EXISTS DBADMIN("
                + "ADMIN_ID INT(5) AUTO_INCREMENT NOT NULL, "
                + "NAME VARCHAR(20) NOT NULL, "
                + "EMAIL_ID VARCHAR(50) NOT NULL, "
                + "PASSWORD VARCHAR(20) NOT NULL, "
                + "CREATED_DATE DATE NOT NULL, "
                + "UNIQUE (ADMIN_ID), " + "PRIMARY KEY (EMAIL_ID)"
                + ")";

        System.out.println("-------- MySQL JDBC Connection Testing ------------");

        try {
            dbConnection = getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(createAdminTableSQL);
            // execute the SQL stetement
            statement.execute(createAdminTableSQL);

            System.out.println("Table \"DBADMIN\" is created!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public void createDbLoginUserTable() throws SQLException {

        //user_type details 0 -> admin, 1 -> customer, 2 -> lender
        Connection dbConnection = null;
        Statement statement = null;

        String createCustomerTableSQL = "CREATE TABLE IF NOT EXISTS DBLOGIN_USER_TABLE("
                + "ID INT(5) AUTO_INCREMENT NOT NULL, "
                + "EMAIL_ID VARCHAR(50) NOT NULL, "
                + "PASSWORD VARCHAR(20) NOT NULL, "
                + "USER_ID INT(5) NOT NULL, "
                + "USER_TYPE INT(2) NOT NULL, "
                + "PRIMARY KEY (ID)"
                + ")";

        System.out.println("-------- MySQL JDBC Connection Testing ------------");

        try {
            dbConnection = getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(createCustomerTableSQL);
            // execute the SQL stetement
            statement.execute(createCustomerTableSQL);
            System.out.println("Table \"DbLoginUserTable\" is created!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public void createDbLenderLoanListTable() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String createLenderLoanListTableSQL = "CREATE TABLE IF NOT EXISTS DBLENDERS_LOANLIST("
                + "LOAN_ID INT(5) AUTO_INCREMENT NOT NULL, "
                + "LOAN_NAME VARCHAR(50) NOT NULL, "
                + "LENDER_ID INT(5) NOT NULL, "
                + "LOAN_TYPE INT(2) NOT NULL, "
                + "RATE_OF_INTEREST DOUBLE(4,2) NOT NULL, "
                + "TENURE_PERIOD DOUBLE(4,2) NOT NULL, "
                + "MAX_AMOUNT_PROVIDED INT(20) NOT NULL, "
                + "CREATED_DATE DATE NOT NULL, "
                + "PRIMARY KEY (LOAN_ID)"
                + ")";

        System.out.println("-------- MySQL JDBC Connection Testing ------------");

        try {
            dbConnection = getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(createLenderLoanListTableSQL);
            // execute the SQL stetement
            statement.execute(createLenderLoanListTableSQL);

            System.out.println("Table \"DBLENDERS_LOANLIST\" is created!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public void createDbCustomerLoanListTable() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        //STATUS_OF_LOAN 0-> pending, 1-> processing, 2-> completed
        String createCustomerLoanListTableSQL = "CREATE TABLE IF NOT EXISTS DBCUSTOMERS_LOANLIST("
                + "CUSTOMER_LOAN_ID INT(5) AUTO_INCREMENT NOT NULL, "
                + "LENDER_LOAN_ID INT(5) NOT NULL, "
                + "LOAN_NAME VARCHAR(50) NOT NULL, "
                + "CUSTOMER_ID INT(5) NOT NULL, "
                + "LENDER_ID INT(5) NOT NULL, "
                + "LOAN_TYPE INT(2) NOT NULL, "
                + "RATE_OF_INTEREST DOUBLE(4,2) NOT NULL, "
                + "TENURE_PERIOD DOUBLE(4,2) NOT NULL, "
                + "LOAN_AMOUNT_TAKEN INT(10) NOT NULL, "
                + "STATUS_OF_LOAN INT(2) NOT NULL, "
                + "CREATED_DATE DATE NOT NULL, "
                + "PRIMARY KEY (CUSTOMER_LOAN_ID)"
                + ")";

        System.out.println("-------- MySQL JDBC Connection Testing ------------");

        try {
            dbConnection = getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(createCustomerLoanListTableSQL);
            // execute the SQL stetement
            statement.execute(createCustomerLoanListTableSQL);

            System.out.println("Table \"DBCUSTOMERS_LOANLIST\" is created!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public void createDbTypesOfLoanTable() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String createTypesOfLoanTableSQL = "CREATE TABLE IF NOT EXISTS DBTYPES_OF_LOAN("
                + "LOAN_TYPE_ID INT(5) NOT NULL, "
                + "LOAN_TYPE_NAME VARCHAR(15) NOT NULL, "
                + "UNIQUE (LOAN_TYPE_NAME), PRIMARY KEY (LOAN_TYPE_ID)"
                + ")";

        System.out.println("-------- MySQL JDBC Connection Testing ------------");

        try {
            dbConnection = getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(createTypesOfLoanTableSQL);
            // execute the SQL stetement
            statement.execute(createTypesOfLoanTableSQL);

            System.out.println("Table \"DBTYPES_OF_LOAN\" is created!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public void insertRecordIntoDbTypesOfLoan() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String insertIntoTypesOfLoanTableSQL1 = "INSERT INTO DBTYPES_OF_LOAN"
                + "(LOAN_TYPE_ID, LOAN_TYPE_NAME) VALUES" + "(1, 'HOME LOAN')";
        String insertIntoTypesOfLoanTableSQL2 = "INSERT INTO DBTYPES_OF_LOAN"
                + "(LOAN_TYPE_ID, LOAN_TYPE_NAME) VALUES" + "(2, 'BUSINESS LOAN')";
        String insertIntoTypesOfLoanTableSQL3 = "INSERT INTO DBTYPES_OF_LOAN"
                + "(LOAN_TYPE_ID, LOAN_TYPE_NAME) VALUES" + "(3, 'PERSONAL LOAN')";
        String insertIntoTypesOfLoanTableSQL4 = "INSERT INTO DBTYPES_OF_LOAN"
                + "(LOAN_TYPE_ID, LOAN_TYPE_NAME) VALUES" + "(4, 'STUDENT LOAN')";
        String insertIntoTypesOfLoanTableSQL5 = "INSERT INTO DBTYPES_OF_LOAN"
                + "(LOAN_TYPE_ID, LOAN_TYPE_NAME) VALUES" + "(5, 'VEHICLE LOAN')";

        try {
            dbConnection = getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(insertIntoTypesOfLoanTableSQL1);

            // execute insert SQL stetement
            statement.executeUpdate(insertIntoTypesOfLoanTableSQL1);
            statement.executeUpdate(insertIntoTypesOfLoanTableSQL2);
            statement.executeUpdate(insertIntoTypesOfLoanTableSQL3);
            statement.executeUpdate(insertIntoTypesOfLoanTableSQL4);
            statement.executeUpdate(insertIntoTypesOfLoanTableSQL5);

            System.out.println("Record is inserted into DBTYPES_OF_LOAN table!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public void insertRecordIntoDbLenderLoanListTable() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String insertIntoLenderLoanListTableSQL1 = "INSERT INTO DBLENDERS_LOANLIST"
                + "(LOAN_NAME, LENDER_ID, LOAN_TYPE, RATE_OF_INTEREST, TENURE_PERIOD, MAX_AMOUNT_PROVIDED, CREATED_DATE) VALUES"
                + "('SANTANDER-HOME-LOAN', 1, 1, 9, 10, 200000, " + "str_to_date('"
                + getCurrentTimeStamp() + "', '%Y/%m/%d %H:%i:%s'))";
        String insertIntoLenderLoanListTableSQL2 = "INSERT INTO DBLENDERS_LOANLIST"
                + "(LOAN_NAME, LENDER_ID, LOAN_TYPE, RATE_OF_INTEREST, TENURE_PERIOD, MAX_AMOUNT_PROVIDED, CREATED_DATE) VALUES"
                + "('SANTANDER-BUSINESS-LOAN', 1, 2, 11, 2, 500000, " + "str_to_date('"
                + getCurrentTimeStamp() + "', '%Y/%m/%d %H:%i:%s'))";
        String insertIntoLenderLoanListTableSQL3 = "INSERT INTO DBLENDERS_LOANLIST"
                + "(LOAN_NAME, LENDER_ID, LOAN_TYPE, RATE_OF_INTEREST, TENURE_PERIOD, MAX_AMOUNT_PROVIDED, CREATED_DATE) VALUES"
                + "('SANTANDER-PERSONAL-LOAN', 1, 3, 7, 2.5, 150000, " + "str_to_date('"
                + getCurrentTimeStamp() + "', '%Y/%m/%d %H:%i:%s'))";
        String insertIntoLenderLoanListTableSQL4 = "INSERT INTO DBLENDERS_LOANLIST"
                + "(LOAN_NAME, LENDER_ID, LOAN_TYPE, RATE_OF_INTEREST, TENURE_PERIOD, MAX_AMOUNT_PROVIDED, CREATED_DATE) VALUES"
                + "('SANTANDER-STUDENT-LOAN', 1, 4, 6, 3, 100000, " + "str_to_date('"
                + getCurrentTimeStamp() + "', '%Y/%m/%d %H:%i:%s'))";
        String insertIntoLenderLoanListTableSQL5 = "INSERT INTO DBLENDERS_LOANLIST"
                + "(LOAN_NAME, LENDER_ID, LOAN_TYPE, RATE_OF_INTEREST, TENURE_PERIOD, MAX_AMOUNT_PROVIDED, CREATED_DATE) VALUES"
                + "('SANTANDER-VEHICLE-LOAN', 1, 5, 13, 2, 20000, " + "str_to_date('"
                + getCurrentTimeStamp() + "', '%Y/%m/%d %H:%i:%s'))";

        String insertIntoLenderLoanListTableSQL6 = "INSERT INTO DBLENDERS_LOANLIST"
                + "(LOAN_NAME, LENDER_ID, LOAN_TYPE, RATE_OF_INTEREST, TENURE_PERIOD, MAX_AMOUNT_PROVIDED, CREATED_DATE) VALUES"
                + "('DEUTSCHE-HOME-LOAN', 2, 1, 8, 1.5, 250000, " + "str_to_date('"
                + getCurrentTimeStamp() + "', '%Y/%m/%d %H:%i:%s'))";
        String insertIntoLenderLoanListTableSQL7 = "INSERT INTO DBLENDERS_LOANLIST"
                + "(LOAN_NAME, LENDER_ID, LOAN_TYPE, RATE_OF_INTEREST, TENURE_PERIOD, MAX_AMOUNT_PROVIDED, CREATED_DATE) VALUES"
                + "('DEUTSCHE-BUSINESS-LOAN', 2, 2, 12, 3.5, 300000, " + "str_to_date('"
                + getCurrentTimeStamp() + "', '%Y/%m/%d %H:%i:%s'))";
        String insertIntoLenderLoanListTableSQL8 = "INSERT INTO DBLENDERS_LOANLIST"
                + "(LOAN_NAME, LENDER_ID, LOAN_TYPE, RATE_OF_INTEREST, TENURE_PERIOD, MAX_AMOUNT_PROVIDED, CREATED_DATE) VALUES"
                + "('DEUTSCHE-PERSONAL-LOAN', 2, 3, 5, 2, 180000, " + "str_to_date('"
                + getCurrentTimeStamp() + "', '%Y/%m/%d %H:%i:%s'))";
        String insertIntoLenderLoanListTableSQL9 = "INSERT INTO DBLENDERS_LOANLIST"
                + "(LOAN_NAME, LENDER_ID, LOAN_TYPE, RATE_OF_INTEREST, TENURE_PERIOD, MAX_AMOUNT_PROVIDED, CREATED_DATE) VALUES"
                + "('DEUTSCHE-STUDENT-LOAN', 2, 4, 6, 4, 205000, " + "str_to_date('"
                + getCurrentTimeStamp() + "', '%Y/%m/%d %H:%i:%s'))";
        String insertIntoLenderLoanListTableSQL10 = "INSERT INTO DBLENDERS_LOANLIST"
                + "(LOAN_NAME, LENDER_ID, LOAN_TYPE, RATE_OF_INTEREST, TENURE_PERIOD, MAX_AMOUNT_PROVIDED, CREATED_DATE) VALUES"
                + "('DEUTSCHE-VEHICLE-LOAN', 2, 5, 12, 3, 25000, " + "str_to_date('"
                + getCurrentTimeStamp() + "', '%Y/%m/%d %H:%i:%s'))";

        try {
            dbConnection = getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(insertIntoLenderLoanListTableSQL1);

            // execute insert SQL stetement
            statement.executeUpdate(insertIntoLenderLoanListTableSQL1);
            statement.executeUpdate(insertIntoLenderLoanListTableSQL2);
            statement.executeUpdate(insertIntoLenderLoanListTableSQL3);
            statement.executeUpdate(insertIntoLenderLoanListTableSQL4);
            statement.executeUpdate(insertIntoLenderLoanListTableSQL5);
            statement.executeUpdate(insertIntoLenderLoanListTableSQL6);
            statement.executeUpdate(insertIntoLenderLoanListTableSQL7);
            statement.executeUpdate(insertIntoLenderLoanListTableSQL8);
            statement.executeUpdate(insertIntoLenderLoanListTableSQL9);
            statement.executeUpdate(insertIntoLenderLoanListTableSQL10);

            System.out.println("Record is inserted into DBTYPES_OF_LOAN table!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public void insertRecordIntoDbAdminTable() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String insertIntoCustomerTableSQL = "INSERT INTO DBADMIN"
                + "(NAME, EMAIL_ID, PASSWORD, CREATED_DATE) " + "VALUES"
                + "('Preethi Venkatesh', 'abc@gmail.com', 'password', " + "str_to_date('"
                + getCurrentTimeStamp() + "', '%Y/%m/%d %H:%i:%s'))";

        try {
            dbConnection = getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(insertIntoCustomerTableSQL);

            // execute insert SQL stetement
            statement.executeUpdate(insertIntoCustomerTableSQL);

            System.out.println("Record is inserted into DBCUSTOMERS table!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public void insertRecordIntoDbCustomerTable() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String insertIntoCustomerTableSQL = "INSERT INTO DBCUSTOMERS"
                + "(FIRST_NAME, LAST_NAME, EMAIL_ID, PASSWORD, PHONE_NUMBER, ADDRESS, CURRENT_EXPENSES, CREATED_DATE) " + "VALUES"
                + "('ABC', 'Inc.', 'abc@gmail.com', 'password', '+4915166083365', 'In Den Breitwiesen 15', " + 10000
                + ", str_to_date('" + getCurrentTimeStamp() + "', '%Y/%m/%d %H:%i:%s'))";

        try {
            dbConnection = getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(insertIntoCustomerTableSQL);

            // execute insert SQL stetement
            statement.executeUpdate(insertIntoCustomerTableSQL);

            System.out.println("Record is inserted into DBCUSTOMERS table!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

//    public void insertRecordIntoDbEmployerTable() throws SQLException {
//
//        Connection dbConnection = null;
//        Statement statement = null;
//
//        String insertIntoCustEmployerTableSQL = "INSERT INTO DBEMPLOYERDETAILS"
//                + "(CUSTOMER_ID, EMPLOYER_NAME, EMPLOYER_ADDRESS, WORKING_FROM, SALARY, TAX_NUMBER, PAY_SLIPS) " + "VALUES"
//                + "(" + dBUtility.getUserId() + ", 'SRH', 'Heidelberg', str_to_date('2017/10/01', '%Y/%m/%d %H:%i:%s'), "
//                + "'2500 EUR', 'ABCDE', 'C:\\Users\\prith\\Documents\\example.txt')";
//
//        try {
//            dbConnection = getDBConnection(DB_NAME);
//            statement = dbConnection.createStatement();
//
//            System.out.println(insertIntoCustEmployerTableSQL);
//
//            // execute insert SQL stetement
//            statement.executeUpdate(insertIntoCustEmployerTableSQL);
//            File paySlips = new File("C:\\Users\\prith\\Documents\\example.txt");
//            dBUtility.writeLOB(paySlips);
//
//            System.out.println("Record is inserted into DBEMPLOYERDETAILS table!");
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } finally {
//
//            if (statement != null) {
//                statement.close();
//            }
//
//            if (dbConnection != null) {
//                dbConnection.close();
//            }
//        }
//    }

    public void insertRecordIntoDbLenderTable() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String insertIntoLenderTableSQL1 = "INSERT INTO DBLENDERS"
                + "(NAME_OF_LENDER, ADDRESS, EMAIL_ID, PASSWORD, PHONE_NUMBER, BANK_RATING, CURRENT_FINANCIAL_STATUS, CREATED_DATE) " + "VALUES"
                + "('Santander Bank', 'Bismarkplatz, Heidelberg, Germany', 'santander@gmail.com', 'password', '06222-41236', 3.5, 'Asset', " + "str_to_date('"
                + getCurrentTimeStamp() + "', '%Y/%m/%d %H:%i:%s'))";

        String insertIntoLenderTableSQL2 = "INSERT INTO DBLENDERS"
                + "(NAME_OF_LENDER, ADDRESS, EMAIL_ID, PASSWORD, PHONE_NUMBER, BANK_RATING, CURRENT_FINANCIAL_STATUS, CREATED_DATE) " + "VALUES"
                + "('Deutsche Bank', 'Seagarten, Heidelberg, Germany', 'deutsche@gmail.com', 'password', '069120-56897', 4.2, 'Liability', " + "str_to_date('"
                + getCurrentTimeStamp() + "', '%Y/%m/%d %H:%i:%s'))";

        try {
            dbConnection = getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(insertIntoLenderTableSQL1);

            // execute insert SQL stetement
            statement.executeUpdate(insertIntoLenderTableSQL1);
            statement.executeUpdate(insertIntoLenderTableSQL2);

            System.out.println("Record is inserted into DBLENDERS table!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public static Connection getDBConnection(String DB_NAME) {

        Connection dbConnection = null;

        try {
            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        }

        try {
            dbConnection = DriverManager.getConnection(
                    DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();

        }
        return dbConnection;
    }

    public void createDbJasperReportTable() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String createJasperTableSQL = "CREATE TABLE IF NOT EXISTS DB_JASPER_REPORT("
                + "LOAN_TYPE VARCHAR(30) NOT NULL, "
                + "LOAN_NAME VARCHAR(30) NOT NULL, "
                + "RATE_OF_INTEREST DOUBLE(4,2) NOT NULL, "
                + "TENURE_PERIOD DOUBLE(4,2) NOT NULL, "
                + "MAX_AMOUNT_PROVIDED INT(20) NOT NULL"
                + ")";
        
        try {
            dbConnection = getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(createJasperTableSQL);
            // execute the SQL stetement
            statement.execute(createJasperTableSQL);

            System.out.println("Table DB_JASPER_REPORT is created!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }
    
    private static String getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return dateFormat.format(today.getTime());
    }
}
