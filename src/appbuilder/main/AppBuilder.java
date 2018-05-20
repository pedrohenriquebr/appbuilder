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

        Classe factory = new Classe("ConnectionFactory", "app", "dao");
        Método metodo = new Método("public", "static", "Connection", "getConnection");
        metodo.addCorpo("return DriverManager.getConnection(\"jdbc:mysql://localhost/mydb\",\"root\",\"root\");");
        factory.addMétodo(metodo);
        Classe.addClasse(factory);

        Classe dao = new Classe("AlunoDAO");
        Método adicionar = new Método("public", "void", "adicionar");
        dao.addMétodo(adicionar);

        Variavel var = new Variavel("PreparedStatement", "stmt");
        adicionar.addCorpo(var.getDeclaração(Classe.getClasse(factory.getNomeCompleto()).getMétodo("")));
        adicionar.addParametro("Aluno", "aluno");
        System.out.println(dao);

    }
}
