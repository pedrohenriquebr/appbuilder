/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder;

import appbuilder.util.classes.Modelo;
import appbuilder.util.*;
import appbuilder.util.classes.Classe;
import appbuilder.util.classes.Método;
import java.io.File;

import java.io.IOException;
import java.util.*;

/**
 *
 * @author aluno
 */
public class AppBuilder {

    /**
     * @param args the command line arguments
     */
    

    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        String caminhoPadrão = "/home/psilva/Documentos/";
        ClassBuilder builder = new ClassBuilder(caminhoPadrão);
        Modelo modelo = null;
        
        Classe classe  = new Classe("Aluno");
        
        
    }   
    //builder.execute();

}
