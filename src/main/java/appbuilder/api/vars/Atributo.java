package appbuilder.api.vars;

import appbuilder.api.classes.*;
import appbuilder.api.vars.Variavel;
import appbuilder.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Atributo é sinônimo de variável de instância
 *
 * @author aluno
 */
public class Atributo extends Variavel {

    public Atributo(String tipo, String nome) {
        super(tipo, nome);
        //por padrão
        addModificador("public");
    }

    public Atributo(String modificador, String tipo, String nome) {
        super(modificador, tipo, nome, "");

    }

    public Atributo(String modificador, String tipo, String nome, String valor) {
        super(modificador, tipo, nome, valor);
        this.classe = Classe.getClasseEstática(tipo);
    }

    @Override
    public String getReferencia() {
        return "this." + nome;
    }

    /**
     * Considera que o atributo é um objeto e que contém métodos e atributos a
     * serem chamados. Call serve para chamar algum método.
     *
     * @param método
     * @param args
     * @return
     */
    public String call(String método, String... args) {
        //no caso aqui é como se fosse acessar uma classe que foi importada
        Classe cl = this.classe.getClasse(getTipo());
        //instância dela 
        Objeto obj = new Objeto(cl);

        return getReferencia() + obj.call(método, args);
    }
    
    /**
     * Considera que o atributo é um objeto e que contém métodos e atributos a
     * serem chamados. Get serve para acessar um atributo.
     * @param atr
     * @return 
     */
    public String get(String atr) {
        Classe cl = this.classe.getClasse(getTipo());
        Objeto obj = new Objeto(cl);

        return getReferencia() + obj.get(atr);
    }

    @Override
    public String getInicialização(String valor) {
        return getReferencia() + " = " + valor + ";\n";
    }

    @Override
    public String getDeclaração() {
        String codigo = "";
        for (String mod : mods) {
            codigo += mod + " ";
        }

        codigo += tipo + " " + nome + ";\n";

        return codigo;
    }

    @Override
    public String toString() {
        return getDeclaração();
    }

}
