package appbuilder.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author aluno
 */
public class Pacote {

    private String nome;
    private String caminho;

    /**
     *
     * @param nome nome do pacote. Ex: java
     */
    public Pacote(String nome) {
        this.nome = nome.toLowerCase();
        this.caminho = this.nome;
    }

    public Pacote() {
        this("pacote");
    }

    /**
     *
     * @param nome nome do pacote. Ex: io
     * @param caminho caminho completo do pacote. Ex: java.io, java.sql
     */
    public Pacote(String nome, String caminho) {
        this(nome);
        if (!caminho.equals("")) {
            this.caminho = caminho + "." + nome;

        }
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public String getCaminho() {
        return caminho;
    }

    public String toString() {

        return "package " + this.caminho;

    }
}
