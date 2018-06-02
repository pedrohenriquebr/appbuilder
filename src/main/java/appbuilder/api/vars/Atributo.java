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

    private boolean inicializar = false;
    private boolean estático = false;

    public Atributo(String tipo, String nome) {
        super(tipo, nome);
        //por padrão
        addModificador("public");
    }

    public void ativarInicialização(String valor) {
        setValor(valor);
        inicializar = true;
    }

    public void desativarInicialização() {
        inicializar = false;
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
        if(this.getMods().contains("static")){
            estático  = true;
            return getClasse().getNome()+"."+nome;
        }
        
        return "this." + nome;
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

        codigo += tipo + " " + nome;

        if (inicializar) {
            codigo += " = " + valor;
        }
        codigo += ";\n";

        return codigo;
    }

    @Override
    public String toString() {
        return getDeclaração();
    }

}
