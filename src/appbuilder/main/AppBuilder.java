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
        Classe.addClasse("Connection","sql","java");
        Classe dao = new Classe("AlunoDAO");
        Método adicionar = new Método("public", "void", "adicionar");
        dao.addMétodo(adicionar);
        dao.addImportação(Classe.addClasse(factory));
        dao.addImportação(Classe.addClasse("Connection","sql","java"));
        
       
        
      
//        dao.addImportação(factory.getPacote().getCaminho);
        Variavel var = new Variavel("PreparedStatement", "stmt");

        dao.addAtributo(new Atributo("private", "Connection", "con",
                (dao.getClasse("ConnectionFactory")).
                        getMétodo("getConnection").
                        getChamadaEstática(factory.getNome())));
        adicionar.addCorpo(var.getDeclaração(
                dao.getAtributo("con").call("prepareStatement", "\"INSERT INTO Aluno VALUES(?,?,?,?)\"")));

        adicionar.addParametro("Aluno", "aluno");
        
        System.out.println(dao);

    }
}
