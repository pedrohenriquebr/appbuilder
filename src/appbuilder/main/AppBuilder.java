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
import java.sql.Connection;

/**
 *
 * @author aluno
 */
public class AppBuilder {

    /**
     * @param args the command line argumentss
     */
    public static void main(String[] args) throws ClassNotFoundException {

        Classe factory = new Classe("ConnectionFactory", "app", "dao");
        Método metodo = new Método("public", "static", "Connection", "getConnection");
        metodo.addCorpo("return DriverManager.getConnection(\"jdbc:mysql://localhost/mydb\",\"root\",\"root\");");
        factory.addMétodo(metodo);
        Classe.addClasse(factory);

        Classe dao = new Classe("AlunoDAO");
        Método adicionar = new Método("public", "void", "adicionar");
        dao.addMétodo(adicionar);

        Variavel var = new Variavel("PreparedStatement", "stmt");

        dao.addAtributo(new Atributo("private", "Connection", "con",
                Classe.getClasse(factory.getNomeCompleto()).
                        getMétodo("getConnection").
                        getChamadaEstática(factory.getNome())));

        Objeto obj = Objeto.instancia("java.sql.Connection");
        adicionar.addCorpo(var.getDeclaração(
                dao.getAtributo("con").getReferencia() + obj.call("prepareStatement", "\"INSERT INTO aluno VALUES(?,?,?)\"")));

        Class con = Class.forName("java.sql.Connection");
        System.out.println(con.getName());

        adicionar.addParametro("Aluno", "aluno");
        System.out.println(dao);

    }
}
