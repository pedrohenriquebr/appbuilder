/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.index;

import appbuilder.api.classes.*;
import appbuilder.api.classes.ClassBuilder;
import appbuilder.api.database.DatabaseBuilder;
import appbuilder.api.methods.MethodBuilder;
import appbuilder.api.packages.PackageBuilder;
import appbuilder.models.Projeto;
import appbuilder.api.vars.AttributeBuilder;
import appbuilder.api.vars.VarBuilder;
import appbuilder.views.BuildingMenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Classe para fazer testes !
 *
 * @author psilva
 */
public class Testes implements Filter {

    private static final Logger logger = Logger.getLogger(ClassBuilder.class.getName());

    public static void println(String... args) {
        System.out.println(args);
    }

    public static void main(String[] args) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {

      
        Projeto proj = new Projeto("/home/psilva/Documentos/ProjetoTeste", "projeto");
        proj.setPacotePrincipal(new PackageBuilder("br.com." + proj.getNome().trim()));
        appbuilder.util.ClassBuilder builder = new appbuilder.util.ClassBuilder(proj.getCaminho());
        PackageBuilder packageBuilderPrincipal = proj.getPacotePrincipal();
        PackageBuilder packageBuilderMain = new PackageBuilder("main", packageBuilderPrincipal.getCaminho());
        Janela principal = new Janela("Principal", packageBuilderMain.getNome(), proj.getPacotePrincipal().getCaminho());
        principal.addImport("java.awt.EventQueue");
        principal.setPrincipal(true);
        MethodBuilder main = principal.getMain();
        VarBuilder obj = new VarBuilder(principal.getName(), "obj");
        obj.setClasse(principal);
        main.addCorpo(obj.getDeclaração(obj.instancia().getInstancia()));
        main.addCorpo(obj.call("setVisible", "true"));

        List<ClassBuilder> aClasses = new ArrayList<>();
        aClasses.add(principal);
        ModelBuilder modelBuilder = new ModelBuilder("Pessoa", "models", "br.com.mycompany");
        modelBuilder.addStrings("Nome", "DataNascimento", "CPF", "Endereço");
        modelBuilder.setKey("Nome");
        ConnectionFactory factory = new ConnectionFactory("factory", "br.com.testes");
        factory.setBaseDeDados("projeto");
        DatabaseBuilder database = new DatabaseBuilder(modelBuilder, factory);
        DaoBuilder dao = new DaoBuilder(modelBuilder, factory, database);
        for (AttributeBuilder atr : modelBuilder.getAttributes()) {
            principal.addCampoEntrada(atr.getName());
        }

        principal.loadButtons();

        System.out.println(dao);
        System.exit(0);
        
        //eu sou pedro

        //Cria o manifesto
        List<File> codigoFonte = null;
        List<File> compilados = null;
        //Constrói as classes
        builder = new appbuilder.util.ClassBuilder(proj.getCaminho());

        try {
            codigoFonte = builder.build(aClasses);
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
