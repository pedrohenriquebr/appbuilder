/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder;

import java.util.*;

/**
 *
 * @author psilva
 */
public class Variavel {

    protected String nome;
    protected String tipo;
    protected List<String> mods = new ArrayList<>();//modificadores
    protected String valor;

    public static final int DECLARAR = 0;
    public static final int ATRIBUIR = 1;
    public static final int REFERENCIAR = 2;
    //Variável tem exceção quanto toString()
    protected int estado = DECLARAR;

    //foi declarada;
    public Variavel(String tipo, String nome) {
        this.nome = nome;
        this.tipo = tipo;
        this.valor = "null";
    }

    public Variavel(String tipo, String nome, String valor) {
        this(tipo, nome);
        setValor(valor);
    }

    public Variavel(String tipo, String nome, String valor, String modificador) {
        this(tipo, nome, valor);
        addModificador(modificador);
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public boolean addModificador(String mod) {
        return mods.add(mod);
    }

    public List<String> getMods() {
        return mods;
    }

    public void setMods(List<String> mods) {
        this.mods = mods;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String toString() {
        String codigo = "";
        switch (estado) {
            case DECLARAR: {
                codigo += tipo + " " + nome + ";\n";
                break;
            }
        }

        return codigo;

    }
}
