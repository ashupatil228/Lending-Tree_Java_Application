/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendingtreeapplication;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Sameeksha
 */
public class CustomerApplicationStatusLayout {

    String statusOfLoanS[] = {"Pending", "Processing", "Completed"};
    private JPanel pendingJPanel = new JPanel();
    private JPanel processingJPanel = new JPanel();
    private JPanel completedJPanel = new JPanel();
    private JTabbedPane jTabbedPane5 = new JTabbedPane();
    DBUtility dBUtility = new DBUtility();

    public void CustomerApplicationStatusLayout(JPanel jPanel) {

        jTabbedPane5.addTab(statusOfLoanS[0], pendingJPanel);
        jTabbedPane5.addTab(statusOfLoanS[1], processingJPanel);
        jTabbedPane5.addTab(statusOfLoanS[2], completedJPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jTabbedPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jTabbedPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE))
        );
        createtable(pendingJPanel, 0);
        createtable(processingJPanel, 1);
        createtable(completedJPanel, 2);
    }

    Component createtable(JPanel loanListPanel, int statusOfLoanColumn) {

        loanListPanel.removeAll();
        ArrayList<CustomerLoanDetailsListViewItem> loanList = new ArrayList<>();

        JTable table = new JTable(getCustomerApplicationsTableModel(loanList, statusOfLoanColumn)) {
            public Class getColumnClass(int c) {
                return getValueAt(0, c).getClass();
            }
        };
        setUpStatusColumn(table, table.getColumnModel().getColumn(3), loanList, statusOfLoanColumn);
        JScrollPane scrollPane = new JScrollPane(table);

        loanListPanel.add(scrollPane);
        loanListPanel.setLayout(new GridLayout(1, 1));

        //Column.setCellEditor(new DefaultCellEditor(comboBox));
        return loanListPanel;
    }

    public void setUpStatusColumn(JTable table, TableColumn statusColumn, ArrayList<CustomerLoanDetailsListViewItem> loanList, int statusOfLoanColumn) {
        //Set up the editor for the sport cells.
        JComboBox comboBox = new JComboBox();
        comboBox.addItem(statusOfLoanS[0]);
        comboBox.addItem(statusOfLoanS[1]);
        comboBox.addItem(statusOfLoanS[2]);
        statusColumn.setCellEditor(new DefaultCellEditor(comboBox));
        
        if(statusOfLoanColumn == 0) {
            comboBox.setSelectedItem(statusOfLoanS[0]);
        } else if(statusOfLoanColumn == 1) {
            comboBox.setSelectedItem(statusOfLoanS[1]);
        } else if(statusOfLoanColumn == 2) {
            comboBox.setSelectedItem(statusOfLoanS[2]);
        }
        
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent evt) {

                Object item = evt.getItem();

                if (evt.getStateChange() == ItemEvent.SELECTED) {
                    try {
                        // Item was just selected
                        comboBox.setSelectedItem(item);
                        CustomerLoanDetailsListViewItem viewItem = loanList.get(table.getSelectedRow());
                        int loanId = viewItem.getLoanId();
                        int statusOfLoan = -1;
                        if (item.equals(statusOfLoanS[0])) {
                            statusOfLoan = 0;
                        } else if (item.equals(statusOfLoanS[1])) {
                            statusOfLoan = 1;
                        } else if (item.equals(statusOfLoanS[2])) {
                            statusOfLoan = 2;
                        }

                        boolean result = dBUtility.updateStatusOfLoan(loanId, statusOfLoan);
                        if (result) {
                            createtable(pendingJPanel, 0);
                            createtable(processingJPanel, 1);
                            createtable(completedJPanel, 2);

                            //String tabName = jTabbedPane5.getTitleAt(jTabbedPane5.getSelectedIndex());
                        } else {
                            JOptionPane.showMessageDialog(jTabbedPane5, "Unable to update the status of the application. Try later.");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(CustomerApplicationStatusLayout.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if (evt.getStateChange() == ItemEvent.DESELECTED) {
                    // Item is no longer selected
                }
            }
        });

        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer
                = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        statusColumn.setCellRenderer(renderer);
    }

    DefaultTableModel getCustomerApplicationsTableModel(ArrayList<CustomerLoanDetailsListViewItem> loanList, int statusOfLoanColumn) {
        try {

            ResultSet rs = dBUtility.getCustomersLoanListByLenderId(statusOfLoanColumn);

            while (rs.next()) {
                int LoanId = rs.getInt("CUSTOMER_LOAN_ID");
                String LoanName = rs.getString("LOAN_NAME");
                int lenderLoanId = rs.getInt("LENDER_LOAN_ID");
                int CustomerId = rs.getInt("CUSTOMER_ID");
                int LenderId = rs.getInt("LENDER_ID");
                int LoanType = rs.getInt("LOAN_TYPE");
                double Rateofinterest = rs.getDouble("RATE_OF_INTEREST");
                double Tenureperiod = rs.getDouble("TENURE_PERIOD");
                int Loanamounttaken = rs.getInt("LOAN_AMOUNT_TAKEN");
                int Statusofloan = rs.getInt("STATUS_OF_LOAN");

                CustomerLoanDetailsListViewItem item = new CustomerLoanDetailsListViewItem(LoanId, lenderLoanId, LoanName,
                        CustomerId, LenderId, LoanType, Rateofinterest, Tenureperiod, Loanamounttaken, Statusofloan);
                //listModel.addElement(item);
                loanList.add(item);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TabContentPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        String colName[] = {"Customer Name", "Loan Type", "Loan Amount Requested", "Status Of Loan"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(colName);
        Object[] row = new Object[4];

        for (int i = 0; i < loanList.size(); i++) {
            try {
                row[0] = dBUtility.getCustomerNameById(loanList.get(i).getCustomerId());
                row[1] = dBUtility.getLoanTypeNameBasedOnId(loanList.get(i).getLoanType());
                row[2] = loanList.get(i).getLoanamounttaken();
                int stOfLoan = loanList.get(i).getStatusofloan();
                row[3] = statusOfLoanS[stOfLoan];
                model.addRow(row);
            } catch (SQLException ex) {
                Logger.getLogger(CustomerApplicationStatusLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return model;
    }
}
