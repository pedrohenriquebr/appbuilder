/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.models;

import appbuilder.api.classes.*;
import appbuilder.api.packages.*;

/**
 *
 * @author psilva
 */
public class Projeto {

    private String caminho; //o diretório em que o projeto se encontra
    private String nome;
    private boolean usaGui = false;
    private boolean usaBaseDeDados = false;

    private PackageBuilder packageBuilderPrincipal;
    private ModelBuilder modelBuilder;

    private ClassBuilder principal;

    //caso use base de dados
    private String usuario;
    private String senha;
    private String baseDeDados;
    private String servidor;

    public Projeto(String caminho, String nome) {
        this.caminho = caminho;
        this.nome = nome;
    }

    public PackageBuilder getPacotePrincipal() {
        return packageBuilderPrincipal;
    }

    public void setPacotePrincipal(PackageBuilder packageBuilder) {
        this.packageBuilderPrincipal = packageBuilder;
    }

    public ModelBuilder getModelo() {
        return modelBuilder;
    }

    public void setModelo(ModelBuilder modelBuilder) {
        this.modelBuilder = modelBuilder;
    }

    public ClassBuilder getPrincipal() {
        return principal;
    }

    public void setPrincipal(ClassBuilder principal) {
        this.principal = principal;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isUsaGui() {
        return usaGui;
    }

    public void setUsaGui(boolean usaGui) {
        this.usaGui = usaGui;
    }

    public boolean isUsaBaseDeDados() {
        return usaBaseDeDados;
    }

    public void setUsaBaseDeDados(boolean usaBaseDeDados) {
        this.usaBaseDeDados = usaBaseDeDados;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getBaseDeDados() {
        return baseDeDados;
    }

    public void setBaseDeDados(String baseDeDados) {
        this.baseDeDados = baseDeDados;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

}
