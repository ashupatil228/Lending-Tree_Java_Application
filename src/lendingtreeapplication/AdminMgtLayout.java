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
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminMgtLayout {

    DBUtility dBUtility = new DBUtility();

    JScrollPane jScrollPane = new JScrollPane();
    JTable tbAdmins;
    JLabel emailL = new JLabel("Email: ");
    JLabel passwordL = new JLabel("Password: ");
    JLabel nameL = new JLabel("Name: ");
    JTextField name = new JTextField();
    JTextField email = new JTextField();
    JPasswordField password = new JPasswordField();
    JButton addBtn = new JButton("Add");
    JButton deleteBtn = new JButton("Delete");
    JButton cancelBtn = new JButton("Cancel");

    public LayoutManager adminMgtLayout(JPanel jPanel) {

        String colName[] = {"Name", "Email", "Password"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(colName);

        try {

            ResultSet rs = dBUtility.getAdminDetails();

            Object[] column = new Object[3];
            while (rs.next()) {
                int rowId = rs.getInt("ADMIN_ID");
                column[0] = rs.getString("NAME");
                column[1] = rs.getString("EMAIL_ID");
                column[2] = rs.getString("PASSWORD");

                model.addRow(column);
                System.out.println(AdminMgtLayout.class.getName() + " rowId " + rowId);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminCustomerMgmtLayout.class.getName()).log(Level.SEVERE, null, ex);
        }

        tbAdmins = new JTable(model){

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jScrollPane.setViewportView(tbAdmins);

        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(jPanel);
        jPanel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(129, 129, 129)
                                                .addComponent(addBtn)
                                                .addComponent(deleteBtn)
                                                .addComponent(cancelBtn))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(22, 22, 22)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(emailL, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(passwordL, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                                                        .addComponent(nameL))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(email)
                                                        .addComponent(password, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                                        .addComponent(name))))
                                .addContainerGap(117, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(nameL)
                                        .addComponent(name))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(emailL)
                                        .addComponent(email))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(password)
                                        .addComponent(passwordL))
                                .addGap(18, 18, 18)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(addBtn)
                                .addComponent(deleteBtn)
                                .addComponent(cancelBtn))
                                                         
                                .addGap(18, 18, 18)
                             
                        .addComponent(jScrollPane))
        );
        return layout;
    }

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAdd2ActionPerformed
        String name = this.name.getText();
        String emailId = email.getText();
        String password = this.password.getText();

        try {
            boolean res = dBUtility.insertIntoDbAdminTable(name, emailId, password);

            if (res) {
                DefaultTableModel model = (DefaultTableModel) tbAdmins.getModel();
                model.addRow(new Object[]{name, emailId, password});
            } else {
                JOptionPane.showMessageDialog(null, "Unable to add the customer!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCustomerMgmtLayout.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_bAdd2ActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {

        try {
            int selectedRow = tbAdmins.getSelectedRow();

            boolean res = dBUtility.deleteFromDbAdminTable(selectedRow + 1);

            if (res) {
                DefaultTableModel model = (DefaultTableModel) tbAdmins.getModel();
                model.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(null, "Unable to delete the admin details!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCustomerMgmtLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt){
        email.setText("");
        password.setText("");
        name.setText("");
    }

}
