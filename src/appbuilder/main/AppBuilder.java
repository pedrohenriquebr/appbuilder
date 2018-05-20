/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.main;

import appbuilder.api.classes.Modelo;
import appbuilder.api.classes.Construtor;
import appbuilder.api.classes.Objeto;
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
        Modelo modelo = new Modelo("Pessoa");
        modelo.addStrings("nome", "telefone");
        Objeto obj = modelo.getInstancia("obj");
        modelo.setPrincipal(true);
        Método metodo = modelo.getMain();
        metodo.addCorpo("System.out.println(\"Eu sou pedro\")");
        metodo.addCorpo(obj.getDeclaração(obj.getInstancia()));
        System.out.println(modelo);

    }
    //builder.execute();

}
