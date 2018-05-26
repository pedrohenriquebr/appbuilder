/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.main;

import java.io.IOException;

import appbuilder.api.classes.Classe;
import appbuilder.api.classes.Exceção;
import appbuilder.api.methods.Método;
import appbuilder.util.Log;

/**
 *
 * @author aluno
 */
public class AppBuilder {

    /**
     * @param args the command line argumentss
     */
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        // classe Pai ou superclasse
        Log.setEstado(false);
        Exceção exp = new Exceção("MinhaException");

        System.out.println(exp);

    }
}
