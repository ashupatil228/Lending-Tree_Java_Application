/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendingtreeapplication;

import java.sql.SQLException;

/**
 *
 * @author prith
 */
public class StartPage {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        try {
            DBUtility dBUtility = new DBUtility();
            DBHelper dBHelper = new DBHelper();
            
            if (!dBHelper.checkDBExists()) {
                dBHelper.createDatabase();
                dBHelper.createDbCustomerTable();
                dBHelper.createDbLenderTable();
                dBHelper.createDbAdminTable();
                dBHelper.createDbLenderLoanListTable();
                dBHelper.createDbCustomerLoanListTable();
                dBHelper.createDbTypesOfLoanTable();
                dBHelper.createDbLoginUserTable();
                dBHelper.createDbJasperReportTable();

                dBHelper.insertRecordIntoDbTypesOfLoan();
                dBHelper.insertRecordIntoDbCustomerTable();
                dBHelper.insertRecordIntoDbLenderTable();
                dBHelper.insertRecordIntoDbAdminTable();
                dBHelper.insertRecordIntoDbLenderLoanListTable();
                
                System.out.println("Database created");
            }

            //dBUtility.checkLoginDetailsOfCustomer("prithu.kuti@gmail.com", "12345", 1);
            //dBUtility.checkLoginDetailsOfLender("sanatanderbk1@santanderbank.de", "12345", 2);
            //dBHelper.insertRecordIntoDbEmployerTable();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePage().createCustomerTabbedPane();
            }
        });
    }

}
