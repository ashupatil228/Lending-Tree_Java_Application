/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendingtreeapplication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author prith
 */
public class DBUtility {

    private static final String DB_NAME = "LENDING_TREE";
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    ResultSet getLoanDetailsBasedType(String loanName) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        ResultSet rs = null;

        String selectLoanDetailsBasedType = "SELECT * FROM DBLENDERS_LOANLIST WHERE LOAN_TYPE = "
                + "(SELECT LOAN_TYPE_ID FROM DBTYPES_OF_LOAN WHERE LOAN_TYPE_NAME = '" + loanName + "')";

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(selectLoanDetailsBasedType);

            // execute select SQL stetement
            rs = statement.executeQuery(selectLoanDetailsBasedType);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            /* if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }*/
        }
        return rs;
    }

    ResultSet getLoanTypes() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        ResultSet rs = null;

        String selectLoanTypesSQL = "SELECT LOAN_TYPE_NAME FROM DBTYPES_OF_LOAN";

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(selectLoanTypesSQL);

            // execute select SQL stetement
            rs = statement.executeQuery(selectLoanTypesSQL);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            /*if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }*/
        }
        return rs;
    }

    int getLoanTypeIdBasedOnName(String loanTypeName) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        ResultSet rs = null;
        int loanTypeId = -1;

        String selectLoanTypeIdSQL = "SELECT LOAN_TYPE_ID FROM DBTYPES_OF_LOAN WHERE LOAN_TYPE_NAME = '" + loanTypeName + "'";

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(selectLoanTypeIdSQL);

            // execute select SQL stetement
            rs = statement.executeQuery(selectLoanTypeIdSQL);

            while (rs.next()) {
                loanTypeId = rs.getInt("LOAN_TYPE_ID");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            rs.close();
            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return loanTypeId;
    }

    String getLoanTypeNameBasedOnId(int loanTypeId) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        ResultSet rs = null;
        String loanTypeName = null;

        String selectLoanTypeIdSQL = "SELECT LOAN_TYPE_NAME FROM DBTYPES_OF_LOAN WHERE LOAN_TYPE_ID = " + loanTypeId;

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(selectLoanTypeIdSQL);

            // execute select SQL stetement
            rs = statement.executeQuery(selectLoanTypeIdSQL);

            while (rs.next()) {
                loanTypeName = rs.getString("LOAN_TYPE_NAME");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            rs.close();
            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return loanTypeName;
    }

    String getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return dateFormat.format(today.getTime());
    }

    int checkLoginDetailsOfCustomer(String emailId, String password, int userType) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        int result = -1; // 0 is login successfull, 1 is emailId or password incorrect, 2 is emailId not registered

        String selectEmailIdCustomerTableSQL = "SELECT * FROM DBCUSTOMERS WHERE EMAIL_ID = '" + emailId + "'";
        String checkLoginDetailsCustomerTableSQL = "SELECT * FROM DBCUSTOMERS WHERE EMAIL_ID = '" + emailId + "' AND PASSWORD = '" + password + "'";

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(selectEmailIdCustomerTableSQL);

            // execute select SQL stetement
            ResultSet rs = statement.executeQuery(selectEmailIdCustomerTableSQL);

            if (!rs.isBeforeFirst()) {
                result = 2;
            } else {
                ResultSet rs1 = statement.executeQuery(checkLoginDetailsCustomerTableSQL);

                if (!rs1.isBeforeFirst()) {
                    result = 1;
                } else {
                    result = 0;
                    while (rs1.next()) {
                        String emailID = rs1.getString("EMAIL_ID");
                        String pwd = rs1.getString("PASSWORD");
                        int userID = rs1.getInt("CUSTOMER_ID");
                        insertIntoDbLoginUserTable(emailID, pwd, userID, userType);
                    }
                }
                rs1.close();
            }
            rs.close();

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
        return result;
    }

    boolean checkRegisteredDetails(String emailId, int userId) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        boolean result = false;
        ResultSet rs = null;

        //userid-> 1- customer, 2- lender
        String selectCustomerTableSQL = "SELECT * FROM DBCUSTOMERS"
                + " WHERE EMAIL_ID = '" + emailId + "'";
        String selectLenderTableSQL = "SELECT * FROM DBLENDERS"
                + " WHERE EMAIL_ID = '" + emailId + "'";
        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(selectCustomerTableSQL);

            // execute insert SQL stetement
            if (userId == 1) {
                rs = statement.executeQuery(selectCustomerTableSQL);
            } else if (userId == 2) {
                rs = statement.executeQuery(selectLenderTableSQL);
            }

            result = rs.last();

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

        return result;
    }

    boolean insertIntoDbCustomerTable(String firstName, String lastName, String emailId,
            String password, String phoneNumber, String address, int currentExpenses) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        boolean result = false;

        String insertIntoCustomerTableSQL = "INSERT INTO DBCUSTOMERS"
                + "(FIRST_NAME, LAST_NAME, EMAIL_ID, PASSWORD, PHONE_NUMBER, ADDRESS, CURRENT_EXPENSES, CREATED_DATE) " + "VALUES"
                + "('" + firstName + "', '" + lastName + "', '" + emailId + "', '" + password + "', '" + phoneNumber + "', '"
                + address + "', " + currentExpenses + ", str_to_date('" + getCurrentTimeStamp() + "', '%Y/%m/%d %H:%i:%s'))";

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(insertIntoCustomerTableSQL);

            // execute insert SQL stetement
            statement.execute(insertIntoCustomerTableSQL);
            result = true;

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

        return result;
    }

    boolean insertIntoDbEmployerTable(String employerName, String employerAddress, Date workingFrom,
            String salary, String taxNumber, File paySlips) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        boolean result = false;

        String insertIntoEmployerTableSQL = "INSERT INTO DBEMPLOYERDETAILS"
                + "(CUSTOMER_ID, EMPLOYER_NAME, EMPLOYER_ADDRESS, SALARY, TAX_NUMBER, PAY_SLIPS, WORKING_FROM) " + "VALUES"
                + "(" + getUserId() + ",'" + employerName + "', '" + employerAddress + "', '"
                + salary + "', '" + taxNumber + "', ' ', str_to_date('" + dateFormat.format(workingFrom) + "', '%Y/%m/%d %H:%i:%s'))";

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(insertIntoEmployerTableSQL);

            // execute insert SQL stetement
            statement.execute(insertIntoEmployerTableSQL);
            writeLOB(paySlips);
            result = true;

            System.out.println("Record is inserted into DBEMPLOYERDETAILS table!");

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

        return result;
    }

    void insertIntoDbLoginUserTable(String emailId, String password, int userId, int userType) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String insertIntoLoginUserTableSQL = "INSERT INTO DBLOGIN_USER_TABLE"
                + "(EMAIL_ID, PASSWORD, USER_ID, USER_TYPE) " + "VALUES"
                + "('" + emailId + "', '" + password + "', " + userId + ", " + userType + ")";

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(insertIntoLoginUserTableSQL);

            //remove all the data from table if any exists
            statement.execute("DELETE FROM DBLOGIN_USER_TABLE");
            // execute insert SQL stetement
            statement.executeUpdate(insertIntoLoginUserTableSQL);

            System.out.println("Record is inserted into DBLOGIN_USER_TABLE table!");

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

    void deleteFromDbLoginUserTable() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String deleteFromLoginUserTableSQL = "DELETE FROM DBLOGIN_USER_TABLE";

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(deleteFromLoginUserTableSQL);

            //remove all the data from table if any exists
            statement.execute(deleteFromLoginUserTableSQL);

            System.out.println("Record is deleted from DBLOGIN_USER_TABLE table!");

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

    int checkLoginDetailsOfLender(String emaiId, String password, int userType) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        int result = -1; // 0 is login successfull, 1 is emailId or password incorrect, 2 is emailId not registered

        String selectEmailIdLenderTableSQL = "SELECT * FROM DBLENDERS WHERE EMAIL_ID = '" + emaiId + "'";
        String checkLoginDetailsLenderTableSQL = "SELECT * FROM DBLENDERS WHERE EMAIL_ID = '" + emaiId + "' AND PASSWORD = '" + password + "'";

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(selectEmailIdLenderTableSQL);

            // execute select SQL stetement
            ResultSet rs = statement.executeQuery(selectEmailIdLenderTableSQL);

            if (!rs.isBeforeFirst()) {
                result = 2;
            } else {
                System.out.println(checkLoginDetailsLenderTableSQL);
                ResultSet rs1 = statement.executeQuery(checkLoginDetailsLenderTableSQL);

                if (!rs1.isBeforeFirst()) {
                    result = 1;
                } else {
                    result = 0;
                    while (rs1.next()) {
                        System.out.println("Fetching lender details");
                        String emailID = rs1.getString("EMAIL_ID");
                        String pwd = rs1.getString("PASSWORD");
                        int userID = rs1.getInt("LENDER_ID");
                        insertIntoDbLoginUserTable(emailID, pwd, userID, userType);
                    }
                }
                rs1.close();
            }
            rs.close();

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
        return result;
    }

    int checkLoginDetailsOfAdmin(String emaiId, String password, int userType) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        int result = -1; // 0 is login successfull, 1 is emailId or password incorrect, 2 is emailId not registered

        String selectEmailIdAdminTableSQL = "SELECT * FROM DBADMIN WHERE EMAIL_ID = '" + emaiId + "'";
        String checkLoginDetailsAdminTableSQL = "SELECT * FROM DBADMIN WHERE EMAIL_ID = '" + emaiId + "' AND PASSWORD = '" + password + "'";

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(selectEmailIdAdminTableSQL);

            // execute select SQL stetement
            ResultSet rs = statement.executeQuery(selectEmailIdAdminTableSQL);

            if (!rs.isBeforeFirst()) {
                result = 2;
            } else {
                ResultSet rs1 = statement.executeQuery(checkLoginDetailsAdminTableSQL);

                if (!rs1.isBeforeFirst()) {
                    result = 1;
                } else {
                    result = 0;
                    while (rs1.next()) {
                        String emailID = rs1.getString("EMAIL_ID");
                        String pwd = rs1.getString("PASSWORD");
                        int userID = rs1.getInt("ADMIN_ID");
                        insertIntoDbLoginUserTable(emailID, pwd, userID, userType);
                    }
                }
                rs1.close();
            }
            rs.close();

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
        return result;
    }

    boolean insertIntoDbLenderTable(String nameOfLender, String address, String emailId,
            String password, String phoneNumber, double bankRating, String current_financial_status) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        boolean result = false;

        String insertIntoLenderTableSQL = "INSERT INTO DBLENDERS"
                + "(NAME_OF_LENDER, ADDRESS, EMAIL_ID, PASSWORD, PHONE_NUMBER, BANK_RATING, CURRENT_FINANCIAL_STATUS, CREATED_DATE) " + "VALUES"
                + "('" + nameOfLender + "', '" + address + "', '" + emailId + "', '" + password + "', '" + phoneNumber
                + "', " + bankRating + ", '" + current_financial_status + "', " + "str_to_date('"
                + getCurrentTimeStamp() + "', '%Y/%m/%d %H:%i:%s'))";

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(insertIntoLenderTableSQL);

            // execute insert SQL stetement
            statement.execute(insertIntoLenderTableSQL);
            result = true;

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

        return result;
    }

    String getLoanProviderName(int loanProviderId) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        String loanProviderName = null;

        String getLoanProviderNameSQL = "SELECT NAME_OF_LENDER FROM DBLENDERS WHERE LENDER_ID = " + loanProviderId;

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(getLoanProviderNameSQL);

            // execute select SQL stetement
            ResultSet rs = statement.executeQuery(getLoanProviderNameSQL);

            while (rs.next()) {
                loanProviderName = rs.getString("NAME_OF_LENDER");
            }
            rs.close();

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
        return loanProviderName;
    }

    int getUserId() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        int userID = -1;

        String getUserIdSQL = "SELECT USER_ID FROM DBLOGIN_USER_TABLE";

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(getUserIdSQL);

            // execute select SQL stetement
            ResultSet rs = statement.executeQuery(getUserIdSQL);

            while (rs.next()) {
                userID = rs.getInt("USER_ID");
            }
            rs.close();

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
        return userID;

    }

    ResultSet getLoanDetailsBasedOnLender(int lenderId) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        ResultSet rs = null;

        String selectLoanDetailsBasedOnLender = "SELECT * FROM DBLENDERS_LOANLIST WHERE LENDER_ID = " + lenderId;

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(selectLoanDetailsBasedOnLender);

            // execute select SQL stetement
            rs = statement.executeQuery(selectLoanDetailsBasedOnLender);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            /* if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }*/
        }
        return rs;
    }

    ResultSet getLoanDetailsBasedOnCustomer(int customerId) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        ResultSet rs = null;

        String selectLoanDetailsBasedOnCustomer = "SELECT * FROM DBCUSTOMERS_LOANLIST WHERE CUSTOMER_ID = " + customerId;

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(selectLoanDetailsBasedOnCustomer);

            // execute select SQL stetement
            rs = statement.executeQuery(selectLoanDetailsBasedOnCustomer);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            /* if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }*/
        }
        return rs;
    }

    boolean updateDbCustomerTable(String fName, String lName, String password, String phNo, String address, int currentExpenses, int userID)
            throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        boolean result = false;

        String updateCustomerTable = "UPDATE DBCUSTOMERS SET FIRST_NAME = '" + fName
                + "', LAST_NAME = '" + lName + "', ADDRESS = '" + address
                + "', PASSWORD = '" + password + "', PHONE_NUMBER = '" + phNo
                + "', CURRENT_EXPENSES = " + currentExpenses
                + " WHERE CUSTOMER_ID = " + userID;

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(updateCustomerTable);

            // execute update SQL stetement
            statement.executeUpdate(updateCustomerTable);

            result = true;

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
        return result;
    }

    boolean updateDbEmployerTable(String employerName, String employerAddress, File paySlips, Date workingFrom, String salary, String taxNumber)
            throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        boolean result = false;

        String updateCustomerEmployerTable = "UPDATE DBEMPLOYERDETAILS SET EMPLOYER_NAME = '" + employerName
                + "', EMPLOYER_ADDRESS = '" + employerAddress + "', WORKING_FROM = str_to_date('" + dateFormat.format(workingFrom)
                + "', '%Y/%m/%d %H:%i:%s'), SALARY = '" + salary
                + "', TAX_NUMBER = '" + taxNumber + "' WHERE CUSTOMER_ID = " + getUserId();

        ResultSet rs = getEmployerDetailsById();
        rs.last();
        int count = rs.getRow();

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(updateCustomerEmployerTable);

            if (count <= 0) {
                result = insertIntoDbEmployerTable(employerName, employerAddress, workingFrom, salary, taxNumber, paySlips);
            } else {
                // execute update SQL stetement
                statement.executeUpdate(updateCustomerEmployerTable);
                writeLOB(paySlips);
                result = true;
            }

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
        return result;
    }

    ResultSet getCustomerDetails() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        ResultSet rs = null;

        String selectCustomerDetails = "SELECT * FROM DBCUSTOMERS";

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(selectCustomerDetails);

            // execute select SQL stetement
            rs = statement.executeQuery(selectCustomerDetails);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            /* if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }*/
        }
        return rs;
    }

    ResultSet getCustomerDetailsById() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        ResultSet rs = null;

        String selectCustomerDetails = "SELECT * FROM DBCUSTOMERS WHERE CUSTOMER_ID = " + getUserId();

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(selectCustomerDetails);

            // execute select SQL stetement
            rs = statement.executeQuery(selectCustomerDetails);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            /* if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }*/
        }
        return rs;
    }

    ResultSet getEmployerDetailsById() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        ResultSet rs = null;

        String selectEmployerDetails = "SELECT * FROM DBEMPLOYERDETAILS WHERE CUSTOMER_ID = " + getUserId();

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(selectEmployerDetails);

            // execute select SQL stetement
            rs = statement.executeQuery(selectEmployerDetails);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            /* if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }*/
        }
        return rs;
    }

    boolean updateDbLenderTable(String bankName, String address, String password, String phNo, double rating, String financialStatus, int userID)
            throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        boolean result = false;

        String updateLenderTable = "UPDATE DBLENDERS SET NAME_OF_LENDER = '" + bankName
                + "', ADDRESS = '" + address + "', PASSWORD = '" + password
                + "', PHONE_NUMBER = '" + phNo + "', BANK_RATING = " + rating
                + ", CURRENT_FINANCIAL_STATUS = '" + financialStatus
                + "' WHERE LENDER_ID = " + userID;

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(updateLenderTable);

            // execute update SQL stetement
            statement.executeUpdate(updateLenderTable);
            result = true;

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
        return result;
    }

    ResultSet getLenderDetails() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        ResultSet rs = null;

        String selectLenderDetails = "SELECT * FROM DBLENDERS";

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(selectLenderDetails);

            // execute select SQL stetement
            rs = statement.executeQuery(selectLenderDetails);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            /* if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }*/
        }
        return rs;
    }

    ResultSet getLenderDetailsById() throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        ResultSet rs = null;

        String selectCustomerDetails = "SELECT * FROM DBLENDERS WHERE LENDER_ID = " + getUserId();

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(selectCustomerDetails);

            // execute select SQL stetement
            rs = statement.executeQuery(selectCustomerDetails);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            /* if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }*/
        }
        return rs;
    }

    public void writeLOB(File paySlips) throws SQLException {

        Connection dbConnection = null;
        PreparedStatement pstmt = null;
        String sqlWrite = "UPDATE DBEMPLOYERDETAILS SET PAY_SLIPS = ? WHERE CUSTOMER_ID = ?";

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            pstmt = dbConnection.prepareStatement(sqlWrite);
            pstmt.setInt(2, getUserId());

            // Get input from a file stream---read text
            /*File file = new File("C:\\Users\\prith\\Documents\\example");
            try {
                InputStreamReader reader = new InputStreamReader(new FileInputStream("D:\\test.txt"));
                pstmt.setCharacterStream(1, reader, (int) file.length());
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            InputStream in;
            try {
                in = new BufferedInputStream(new FileInputStream(paySlips));
                pstmt.setBinaryStream(1, in, (int) paySlips.length());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (pstmt.executeUpdate() == 1) {
                System.out.println(" Congratulations, you successfully added records  !!");
            } else {
                System.out.println(" I'm sorry you add record failed  !!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (pstmt != null) {
                pstmt.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public File readLOB() throws SQLException {

        File file = new File("d:/payslip.txt");

        Connection dbConnection = null;
        PreparedStatement pstmt = null;
        String sqlRead = "SELECT PAY_SLIPS FROM DBEMPLOYERDETAILS WHERE CUSTOMER_ID = " + getUserId();

        ResultSet rs = null;
        BufferedReader br = null;

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            pstmt = dbConnection.prepareStatement(sqlRead);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                //read character stream
                /*Reader rd = rs.getCharacterStream(3);
                br = new BufferedReader(rd);
                String str = null;
                while((str = br.readLine()) != null){
                        System.out.println(str);
                }*/
                //read binary stream
                Blob blob = rs.getBlob("PAY_SLIPS");
                BufferedInputStream bis = new BufferedInputStream(blob.getBinaryStream());
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                byte[] buffer = new byte[1024];
                int count = -1;
                while ((count = bis.read(buffer, 0, 1024)) != -1) {
                    bos.write(buffer, 0, count);
                }
                bos.flush();
                bos.close();
                System.out.println("\n-------> Picture written  !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (pstmt != null) {
                pstmt.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }

        return file;
    }

    boolean insertIntoDbCustomersLoanListTable(int lenderLoanId, String loanName, int customerId, int lenderId, int loanType,
            double rateOfInterest, double tenurePeriod, int loanAmtTaken, int statusOfLoan) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        boolean result = false;

        String insertCustomerLoanListTableSQL = "INSERT INTO DBCUSTOMERS_LOANLIST"
                + "(LOAN_NAME, LENDER_LOAN_ID, CUSTOMER_ID, LENDER_ID, LOAN_TYPE, RATE_OF_INTEREST, TENURE_PERIOD, LOAN_AMOUNT_TAKEN, STATUS_OF_LOAN, CREATED_DATE) " + "VALUES"
                + "('" + loanName + "', " + lenderLoanId + ", " + customerId + ", " + lenderId + ", " + loanType + ", " + rateOfInterest
                + ", " + tenurePeriod + ", " + loanAmtTaken + ", " + statusOfLoan + ", str_to_date('"
                + getCurrentTimeStamp() + "', '%Y/%m/%d %H:%i:%s'))";

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(insertCustomerLoanListTableSQL);

            // execute insert SQL stetement
            statement.execute(insertCustomerLoanListTableSQL);
            result = true;

            System.out.println("Record is inserted into DBCUSTOMERS_LOANLIST table!");

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

        return result;
    }

    boolean insertIntoDbLendersLoanListTable(String loanName, int lenderId, int loanType, double rateOfInterest,
            double tenurePeriod, int maxAmtProvided) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        boolean result = false;

        String insertLenderLoanListTableSQL = "INSERT INTO DBLENDERS_LOANLIST"
                + "(LOAN_NAME, LENDER_ID, LOAN_TYPE, RATE_OF_INTEREST, TENURE_PERIOD, MAX_AMOUNT_PROVIDED, CREATED_DATE) " + "VALUES"
                + "('" + loanName + "', " + lenderId + ", " + loanType + ", " + rateOfInterest
                + ", " + tenurePeriod + ", " + maxAmtProvided + ", str_to_date('"
                + getCurrentTimeStamp() + "', '%Y/%m/%d %H:%i:%s'))";

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(insertLenderLoanListTableSQL);

            // execute insert SQL stetement
            statement.execute(insertLenderLoanListTableSQL);
            result = true;

            System.out.println("Record is inserted into DBLENDERS_LOANLIST table!");

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
        return result;
    }

    boolean updateDbLendersLoanListTable(int loanId, String loanName, int lenderId, int loanType, double rateOfInterest,
            double tenurePeriod, int maxAmtProvided) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        boolean result = false;

        String updateLenderLoanListTableSQL = "UPDATE DBLENDERS_LOANLIST SET LOAN_NAME = '" + loanName
                + "', LENDER_ID = " + lenderId + ", LOAN_TYPE = " + loanType
                + ", RATE_OF_INTEREST = " + rateOfInterest + ", TENURE_PERIOD = " + tenurePeriod
                + ", MAX_AMOUNT_PROVIDED = " + maxAmtProvided
                + " WHERE LOAN_ID = " + loanId;

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(updateLenderLoanListTableSQL);

            // execute insert SQL stetement
            statement.execute(updateLenderLoanListTableSQL);
            result = true;

            System.out.println("Record is updated into DBLENDERS_LOANLIST table!");

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
        return result;
    }

    ResultSet getCustomersLoanListByLenderId(int statusOfLoan) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        ResultSet rs = null;

        String selectCustomerDetails = "SELECT * FROM DBCUSTOMERS_LOANLIST "
                + "WHERE LENDER_ID = " + getUserId() + " AND STATUS_OF_LOAN = " + statusOfLoan;

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(selectCustomerDetails);

            // execute select SQL stetement
            rs = statement.executeQuery(selectCustomerDetails);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            /* if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }*/
        }
        return rs;
    }

    String getCustomerNameById(int customerID) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        ResultSet rs = null;
        String customerName = null;

        String selectCustomerDetails = "SELECT FIRST_NAME, LAST_NAME FROM DBCUSTOMERS WHERE CUSTOMER_ID = " + customerID;

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(selectCustomerDetails);

            // execute select SQL stetement
            rs = statement.executeQuery(selectCustomerDetails);

            while (rs.next()) {
                customerName = rs.getString("FIRST_NAME") + " " + rs.getString("LAST_NAME");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            rs.close();

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return customerName;
    }

    boolean deleteDbLendersLoanBasedOnId(int loanId, int lenderId) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        boolean result = false;

        String deleteLenderLoanListTable = "DELETE FROM DBLENDERS_LOANLIST WHERE LOAN_ID = " + loanId
                + " AND LENDER_ID = " + lenderId;

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(deleteLenderLoanListTable);

            // execute update SQL stetement
            statement.executeUpdate(deleteLenderLoanListTable);
            result = true;

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
        return result;
    }

    boolean updateStatusOfLoan(int loanId, int statusOfLoan) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        boolean result = false;

        String updateStatusOfLoanSQL = "UPDATE DBCUSTOMERS_LOANLIST SET STATUS_OF_LOAN = " + statusOfLoan
                + " WHERE CUSTOMER_LOAN_ID = " + loanId;

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(updateStatusOfLoanSQL);

            // execute insert SQL stetement
            statement.execute(updateStatusOfLoanSQL);
            result = true;

            System.out.println("Record is updated into DBCUSTOMERS_LOANLIST table!");

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
        return result;
    }

    boolean deleteFromDbCustomersTable(int customerId) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        boolean result = false;

        String deleteFromCustomersTable = "DELETE FROM DBCUSTOMERS WHERE CUSTOMER_ID = " + customerId;

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(deleteFromCustomersTable);

            // execute update SQL stetement
            statement.executeUpdate(deleteFromCustomersTable);
            result = true;

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
        return result;
    }

    boolean deleteFromDbLendersTable(int lenderId) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        boolean result = false;

        String deleteFromLendersTable = "DELETE FROM DBLENDERS WHERE LENDER_ID = " + lenderId;

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(deleteFromLendersTable);

            // execute update SQL stetement
            statement.executeUpdate(deleteFromLendersTable);
            result = true;

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
        return result;
    }

    ResultSet getAdminDetails() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        ResultSet rs = null;

        String selectAdminDetails = "SELECT * FROM DBADMIN";

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(selectAdminDetails);

            // execute select SQL stetement
            rs = statement.executeQuery(selectAdminDetails);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            /* if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }*/
        }
        return rs;
    }

    boolean insertIntoDbAdminTable(String name, String emailId, String password) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        boolean result = false;

        String insertIntoAdminTableSQL = "INSERT INTO DBADMIN"
                + "(EMAIL_ID, PASSWORD, NAME, CREATED_DATE) " + "VALUES"
                + "('" + emailId + "', '" + password + "', '" + name + "', str_to_date('" + getCurrentTimeStamp() + "', '%Y/%m/%d %H:%i:%s'))";

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(insertIntoAdminTableSQL);

            // execute insert SQL stetement
            statement.executeUpdate(insertIntoAdminTableSQL);
            result = true;

            System.out.println("Record is inserted into DBADMIN table!");

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
        return result;
    }

    boolean deleteFromDbAdminTable(int adminID) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        boolean result = false;

        String deleteFromAdminTable = "DELETE FROM DBADMIN WHERE ADMIN_ID = " + adminID;

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(deleteFromAdminTable);

            // execute update SQL stetement
            statement.executeUpdate(deleteFromAdminTable);
            result = true;

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
        return result;
    }

    boolean checkLoanInsertedCustomer(int lenderLoanId, int customerId, int lenderId) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        boolean result = false;

        String checkCustomerLoanDetails = "SELECT * FROM DBCUSTOMERS_LOANLIST "
                + "WHERE LENDER_ID = " + lenderId + " AND LENDER_LOAN_ID = " + lenderLoanId + " AND CUSTOMER_ID = " + customerId;

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            System.out.println(checkCustomerLoanDetails);

            // execute select SQL stetement
            ResultSet rs = statement.executeQuery(checkCustomerLoanDetails);
            result = rs.last();

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
        return result;
    }
    
    boolean insertIntoJasperTable(ArrayList<LoanDetailsListViewItem> loanList) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        boolean result = false;
        String insertJasperTableSQL = null;

        try {
            dbConnection = DBHelper.getDBConnection(DB_NAME);
            statement = dbConnection.createStatement();

            //remove all the data from table if any exists
            statement.execute("DELETE FROM DB_JASPER_REPORT");
            
            for (int i = 0; i < loanList.size(); i++) {
                insertJasperTableSQL = "INSERT INTO DB_JASPER_REPORT"
                        + "(LOAN_TYPE, LOAN_NAME, RATE_OF_INTEREST, TENURE_PERIOD, MAX_AMOUNT_PROVIDED) " + "VALUES"
                        + "('" + getLoanTypeNameBasedOnId(loanList.get(i).getLoanType()) + "', '"
                        + loanList.get(i).getLoanName() + "', " + loanList.get(i).getRateOfInterest() + ", "
                        + loanList.get(i).getTenurePeriod() + ", " + loanList.get(i).getMaxAmountProvided() + ")";
                statement.executeUpdate(insertJasperTableSQL);

            }
            result = true;

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
        return result;
    }

}
