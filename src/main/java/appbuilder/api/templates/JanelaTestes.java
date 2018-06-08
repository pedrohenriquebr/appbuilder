/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.templates;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;

/**
 *
 * @author psilva
 */
public class JanelaTestes extends JFrame {

    public JanelaTestes() {
        initComponents();
    }

    public void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        GridBagLayout layout = new GridBagLayout();
        Container c = getContentPane();
        GridBagConstraints gbc = new GridBagConstraints();
        c.setLayout(layout);
        gbc.gridx = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        c.add(new JLabel("Seu nome:"), gbc);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JTextField txtNome = new JTextField("");
        txtNome.setToolTipText("Seu nome por favor");
        txtNome.setColumns(20);
        c.add(txtNome, gbc);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        c.add(new JLabel("Sua idade: "),gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        
        JTextField txtIdade = new JTextField("");
        txtIdade.setToolTipText("Sua idade por favor");
        txtIdade.setColumns(20);
        c.add(txtIdade, gbc);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        JButton btn1 = new JButton("1");
        JButton btn2 = new JButton("2");
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.anchor = GridBagConstraints.CENTER;
        gbc2.insets = new Insets(5, 5, 5, 5);
        panel.add(btn1, gbc2);

        panel.add(btn2, gbc2);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(panel,gbc);

    }

    public static void main(String[] args) {
        new JanelaTestes().setVisible(true);
    }
}
