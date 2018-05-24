/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.main;

import appbuilder.api.classes.*;
import static appbuilder.api.classes.Classe.classes;
import appbuilder.api.methods.*;
import appbuilder.api.vars.*;
import appbuilder.util.*;
import java.io.File;
import java.io.IOException;
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
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        //classe Pai ou superclasse
        Classe cl1 = new Classe("Pai", "pais", "familia");
        Atributo atr = new Atributo("public", "String", "nomeCompleto", "Luciano da Silva Santos");
        cl1.addAtributo(atr);

        //classe filha ou subclasse
        Classe cl2 = new Classe("Filha", "filhos", "familia");
        Classe.addClasse(cl1);
        cl2.addImportação(cl1);
        cl2.setSuperClasse(cl1);

        System.out.println(cl2);
        System.exit(0);

        Classe factory = new Classe("ConnectionFactory", "app", "dao");
        Método metodo = new Método("public", "static", "Connection", "getConnection");
        metodo.addCorpo("return DriverManager.getConnection(\"jdbc:mysql://localhost/mydb\",\"root\",\"root\");");
        factory.addMétodo(metodo);
        Classe.addClasse(factory);
        Classe.addClasse("Connection", "sql", "java");

        Classe dao = new Classe("AlunoDAO");
        Método adicionar = new Método("public", "void", "adicionar");
        dao.addMétodo(adicionar);
        dao.addImportação(Classe.addClasse(factory));
        dao.addImportação(Classe.addClasse("Connection", "sql", "java"));

        dao.addAtributo(new Atributo("private", "Connection", "con",
                (dao.getClasse("ConnectionFactory")).
                        getMétodo("getConnection").
                        getChamadaEstática(factory.getNome())));
        Variavel var = new Variavel("PreparedStatement", "stmt");

        adicionar.addParametro("Aluno", "aluno");
        adicionar.addCorpo(var.getDeclaração(
                dao.getAtributo("con").call("prepareStatement", "\"INSERT INTO Aluno VALUES(?,?,?,?)\"")));

        System.out.println(dao);

    }
}
