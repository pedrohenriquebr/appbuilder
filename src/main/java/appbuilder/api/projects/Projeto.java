/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.projects;

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

    private Pacote pacotePrincipal;
    private Modelo modelo;
    //private ClasseDAO dao ;
    private Classe principal;

    //caso use base de dados
    private String usuario;
    private String senha;
    private String baseDeDados;

    public Projeto(String caminho, String nome) {
        this.caminho = caminho;
        this.nome = nome;
    }
    
    

    public Pacote getPacotePrincipal() {
        return pacotePrincipal;
    }

    public void setPacotePrincipal(Pacote pacote) {
        this.pacotePrincipal = pacote;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Classe getPrincipal() {
        return principal;
    }

    public void setPrincipal(Classe principal) {
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

}
