package appbuilder.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private List<Método> métodos = new ArrayList<Método>();
    private List<Importação> importações = new ArrayList<Importação>();

    //construtor principal
    private Construtor construtorPrincipal;
    //método principal
    private Método métodoMain;
    
    public Classe(String nome) {
        this.nome = nome;
        this.pacote = null;
        this.modAcesso = "public";
        this.pacote = new Pacote();

        //adicionar um construtor padrão
        construtorPrincipal = new Construtor("public", this.getNome());
        addConstrutor(construtorPrincipal);
    }
    
    public Classe(String nome, String pacote) {
        this(nome);
        this.pacote = new Pacote(pacote);
    }
    
    public Classe(String nome, String pacote, String caminho) {
        this(nome);
        this.pacote = new Pacote(pacote, caminho);
    }
    
    public boolean setPrincipal(boolean b) {
        if (b) {
            métodoMain = new Método("public", "static", "void", "main");
            métodoMain.addParametro("String[]", "args");
            return addMetódo(métodoMain);
        } else {
            boolean rt = métodos.remove(métodoMain);
            métodoMain = null;
            return rt;
            
        }
    }
    
    public Pacote getPacote() {
        return this.pacote;
    }
    
    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }
    
    public void setPacote(String pacote) {
        this.pacote = new Pacote(pacote);
    }
    
    public Construtor getConstrutorPrincipal() {
        return construtorPrincipal;
    }
    
    public void setConstrutorPrincipal(Construtor construtorPrincipal) {
        this.construtorPrincipal = construtorPrincipal;
    }
    
    public String getNomeCompleto() {
        return pacote.getCaminho() + "." + getNome();
    }
    
    public Método getMain() {
        return this.métodoMain;
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
        
        for (Método met : métodos) {
            codigo += met;
        }
        
        codigo += "} \n\n";
        
        return codigo;
    }
    
    public boolean addAtributo(Atributo atr) {
        
        return this.atributos.add(atr);
    }
    
    public boolean addConstrutor(Construtor contrutor) {
        return this.métodos.add(contrutor);
    }
    
    public boolean addMetódo(Método metodo) {
        return this.métodos.add(metodo);
    }
    
    public boolean removeMétodo(Método metodo) {
        return this.métodos.remove(metodo);
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
    
    public List<Método> getMetódos() {
        return métodos;
    }
    
    public void setMetódos(List<Método> metódos) {
        this.métodos = metódos;
    }
    
    public List<Importação> getImportações() {
        return importações;
    }
    
    public void setImportações(List<Importação> importações) {
        this.importações = importações;
    }
    
    void setConstrutorPrincipal(String apublic, String nome) {
        setConstrutorPrincipal(new Construtor(apublic, nome));
    }
    
}
