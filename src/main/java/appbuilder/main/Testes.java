/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.main;

import appbuilder.api.classes.Classe;
import appbuilder.api.classes.ConnectionFactory;
import appbuilder.api.classes.Construtor;
import appbuilder.api.classes.Dao;
import appbuilder.api.classes.Interface;
import appbuilder.api.classes.Janela;
import appbuilder.api.classes.Modelo;
import appbuilder.api.database.BaseDeDados;
import appbuilder.api.database.MyConnectionFactory;
import appbuilder.api.methods.Método;
import appbuilder.api.methods.Parametro;
import appbuilder.api.packages.Pacote;
import appbuilder.api.projects.Manifesto;
import appbuilder.api.projects.Projeto;
import appbuilder.api.vars.Atributo;
import appbuilder.api.vars.Variavel;
import appbuilder.menus.BuildingMenu;
import appbuilder.util.ClassBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * Classe para fazer testes !
 *
 * @author psilva
 */
public class Testes implements Filter {

    private static final Logger logger = Logger.getLogger(Classe.class.getName());

    public static void println(String... args) {
        System.out.println(args);
    }

    public static void main(String[] args) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
        /* Modelo modelo = new Modelo("Pessoa", "main", "br.com.testes");
        modelo.addStrings("código", "nome", "telefone");
        modelo.addAtributo("Calendar", "nascimento");
        modelo.setChave("código");
        ConnectionFactory factory = new ConnectionFactory("factory", "br.com.testes");
        factory.setBaseDeDados("projeto");
        BaseDeDados database = new BaseDeDados(modelo, factory);
        Dao dao = new Dao(modelo, factory, database);
         */
        
        
        
        

        Projeto proj = new Projeto("/home/pedro/Documentos/ProjetoTeste", "projeto");
        proj.setPacotePrincipal(new Pacote("br.com." + proj.getNome().trim()));
        ClassBuilder builder = new ClassBuilder(proj.getCaminho());
        Pacote pacotePrincipal = proj.getPacotePrincipal();
        Pacote pacoteMain = new Pacote("main", pacotePrincipal.getCaminho());
        Janela principal = new Janela("Principal", pacoteMain.getNome(), proj.getPacotePrincipal().getCaminho());
        principal.addInterface((Interface) principal.getClasse("Runnable"));
        principal.setPrincipal(true);
        Método main = principal.getMain();
        Variavel obj = new Variavel("Thread", "janela");
        obj.setClasse(principal);
        main.addCorpo(obj.getDeclaração(
                obj.instancia(
                        principal.getInstancia().getInstancia()).
                        getInstancia()));
        
        main.addCorpo(obj.call("start"));
        
        Método run = principal.getMétodo("run");
        obj = new Variavel(principal.getNome(),"obj");
        obj.setClasse(principal);
        run.addCorpo(obj.getDeclaração(obj.instancia().getInstancia()));
        run.addCorpo(obj.call("setSize","200","300"));
        run.addCorpo(obj.call("setVisible", "true"));

        List<Classe> classes = new ArrayList<>();
        classes.add(principal);
        
        
       

        //Cria o manifesto
      
        List<File> codigoFonte = null;
        List<File> compilados = null;
        //Constrói as classes
        builder = new ClassBuilder(proj.getCaminho());

        try {
            codigoFonte = builder.build(classes);
        } catch (IOException ex) {
            Logger.getLogger(BuildingMenu.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Erro ao construir projeto !");
        }
        //Compila os arquivos
        try {
            compilados = builder.compile(codigoFonte);
        } catch (IOException ex) {
            Logger.getLogger(BuildingMenu.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Erro ao compilar os códigos fonte !");
        }
       
        
        builder.execute(principal);
    }

    @Override
    public boolean isLoggable(LogRecord lr) {
        if (lr == null) {
            return false;
        }

        return false;
    }

}
