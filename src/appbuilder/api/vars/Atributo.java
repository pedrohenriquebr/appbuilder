package appbuilder.api.vars;

import appbuilder.api.vars.Variavel;
import appbuilder.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
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

    @Override
    public String getReferencia() {
        return "this." + nome;
    }

    @Override
    public String getInicialização(String valor) {
        return getReferencia() + " = "  + valor + ";\n";
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
