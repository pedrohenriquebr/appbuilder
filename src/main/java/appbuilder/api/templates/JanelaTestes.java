/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.templates;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.*;

/**
 *
 * @author psilva
 */
public class JanelaTestes extends JFrame {

    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        addAButton("Button 1", pane);
        pane.add(new JLabel("oi"));
        addAButton("Button 2", pane);
        addAButton("Button 3", pane);
        addAButton("Long-Named Button 4", pane);
        addAButton("5", pane);
    }

    private static void addAButton(String text, Container container) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(button);
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("BoxLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        createAndShowGUI();
    }
}
