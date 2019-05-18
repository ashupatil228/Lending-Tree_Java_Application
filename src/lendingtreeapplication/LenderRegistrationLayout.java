/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendingtreeapplication;

import java.awt.LayoutManager;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author prith
 */
public class LenderRegistrationLayout {

    JTextField bankName = new JTextField();
    JPasswordField password = new JPasswordField();
    JTextField address = new JTextField();
    JPasswordField confirmPassword = new JPasswordField();
    JButton submitBtn = new JButton("Submit");
    JButton cancelBtn = new JButton("Cancel");
    JLabel bankNameL = new JLabel("Name of Bank :");
    JLabel addressL = new JLabel("Address :");
    JLabel phNoL = new JLabel("Phone No :");
    JLabel mailIdL = new JLabel("Email Id :");
    JLabel passwordL = new JLabel("Password :");
    JLabel confirmPasswordL = new JLabel("Renter Password :");
    JLabel ratingL = new JLabel("Bank Ratings :");
    JLabel financialStatusL = new JLabel("Current Financial Status :");
    JLabel jLabel9 = new JLabel();
    JTextField mailId = new JTextField();
    JTextField phNo = new JTextField();
    JTextField rating = new JTextField();
    JTextField financialStatus = new JTextField();

    public LayoutManager lenderRegistrationLayout(JPanel jPanel) {

        //HomePage.setKeyListenerToNumber(rating);
        
        jPanel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jPanel.setName("Lender Registration Form"); // NOI18N

        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                submitOnRegisterActionPerformed(evt, jPanel);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel9.setText("Lender Registration Form");

        GroupLayout layout = new GroupLayout(jPanel);
        jPanel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(bankNameL)
                                                                        .addComponent(addressL)
                                                                        .addComponent(phNoL)
                                                                        .addComponent(mailIdL)
                                                                        .addComponent(passwordL)
                                                                        .addComponent(confirmPasswordL)
                                                                        .addComponent(ratingL)
                                                                        .addComponent(financialStatusL))
                                                                .addGap(49, 49, 49)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(bankName)
                                                                        .addComponent(address)
                                                                        .addComponent(phNo)
                                                                        .addComponent(mailId)
                                                                        .addComponent(password)
                                                                        .addComponent(confirmPassword)
                                                                        .addComponent(rating)
                                                                        .addComponent(financialStatus)))
                                                        .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(submitBtn)
                                                                .addGap(37, 37, 37)
                                                                .addComponent(cancelBtn)
                                                                .addGap(73, 73, 73)))))
                                .addContainerGap(165, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
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
                                        .addComponent(mailIdL)
                                        .addComponent(mailId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
                                        .addComponent(ratingL)
                                        .addComponent(rating, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(financialStatusL)
                                        .addComponent(financialStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(submitBtn)
                                        .addComponent(cancelBtn))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        return layout;
    }

    private void submitOnRegisterActionPerformed(java.awt.event.ActionEvent evt, JPanel jPanel) {//GEN-FIRST:event_SubmitOnRegisterActionPerformed
        // TODO add your handling code here:
        DBUtility dBUtility = new DBUtility();
        String bankName = this.bankName.getText();
        String address = this.address.getText();
        String emailID = mailId.getText();
        String password = this.password.getText();
        String confirmPass = confirmPassword.getText();
        String phNo = this.phNo.getText();
        double rating = Double.parseDouble(this.rating.getText());
        String financialStatus = this.financialStatus.getText();
        boolean result = false;

        try {
            if (!dBUtility.checkRegisteredDetails(emailID, 2)) {
                if (password.equals(confirmPass)) {
                    confirmPass = password;
                    result = dBUtility.insertIntoDbLenderTable(bankName, address, emailID, password, phNo, rating, financialStatus);
                } else {
                    JOptionPane.showMessageDialog(jPanel, "Passwords donot match!! Renter password");
                }
            } else {
                JOptionPane.showMessageDialog(jPanel, "EmailId already exists!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerRegistrationLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.bankName.setText("");
        this.address.setText("");
        this.mailId.setText("");
        this.password.setText("");
        this.confirmPassword.setText("");
        this.phNo.setText("");
        this.rating.setText("");
        this.financialStatus.setText("");

        if (result) {
            HomePage.setTabBasedOnName("Login");
        }
    }//GEN-LAST:event_SubmitOnRegisterActionPerformed
}
