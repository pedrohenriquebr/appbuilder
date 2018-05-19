/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.main;

import appbuilder.api.classes.Modelo;
import appbuilder.api.classes.Construtor;
import appbuilder.api.methods.Método;
import appbuilder.api.vars.Variavel;
import appbuilder.util.*;

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

        Modelo classe = new Modelo("Aluno");
        classe.addString("nome");
        Construtor construtor = new Construtor("public", classe.getNome());
        construtor.addParametro("String", "nome");
        classe.addConstrutor(construtor);
        classe.setPrincipal(true);
        Método main = classe.getMain();
        construtor.setCorpo(classe.getAtributo("nome").getInicialização("nome"));
        Variavel var = new Variavel(classe.getNome(),"meuAluno");
        main.setCorpo(var.getDeclaração()+""+var.getInicialização(classe.getInstancia("\"Pedro Henrique\"")));
        System.out.println(classe.toString());
        

    }
    //builder.execute();

}
