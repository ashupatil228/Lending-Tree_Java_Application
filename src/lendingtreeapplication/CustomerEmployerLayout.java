/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendingtreeapplication;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

/**
 *
 * @author User
 */
public class CustomerEmployerLayout {

    DBUtility dBUtility = new DBUtility();
    private static JFrame jFrame = new JFrame("Emplyor Details");

    public CustomerEmployerLayout() {
        jFrame.setLayout(new BorderLayout());
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //jFrame.setLocationByPlatform(true);
        jFrame.setSize(800, 800);
        jFrame.setVisible(true);
    }

    JLabel EmpNameL = new JLabel("Employer Name :");
    JLabel AddressL = new JLabel("Employer Address :");
    JLabel PayslipL = new JLabel("Payslip :");
    JLabel WorkingFromL = new JLabel("Working From :");
    JLabel SalaryL = new JLabel("Salary :");
    JLabel TaxnoL = new JLabel("Tax No :");
    JButton Submitbtn = new JButton("Submit");
    JTextField EmpName = new JTextField();
    JTextField Address = new JTextField();
    JTextField Salary = new JTextField();
    JTextField Taxno = new JTextField();
    JFileChooser Payslip = new JFileChooser();

    SqlDateModel workingFromModel = new SqlDateModel();
    JDatePanelImpl workingFromDatePanel = new JDatePanelImpl(workingFromModel, new Properties());
    final JDatePickerImpl workingFromDatePicker = new JDatePickerImpl(workingFromDatePanel, new DateLabelFormatter());
    FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf", "doc*", "txt");

    public LayoutManager customerEmpDetails(final JPanel jpanel) {

        Salary.setToolTipText("Please enter amount in terms of euros");
        HomePage.setKeyListenerToNumber(Salary);
        
        System.out.println("inside customerDetails");
        Submitbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitbtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(jpanel);
        jpanel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(165, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(EmpNameL)
                                                        .addComponent(AddressL)
                                                        .addComponent(SalaryL)
                                                        .addComponent(TaxnoL)
                                                        .addComponent(WorkingFromL)
                                                        .addComponent(PayslipL))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(EmpName)
                                                        .addComponent(Address)
                                                        .addComponent(Salary)
                                                        .addComponent(Taxno)
                                                        .addComponent(workingFromDatePicker)
                                                        .addComponent(Payslip))
                                                .addContainerGap())
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(Submitbtn))))
                                .addContainerGap(165, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(EmpNameL)
                                        .addComponent(EmpName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(AddressL)
                                        .addComponent(Address, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(SalaryL)
                                        .addComponent(Salary, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(TaxnoL)
                                        .addComponent(Taxno, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(WorkingFromL)
                                        .addComponent(workingFromDatePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(PayslipL)
                                        .addComponent(Payslip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(Submitbtn))
                                .addContainerGap(30, Short.MAX_VALUE))
        );
        jFrame.getContentPane().add(jpanel);
        jFrame.getContentPane().doLayout();
        // jFrame.revalidate();
        //jFrame.repaint();

        return layout;
    }

    private void SubmitbtnActionPerformed(java.awt.event.ActionEvent evt) {

        String empName = EmpName.getText();
        String address = Address.getText();
        String salary = Salary.getText();
        String taxNo = Taxno.getText();
        File paySlips = Payslip.getSelectedFile();
        Date workingFrom = (Date) workingFromDatePicker.getModel().getValue();
        boolean result = false;

        try {
            System.out.println("before insert employer table");
            dBUtility.insertIntoDbEmployerTable(empName, address, workingFrom, salary, taxNo, paySlips);
            System.out.println("after insert employer table");
            result = true;

        } catch (SQLException ex) {
            Logger.getLogger(CustomerRegistrationLayout.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (result) {
            jFrame.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(jFrame.getContentPane(), "Error in updating");
        }

    }
}
