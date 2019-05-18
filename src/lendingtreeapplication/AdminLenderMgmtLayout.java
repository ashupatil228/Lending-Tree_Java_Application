/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendingtreeapplication;

/**
 *
 * @author User
 */
import java.awt.LayoutManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminLenderMgmtLayout {

    DBUtility dBUtility = new DBUtility();

    JLabel jLabel1 = new JLabel("Administration of Banks");
    JTable tbBankAdmin;
    JLabel jLabel2 = new JLabel("Bank Name");
    JTextField tfBankName = new JTextField();
    JLabel jLabel3 = new JLabel("Bank Address");
    JTextField tfBankAddress = new JTextField();
    JLabel jLabel7 = new JLabel("Email Id");
    JTextField tfEmailId = new JTextField();
    JLabel jLabel8 = new JLabel("Password");
    JPasswordField tfPassword = new JPasswordField();
    JLabel jLabel4 = new JLabel("Contact Details");
    JTextField tfContact = new JTextField();
    JLabel jLabel5 = new JLabel("Bank Rating");
    JTextField tfBankRating = new JTextField();
    JLabel jLabel6 = new JLabel("Current Financial Status");
    JTextField tfCFS = new JTextField();
    JButton bAdd = new JButton("Add");
    JButton bUpdate = new JButton("Update");
    JButton bDelete = new JButton("Delete");
    JButton bCancel = new JButton("Cancel");
    JScrollPane jScrollPane2 = new JScrollPane();

    public LayoutManager adminLenderMgmtLayout(final JPanel jPanel) {

        tfBankRating.setToolTipText("Please enter amount in terms of euros");
        //HomePage.setKeyListenerToNumber(tfBankRating);
        
        String colName[] = {"Bank Name", "Bank Address", "Email", "Password", "Phone Number", "Bank Rating", "Current Financial Status"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(colName);

        try {

            ResultSet rs = dBUtility.getLenderDetails();

            Object[] column = new Object[7];
            while (rs.next()) {
                int rowId = rs.getInt("LENDER_ID");
                column[0] = rs.getString("NAME_OF_LENDER");
                column[1] = rs.getString("ADDRESS");
                column[2] = rs.getString("EMAIL_ID");
                column[3] = rs.getString("PASSWORD");
                column[4] = rs.getString("PHONE_NUMBER");
                column[5] = rs.getString("BANK_RATING");
                column[6] = rs.getString("CURRENT_FINANCIAL_STATUS");

                model.addRow(column);
                System.out.println(AdminLenderMgmtLayout.class.getName() + " rowId " + rowId);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminCustomerMgmtLayout.class.getName()).log(Level.SEVERE, null, ex);
        }

        tbBankAdmin = new JTable(model){

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jScrollPane2.setViewportView(tbBankAdmin);

        tbBankAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBankAdminMouseClicked(evt);
            }
        });

        bAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddActionPerformed(evt);
            }
        });

        bUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUpdateActionPerformed(evt);
            }
        });

        bDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteActionPerformed(evt);
            }
        });
        bCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(jPanel);
        jPanel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(188, 188, 188)
                                                .addComponent(jLabel1))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(59, 59, 59)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel3)
                                                                        .addComponent(jLabel7)
                                                                        .addComponent(jLabel8)
                                                                        .addComponent(jLabel4)
                                                                        .addComponent(jLabel5))
                                                                .addGap(60, 60, 60))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel6)
                                                                .addGap(18, 18, 18)))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(tfBankName, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                                        .addComponent(tfBankAddress)
                                                        .addComponent(tfEmailId)
                                                        .addComponent(tfPassword)
                                                        .addComponent(tfContact)
                                                        .addComponent(tfBankRating)
                                                        .addComponent(tfCFS))
                                                .addGap(107, 107, 107)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(bAdd)
                                                        .addComponent(bUpdate)
                                                        .addComponent(bDelete)
                                                        .addComponent(bCancel))))
                                .addGap(125, 125, 125))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(tfBankName, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                                        .addComponent(bAdd))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel3)
                                                        .addComponent(tfBankAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(26, 26, 26)
                                                .addComponent(bUpdate)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel7)
                                                        .addComponent(tfEmailId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel8)
                                                        .addComponent(tfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel4)
                                                        .addComponent(tfContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(tfBankRating, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel5)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(35, 35, 35)
                                                .addComponent(bDelete)))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(tfCFS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6)
                                        .addComponent(bCancel))
                                        .addGap(18,18,18)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap())
                               
        );

        return layout;
    }

    private void bAddActionPerformed(java.awt.event.ActionEvent evt) {
        String bankName = tfBankName.getText();
        String address = tfBankAddress.getText();
        String emailId = tfEmailId.getText();
        String password = tfPassword.getText();
        String phNo = tfContact.getText();
        float bankRating = Float.parseFloat(tfBankRating.getText());
        String financialStatus = tfCFS.getText();

        try {
            boolean res = dBUtility.insertIntoDbLenderTable(bankName, address, emailId, password, phNo, bankRating, financialStatus);

            if (res) {
                DefaultTableModel model = (DefaultTableModel) tbBankAdmin.getModel();
                model.addRow(new Object[]{bankName, address, emailId, password, phNo, bankRating, financialStatus});

                tfBankName.setText("");
                tfBankAddress.setText("");
                tfEmailId.setText("");
                tfPassword.setText("");
                tfContact.setText("");
                tfBankRating.setText("");
                tfCFS.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Unable to add the customer!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCustomerMgmtLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void bUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        try {

            tfEmailId.setVisible(false);
            jLabel7.setVisible(false);

            int selectedRow = tbBankAdmin.getSelectedRow();

            String bankName = tfBankName.getText();
            String address = tfBankAddress.getText();
            String password = tfPassword.getText();
            String phNo = tfContact.getText();
            float bankRating = Float.parseFloat(tfBankRating.getText());
            String financialStatus = tfCFS.getText();

            boolean res = dBUtility.updateDbLenderTable(bankName, address, password, phNo, bankRating, financialStatus, selectedRow + 1);

            if (res) {
                DefaultTableModel model = (DefaultTableModel) tbBankAdmin.getModel();
                model.setValueAt(bankName, selectedRow, tbBankAdmin.getColumn("Bank Name").getModelIndex());
                model.setValueAt(address, selectedRow, tbBankAdmin.getColumn("Bank Address").getModelIndex());
                model.setValueAt(password, selectedRow, tbBankAdmin.getColumn("Password").getModelIndex());
                model.setValueAt(phNo, selectedRow, tbBankAdmin.getColumn("Phone Number").getModelIndex());
                model.setValueAt(bankRating, selectedRow, tbBankAdmin.getColumn("Bank Rating").getModelIndex());
                model.setValueAt(financialStatus, selectedRow, tbBankAdmin.getColumn("Current Financial Status").getModelIndex());

                tfEmailId.setVisible(true);
                jLabel7.setVisible(true);

                tfBankName.setText("");
                tfBankAddress.setText("");
                tfEmailId.setText("");
                tfPassword.setText("");
                tfContact.setText("");
                tfBankRating.setText("");
                tfCFS.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Unable to update the details!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCustomerMgmtLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void tbBankAdminMouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = tbBankAdmin.getSelectedRow();

        DefaultTableModel model = (DefaultTableModel) tbBankAdmin.getModel();

        tfBankName.setText(model.getValueAt(selectedRow, tbBankAdmin.getColumn("Bank Name").getModelIndex()).toString());
        tfBankAddress.setText(model.getValueAt(selectedRow, tbBankAdmin.getColumn("Bank Address").getModelIndex()).toString());
        tfEmailId.setText(model.getValueAt(selectedRow, tbBankAdmin.getColumn("Email").getModelIndex()).toString());
        tfPassword.setText(model.getValueAt(selectedRow, tbBankAdmin.getColumn("Password").getModelIndex()).toString());
        tfContact.setText(model.getValueAt(selectedRow, tbBankAdmin.getColumn("Phone Number").getModelIndex()).toString());
        tfBankRating.setText(model.getValueAt(selectedRow, tbBankAdmin.getColumn("Bank Rating").getModelIndex()).toString());
        tfCFS.setText(model.getValueAt(selectedRow, tbBankAdmin.getColumn("Current Financial Status").getModelIndex()).toString());

    }

    private void bDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int selectedRow = tbBankAdmin.getSelectedRow();

            boolean res = dBUtility.deleteFromDbLendersTable(selectedRow + 1);

            if (res) {
                DefaultTableModel model = (DefaultTableModel) tbBankAdmin.getModel();
                model.removeRow(selectedRow);

                tfBankName.setText("");
                tfBankAddress.setText("");
                tfEmailId.setText("");
                tfPassword.setText("");
                tfContact.setText("");
                tfBankRating.setText("");
                tfCFS.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Unable to delete the lender details!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCustomerMgmtLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void bCancelActionPerformed(java.awt.event.ActionEvent evt) { 
                tfBankName.setText("");
                tfBankAddress.setText("");
                tfEmailId.setText("");
                tfPassword.setText("");
                tfContact.setText("");
                tfBankRating.setText("");
                tfCFS.setText("");
        
    }
}
