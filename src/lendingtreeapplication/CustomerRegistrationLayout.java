/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendingtreeapplication;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author prith
 */
public class CustomerRegistrationLayout {

    // Variables declaration - do not modify//GEN-BEGIN:variables
    final JPasswordField password = new JPasswordField();
    final JPasswordField confirmPassword = new JPasswordField();
    final JButton SubmitOnRegister = new JButton("Register");
    final JTextField emailId = new JTextField();
    final JTextField firstName = new JTextField();
    final JButton cancelBtn = new JButton("Cancel");
    final JLabel jLabel1 = new JLabel("jLabel1");
    final JLabel firstNameL = new JLabel("First Name:");
    final JLabel lastNameL = new JLabel("Last Name:");
    final JLabel emailIdL = new JLabel("Email:");
    final JLabel passwordL = new JLabel("Password:");
    final JLabel confirmPasswordL = new JLabel("Confirm Password:");
    final JLabel phoneNumL = new JLabel("Phone no:");
    final JLabel addressL = new JLabel("Address:");
    final JLabel currentExpensesL = new JLabel("Current Expenses:");
    final JLabel jLabel8 = new JLabel();
    final JTextField lastName = new JTextField();
    final JTextField phoneNum = new JTextField();
    final JTextField address = new JTextField();
    final JTextField currentExpenses = new JTextField();
    // End of variables declaration//GEN-END:variables

    public LayoutManager customerRegistrationLayout(JPanel jPanel) {

        HomePage.setKeyListenerToNumber(currentExpenses);
        
        jPanel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jPanel.setName("Customer Registration Form"); // NOI18N

        SubmitOnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitOnRegisterActionPerformed(evt, jPanel);
            }
        });

        currentExpenses.setToolTipText("Enter in terms of Euros");
        
        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel8.setText("Customer Registration Form");

        GroupLayout layout = new GroupLayout(jPanel);
        jPanel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(firstNameL)
                                                                        .addComponent(lastNameL)
                                                                        .addComponent(emailIdL)
                                                                        .addComponent(passwordL)
                                                                        .addComponent(confirmPasswordL)
                                                                        .addComponent(phoneNumL)
                                                                        .addComponent(addressL)
                                                                        .addComponent(currentExpensesL))
                                                                .addGap(49, 49, 49)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(firstName)
                                                                        .addComponent(lastName)
                                                                        .addComponent(emailId)
                                                                        .addComponent(password)
                                                                        .addComponent(confirmPassword)
                                                                        .addComponent(phoneNum)
                                                                        .addComponent(address)
                                                                        .addComponent(currentExpenses)))
                                                        .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGap(37, 37, 37)
                                                                .addComponent(SubmitOnRegister)
                                                                .addGap(73, 73, 73)
                                                                .addComponent(cancelBtn)))))
                                .addContainerGap(165, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
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
                                        .addComponent(emailIdL)
                                        .addComponent(emailId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(passwordL)
                                        .addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(confirmPasswordL)
                                        .addComponent(confirmPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(SubmitOnRegister)
                                        .addComponent(cancelBtn))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        return layout;
    }

    private void submitOnRegisterActionPerformed(java.awt.event.ActionEvent evt, JPanel jPanel) {//GEN-FIRST:event_SubmitOnRegisterActionPerformed
        // TODO add your handling code here:
        DBUtility dBUtility = new DBUtility();
        String fName = firstName.getText();
        String lName = lastName.getText();
        String emailID = emailId.getText();
        String password = this.password.getText();
        String confirmPass = confirmPassword.getText();
        String phNo = phoneNum.getText();
        String address = this.address.getText();
        int currentExpenses = Integer.parseInt(this.currentExpenses.getText());
        boolean result = false;

        try {
            if (!dBUtility.checkRegisteredDetails(emailID, 1)) {
                if (password.equals(confirmPass)) {
                    confirmPass = password;
                    result = dBUtility.insertIntoDbCustomerTable(fName, lName, emailID, confirmPass, phNo, address, currentExpenses);

                } else {
                    JOptionPane.showMessageDialog(jPanel, "Passwords donot match!! Renter password");
                }
            } else {
                JOptionPane.showMessageDialog(jPanel, "EmailId already exists!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerRegistrationLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
        firstName.setText("");
        lastName.setText("");
        emailId.setText("");
        this.password.setText("");
        confirmPassword.setText("");
        phoneNum.setText("");
        this.address.setText("");
        this.currentExpenses.setText("");

        if (result) {
            HomePage.setTabBasedOnName("Login");
        }
    }//GEN-LAST:event_SubmitOnRegisterActionPerformed

}
