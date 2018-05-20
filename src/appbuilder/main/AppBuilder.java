/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.main;

import appbuilder.api.classes.*;
import appbuilder.api.methods.*;
import appbuilder.api.vars.*;
import appbuilder.util.*;
import java.lang.reflect.*;

/**
 *
 * @author aluno
 */
public class AppBuilder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Modelo modelo = new Modelo("Aluno");
        modelo.addAtributo(
                new Atributo("String", "nome",
                        Classe.get("String").toString()));
        
        
        System.out.println(modelo);
    }
}
