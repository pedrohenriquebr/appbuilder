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

    public static void main(String[] args) throws SQLException, FileNotFoundException, ClassNotFoundException {
        /* Modelo modelo = new Modelo("Pessoa", "main", "br.com.testes");
        modelo.addStrings("código", "nome", "telefone");
        modelo.addAtributo("Calendar", "nascimento");
        modelo.setChave("código");
        ConnectionFactory factory = new ConnectionFactory("factory", "br.com.testes");
        factory.setBaseDeDados("projeto");
        BaseDeDados database = new BaseDeDados(modelo, factory);
        Dao dao = new Dao(modelo, factory, database);
         */

        Projeto proj = new Projeto("C:\\Users\\aluno\\Documents\\ProjetoTeste", "projeto");
        proj.setPacotePrincipal(new Pacote("br.com." + proj.getNome().trim()));
        File executavel = null;
        ClassBuilder builder = new ClassBuilder(proj.getCaminho());
        Pacote pacotePrincipal = proj.getPacotePrincipal();
        Pacote pacoteDeModelos = new Pacote("models", pacotePrincipal.getCaminho());
        Pacote pacoteMain = new Pacote("main", pacotePrincipal.getCaminho());
        Pacote pacoteDao = new Pacote("dao", pacotePrincipal.getCaminho());

        Modelo modelo = new Modelo("Funcionário", pacoteDeModelos.getNome(), pacotePrincipal.getCaminho());
        ConnectionFactory factory = new ConnectionFactory(pacoteDeModelos.getNome(), pacotePrincipal.getCaminho());
        BaseDeDados database = null;
        Dao dao = null;

        modelo.addImportação("java.util.Calendar");

        //Verifica se usa base de dados
        if (proj.isUsaBaseDeDados()) {
            factory.setUsuário(proj.getUsuario());
            factory.setBaseDeDados(proj.getBaseDeDados());
            factory.setSenha(proj.getSenha());
            factory.setServidor(proj.getServidor());
        }

        modelo.addStrings("nome", "telefone");
        modelo.addInteiro("código");
        modelo.addAtributo("Calendar", "nascimento");
        modelo.setChave("código");

        //Constroi a classe Principal
        Classe.addClasse(modelo);
        Janela principal = new Janela("Principal", pacoteMain.getNome(), proj.getPacotePrincipal().getCaminho());
        principal.addImportação(modelo.getNomeCompleto());
        try {
            principal.addImportação(Classe.addClasse("Scanner", "util", "java"));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BuildingMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Metaclasse Dao e base de dados
        try {
            database = new BaseDeDados(modelo, factory);
            database.buildAll();
            dao = new Dao(modelo, factory, database);
            dao.setPacote(pacoteDao);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BuildingMenu.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Erro ao criar a metaclasse Dao!");
        } catch (SQLException ex) {
            Logger.getLogger(BuildingMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

        dao.addMétodoPesquisa("código");
        dao.addMétodoPesquisa("nome");

        principal.setPrincipal(true);
        Método main = principal.getMain();
        Variavel obj = new Variavel(principal.getNome(), "janela");
        obj.setClasse(principal);
        main.addCorpo(obj.getDeclaração(obj.instancia().getInstancia()));
        main.addCorpo(obj.call("setVisible", "true"));
        main.addCorpo(obj.call("setSize", "500","400"));

        List<Classe> classes = new ArrayList<>();
        classes.add(principal);
        classes.add(modelo);
        classes.add(dao);
        classes.add(factory);

        //Cria o manifesto
        Manifesto manifesto = new Manifesto();
        manifesto.setClassePrincipal(principal.getNomeCompleto());
        List<File> codigoFonte = null;
        List<File> compilados = null;

        try {
            manifesto.write(proj.getCaminho());
        } catch (IOException ex) {
            Logger.getLogger(BuildingMenu.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Erro ao escrever manifesto do projeto !");
        }
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
        //Empacota as classes 
        try {
            executavel = builder.packJar(proj.getNome(), manifesto);
        } catch (IOException ex) {
            Logger.getLogger(BuildingMenu.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Erro ao empacotar o projeto !");
        }
        
        System.out.println("Executando o jar ");
        try {
            builder.executeJar(executavel);
        } catch (IOException ex) {
            Logger.getLogger(Testes.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Problemas ao executar o jar !");
        }
    }

    @Override
    public boolean isLoggable(LogRecord lr) {
        if (lr == null) {
            return false;
        }

        return false;
    }

}
