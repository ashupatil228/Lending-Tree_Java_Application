/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendingtreeapplication;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.jdatepicker.impl.*;

/**
 *
 * @author prith
 */
public class CustomerProfileLayout {

    DBUtility dBUtility = new DBUtility();

    static JTabbedPane jProfileTabbedPane = new JTabbedPane(JTabbedPane.TOP);

    final JTable jTable = new JTable();

    final JButton updateBtn = new JButton("Update");
    final JButton editBtn = new JButton("Edit");
    final JButton cancelBtn = new JButton("Cancel");

    final JPasswordField password = new JPasswordField();
    final JTextField firstName = new JTextField();
    final JTextField lastName = new JTextField();
    final JTextField phoneNum = new JTextField();
    final JTextField address = new JTextField();
    final JTextField currentExpenses = new JTextField();
    final JTextField empName = new JTextField();
    final JTextField empAddress = new JTextField();
    final JTextField salary = new JTextField();
    final JTextField taxNumber = new JTextField();

    SqlDateModel workingFromModel = new SqlDateModel();
    JDatePanelImpl workingFromDatePanel = new JDatePanelImpl(workingFromModel, new Properties());
    final JDatePickerImpl workingFromDatePicker = new JDatePickerImpl(workingFromDatePanel, new DateLabelFormatter());
    final JFileChooser paySlips = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf", "doc*", "txt");

    final JLabel firstNameL = new JLabel("First Name:");
    final JLabel lastNameL = new JLabel("Last Name:");
    final JLabel passwordL = new JLabel("Password:");
    final JLabel phoneNumL = new JLabel("Phone no:");
    final JLabel addressL = new JLabel("Address:");
    final JLabel currentExpensesL = new JLabel("Current Expenses:");
    final JLabel empNameL = new JLabel("Employer Name:");
    final JLabel empAddressL = new JLabel("Employer Address:");
    final JLabel paySlipsL = new JLabel("Salary Slips:");
    final JLabel workingFromL = new JLabel("Working From:");
    final JLabel salaryL = new JLabel("Salary:");
    final JLabel taxNumberL = new JLabel("Tax Number:");

    public LayoutManager customerProfileLayout(JPanel jPanel) {

        System.out.println("inside customerProfileLayout");

        salary.setToolTipText("Please enter amount in terms of euros");
        currentExpenses.setToolTipText("Please enter amount in terms of euros");
        HomePage.setKeyListenerToNumber(currentExpenses);
        HomePage.setKeyListenerToNumber(salary);
        
        jPanel.removeAll();
        jProfileTabbedPane.removeAll();
        jTable.removeAll();
        
        jProfileTabbedPane.addTab("Personal Information", personalInfoLayout());
        jProfileTabbedPane.addTab("Loans Interested", loansInterestedLayout());

        jPanel.add(jProfileTabbedPane, BorderLayout.NORTH);
        jPanel.revalidate();
        jPanel.repaint();

        return jPanel.getLayout();
    }

    public void setTabComponent() {
        jProfileTabbedPane.setComponentAt(jProfileTabbedPane.indexOfTab("Loans Interested"), loansInterestedLayout());
    }
    
    Component loansInterestedLayout() {

        JPanel jPanel = new JPanel();
        String statusOfLoanS[] = {"Pending", "Processing", "Completed"};

        String colName[] = {"Loan Name", "Loan Type", "Rate of Interest(%)", "Tenure Period (in Years)", "Loan Amount Requested (in EUR)", "Status"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(colName);
        Object[] column = new Object[6];

        try {
            ResultSet rs = dBUtility.getLoanDetailsBasedOnCustomer(dBUtility.getUserId());

            while (rs.next()) {
                column[0] = rs.getString("LOAN_NAME");
                column[1] = dBUtility.getLoanTypeNameBasedOnId(rs.getInt("LOAN_TYPE"));
                column[2] = rs.getString("RATE_OF_INTEREST");
                column[3] = rs.getString("TENURE_PERIOD");
                column[4] = rs.getString("LOAN_AMOUNT_TAKEN");
                column[5] = statusOfLoanS[rs.getInt("STATUS_OF_LOAN")];
                model.addRow(column);

            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TabContentPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        jTable.setModel(model);
        JScrollPane scrollPane = new JScrollPane(jTable);
        jPanel.add(scrollPane, BorderLayout.PAGE_START);

        return jPanel;
    }

    Component personalInfoLayout() {

        JPanel jPanel = new JPanel();
        System.out.println("inside personalInfoLayout");

        GroupLayout layout = new GroupLayout(jPanel);
        jPanel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(firstNameL)
                                                        .addComponent(lastNameL)
                                                        .addComponent(passwordL)
                                                        .addComponent(phoneNumL)
                                                        .addComponent(addressL)
                                                        .addComponent(currentExpensesL)
                                                        .addComponent(empNameL)
                                                        .addComponent(empAddressL)
                                                        .addComponent(paySlipsL)
                                                        .addComponent(workingFromL)
                                                        .addComponent(salaryL)
                                                        .addComponent(taxNumberL))
                                                .addGap(58, 58, 58)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(firstName)
                                                        .addComponent(lastName)
                                                        .addComponent(password)
                                                        .addComponent(phoneNum)
                                                        .addComponent(address)
                                                        .addComponent(currentExpenses)
                                                        .addComponent(empName)
                                                        .addComponent(empAddress)
                                                        .addComponent(paySlips)
                                                        .addComponent(workingFromDatePicker)
                                                        .addComponent(salary)
                                                        .addComponent(taxNumber, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
                                                .addContainerGap())
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(editBtn)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(updateBtn)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(cancelBtn))
                                                        ))))
                                .addContainerGap(165, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(firstNameL)
                                        .addComponent(firstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lastNameL)
                                        .addComponent(lastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(passwordL)
                                        .addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(phoneNumL)
                                        .addComponent(phoneNum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(addressL)
                                        .addComponent(address, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(currentExpensesL)
                                        .addComponent(currentExpenses, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(empNameL)
                                        .addComponent(empName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(empAddressL)
                                        .addComponent(empAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(paySlipsL)
                                        .addComponent(paySlips, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(workingFromL)
                                        .addComponent(workingFromDatePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(salaryL)
                                        .addComponent(salary, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(taxNumberL)
                                        .addComponent(taxNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(editBtn))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(updateBtn)
                                        .addComponent(cancelBtn))
                                .addContainerGap(30, Short.MAX_VALUE))
        );

        paySlips.setFileFilter(filter);
        setTextFieldDetails();
        enableOrDisableTextFields(false);
        updateBtn.setVisible(false);
        cancelBtn.setVisible(false);

        updateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                updateBtnActionPerformed(evt, jPanel);
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                updateBtn.setVisible(false);
                cancelBtn.setVisible(false);

                setTextFieldDetails();
                enableOrDisableTextFields(false);
            }
        });

        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                updateBtn.setVisible(true);
                cancelBtn.setVisible(true);

                setTextFieldDetails();
                enableOrDisableTextFields(true);
            }
        });

        return jPanel;
    }

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt, JPanel jPanel) {

        String fName = firstName.getText();
        String lName = lastName.getText();
        String password = this.password.getText();
        String phNo = phoneNum.getText();
        String address = this.address.getText();
        int currentExpenses = Integer.parseInt(this.currentExpenses.getText());
        String employerName = this.empName.getText();
        String employerAddress = this.empAddress.getText();
        File paySlips = this.paySlips.getSelectedFile();
        Date workingFrom = (Date) this.workingFromDatePicker.getModel().getValue();
        String salary = this.salary.getText();
        String taxNumber = this.taxNumber.getText();
        boolean result = false;

        try {
            System.out.println("before update customer table");
            dBUtility.updateDbCustomerTable(fName, lName, password, phNo, address, currentExpenses, dBUtility.getUserId());
            dBUtility.updateDbEmployerTable(employerName, employerAddress, paySlips, workingFrom, salary, taxNumber);
            System.out.println("after update customer table");
            result = true;

        } catch (SQLException ex) {
            Logger.getLogger(CustomerRegistrationLayout.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (result) {
            enableOrDisableTextFields(false);
            updateBtn.setVisible(false);
            cancelBtn.setVisible(false);
            setTextFieldDetails();

        } else {
            enableOrDisableTextFields(true);
            JOptionPane.showMessageDialog(jPanel, "Error in updating");
        }
    }

    void enableOrDisableTextFields(boolean value) {

        firstName.setEditable(value);
        lastName.setEditable(value);
        password.setEditable(value);
        phoneNum.setEditable(value);
        address.setEditable(value);
        currentExpenses.setEditable(value);
        empName.setEditable(value);
        empAddress.setEditable(value);
        salary.setEditable(value);
        taxNumber.setEditable(value);
        workingFromDatePicker.setTextEditable(value);
        paySlips.setEnabled(value);
    }

    void setTextFieldDetails() {

        try {
            ResultSet rsCust = dBUtility.getCustomerDetailsById();
            ResultSet rsEmp = dBUtility.getEmployerDetailsById();

            rsCust.first();
            System.out.println("getCustomerDetailsById : " + rsCust.getString("FIRST_NAME"));
            rsCust.beforeFirst();
            rsEmp.isBeforeFirst();

            while (rsCust.next()) {
                System.out.println("inside rsCust while");

                firstName.setText(rsCust.getString("FIRST_NAME"));
                lastName.setText(rsCust.getString("LAST_NAME"));
                password.setText(rsCust.getString("PASSWORD"));
                phoneNum.setText(rsCust.getString("PHONE_NUMBER"));
                address.setText(rsCust.getString("ADDRESS"));
                currentExpenses.setText(rsCust.getInt("CURRENT_EXPENSES") + "");

                System.out.println("before rsCust while end");
            }

            while (rsEmp.next()) {
                System.out.println("inside rsEmp while");

                empName.setText(rsEmp.getString("EMPLOYER_NAME"));
                empAddress.setText(rsEmp.getString("EMPLOYER_ADDRESS"));
                salary.setText(rsEmp.getString("SALARY"));
                taxNumber.setText(rsEmp.getString("TAX_NUMBER"));
                workingFromModel.setValue(rsEmp.getDate("WORKING_FROM"));
                paySlips.setSelectedFile(dBUtility.readLOB());

                System.out.println("before rsEmp while end");
            }
        } catch (SQLException e) {
            Logger.getLogger(CustomerRegistrationLayout.class.getName()).log(Level.SEVERE, null, e);
        }
        /* int returnVal = salarySlips.showOpenDialog(jPanel);
    if(returnVal == JFileChooser.APPROVE_OPTION) {
       System.out.println("You chose to open this file: " +
            salarySlips.getSelectedFile().getName());
    }*/
    }

}

class DateLabelFormatter extends AbstractFormatter {

    private String datePattern = "dd/MM/yyyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }
}
