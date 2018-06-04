/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.main;

import java.io.IOException;

import appbuilder.api.annotations.Anotação;
import appbuilder.api.classes.*;
import appbuilder.api.classes.exceptions.*;
import appbuilder.api.methods.Método;
import appbuilder.api.projects.Manifesto;
import appbuilder.api.templates.Template;
import appbuilder.api.vars.Atributo;
import appbuilder.api.vars.Variavel;
import appbuilder.menus.MainMenu;
import appbuilder.util.ClassBuilder;
import java.awt.Button;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 *
 * @author aluno
 */
public class AppBuilder implements Filter {

    private static final Logger logger = Logger.getLogger(Classe.class.getName());

    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });

    }

    @Override
    public boolean isLoggable(LogRecord lr) {
        if (lr == null) {
            return false;
        }

        if (lr.getMessage().contains("adicionado método:") && lr.getMessage().contains("")) {
            return true;
        }

        return false;
    }

}
