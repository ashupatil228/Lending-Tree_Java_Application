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
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.*;

public class ButtonColumn extends AbstractCellEditor
        implements TableCellRenderer, TableCellEditor, ActionListener {

    DBUtility dBUtility = new DBUtility();
    ArrayList<LoanDetailsListViewItem> loanList;
    JTable table;
    JButton renderButton;
    JButton editButton;
    String text;
    JPanel jPanel;

    public ButtonColumn(JTable table, int column, ArrayList<LoanDetailsListViewItem> loanList, JPanel jPanel) {
        super();
        this.table = table;
        this.loanList = loanList;
        this.jPanel = jPanel;
        renderButton = new JButton();

        editButton = new JButton();
        editButton.setFocusPainted(false);
        editButton.addActionListener(this);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(this);
        columnModel.getColumn(column).setCellEditor(this);

        System.out.println("buttonColumn constructor = " + column + " " + loanList.size());
    }

    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (hasFocus) {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        } else if (isSelected) {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        } else {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }

        renderButton.setText((value == null) ? "" : value.toString());
        return renderButton;
    }

    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {
        text = (value == null) ? "" : value.toString();
        editButton.setText(text);
        return editButton;
    }

    public Object getCellEditorValue() {
        return text;
    }

    public void actionPerformed(ActionEvent e) {

        String buttonName = editButton.getText();

        System.out.println("lendingtreeapplication.ButtonColumn.actionPerformed()");

        if (buttonName.equals("Interested")) {
            onClickInterestedBtn();
        } else if (buttonName.equals("Edit")) {
            onClickEditBtn();
        } else if (buttonName.equals("Delete")) {
            onClickDeleteBtn();
        }
    }

    void onClickInterestedBtn() {
        try {

            if (dBUtility.getUserId() != -1) {
                ResultSet rs = dBUtility.getEmployerDetailsById();
                rs.last();
                int count = rs.getRow();

                if (count <= 0) {
                    JPanel custDetails = new JPanel();
                    fireEditingStopped();
                    CustomerEmployerLayout cel = new CustomerEmployerLayout();
                    cel.customerEmpDetails(custDetails);
                } else {
                    LoanDetailsListViewItem item = loanList.get(table.getSelectedRow());
                    int lenderLoanId = item.getLoanId();
                    String loanName = item.getLoanName();
                    int customerId = dBUtility.getUserId();
                    int lenderId = item.getLenderId();
                    int loanType = item.getLoanType();
                    double rateOfInterest = item.getRateOfInterest();
                    double tenurePeriod = item.getTenurePeriod();
                    int loanAmtTaken = item.getMaxAmountProvided();
                    int statusOfLoan = 0;

                    boolean checkLoanInserted = dBUtility.checkLoanInsertedCustomer(lenderLoanId, customerId, lenderId);

                    if (!checkLoanInserted) {

                        boolean result = dBUtility.insertIntoDbCustomersLoanListTable(lenderLoanId, loanName, customerId, lenderId, loanType,
                                rateOfInterest, tenurePeriod, loanAmtTaken, statusOfLoan);

                        if (result) {
                            JOptionPane.showMessageDialog(null, "Your request is sent to bank");
                            CustomerProfileLayout cpl = new CustomerProfileLayout();
                            cpl.setTabComponent();
                            //DefaultTableModel model = (DefaultTableModel) cpl.jTable.getModel();
                            //model.addRow(new Object[]{loanName, dBUtility.getLoanTypeNameBasedOnId(loanType), rateOfInterest, tenurePeriod, loanAmtTaken, "Pending"});
                        } else {
                            JOptionPane.showMessageDialog(null, "Error occurred while sending details. Please try again");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Your have already requested! Please check your profile for status of loan");
                    }
                }
            } else {
                HomePage.setTabBasedOnName("Login");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ButtonColumn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void onClickDeleteBtn() {
        try {
            System.out.println("delete btn clicked");

            LoanDetailsListViewItem item = loanList.get(table.getSelectedRow());
            int loanId = item.getLoanId();
            int lenderId = item.getLenderId();

            System.out.println("delete btn loanid = " + loanId);
            boolean result = dBUtility.deleteDbLendersLoanBasedOnId(loanId, lenderId);

            if (result) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.removeRow(table.getSelectedRow());

            } else {
                JOptionPane.showMessageDialog(null, "Error occurred while deleting details. Please try again");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ButtonColumn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void onClickEditBtn() {
        try {
            System.out.println("edit btn clicked");

            LoanDetailsListViewItem item = loanList.get(table.getSelectedRow());
            //int loanId = item.getLoanId();
            //int lenderId = item.getLenderId();
            double rateOfInterest = item.getRateOfInterest();
            int loanType = item.getLoanType();
            String loanName = item.getLoanName();
            int maxAmtProvided = item.getMaxAmountProvided();
            double tenurePeriod = item.getTenurePeriod();

            HashMap<String, JTextField> hmTextField = new HashMap<>();
            HashMap<String, JButton> hmBtn = new HashMap<>();
            JComboBox<String> loanTypesCB = null;
            Component[] components = jPanel.getComponents();

            for (Component component : components) {
                if (component.getClass().equals(JTextField.class)) {
                    hmTextField.put(component.getName(), (JTextField) component);
                } else if (component.getClass().equals(JButton.class)) {
                    hmBtn.put(component.getName(), (JButton) component);
                } else if (component.getClass().equals(JComboBox.class)) {
                    loanTypesCB = (JComboBox) component;
                }
            }

            System.out.println("listTextField size = " + hmTextField.size() + ", listBtn size = " + hmBtn.size() + ", loanTypesCB =" + loanTypesCB.getName());
            System.out.println("hmTextField = " + hmTextField.get("loanName"));

            loanTypesCB.setSelectedItem(dBUtility.getLoanTypeNameBasedOnId(loanType));

            for (String keyName : hmTextField.keySet()) {
                if (hmTextField.get(keyName).getName().equals("loanName")) {
                    hmTextField.get(keyName).setText(loanName);
                } else if (hmTextField.get(keyName).getName().equals("rateOfInterest")) {
                    hmTextField.get(keyName).setText(rateOfInterest + "");
                } else if (hmTextField.get(keyName).getName().equals("tenurePeriod")) {
                    hmTextField.get(keyName).setText(tenurePeriod + "");
                } else if (hmTextField.get(keyName).getName().equals("maxAmount")) {
                    hmTextField.get(keyName).setText(maxAmtProvided + "");
                }
            }

            for (String keyName : hmBtn.keySet()) {
                if (hmBtn.get(keyName).getName().equals("addBtn")) {
                    hmBtn.get(keyName).setText("Update");
                    hmBtn.get(keyName).setName("updateBtn");
                } else if (hmBtn.get(keyName).getName().equals("cancelBtn")) {
                    hmBtn.get(keyName).setText("Cancel");
                }
            }

            System.out.println("edit btn executed");

        } catch (SQLException ex) {
            Logger.getLogger(ButtonColumn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
