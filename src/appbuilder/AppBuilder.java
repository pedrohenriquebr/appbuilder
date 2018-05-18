/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder;

import appbuilder.util.*;

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

        Classe classe = new Classe("Teste");
        classe.addAtributo(new Atributo("public", "String", "nome", "pedro"));
        classe.setPrincipal(true);
        Método metodo = classe.getMain();
        metodo.setCorpo("System.out.println(\"Eu sou Pedro\");");
        ClassBuilder builder = new ClassBuilder("/home/psilva/Documentos/");
        builder.build(classe);

        //javac principal/teste/pacotes/Teste.java
        builder.compile();
        builder.execute();

    }

}
