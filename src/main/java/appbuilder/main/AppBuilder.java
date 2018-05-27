/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.main;

import java.io.IOException;

import appbuilder.api.annotations.Anotação;
import appbuilder.api.classes.Modelo;
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
        Log.setEstado(false);
        Modelo modelo = new Modelo("Diretor");
        modelo.addStrings("nome", "departamento");
        modelo.addConstrutorPara("nome", "departamento");

        int valor  = modelo.getMétodo("getNome").getAnotações();
        boolean b  = Anotação.temAnotação(valor, Anotação.OVERRIDE);
        System.out.println(b);
    }

}
