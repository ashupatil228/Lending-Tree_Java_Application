/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendingtreeapplication;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author prith
 */
public class MainRegistrationLayout {

    JLabel jLabel9 = new JLabel();
    ButtonGroup buttonGroup = new ButtonGroup();
    JRadioButton lenderBox = new JRadioButton("Lender");
    JRadioButton customerBox = new JRadioButton("Customer");
    JButton getForm = new JButton("Get Form");

    public LayoutManager mainRegistrationLayout(JPanel jPanel) {

        buttonGroup.add(lenderBox);
        buttonGroup.add(customerBox);
        
        buttonGroup.clearSelection();

        jLabel9.setText("Please Select any one Option to Register.....");

        GroupLayout registerLayout = new GroupLayout(jPanel);
        jPanel.setLayout(registerLayout);
        
        registerLayout.setHorizontalGroup(
                registerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(registerLayout.createSequentialGroup()
                                .addGroup(registerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(registerLayout.createSequentialGroup()
                                                .addGap(34, 34, 34)
                                                .addComponent(lenderBox)
                                                .addGap(82, 82, 82)
                                                .addComponent(customerBox))
                                        .addGroup(registerLayout.createSequentialGroup()
                                                .addGap(122, 122, 122)
                                                .addComponent(getForm))
                                        .addGroup(registerLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel9, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(302, Short.MAX_VALUE))
        );
        registerLayout.setVerticalGroup(
                registerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(registerLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(jLabel9, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(registerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(customerBox)
                                        .addComponent(lenderBox))
                                .addGap(18, 18, 18)
                                .addComponent(getForm)
                                .addContainerGap(175, Short.MAX_VALUE))
        );

        return registerLayout;
    }
}
