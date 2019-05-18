/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendingtreeapplication;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author prith
 */
public class TabContentPanel {

    String REPORT = "D:\\LendingTree\\LendingTreeApplication\\src\\lendingtreeapplication\\SampleReport.jrxml";
    private static final String DB_NAME = "LENDING_TREE";

    DBUtility dBUtility = new DBUtility();
    CustomerRegistrationLayout crl;
    LenderRegistrationLayout lrl;
    MainRegistrationLayout mrl;
    CustomerProfileLayout cpl;
    LenderProfileLayout lpl;

    int filterROI, filterMAmt;
    float filterTP;

    Component makeLoanListPanel(String tabName) {

        JPanel loanListPanel = new JPanel(new GridLayout(2, 1));

//create the model and add elements
        //DefaultListModel<LoanDetailsListViewItem> listModel = new DefaultListModel<>();
        ArrayList<LoanDetailsListViewItem> loanList = new ArrayList<>();
        ArrayList<LoanDetailsListViewItem> result = new ArrayList<LoanDetailsListViewItem>();

        try {

            ResultSet rs = dBUtility.getLoanDetailsBasedType(tabName);

            while (rs.next()) {
                int loanId = rs.getInt("LOAN_ID");
                int loanType = rs.getInt("LOAN_TYPE");
                String loanName = rs.getString("LOAN_NAME");
                double rateOfInterest = rs.getDouble("RATE_OF_INTEREST");
                int loanProviderId = rs.getInt("LENDER_ID");
                double tenurePeriod = rs.getDouble("TENURE_PERIOD");
                int maxAmountProvided = rs.getInt("MAX_AMOUNT_PROVIDED");

                String loanProviderName = dBUtility.getLoanProviderName(loanProviderId);

                LoanDetailsListViewItem item = new LoanDetailsListViewItem(loanId, loanProviderId, loanType, loanProviderName, rateOfInterest, tenurePeriod, loanName, maxAmountProvided);

//listModel.addElement(item);
                loanList.add(item);
            }
            result.addAll(loanList);
            dBUtility.insertIntoJasperTable(result);
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TabContentPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        String colName[] = {"Loan Name", "Rate of Interest(%)", "Tenure Period (in Years)", "Max Amount Provided (in EUR)", " "};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(colName);

        Object[] column = new Object[5];
        for (int i = 0; i < loanList.size(); i++) {
            column[0] = loanList.get(i).getLoanName();
            column[1] = loanList.get(i).getRateOfInterest();
            column[2] = loanList.get(i).getTenurePeriod();
            column[3] = loanList.get(i).getMaxAmountProvided();
            column[4] = "Interested";
            model.addRow(column);
        }
        JTable table = new JTable(model) {

            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(super.getPreferredSize().width,
                        getRowHeight() * getRowCount());
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 ? true : false;
            }

            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };
        table.setFillsViewportHeight(true);

        ButtonColumn buttonColumn = new ButtonColumn(table, 4, loanList, loanListPanel);
        JScrollPane scrollPane = new JScrollPane(table);

        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
        JPanel sliderPanel = new JPanel(flowLayout);

        // add a slider with numeric labels
        JSlider slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setValue(0);
        slider.setMaximum(25);
        slider.setMajorTickSpacing(5);
        slider.setMinorTickSpacing(1);
        addSlider(slider, "Rate of Interest(%)", sliderPanel);

        slider = new JSlider();
        Dimension d = slider.getPreferredSize();
        slider.setPreferredSize(new Dimension(d.width + 500, d.height + 100));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setExtent(100000);
        slider.setValue(0);
        slider.setMaximum(100000);
        slider.setMajorTickSpacing(10000);
        slider.setMinorTickSpacing(1000);
        addSlider(slider, "Amount Required(in EUR)", sliderPanel);

        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setValue(0);
        slider.setMaximum(30);
        slider.setMajorTickSpacing(5);
        slider.setMinorTickSpacing(1);
        addSlider(slider, "Tenure Period(in Years)", sliderPanel);

        JButton search = new JButton("Search/Filter");
        JButton genReport = new JButton("Generate Report");
        sliderPanel.add(search);
        sliderPanel.add(genReport);

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                result.clear();

                System.out.println("filterROI : " + filterROI);
                System.out.println("filterMAmt : " + filterMAmt);
                System.out.println("filterTP : " + filterTP);

                for (LoanDetailsListViewItem item : loanList) {

                    double roi = item.getRateOfInterest();
                    double tp = item.getTenurePeriod();
                    int mAmt = item.getMaxAmountProvided();

                    System.out.println("roi:" + roi + " tp:" + tp + " mAmt:" + mAmt);
                    if (roi <= filterROI && (mAmt >= filterMAmt && tp >= filterTP)) {
                        result.add(item);
                    }
                }
                System.out.println("result list : " + result.toString());
                System.out.println("getRowCount : " + model.getRowCount());

                while (model.getRowCount() > 0) {
                    System.out.println("model : " + model.getValueAt(0, 0));
                    model.removeRow(0);
                }
                Object[] column = new Object[5];
                for (int i = 0; i < result.size(); i++) {
                    column[0] = result.get(i).getLoanName();
                    column[1] = result.get(i).getRateOfInterest();
                    column[2] = result.get(i).getTenurePeriod();
                    column[3] = result.get(i).getMaxAmountProvided();
                    column[4] = "Interested";
                    model.addRow(column);
                }
                model.fireTableDataChanged();
                try {
                    dBUtility.insertIntoJasperTable(result);
                } catch (SQLException ex) {
                    Logger.getLogger(TabContentPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        genReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (new File(REPORT).exists() == false) {
                        JOptionPane.showMessageDialog(null, "Please go to setting and Choose report Source");
                        return;
                    }
                    //JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(result);
                    //HashMap parameters = new HashMap();
                    /**
                     * Passing ReportTitle and Author as parameters
                     */
                   // parameters.put("ReportTitle", "List of Loan Offered");
                   // parameters.put("Author", "Prepared By LendingTreeApp");
                    //parameters.put("loanName", beanColDataSource);

                    //System.out.println(parameters.size() + " " + beanColDataSource.getRecordCount());
                    
                    JasperReport JASP_REP = JasperCompileManager.compileReport(REPORT);
                    JasperPrint JASP_PRINT = JasperFillManager.fillReport(JASP_REP, null, DBHelper.getDBConnection(DB_NAME));
                    JasperViewer.viewReport(JASP_PRINT, false);
                    String pdfPath = System.getProperty("user.home") + File.separatorChar + "My Documents\\jasperreport.pdf";
                    JasperExportManager.exportReportToPdfFile(JASP_PRINT, pdfPath);
                    
                    System.out.println(pdfPath);
                } catch (JRException ex) {
                    Logger.getLogger(TabContentPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        loanListPanel.add(sliderPanel);
        loanListPanel.add(scrollPane, BorderLayout.CENTER);

//create the list
        //JList<LoanDetailsListViewItem> loanList = new JList<>(listModel);
        //loanListPanel.add(new JScrollPane(loanList));
        //countryList.setCellRenderer(new CountryRenderer());
        //p.add(new Label(text));
        return loanListPanel;

    }

    int getPosition(String contentString) {
        int position = -1;
        Pattern p = Pattern.compile("(%|Y|y|E|e| )");  // insert your pattern here
        Matcher m = p.matcher(contentString);
        if (m.find()) {
            position = m.start();
        }
        return position;
    }

    public void addSlider(JSlider s, String description, JPanel sliderPanel) {

        s.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                JSlider source = (JSlider) event.getSource();
                if (description.equals("Rate of Interest(%)")) {
                    filterROI = source.getValue();
                } else if (description.equals("Tenure Period(in Years)")) {
                    filterTP = source.getValue();
                } else if (description.equals("Amount Required(in EUR)")) {
                    filterMAmt = source.getValue();
                }
            }
        });
        JPanel panel = new JPanel();
        panel.add(new JLabel(description));
        panel.add(s);
        sliderPanel.add(panel);
    }

    Component makeLoginPanel(String tabName) {

        JPanel loginPanel = new JPanel();

        JLabel jLabel1 = new JLabel("Email : ");
        JLabel jLabel2 = new JLabel("Password : ");
        JButton submitOnLogin = new JButton("Submit");
        JButton cancel = new JButton("Cancel");
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton customerCheck = new JRadioButton("Customer");
        JRadioButton lenderCheck = new JRadioButton("Lender");
        JRadioButton adminCheck = new JRadioButton("Admin");
        JPasswordField password = new JPasswordField();
        JTextField emailId = new JTextField();

        buttonGroup.add(adminCheck);
        buttonGroup.add(lenderCheck);
        buttonGroup.add(customerCheck);

        submitOnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String emailid = emailId.getText();
                String pasword = password.getText();
                int result = -1;

                try {
                    //user_type details 0 -> admin, 1 -> customer, 2 -> lender
                    if (customerCheck.isSelected()) {
                        result = dBUtility.checkLoginDetailsOfCustomer(emailid, pasword, 1);
                        displayLoginResult(result);
                        if (result == 0) {
                            HomePage.setTabBasedOnIndex(0);
                            HomePage.setCustomerProfile();
                        }

                    } else if (lenderCheck.isSelected()) {
                        result = dBUtility.checkLoginDetailsOfLender(emailid, pasword, 2);
                        displayLoginResult(result);
                        if (result == 0) {
                            HomePage.createLenderTabbedPane();
                        }

                    } else if (adminCheck.isSelected()) {
                        result = dBUtility.checkLoginDetailsOfAdmin(emailid, pasword, 0);
                        displayLoginResult(result);
                        if (result == 0) {
                            HomePage.createAdminTabbedPane();
                        }
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        });

        GroupLayout loginPaneLayout = new GroupLayout(loginPanel);
        loginPanel.setLayout(loginPaneLayout);

        loginPaneLayout.setHorizontalGroup(
                loginPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(loginPaneLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(loginPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(loginPaneLayout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGroup(loginPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(loginPaneLayout.createSequentialGroup()
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(loginPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addGroup(loginPaneLayout.createSequentialGroup()
                                                                                .addComponent(submitOnLogin)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(cancel))
                                                                        .addGroup(loginPaneLayout.createSequentialGroup()
                                                                                .addComponent(lenderCheck)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(customerCheck)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(adminCheck)
                                                                        )))
                                                        .addGroup(loginPaneLayout.createSequentialGroup()
                                                                .addGap(24, 24, 24)
                                                                .addComponent(password, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(loginPaneLayout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(emailId, GroupLayout.PREFERRED_SIZE, 229, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(270, Short.MAX_VALUE))
        );
        loginPaneLayout.setVerticalGroup(
                loginPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(loginPaneLayout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addGroup(loginPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(emailId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(loginPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(loginPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lenderCheck)
                                        .addComponent(customerCheck)
                                        .addComponent(adminCheck))
                                .addGap(18, 18, 18)
                                .addGroup(loginPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(submitOnLogin)
                                        .addComponent(cancel))
                                .addContainerGap(122, Short.MAX_VALUE))
        );

        return loginPanel;
    }

    public void displayLoginResult(int result) {

        if (result == 0) {
            JOptionPane.showMessageDialog(null, "Login Successfull");
        } else if (result == 1) {
            JOptionPane.showMessageDialog(null, "Email-id or password is incorrect. Please check login details!");
        } else if (result == 2) {
            JOptionPane.showMessageDialog(null, "Email-Id is not registered. Please register to continue.!");
        }
    }

    Component makeRegisterPanel(String tabName) {

        crl = new CustomerRegistrationLayout();
        lrl = new LenderRegistrationLayout();
        mrl = new MainRegistrationLayout();

        JPanel registerPanel = new JPanel();

        mrl.mainRegistrationLayout(registerPanel);

        mrl.getForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // TODO add your handling code here:
                if (!(mrl.lenderBox.isSelected() || mrl.customerBox.isSelected())) {
                    JOptionPane.showMessageDialog(null, "Please Select any one Option");
                } else if (mrl.lenderBox.isSelected()) {
                    registerPanel.removeAll();
                    lrl.lenderRegistrationLayout(registerPanel);
                } else if (mrl.customerBox.isSelected()) {
                    registerPanel.removeAll();
                    crl.customerRegistrationLayout(registerPanel);
                    //registerPanel.paintAll(grphcs);
                }
            }
        });

        crl.cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                registerPanel.removeAll();
                mrl.mainRegistrationLayout(registerPanel);
            }
        });

        lrl.cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                registerPanel.removeAll();
                mrl.mainRegistrationLayout(registerPanel);
            }
        });

        return registerPanel;
    }

    Component makeProfilePanel(String tabName, int userType) {
        //user_type details 0 -> admin, 1 -> customer, 2 -> lender

        System.out.println("inside profile panel");

        JPanel profilePanel = new JPanel();

        try {
            if (userType == 1) {
                System.out.println("customer usertype");
                cpl = new CustomerProfileLayout();
                System.out.println("customer profile panel called");
                cpl.customerProfileLayout(profilePanel);
                System.out.println("customer profile panel layout set");
            } else if (userType == 2) {
                System.out.println("lender usertype");
                lpl = new LenderProfileLayout();
                System.out.println("lender profile panel called");
                lpl.lenderProfileLayout(profilePanel);
                System.out.println("lender profile panel layout set");
            }
        } catch (Exception e) {
            System.out.println("makeProfilePanel exception " + e);
        }

        return profilePanel;
    }

    Component makeLoansOfferedPanel(String tabName) {

        JPanel loansOfferedListPanel = new JPanel();

        // Variables declaration - do not modify                     
        JButton addBtn = new JButton("Add Loan");
        JButton cancelBtn = new JButton("Cancel");
        JComboBox<String> loanTypesCB = new JComboBox<>();
        JLabel addLoanInfoL = new JLabel("Add Loan Information");
        JLabel selectLoanTypeL = new JLabel("Select Type Of Loan ");
        JLabel loanNameL = new JLabel("Loan Name");
        JLabel rateOfInterestL = new JLabel("Rate Of Interest");
        JLabel tenurePeriodL = new JLabel("Tenure Period");
        JLabel maxAmountL = new JLabel("Maximum Amount Range");
        JTextField loanName = new JTextField();
        JTextField rateOfInterest = new JTextField();
        JTextField tenurePeriod = new JTextField();
        tenurePeriod.setToolTipText("Please enter tenure period in terms of years");
        JTextField maxAmount = new JTextField();
        maxAmount.setToolTipText("Please enter amount in terms of EUR");

        //HomePage.setKeyListenerToNumber(rateOfInterest);
        //HomePage.setKeyListenerToNumber(tenurePeriod);
        HomePage.setKeyListenerToNumber(maxAmount);
        
        loanName.setName("loanName");
        rateOfInterest.setName("rateOfInterest");
        tenurePeriod.setName("tenurePeriod");
        maxAmount.setName("maxAmount");
        addBtn.setName("addBtn");
        cancelBtn.setName("cancelBtn");
        loanTypesCB.setName("loanTypesCB");
        // End of variables declaration 

        try {
            ResultSet rs = dBUtility.getLoanTypes();
            rs.last();
            int count = rs.getRow();
            rs.beforeFirst();
            String[] loanTypes = new String[count];

            while (rs.next()) {
                loanTypes[rs.getRow() - 1] = rs.getString("LOAN_TYPE_NAME");
            }

            loanTypesCB.setModel(new DefaultComboBoxModel<>(loanTypes));

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<LoanDetailsListViewItem> loanList = new ArrayList<>();
        try {
            loanList.clear();

            int lenderId = dBUtility.getUserId();
            ResultSet rs = dBUtility.getLoanDetailsBasedOnLender(lenderId);

            while (rs.next()) {
                int loanId = rs.getInt("LOAN_ID");
                int loanType = rs.getInt("LOAN_TYPE");
                String loanNameS = rs.getString("LOAN_NAME");
                double rateOfInterestS = rs.getDouble("RATE_OF_INTEREST");
                int loanProviderId = rs.getInt("LENDER_ID");
                double tenurePeriodS = rs.getDouble("TENURE_PERIOD");
                int maxAmountProvided = rs.getInt("MAX_AMOUNT_PROVIDED");

                String loanProviderName = dBUtility.getLoanProviderName(lenderId);

                LoanDetailsListViewItem item = new LoanDetailsListViewItem(loanId, loanProviderId, loanType, loanProviderName, rateOfInterestS, tenurePeriodS, loanNameS, maxAmountProvided);
                //listModel.addElement(item);
                loanList.add(item);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TabContentPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        String colName[] = {"Loan Name", "Rate of Interest (%)", "Tenure Period (in Years)", "Max Amount Provided (in EUR)", " ", " "};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(colName);

        System.out.println("loanList size = " + loanList.size());
        Object[] column = new Object[6];
        for (int i = 0; i < loanList.size(); i++) {
            column[0] = loanList.get(i).getLoanName();
            column[1] = loanList.get(i).getRateOfInterest();
            column[2] = loanList.get(i).getTenurePeriod();
            column[3] = loanList.get(i).getMaxAmountProvided();
            column[4] = "Edit";
            column[5] = "Delete";
            model.addRow(column);
        }

        JTable table = new JTable(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 5 ? true : false;
            }

            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };

        ButtonColumn buttonColumn = new ButtonColumn(table, 4, loanList, loansOfferedListPanel);
        ButtonColumn buttonColumn1 = new ButtonColumn(table, 5, loanList, loansOfferedListPanel);

        System.out.println("buttonColumn set");

        ColumnAutoSizer.sizeColumnsToFit(table);
        JScrollPane scrollPane = new JScrollPane(table);

        GroupLayout layout = new GroupLayout(loansOfferedListPanel);
        loansOfferedListPanel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(addLoanInfoL)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(selectLoanTypeL)
                                                        .addComponent(loanNameL)
                                                        .addComponent(rateOfInterestL)
                                                        .addComponent(tenurePeriodL)
                                                        .addComponent(maxAmountL))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(loanTypesCB)
                                                        .addComponent(loanName)
                                                        .addComponent(rateOfInterest)
                                                        .addComponent(tenurePeriod)
                                                        .addComponent(maxAmount))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addContainerGap()
                                                        .addComponent(addBtn)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(cancelBtn)
                                                ))))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(scrollPane)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(addLoanInfoL, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(selectLoanTypeL)
                                        .addComponent(loanTypesCB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(loanNameL)
                                        .addComponent(loanName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(rateOfInterestL)
                                        .addComponent(rateOfInterest, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(tenurePeriodL)
                                        .addComponent(tenurePeriod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(maxAmountL)
                                        .addComponent(maxAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(addBtn)
                                        .addComponent(cancelBtn))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(scrollPane))
        );

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                String name = addBtn.getName();
                System.out.println("update........" + name);

                if (name.equals("addBtn")) {
                    try {

                        String loanNameS = loanName.getText();
                        int lenderId = dBUtility.getUserId();
                        int loanType = dBUtility.getLoanTypeIdBasedOnName(loanTypesCB.getSelectedItem().toString());
                        double rateOfInterestS = Double.parseDouble(rateOfInterest.getText());
                        double tenurePeriodS = Double.parseDouble(tenurePeriod.getText());
                        int maxAmtProvidedS = Integer.parseInt(maxAmount.getText());

                        dBUtility.insertIntoDbLendersLoanListTable(loanNameS, lenderId, loanType, rateOfInterestS, tenurePeriodS, maxAmtProvidedS);

                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.addRow(new Object[]{loanNameS, rateOfInterestS, tenurePeriodS, maxAmtProvidedS, "Edit", "Delete"});

                        System.out.println("buttonColumn set addbtn");

                    } catch (SQLException ex) {
                        Logger.getLogger(TabContentPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (name.equals("updateBtn")) {

                    LoanDetailsListViewItem item = loanList.get(table.getSelectedRow());
                    int loanId = item.getLoanId();
                    int lenderId = item.getLenderId();

                    try {
                        String loanNameS = loanName.getText();
                        double rateOfInterestS = Double.parseDouble(rateOfInterest.getText());
                        double tenurePeriodS = Double.parseDouble(tenurePeriod.getText());
                        int maxAmountS = Integer.parseInt(maxAmount.getText());
                        int loanTypeS = dBUtility.getLoanTypeIdBasedOnName(loanTypesCB.getSelectedItem().toString());

                        boolean result = dBUtility.updateDbLendersLoanListTable(loanId, loanNameS, lenderId, loanTypeS, rateOfInterestS, tenurePeriodS, maxAmountS);

                        if (result) {
                            DefaultTableModel model = (DefaultTableModel) table.getModel();
                            model.setValueAt(loanNameS, table.getSelectedRow(), 0);
                            model.setValueAt(rateOfInterestS, table.getSelectedRow(), 1);
                            model.setValueAt(tenurePeriodS, table.getSelectedRow(), 2);
                            model.setValueAt(maxAmountS, table.getSelectedRow(), 3);

                            loanName.setText("");
                            rateOfInterest.setText("");
                            tenurePeriod.setText("");
                            maxAmount.setText("");
                            addBtn.setText("Add Loan");
                            loanTypesCB.setSelectedIndex(0);

                            addBtn.setName("addBtn");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error occurred while updating details. Please try again");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(TabContentPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    System.out.println("buttonColumn set updateBtn");
                }
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                System.out.println("buttonColumn set cancelbtn");
                loanName.setText("");
                rateOfInterest.setText("");
                tenurePeriod.setText("");
                maxAmount.setText("");
                addBtn.setText("Add Loan");
                loanTypesCB.setSelectedIndex(0);
            }
        });

        return loansOfferedListPanel;
    }

    Component makeCustomerApplicationsPanel(String tabName) {
        JPanel loanListPanel = new JPanel();

        CustomerApplicationStatusLayout casl = new CustomerApplicationStatusLayout();
        casl.CustomerApplicationStatusLayout(loanListPanel);

        return loanListPanel;
    }

    Component makeAdminMgmtPanel(String tabName) {
        JPanel loanListPanel = new JPanel();

        if (tabName.equals("Customer Mgmt")) {
            AdminCustomerMgmtLayout acml = new AdminCustomerMgmtLayout();
            acml.adminCustomerMgmtLayout(loanListPanel);
        } else if (tabName.equals("Lender Mgmt")) {
            AdminLenderMgmtLayout alml = new AdminLenderMgmtLayout();
            alml.adminLenderMgmtLayout(loanListPanel);
        } else if (tabName.equals("Admin Mgmt")) {
            AdminMgtLayout aml = new AdminMgtLayout();
            aml.adminMgtLayout(loanListPanel);
        }

        return loanListPanel;
    }
}
