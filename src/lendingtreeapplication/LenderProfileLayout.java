/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendingtreeapplication;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author prith
 */
public class LenderProfileLayout {

    DBUtility dBUtility = new DBUtility();

    JTextField bankName = new JTextField();
    JPasswordField password = new JPasswordField();
    JTextField address = new JTextField();
    JButton saveBtn = new JButton("Save");
    JButton editBtn = new JButton("Edit");
    JButton cancelBtn = new JButton("Cancel");
    JLabel bankNameL = new JLabel("Name of Bank :");
    JLabel addressL = new JLabel("Address :");
    JLabel phNoL = new JLabel("Phone No :");
    JLabel mailIdL = new JLabel("Email Id :");
    JLabel passwordL = new JLabel("Password :");
    JLabel ratingL = new JLabel("Bank Ratings :");
    JLabel financialStatusL = new JLabel("Current Financial Status :");
    JTextField mailId = new JTextField();
    JTextField phNo = new JTextField();
    JTextField rating = new JTextField();
    JTextField financialStatus = new JTextField();

    public LayoutManager lenderProfileLayout(JPanel jPanel) {

        //HomePage.setKeyListenerToNumber(rating);
        
        jPanel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jPanel.setName("Lender Registration Form"); // NOI18N

        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                saveChangesActionPerformed(evt, jPanel);
            }
        });

        editBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                saveBtn.setVisible(true);
                cancelBtn.setVisible(true);

                setTextFieldDetails();
                enableOrDisableTextFields(true);
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                saveBtn.setVisible(false);
                cancelBtn.setVisible(false);

                setTextFieldDetails();
                enableOrDisableTextFields(false);
            }
        });

        setTextFieldDetails();
        enableOrDisableTextFields(false);
        saveBtn.setVisible(false);
        cancelBtn.setVisible(false);

        GroupLayout layout = new GroupLayout(jPanel);
        jPanel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(bankNameL)
                                                        .addComponent(addressL)
                                                        .addComponent(phNoL)
                                                        .addComponent(passwordL)
                                                        .addComponent(ratingL)
                                                        .addComponent(financialStatusL))
                                                .addGap(58, 58, 58)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(bankName)
                                                        .addComponent(address)
                                                        .addComponent(phNo)
                                                        .addComponent(password)
                                                        .addComponent(rating)
                                                        .addComponent(financialStatus))
                                                .addContainerGap())
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(editBtn)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(saveBtn)
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
                                        .addComponent(bankNameL)
                                        .addComponent(bankName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(addressL)
                                        .addComponent(address, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(phNoL)
                                        .addComponent(phNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(passwordL)
                                        .addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(ratingL)
                                        .addComponent(rating, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(financialStatusL)
                                        .addComponent(financialStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(editBtn))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(saveBtn)
                                        .addComponent(cancelBtn))
                                .addContainerGap(30, Short.MAX_VALUE))
        );

        return layout;
    }

    private void saveChangesActionPerformed(java.awt.event.ActionEvent evt, JPanel jPanel) {//GEN-FIRST:event_SubmitOnRegisterActionPerformed
        // TODO add your handling code here:
        String bankName = this.bankName.getText();
        String address = this.address.getText();
        String password = this.password.getText();
        String phNo = this.phNo.getText();
        double rating = Double.parseDouble(this.rating.getText());
        String financialStatus = this.financialStatus.getText();
        boolean result = false;

        try {
            System.out.println("before update customer table");
            dBUtility.updateDbLenderTable(bankName, address, password, phNo, rating, financialStatus, dBUtility.getUserId());
            System.out.println("after update customer table");
            result = true;

        } catch (SQLException ex) {
            Logger.getLogger(CustomerRegistrationLayout.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (result) {
            setTextFieldDetails();
            enableOrDisableTextFields(false);
            saveBtn.setVisible(false);
            cancelBtn.setVisible(false);
        } else {
            enableOrDisableTextFields(true);
            JOptionPane.showMessageDialog(jPanel, "Error in updating");
        }
    }

    void enableOrDisableTextFields(boolean value) {

        bankName.setEditable(value);
        password.setEditable(value);
        address.setEditable(value);
        phNo.setEditable(value);
        rating.setEditable(value);
        financialStatus.setEditable(value);
    }

    void setTextFieldDetails() {

        try {
            ResultSet rs = dBUtility.getLenderDetailsById();

            rs.first();
            System.out.println("getLenderDetailsById : " 
                    + rs.getString("NAME_OF_LENDER") + " "
                    + rs.getString("ADDRESS") + " "
                    + rs.getString("PASSWORD") + " "
                    + rs.getString("PHONE_NUMBER") + " "
                    + rs.getFloat("BANK_RATING") + " "
                    + rs.getString("CURRENT_FINANCIAL_STATUS"));
            
            rs.beforeFirst();
            while (rs.next()) {
                System.out.println("inside while");

                bankName.setText(rs.getString("NAME_OF_LENDER"));
                address.setText(rs.getString("ADDRESS"));
                password.setText(rs.getString("PASSWORD"));
                phNo.setText(rs.getString("PHONE_NUMBER"));
                rating.setText(rs.getFloat("BANK_RATING") + "");
                financialStatus.setText(rs.getString("CURRENT_FINANCIAL_STATUS"));

                System.out.println("before while end");
            }
        } catch (SQLException e) {
            Logger.getLogger(CustomerRegistrationLayout.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
