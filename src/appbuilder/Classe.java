/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder;

import java.util.*;

/**
 *
 * @author aluno
 */
public class Classe {

    private Pacote pacote;
    private String modAcesso;//modificador de acesso ,Ex: public, private 
    private String nome;

    private List<Atributo> atributos = new ArrayList<Atributo>();
    private List<Metódo> metódos = new ArrayList<Metódo>();
    private List<Importação> importações = new ArrayList<Importação>();

    public Classe(String nome) {
        this.nome = nome;
        this.pacote = new Pacote("default");
        this.modAcesso = "public";
    }

    public Classe(String nome, String pacote) {
        this(nome);
        this.pacote = new Pacote(pacote);
    }

    public Classe(String nome, String pacote, String caminho) {
        this(nome);
        this.pacote = new Pacote(pacote, caminho);
    }

    public Pacote getPacote() {
        return this.pacote;
    }

    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }

    public String toString() {
        String codigo = "";

        //pacote
        codigo += this.pacote + ";\n\n";
        //classe
        codigo += this.modAcesso + " class " + this.nome + " { \n\n";

        for (Atributo var : atributos) {
            codigo += var;
        }

        for (Metódo met : metódos) {
            codigo += met;
        }

        codigo += "} \n\n";

        return codigo;
    }
    
    public boolean addAtributo(Atributo atr ){
        return this.atributos.add(atr);
    }

    public boolean addConstrutor(Construtor contrutor) {
        return this.metódos.add(contrutor);
    }

    public boolean addMetódo(Metódo metodo) {
        return this.metódos.add(metodo);
    }

    public String getModAcesso() {
        return modAcesso;
    }

    public void setModAcesso(String modAcesso) {
        this.modAcesso = modAcesso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Atributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<Atributo> atributos) {
        this.atributos = atributos;
    }

    public List<Metódo> getMetódos() {
        return metódos;
    }

    public void setMetódos(List<Metódo> metódos) {
        this.metódos = metódos;
    }

    public List<Importação> getImportações() {
        return importações;
    }

    public void setImportações(List<Importação> importações) {
        this.importações = importações;
    }

}
