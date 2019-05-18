/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendingtreeapplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author prith
 */
public class HomePage {

    static DBUtility dBUtility = new DBUtility();
    static TabContentPanel tabContentPanel = new TabContentPanel();

    // Variables declaration - do not modify                 
    private static JTabbedPane jCustomerTabbedPane = new JTabbedPane(JTabbedPane.TOP);
    private static JTabbedPane jLenderTabbedPane = new JTabbedPane(JTabbedPane.TOP);
    private static JTabbedPane jAdminTabbedPane = new JTabbedPane(JTabbedPane.TOP);
    private static JFrame jFrame = new JFrame("Testing");
    private static JButton logoutBtn = new JButton("Logout");

    /**
     * Creates new form HomePage
     */
    public HomePage() {

        jFrame.setLayout(new BorderLayout());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationByPlatform(true);
        jFrame.setSize(800, 800);
        jFrame.setVisible(true);
    }

    public static void createCustomerTabbedPane() {

// set grid layout for the frame
// jFrame.getContentPane().setLayout(new GridLayout(1, 1));
        System.out.println("customer tabbedpane creation");
        
        jCustomerTabbedPane.removeAll();
        jFrame.getContentPane().removeAll();
        try {

            ResultSet rs = dBUtility.getLoanTypes();

            while (rs.next()) {
                String loanTypeName = rs.getString("LOAN_TYPE_NAME");
                jCustomerTabbedPane.addTab(loanTypeName, tabContentPanel.makeLoanListPanel(loanTypeName));
            }
            jCustomerTabbedPane.addTab("Login", tabContentPanel.makeLoginPanel("Login"));
            jCustomerTabbedPane.addTab("Register", tabContentPanel.makeRegisterPanel("Register"));
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }

        JScrollPane jScrollPane = new JScrollPane(jCustomerTabbedPane);
        jFrame.getContentPane().add(jScrollPane, BorderLayout.CENTER);
        jFrame.getContentPane().doLayout();
        jFrame.revalidate();
        jFrame.repaint();
        //jFrame.pack();

// get the currently selected index for this tabbedpane
        int selectedIndex = jCustomerTabbedPane.getSelectedIndex();
        System.out.println("Default Index:" + selectedIndex);
// select the last tab
        jCustomerTabbedPane.setSelectedIndex(jCustomerTabbedPane.getTabCount() - 1);
        selectedIndex = jCustomerTabbedPane.getSelectedIndex();
        System.out.println("New Index:" + selectedIndex);
    }

    public static void createLenderTabbedPane() {

        System.out.println("lender tabbedpane creation");

        jLenderTabbedPane.removeAll();
        jFrame.getContentPane().removeAll();

        jLenderTabbedPane.addTab("Loans Offered", tabContentPanel.makeLoansOfferedPanel("Loans Offered"));
        jLenderTabbedPane.addTab("Customer Applications", tabContentPanel.makeCustomerApplicationsPanel("Customer Applications"));
        jLenderTabbedPane.addTab("Profile", tabContentPanel.makeProfilePanel("Profile", 2));

        JScrollPane jScrollPane = new JScrollPane(jLenderTabbedPane);
        jFrame.getContentPane().add(logoutBtn, BorderLayout.NORTH);
        jFrame.getContentPane().add(jScrollPane, BorderLayout.CENTER);
        jFrame.getContentPane().doLayout();
        jFrame.revalidate();
        jFrame.repaint();
        
        logoutBtnActionPerformed();

// get the currently selected index for this tabbedpane
        int selectedIndex = jLenderTabbedPane.getSelectedIndex();
        System.out.println("Default Index:" + selectedIndex);
// select the last tab
        jLenderTabbedPane.setSelectedIndex(jLenderTabbedPane.getTabCount() - 1);
        selectedIndex = jLenderTabbedPane.getSelectedIndex();
        System.out.println("New Index:" + selectedIndex);
    }

    public static void createAdminTabbedPane() {
        
        System.out.println("admin tabbedpane creation");
        
        jAdminTabbedPane.removeAll();
        jFrame.getContentPane().removeAll();
        
        jAdminTabbedPane.addTab("Customer Mgmt", tabContentPanel.makeAdminMgmtPanel("Customer Mgmt"));
        jAdminTabbedPane.addTab("Lender Mgmt", tabContentPanel.makeAdminMgmtPanel("Lender Mgmt"));
        jAdminTabbedPane.addTab("Admin Mgmt", tabContentPanel.makeAdminMgmtPanel("Admin Mgmt"));
        
        JScrollPane jScrollPane = new JScrollPane(jAdminTabbedPane);
        jFrame.getContentPane().add(logoutBtn, BorderLayout.NORTH); 
        jFrame.getContentPane().add(jScrollPane, BorderLayout.CENTER);
        jFrame.getContentPane().doLayout();
        jFrame.revalidate();
        jFrame.repaint();
        
        logoutBtnActionPerformed();

// get the currently selected index for this tabbedpane
        int selectedIndex = jAdminTabbedPane.getSelectedIndex();
        System.out.println("Default Index:" + selectedIndex);

    }
    
    public static void setTabBasedOnName(String tabName) {
        System.out.println("Index based on name:" + jCustomerTabbedPane.indexOfTab(tabName));
        jCustomerTabbedPane.setSelectedIndex(jCustomerTabbedPane.indexOfTab(tabName));
    }

    public static void setTabBasedOnIndex(int tabIndex) {
        System.out.println("Index based on name:" + tabIndex);
        jCustomerTabbedPane.setSelectedIndex(tabIndex);
    }

    public static void setCustomerProfile() {
        //user_type details 0 -> admin, 1 -> customer, 2 -> lender
        jCustomerTabbedPane.setTitleAt(jCustomerTabbedPane.indexOfTab("Login"), "Profile");
        System.out.println("customer loggedIn. Set profile");
        jCustomerTabbedPane.setComponentAt(jCustomerTabbedPane.indexOfTab("Profile"), tabContentPanel.makeProfilePanel("Profile", 1));
        System.out.println("customer loggedIn. Set profile component");
        jCustomerTabbedPane.removeTabAt(jCustomerTabbedPane.getTabCount() -1);
        jFrame.getContentPane().add(logoutBtn, BorderLayout.NORTH); 
        jFrame.doLayout();
        jFrame.revalidate();
        jFrame.repaint();
        
        logoutBtnActionPerformed();
    }
    
    public static void logoutBtnActionPerformed() {
        
        //jFrame.pack();
        
        System.out.println("logout btn clicked");
        
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    dBUtility.deleteFromDbLoginUserTable();
                    createCustomerTabbedPane();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static void setKeyListenerToNumber(JTextField jTextField) {
        jTextField.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                char vchar = e.getKeyChar();
                if (!(Character.isDigit(vchar))
                        || (vchar == KeyEvent.VK_BACK_SPACE)
                        || (vchar == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        });
    }
}