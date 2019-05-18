/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendingtreeapplication;

/**
 *
 * @author HP
 */
import java.awt.LayoutManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.*;
import javax.swing.*;

public class AdminCustomerMgmtLayout {

    DBUtility dBUtility = new DBUtility();

    JLabel jLabel1 = new JLabel("Administration of Customers");
    JLabel jLabel2 = new JLabel("First Name");
    JLabel jLabel3 = new JLabel("Last Name");
    JLabel jLabel4 = new JLabel("Email");
    JLabel jLabel5 = new JLabel("Password");
    JLabel jLabel7 = new JLabel("Phone Number ");
    JButton bAdd2 = new JButton("Add");
    JButton bUpdate2 = new JButton("Update");
    JButton bDelete2 = new JButton("Delete");
    JButton bCancel = new JButton("Cancel");
    JScrollPane jScrollPane2 = new JScrollPane();
    JTable tbCustAdmin;
    JTextField tfFirstName = new JTextField();
    JTextField tfLastName = new JTextField();
    JTextField tfEmail = new JTextField();
    JPasswordField tfPassword = new JPasswordField();
    JTextField tfPhone = new JTextField();
    JLabel jLabel8 = new JLabel();
    JLabel jLabel9 = new JLabel("Address");
    JTextField tfAddress = new JTextField();
    JLabel jLabel10 = new JLabel("Current Expenses");
    JTextField tfCurrentExpense = new JTextField();

    public LayoutManager adminCustomerMgmtLayout(final JPanel jPanel) {

        tfCurrentExpense.setToolTipText("Please enter amount in terms of euros");
        HomePage.setKeyListenerToNumber(tfCurrentExpense);
        
        String colName[] = {"First Name", "Last Name", "Email", "Password", "Phone Number", "Address", "Current Expenses"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(colName);

        try {

            ResultSet rs = dBUtility.getCustomerDetails();

            Object[] column = new Object[7];
            while (rs.next()) {
                int rowId = rs.getInt("CUSTOMER_ID");
                column[0] = rs.getString("FIRST_NAME");
                column[1] = rs.getString("LAST_NAME");
                column[2] = rs.getString("EMAIL_ID");
                column[3] = rs.getString("PASSWORD");
                column[4] = rs.getString("PHONE_NUMBER");
                column[5] = rs.getString("ADDRESS");
                column[6] = rs.getInt("CURRENT_EXPENSES");

                model.addRow(column);
                System.out.println(AdminCustomerMgmtLayout.class.getName() + " rowId " + rowId);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminCustomerMgmtLayout.class.getName()).log(Level.SEVERE, null, ex);
        }

        tbCustAdmin = new JTable(model){

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        bAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAdd2ActionPerformed(evt);
            }
        });

        bUpdate2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUpdate2ActionPerformed(evt);
            }
        });

        bDelete2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDelete2ActionPerformed(evt);
            }
        });
        
        bCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelActionPerformed(evt);
            }
        });

        tbCustAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCustAdminMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbCustAdmin);
        GroupLayout layout = new GroupLayout(jPanel);
        jPanel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(225, 225, 225)
                                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(19, 19, 19)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(jLabel5,javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel4)
                                                                        .addComponent(jLabel3)
                                                                        .addComponent(jLabel2)
                                                                        //.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel8)
                                                                                .addComponent(jLabel7)
                                                                                .addComponent(jLabel9)//javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addComponent(jLabel10))// javax.swing.GroupLayout.Alignment.TRAILING))
                                                                .addGap(43, 43, 43)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(tfFirstName,javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                                                        .addComponent(tfLastName)
                                                                        .addComponent(tfEmail)
                                                                        .addComponent(tfPassword)
                                                                        .addComponent(tfPhone)//javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                                                                        .addComponent(tfAddress)
                                                                        .addComponent(tfCurrentExpense))//javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
                                                                .addGap(133, 133, 133)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(bUpdate2)
                                                                        .addComponent(bAdd2)
                                                                        .addComponent(bDelete2)
                                                                        .addComponent(bCancel))))
                                                .addGap(0, 316, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 736, Short.MAX_VALUE)))
                                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel2)
                                                        .addComponent(tfFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(bAdd2))
                                                .addGap(18, 18, 18))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(tfLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel4)
                                                        .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(20, 20, 20))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(bUpdate2)
                                                .addGap(9, 9, 9)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(tfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(tfPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                         .addComponent(bDelete2))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(tfAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bCancel))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10)
                                        .addComponent(tfCurrentExpense, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        return layout;
        //  pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAdd2ActionPerformed
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String emailId = tfEmail.getText();
        String password = tfPassword.getText();
        String phNo = tfPhone.getText();
        String address = tfAddress.getText();
        int currentExpenses = Integer.parseInt(tfCurrentExpense.getText());

        try {
            boolean res = dBUtility.insertIntoDbCustomerTable(firstName, lastName, emailId, password, phNo, address, currentExpenses);

            if (res) {
                DefaultTableModel model = (DefaultTableModel) tbCustAdmin.getModel();
                model.addRow(new Object[]{firstName, lastName, emailId, password, phNo, address, currentExpenses});

                tfFirstName.setText("");
                tfLastName.setText("");
                tfEmail.setText("");
                tfPassword.setText("");
                tfPhone.setText("");
                tfAddress.setText("");
                tfCurrentExpense.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Unable to add the customer!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCustomerMgmtLayout.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_bAdd2ActionPerformed

    private void bUpdate2ActionPerformed(java.awt.event.ActionEvent evt) {
        try {

            tfEmail.setVisible(false);
            jLabel4.setVisible(false);

            int selectedRow = tbCustAdmin.getSelectedRow();

            String firstName = tfFirstName.getText();
            String lastName = tfLastName.getText();
            String password = tfPassword.getText();
            String phNo = tfPhone.getText();
            String address = tfAddress.getText();
            int currentExpenses = Integer.parseInt(tfCurrentExpense.getText());

            boolean res = dBUtility.updateDbCustomerTable(firstName, lastName, password, phNo, address, currentExpenses, selectedRow + 1);

            if (res) {
                DefaultTableModel model = (DefaultTableModel) tbCustAdmin.getModel();
                model.setValueAt(firstName, selectedRow, tbCustAdmin.getColumn("First Name").getModelIndex());
                model.setValueAt(lastName, selectedRow, tbCustAdmin.getColumn("Last Name").getModelIndex());
                model.setValueAt(password, selectedRow, tbCustAdmin.getColumn("Password").getModelIndex());
                model.setValueAt(phNo, selectedRow, tbCustAdmin.getColumn("Phone Number").getModelIndex());
                model.setValueAt(address, selectedRow, tbCustAdmin.getColumn("Address").getModelIndex());
                model.setValueAt(currentExpenses, selectedRow, tbCustAdmin.getColumn("Current Expenses").getModelIndex());

                tfEmail.setVisible(true);
                jLabel4.setVisible(true);

                tfFirstName.setText("");
                tfLastName.setText("");
                tfEmail.setText("");
                tfPassword.setText("");
                tfPhone.setText("");
                tfAddress.setText("");
                tfCurrentExpense.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Unable to update the details!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCustomerMgmtLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void tbCustAdminMouseClicked(java.awt.event.MouseEvent evt) {

        int selectedRow = tbCustAdmin.getSelectedRow();

        DefaultTableModel model = (DefaultTableModel) tbCustAdmin.getModel();

        tfFirstName.setText(model.getValueAt(selectedRow, tbCustAdmin.getColumn("First Name").getModelIndex()).toString());
        tfLastName.setText(model.getValueAt(selectedRow, tbCustAdmin.getColumn("Last Name").getModelIndex()).toString());
        tfEmail.setText(model.getValueAt(selectedRow, tbCustAdmin.getColumn("Email").getModelIndex()).toString());
        tfPassword.setText(model.getValueAt(selectedRow, tbCustAdmin.getColumn("Password").getModelIndex()).toString());
        tfPhone.setText(model.getValueAt(selectedRow, tbCustAdmin.getColumn("Phone Number").getModelIndex()).toString());
        tfAddress.setText(model.getValueAt(selectedRow, tbCustAdmin.getColumn("Address").getModelIndex()).toString());
        tfCurrentExpense.setText(model.getValueAt(selectedRow, tbCustAdmin.getColumn("Current Expenses").getModelIndex()).toString());
    }

    private void bDelete2ActionPerformed(java.awt.event.ActionEvent evt) {

        try {
            int selectedRow = tbCustAdmin.getSelectedRow();

            boolean res = dBUtility.deleteFromDbCustomersTable(selectedRow + 1);

            if (res) {
                DefaultTableModel model = (DefaultTableModel) tbCustAdmin.getModel();
                model.removeRow(selectedRow);
                tfFirstName.setText("");
                tfLastName.setText("");
                tfEmail.setText("");
                tfPassword.setText("");
                tfPhone.setText("");
                tfAddress.setText("");
                tfCurrentExpense.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Unable to delete the customer details!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCustomerMgmtLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {
                tfFirstName.setText("");
                tfLastName.setText("");
                tfEmail.setText("");
                tfPassword.setText("");
                tfPhone.setText("");
                tfAddress.setText("");
                tfCurrentExpense.setText("");
     }
     
}
