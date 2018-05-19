package appbuilder.util.classes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import appbuilder.util.*;
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

    public Variavel(String modificador, String tipo, String nome, String valor) {
        this(tipo, nome, valor);
        addModificador(modificador);
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

    public String getDeclaração() {
        String codigo = "";

        codigo += tipo + " " + nome + ";\n";

        return codigo;
    }

    //ele retorna a declaração
    public String toString() {
        return getDeclaração();
    }
}
